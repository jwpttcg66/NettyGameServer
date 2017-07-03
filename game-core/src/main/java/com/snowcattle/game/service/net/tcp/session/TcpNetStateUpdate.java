package com.snowcattle.game.service.net.tcp.session;


import com.snowcattle.game.executor.update.entity.AbstractUpdate;

/**
 * Created by jiangwenping on 17/2/21.
 * 网络检查更新
 */
public class TcpNetStateUpdate extends AbstractUpdate {

    public volatile TcpNetState state = TcpNetState.CONNECTED;

    @Override
    public void update() {
        updateConnect();
    }

    public void updateConnect(){

        if(state == TcpNetState.DISCONNECTING){
            setDisconnected();
            return;
        }else if(state == TcpNetState.DISCONNECTED){
            processDisconnect();
            return;
        }
    }

    public void processDisconnect(){
        state = TcpNetState.DESTROY;
    }

    public void setDisconnected(){
        state = TcpNetState.DISCONNECTED;
    }

    public void setDisconnecting(){
        state = TcpNetState.DISCONNECTING;
    }
}
