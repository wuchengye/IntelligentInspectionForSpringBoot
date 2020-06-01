package com.bda.bdaqm.rabbitmq;

import com.bda.bdaqm.mission.service.MissionJobDetailService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CheckListener implements ChannelAwareMessageListener {

    @Autowired
    private MissionJobDetailService missionJobDetailService;

    @Value("#{mqconfig.pythonPath}")
    private String pythonPath;
    @Value("#{mqconfig.pythonCMD}")
    private String pythonCMD;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        Map map = (Map) getObjectFromBytes(message.getBody());
        String mp3FilePath = (String) map.get("mp3FilePath");
        String xmlFilePath = (String) map.get("xmlFilePath");
        int jobId = Integer.parseInt((String) map.get("jobId"));
        System.out.println("mp3Path:"+mp3FilePath);
        System.out.println("xmlPath"+xmlFilePath);
        System.out.println("jobId:"+jobId);

        try {
            missionJobDetailService.updateInspectionStatus(jobId, 0, 4, "质检中");

            String cmd = pythonCMD + " " + pythonPath + "check.py " + mp3FilePath + " " + xmlFilePath;
            System.out.println("cmd:"+cmd);
            Process p = Runtime.getRuntime().exec(cmd);
            InputStream fis=p.getInputStream();
            InputStreamReader isr=new InputStreamReader(fis);
            BufferedReader br=new BufferedReader(isr);
            String line=null;
            System.out.println("cmd执行：");
            StringBuilder output = new StringBuilder();
            while((line=br.readLine())!=null){
                System.out.println(line);
                output.append(line).append("\n");
            }
            p.waitFor();
            System.out.println("执行完");

            fis.close();
            isr.close();
            br.close();
            p.destroy();

            System.out.println("cmd输出："+output);
            if (output.toString().contains("check success")) {
                //质检成功,用正则提取session_id
                Pattern pattern = Pattern.compile("unid=\\S*#");
                Matcher matcher = pattern.matcher(output);
                if (matcher.find()) {
                    String sessionId = matcher.group();
                    System.out.println("sessionId="+sessionId);
                    missionJobDetailService.updateInspectionStatus(jobId, 1, 5, "质检完成");
                } else {
                    System.out.println("找不到sessionId");
                }
            } else {
                //质检失败
                System.out.println("质检失败");
                missionJobDetailService.updateInspectionStatus(jobId, 0, 0, "质检失败");
            }

            //确认ACK
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            e.printStackTrace();
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            missionJobDetailService.updateInspectionStatus(jobId, 0, 0, "质检失败");
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
}
