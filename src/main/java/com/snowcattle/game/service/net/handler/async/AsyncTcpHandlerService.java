package com.snowcattle.game.service.net.handler.async;

import com.snowcattle.game.common.constant.ServiceName;
import com.snowcattle.game.service.IService;
import org.springframework.stereotype.Service;

/**
 * Created by jiangwenping on 2017/5/22.
 * 异步tcp handler服务
 */
@Service
public class AsyncTcpHandlerService implements IService{
    @Override
    public String getId() {
        return ServiceName.AsyncTcpHandlerService;
    }

    @Override
    public void startup() throws Exception {

    }

    @Override
    public void shutdown() throws Exception {

    }
}
