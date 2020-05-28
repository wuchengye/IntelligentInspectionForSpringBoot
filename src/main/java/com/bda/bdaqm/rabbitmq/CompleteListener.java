package com.bda.bdaqm.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.bda.bdaqm.mission.model.InspectionMissionJobDetail;
import com.bda.bdaqm.mission.service.MissionJobDetailService;
import com.bda.bdaqm.mission.service.MissionService;
import com.bda.bdaqm.rabbitmq.model.VoiceResult;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CompleteListener implements ChannelAwareMessageListener {

    @Autowired
    private MissionJobDetailService missionJobDetailService;

    @Autowired
    private RabbitmqProducer rabbitmqProducer;

    @Value("#{mqconfig.mq_check_queue}")
    private String checkQueueId;
    @Value("#{mqconfig.xmlPath}")
    private String xmlPath;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            Map map = (Map) getObjectFromBytes(message.getBody());
            System.out.println("收到：" + map);
            int id = Integer.parseInt((String) map.get("id"));
            String data = (String) map.get("data");
            int missionId = Integer.parseInt((String) map.get("missionId"));
            String errCode = String.valueOf(map.get("err_code"));
            String state = String.valueOf(map.get("state"));
            String path = (String) map.get("path");

            //转写中
            if (state.equals("0")) {
                InspectionMissionJobDetail imj = missionJobDetailService.getByJobId(id);
                missionJobDetailService.updateTransferStatus(imj.getJobId(), 0, 1, "转写中", 0);
            }

            //转写完
            if (state.equals("1")) {
                InspectionMissionJobDetail imj = missionJobDetailService.getByJobId(id);
                if (errCode.equals("0")) {
                    missionJobDetailService.updateTransferStatus(imj.getJobId(), 1, 2, "转写完成", 0);
                    //创建xml文件夹
                    File dir = new File(xmlPath);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    //xml文件路径 文件名以时间结尾
                    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
                    String dateTime = df.format(new Date());
                    File f = new File(path.trim());
                    String xmlFilePath = xmlPath + f.getName() + "_" + dateTime + ".xml";
                    System.out.println("正在生成xml:"+xmlFilePath);
                    //生成xml文件
                    String xmlPath = createXMLFile(xmlFilePath, resolve(data));
                    if (xmlPath == null) {
                        System.out.println("xml生成失败");
                    } else {
                        System.out.println("xml生成成功");
                        //发送消息给check队列，添加质检任务
                        //这里还没写开始质检的数据库操作
                        Map<String,String> checkMap = new HashMap<>();
                        checkMap.put("mp3FilePath", path);
                        checkMap.put("xmlFilePath", xmlPath);
                        rabbitmqProducer.sendQueue(checkQueueId+"_exchange", checkQueueId+"_patt", checkMap);
                    }
                } else {
                    System.out.println("转写失败，原因："+data);
                    missionJobDetailService.updateTransferStatus(imj.getJobId(), 0, 0, "转写失败", 1);
                }
            }

            //确认ACK
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            e.printStackTrace();
        }
    }

    //字节码转化为对象
    private Object getObjectFromBytes(byte[] objBytes) throws Exception {
        if (objBytes == null || objBytes.length == 0) {
            return null;
        }
        ByteArrayInputStream bi = new ByteArrayInputStream(objBytes);
        ObjectInputStream oi = new ObjectInputStream(bi);
        return oi.readObject();
    }

    //生成xml文件
    private String createXMLFile(String path, Map<String,String> map) {
        String textStr1 = map.get("text1");
        String textStr2 = map.get("text2");
        String timeStr1 = map.get("time1");
        String timeStr2 = map.get("time2");

        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();
            Document document = db.newDocument();
            // 不显示standalone="no"
            //document.setXmlStandalone(true);

            Element result = document.createElement("result");
            Element instance = document.createElement("instance");
            Element subject = document.createElement("subject");

            instance.setAttribute("waveuri", "./wav/qwert.wav");
            subject.setAttribute("value", "search");

            //客服 n0
            Element channel_n0 = document.createElement("channel");
            Element function_n0 = document.createElement("function");
            Element text1 = document.createElement("text");
            Element time1 = document.createElement("time");

            channel_n0.setAttribute("no", "n0");
            text1.setTextContent(textStr1);
            time1.setTextContent(timeStr1);

            function_n0.appendChild(text1);
            function_n0.appendChild(time1);
            channel_n0.appendChild(function_n0);
            subject.appendChild(channel_n0);

            //客户 n1
            Element channel_n1 = document.createElement("channel");
            Element function_n1 = document.createElement("function");
            Element text2 = document.createElement("text");
            Element time2 = document.createElement("time");

            channel_n1.setAttribute("no", "n1");
            text2.setTextContent(textStr2);
            time2.setTextContent(timeStr2);

            function_n1.appendChild(text2);
            function_n1.appendChild(time2);
            channel_n1.appendChild(function_n1);
            subject.appendChild(channel_n1);

            instance.appendChild(subject);
            result.appendChild(instance);
            document.appendChild(result);

            // 创建TransformerFactory对象
            TransformerFactory tff = TransformerFactory.newInstance();
            // 创建 Transformer对象
            Transformer tf = tff.newTransformer();

            // 输出内容是否使用换行
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            // 创建xml文件并写入内容
            tf.transform(new DOMSource(document), new StreamResult(new File(path)));
            System.out.println("生成"+path+"成功");
            return path;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("生成"+path+"失败");
            return null;
        }
    }

    private Map<String, String> resolve(String str) {
        //客服text1,time1  客户text2,time2
        StringBuilder text1 = new StringBuilder();
        StringBuilder time1 = new StringBuilder();
        StringBuilder text2 = new StringBuilder();
        StringBuilder time2 = new StringBuilder();

        //转成VoiceResult对象
        JSONObject jsonObject=JSONObject.parseObject(str);
        VoiceResult voiceResult = (VoiceResult) JSONObject.toJavaObject(jsonObject, VoiceResult.class);
        //csNum 区分哪个是客服
        String csNum = voiceResult.getCustomer_service();
        //对话列表
        List<VoiceResult.ContentBean> contentList = voiceResult.getContent();
        for (VoiceResult.ContentBean content : contentList
        ) {
            if (content.getSpeaker().equals(csNum)) {
                //添加到客服
                text1.append(content.getOnebest().replace(" ", ""))
                        .append(" ");
                time1.append(content.getBg())
                        .append(",")
                        .append(content.getEd())
                        .append(" ");
            } else {
                //添加到客户
                text2.append(content.getOnebest().replace(" ", ""))
                        .append(" ");
                time2.append(content.getBg())
                        .append(",")
                        .append(content.getEd())
                        .append(" ");
            }
        }
        Map<String, String> map = new HashMap<>();
        map.put("text1", text1.toString().trim());
        map.put("time1", time1.toString().trim());
        map.put("text2", text2.toString().trim());
        map.put("time2", time2.toString().trim());
        return map;
    }
}
