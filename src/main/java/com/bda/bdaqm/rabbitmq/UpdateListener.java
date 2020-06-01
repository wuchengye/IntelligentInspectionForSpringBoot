package com.bda.bdaqm.rabbitmq;

import com.bda.bdaqm.mission.model.InspectionMission;
import com.bda.bdaqm.mission.model.InspectionMissionJobDetail;
import com.bda.bdaqm.mission.service.MissionJobDetailService;
import com.bda.bdaqm.mission.service.MissionService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateListener implements ChannelAwareMessageListener {
    @Autowired
    private MissionService missionService;
    @Autowired
    private MissionJobDetailService missionJobDetailService;
    @Autowired
    private RabbitmqProducer rabbitmqProducer;
    @Value("#{mqconfig.mq_ready_queue}")
    private String readyQueueId;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            //清空消息队列
            rabbitmqProducer.queuePurge(readyQueueId+"_queue");
            //获取状态为进行中的任务列表
            List<InspectionMission> list = missionService.getListMissionByStatus(1);
            //添加任务至队列
            for (InspectionMission im : list
            ) {
                System.out.println("找到任务:id="+im.getMissionId());
                List<InspectionMissionJobDetail> missionJDList = missionJobDetailService.getListByMissionId(im.getMissionId());
                for (InspectionMissionJobDetail imjd : missionJDList
                     ) {
                    System.out.println("  找到音频:id="+imjd.getJobId());
                    if (imjd.getFileStatus() == 1 && imjd.getMissionIstransfer() == 1 && imjd.getIsTransferFailed() == 0) {
                        System.out.println("    加入队列成功");
                        sendToQueue(imjd);
                    } else {
                        System.out.println("    不需要加入队列");
                    }
                }
            }
            //确认ACK
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendToQueue(InspectionMissionJobDetail imjd) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", imjd.getJobId());
        map.put("missionId", imjd.getMissionId());
        map.put("name", imjd.getFileName());
        map.put("path", imjd.getFilePath());
        rabbitmqProducer.sendQueue(readyQueueId + "_exchange", readyQueueId + "_patt", map, imjd.getMissionLevel());
    }
}
