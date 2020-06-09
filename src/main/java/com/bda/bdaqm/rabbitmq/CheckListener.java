package com.bda.bdaqm.rabbitmq;

import com.bda.bdaqm.mission.model.InspectionMission;
import com.bda.bdaqm.mission.model.InspectionMissionJobDetail;
import com.bda.bdaqm.mission.service.MissionJobDetailService;
import com.bda.bdaqm.mission.service.MissionService;
import com.bda.bdaqm.mission.service.SessionJobService;
import com.bda.bdaqm.risk.model.ComplaintSession;
import com.bda.bdaqm.risk.model.TabooSession;
import com.bda.bdaqm.risk.service.ComplaintService;
import com.bda.bdaqm.risk.service.DubiousService;
import com.bda.bdaqm.risk.service.UsedTabooService;
import com.rabbitmq.client.Channel;
import org.checkerframework.checker.units.qual.A;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CheckListener implements ChannelAwareMessageListener {

    @Autowired
    private MissionJobDetailService missionJobDetailService;
    @Autowired
    private MissionService missionService;
    @Autowired
    private SessionJobService sessionJobService;
    @Autowired
    private ComplaintService complaintService;
    @Autowired
    private UsedTabooService usedTabooService;
    @Autowired
    private DubiousService dubiousService;

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
                    String sessionId = matcher.group().replace("unid=", "")
                            .replace("#", "");
                    System.out.println("sessionId="+sessionId);
                    missionJobDetailService.updateInspectionStatus(jobId, 1, 5, "质检完成");
                    saveSessionIdToDB(sessionId, jobId);
                    //isMissionInspectionComplete(jobId);
                    updateMissionRemain();
                    isMissionComplete(jobId);
                } else {
                    System.out.println("找不到sessionId");
                    missionJobDetailService.updateInspectionStatus(jobId, 0, 0, "质检失败");
                    //isMissionInspectionComplete(jobId);
                    updateMissionRemain();
                    isMissionComplete(jobId);
                }
            } else {
                //质检失败
                System.out.println("质检失败");
                missionJobDetailService.updateInspectionStatus(jobId, 0, 0, "质检失败");
                //isMissionInspectionComplete(jobId);
                updateMissionRemain();
                isMissionComplete(jobId);
            }

            //确认ACK
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            e.printStackTrace();
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            missionJobDetailService.updateInspectionStatus(jobId, 0, 0, "质检失败");
            //isMissionInspectionComplete(jobId);
            updateMissionRemain();
            isMissionComplete(jobId);
        }
    }

    //判断任务是否完成，若完成则更新数据库状态
    private void isMissionComplete(int jobId) {
        InspectionMissionJobDetail imj = missionJobDetailService.getByJobId(jobId);
        List<InspectionMissionJobDetail> detailList = missionJobDetailService.getListByMissionId(imj.getMissionId());
        missionService.isMissionInspectionComplete(imj.getMissionId(),detailList);
        missionService.isMissionComplete(imj.getMissionId(), detailList);
    }

    //判断任务的质检模型是否完成，完成了多少
    /*private void isMissionInspectionComplete(int jobId){
        InspectionMissionJobDetail imj = missionJobDetailService.getByJobId(jobId);
        List<InspectionMissionJobDetail> detailList = missionJobDetailService.getListByMissionId(imj.getMissionId());
        missionService.isMissionInspectionComplete(imj.getMissionId(),detailList);
    }*/

    //更新所有任务剩余时间
    private void updateMissionRemain(){
        List<InspectionMission> running = missionService.getListMissionByStatus(1);
        if(running.size() > 0) {
            List<InspectionMissionJobDetail> runningJob = missionJobDetailService.getListByMissions(running);
            Map<String, String> map = new HashMap();
            for (InspectionMission in : running) {
                map.put(String.valueOf(in.getMissionId()), String.valueOf(0));
            }
            int fal = 0;
            for (int i = 0; i < runningJob.size(); i++) {
                if (runningJob.get(i).getFileStatus() == 0 || runningJob.get(i).getFileStatus() == 5) {
                    fal++;
                }
                map.put(String.valueOf(runningJob.get(i).getMissionId()), String.valueOf(i + 1 - fal));
            }
            for (String key : map.keySet()) {
                missionService.updateRemainById(key, map.get(key));
            }
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

    //获取到sessionID后的数据库操作，插入关联表、统计个数和替换文件名地址
    private void saveSessionIdToDB(String sessionId, int jobId) {

        InspectionMissionJobDetail job = missionJobDetailService.getByJobId(jobId);

        //插入关联表
        sessionJobService.insertSessionJob(sessionId, jobId);

        //统计个数
        ComplaintSession cs = complaintService.getComplaintSessionById(sessionId);
        TabooSession ts = usedTabooService.getTabooSessionById(sessionId);
        InspectionMission mission = missionService.getMissionByMissionId(job.getMissionId());
        int missionRisk = mission.getMissionRisk();
        int missionTaboo = mission.getMissionTaboo();
        int missionNodubious = mission.getMissionNodubious();
        if (cs == null && ts == null) {
            //非可疑
            missionNodubious++;
        }
        if (cs != null) {
            //投诉风险
            missionRisk++;
            missionJobDetailService.updateIsRiskAndIsTaboo(jobId, 1, 0);
        }
        if (ts != null) {
            //服务禁语
            missionTaboo++;
            missionJobDetailService.updateIsRiskAndIsTaboo(jobId, 0, 1);
        }
        missionService.updateCount(job.getMissionId(), missionRisk, missionTaboo, missionNodubious);

        //替换文件名、地址
        String fileName = job.getFileName();
        String filePath = job.getFilePath();
        dubiousService.updateFileNameAndFilePath(sessionId, fileName, filePath);
        usedTabooService.updateFileNameAndFilePath(sessionId, fileName, filePath);
        complaintService.updateFileNameAndFilePath(sessionId, fileName, filePath);
    }
}
