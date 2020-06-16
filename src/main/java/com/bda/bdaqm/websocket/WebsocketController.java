package com.bda.bdaqm.websocket;

import com.bda.bdaqm.mission.model.InspectionMission;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ServerEndpoint(value = "/websocket/{missionIds}")
public class WebsocketController {

    private static Map<String, Session> onlineUser = new HashMap();
    private static Map<String, String[]> userMission = new HashMap<>();

    //连接时
    @OnOpen
    public void onOpen(Session session , @PathParam("missionIds")String missionIds) throws IOException {
        if(missionIds != null && !missionIds.equals("")){
            String[] ids = missionIds.split("-");
            onlineUser.put(session.getId(),session);
            userMission.put(session.getId(),ids);
            System.out.println("websocket连接---" + session.getId());
            session.getBasicRemote().sendText("连接成功，任务有:" + missionIds);
        }
    }

    @OnClose
    public void onClose(Session session){
        onlineUser.remove(session.getId());
        userMission.remove(session.getId());
        System.out.println("websocket关闭---" + session.getId());
    }

    @OnError
    public void onError(Session session , Throwable throwable){
        onlineUser.remove(session.getId());
        userMission.remove(session.getId());
        System.out.println("websocket错误---" + session.getId() + "---" + throwable.getMessage());
    }


    public static void updateMission(List<InspectionMission> list){
        if(list != null) {
            for (InspectionMission mission : list) {
                for (Map.Entry<String, String[]> entry : userMission.entrySet()) {
                    for (String id : entry.getValue()){
                        if(String.valueOf(mission.getMissionId()).equals(id)){
                            try {
                                onlineUser.get(entry.getKey()).getBasicRemote().sendText(mission.toString());
                            } catch (IOException e) {
                                System.out.println("websocket发送错误---" + e.getMessage());
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    public static void updateMission(InspectionMission inspectionMission){
        if(inspectionMission != null){
            for (Map.Entry<String, String[]> entry : userMission.entrySet()) {
                for(String id : entry.getValue()){
                    if(String.valueOf(inspectionMission.getMissionId()).equals(id)){
                        try {
                            onlineUser.get(entry.getKey()).getBasicRemote().sendText(inspectionMission.toString());
                        } catch (IOException e) {
                            System.out.println("websocket发送错误---" + e.getMessage());
                        }
                        break;
                    }
                }
            }
        }
    }
}
