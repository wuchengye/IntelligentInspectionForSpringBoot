package com.bda.bdaqm.rabbitmq;

import com.bda.bdaqm.mission.model.InspectionMissionJobDetail;
import com.bda.bdaqm.mission.service.MissionJobDetailService;
import com.bda.bdaqm.mission.service.MissionService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

public class CompleteListener implements ChannelAwareMessageListener {

    @Autowired
    private MissionJobDetailService missionJobDetailService;
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            Map map = (Map) getObjectFromBytes(message.getBody());
            System.out.println("收到：" + map);
            int id = Integer.parseInt((String) map.get("id"));
            String data = (String) map.get("data");
            int missionId = Integer.parseInt((String) map.get("missionId"));
            String errCode = (String) map.get("err_code");
            String state = (String) map.get("state");

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
                    //转写成功 在这写
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
}
