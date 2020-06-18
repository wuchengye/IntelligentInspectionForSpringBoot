package com.bda.bdaqm.rabbitmq;

import com.bda.bdaqm.mission.model.BdaqmTotal;
import com.bda.bdaqm.mission.model.InspectionMission;
import com.bda.bdaqm.mission.model.InspectionMissionJobDetail;
import com.bda.bdaqm.mission.service.MissionJobDetailService;
import com.bda.bdaqm.mission.service.MissionService;
import com.bda.bdaqm.mission.service.SessionJobService;
import com.bda.bdaqm.mission.service.TotalService;
import com.bda.bdaqm.risk.model.ComplaintSession;
import com.bda.bdaqm.risk.model.NotDubious;
import com.bda.bdaqm.risk.model.TabooSession;
import com.bda.bdaqm.risk.service.ComplaintService;
import com.bda.bdaqm.risk.service.DubiousService;
import com.bda.bdaqm.risk.service.UsedTabooService;
import com.bda.bdaqm.websocket.WebsocketController;
import com.rabbitmq.client.Channel;
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

public class CheckListener implements ChannelAwareMessageListener {

    @Autowired
    private MissionJobDetailService missionJobDetailService;
    @Autowired
    private MissionService missionService;
    @Autowired
    private SessionJobService sessionJobService;
    @Autowired
    private TotalService totalService;
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
                    //websocket
                    WebsocketController.updateMission(missionService.getMissionByMissionId(missionJobDetailService.getByJobId(jobId).getMissionId()));
                    WebsocketController.updateMission(missionService.getListMissionByStatus(1));
                } else {
                    System.out.println("找不到sessionId");
                    missionJobDetailService.updateInspectionStatus(jobId, 0, 0, "质检失败");
                    //isMissionInspectionComplete(jobId);
                    updateMissionRemain();
                    isMissionComplete(jobId);
                    //websocket
                    WebsocketController.updateMission(missionService.getMissionByMissionId(missionJobDetailService.getByJobId(jobId).getMissionId()));
                    WebsocketController.updateMission(missionService.getListMissionByStatus(1));
                }
            } else {
                //质检失败
                System.out.println("质检失败");
                missionJobDetailService.updateInspectionStatus(jobId, 0, 0, "质检失败");
                //isMissionInspectionComplete(jobId);
                updateMissionRemain();
                isMissionComplete(jobId);
                //websocket
                WebsocketController.updateMission(missionService.getMissionByMissionId(missionJobDetailService.getByJobId(jobId).getMissionId()));
                WebsocketController.updateMission(missionService.getListMissionByStatus(1));
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
            //websocket
            WebsocketController.updateMission(missionService.getMissionByMissionId(missionJobDetailService.getByJobId(jobId).getMissionId()));
            WebsocketController.updateMission(missionService.getListMissionByStatus(1));
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

    //获取到sessionID后的数据库操作，插入总表、统计个数和替换文件名地址
    private void saveSessionIdToDB(String sessionId, int jobId) {

        InspectionMissionJobDetail job = missionJobDetailService.getByJobId(jobId);

        //替换FileName和FilePath
        replaceFileNameAndPath(sessionId, job);

        ComplaintSession cs = complaintService.getComplaintSessionById(sessionId);
        TabooSession ts = usedTabooService.getTabooSessionById(sessionId);

        //插入总表
        BdaqmTotal total = new BdaqmTotal();
        total.setSessionId(sessionId);
        total.setMissionId(job.getMissionId());
        total.setJobId(jobId);
        insertTotal(dubiousService.getDubiousById(sessionId), total);
        insertTotal(cs, total);
        insertTotal(ts, total);

        //统计个数
        Statistics(job, cs, ts);
    }

    //统计非可疑、投诉、禁语个数，更新到数据库
    private void Statistics(InspectionMissionJobDetail job, ComplaintSession cs, TabooSession ts) {
        InspectionMission mission = missionService.getMissionByMissionId(job.getMissionId());
        int missionRisk = mission.getMissionRisk();
        int missionTaboo = mission.getMissionTaboo();
        int missionNodubious = mission.getMissionNodubious();
        if (cs != null) {
            missionRisk++;
            missionJobDetailService.updateIsRiskAndIsTaboo(job.getJobId(), 1, 0);
        }
        if (ts != null) {
            missionTaboo++;
            missionJobDetailService.updateIsRiskAndIsTaboo(job.getJobId(), 0, 1);
        }
        if (cs == null && ts == null) {
            missionNodubious++;
        }
        missionService.updateCount(job.getMissionId(), missionRisk, missionTaboo, missionNodubious);
    }

    //替换FileName和FilePath，更新到数据库
    private void replaceFileNameAndPath(String sessionId, InspectionMissionJobDetail job) {
        String fileName = job.getFileName();
        String filePath = job.getFilePath();
        dubiousService.updateFileNameAndFilePath(sessionId, fileName, filePath);
        usedTabooService.updateFileNameAndFilePath(sessionId, fileName, filePath);
        complaintService.updateFileNameAndFilePath(sessionId, fileName, filePath);
    }

    private void insertTotal(NotDubious ns, BdaqmTotal total) {
        if (ns == null) {
            return;
        }
        total.setType(0);
        total.setFileName(ns.getFileName());
        total.setFilePath(ns.getFilePath());
        total.setContactTime(ns.getContactTime());
        total.setRecordDate(ns.getRecordDate()!=null ? ns.getRecordDate() : "1997-01-01");
        total.setDataUpdateTime(ns.getDataUpdateTime());
        total.setIsmute45s("否");//默认否
        totalService.insertTotal(total);
    }

    private void insertTotal(ComplaintSession cs, BdaqmTotal total) {
        if (cs == null) {
            return;
        }
        total.setType(1);
        total.setFileName(cs.getFileName());
        total.setFilePath(cs.getFilePath());
        total.setKeyword(cs.getKeyword());
        total.setContactTime(cs.getContactTime());
        total.setRecordDate(cs.getRecordDate());
        total.setCheckStatus(cs.getCheckStatus());
        total.setCheckAccounts(cs.getCheckAccounts());
        total.setPersonIsProblem(cs.getPersonIsProblem());
        total.setTransferIstrue(cs.getTransferIstrue());
        total.setProblemDescribe(cs.getProblemDescribe());
        total.setTrueTransferContent(cs.getTrueTransferContent());
        total.setRemark(cs.getRemark());
        total.setDataUpdateTime(cs.getDataUpdateTime());
        total.setIsmute45s("否");//默认否
        totalService.insertTotal(total);
    }

    private void insertTotal(TabooSession ts, BdaqmTotal total) {
        if (ts == null) {
            return;
        }
        total.setType(2);
        total.setFileName(ts.getFileName());
        total.setFilePath(ts.getFilePath());
        total.setKeyword(ts.getKeyword());
        total.setContactTime(ts.getContactTime());
        total.setIsmute45s(ts.getIsmute45s());
        total.setMuteLocation(ts.getMuteLocation());
        total.setRecordDate(ts.getRecordDate());
        total.setCheckStatus(ts.getCheckStatus());
        total.setCheckAccounts(ts.getCheckAccounts());
        total.setPersonIsProblem(ts.getPersonIsProblem());
        total.setTransferIstrue(ts.getTransferIstrue());
        total.setProblemDescribe(ts.getProblemDescribe());
        total.setTrueTransferContent(ts.getTrueTransferContent());
        total.setRemark(ts.getRemark());
        total.setDataUpdateTime(ts.getDataUpdateTime());
        totalService.insertTotal(total);
    }
}
