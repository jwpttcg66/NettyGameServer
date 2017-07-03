package com.snowcattle.game.service.net.tcp.pipeline.factory;


import com.snowcattle.game.service.net.tcp.pipeline.DefaultTcpServerPipeLine;
import com.snowcattle.game.service.net.tcp.pipeline.IServerPipeLine;

/**
 * Created by jiangwenping on 17/2/14.
 */
public class DefaultTcpServerPipelineFactory implements IServerPipelineFactory {
    @Override
    public IServerPipeLine createServerPipeLine() {
        return new DefaultTcpServerPipeLine();
    }
}
