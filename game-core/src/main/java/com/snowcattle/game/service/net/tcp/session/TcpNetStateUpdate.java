package com.snowcattle.game.service.net.tcp.session;


import com.snowcattle.game.executor.update.entity.AbstractUpdate;

/**
 * Created by jiangwenping on 17/2/21.
 * 网络检查更新
 */
public class TcpNetStateUpdate extends AbstractUpdate {

    private static final long serialVersionUID = 8114145039789355085L;
    public volatile TcpNetState state = TcpNetState.CONNECTED;

    @Override
    public void update() {
        updateConnect();
    }

    public void updateConnect(){

        if(state == TcpNetState.DISCONNECTING){
            setDisconnected();
        }else if(state == TcpNetState.DISCONNECTED){
            processDisconnect();
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
