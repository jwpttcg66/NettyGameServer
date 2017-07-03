package com.snowcattle.game.service.net.tcp.pipeline.factory;

import com.snowcattle.game.service.net.tcp.pipeline.DefaultUdpServerPipeLine;
import com.snowcattle.game.service.net.tcp.pipeline.IServerPipeLine;

/**
 * Created by jiangwenping on 17/2/20.
 */
public class DefaultUdpServerPipelineFactory implements IServerPipelineFactory{
    @Override
    public IServerPipeLine createServerPipeLine() {
        return new DefaultUdpServerPipeLine();
    }
}
