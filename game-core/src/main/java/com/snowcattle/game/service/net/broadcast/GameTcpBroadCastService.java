package com.snowcattle.game.service.net.broadcast;

import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.service.lookup.NetTcpSessionLoopUpService;
import com.snowcattle.game.service.message.AbstractNetMessage;
import com.snowcattle.game.service.net.tcp.session.NettySession;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jiangwenping on 2017/11/14.
 */
@Service
public class GameTcpBroadCastService implements IBroadCastService{
    @Override
    public void broadcastMessage(long sessionId, AbstractNetMessage netMessage) {
        NetTcpSessionLoopUpService netTcpSessionLoopUpService = LocalMananger.getInstance().getLocalSpringServiceManager().getNetTcpSessionLoopUpService();
        ConcurrentHashMap<Long, NettySession> sessions = netTcpSessionLoopUpService.getSessions();
        for (Map.Entry<Long, NettySession> temp: sessions.entrySet()){
            long destId = temp.getKey();
            if(destId != sessionId){
                NettySession nettySession = temp.getValue();
                try {
                    nettySession.write(netMessage);
                } catch (Exception e) {
//                    e.printStackTrace();
                }
            }
        }
    }
}
