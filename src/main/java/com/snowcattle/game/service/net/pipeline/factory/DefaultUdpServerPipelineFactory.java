package com.snowcattle.game.service.net.pipeline.factory;

import com.snowcattle.game.service.net.pipeline.DefaultUdpServerPipeLine;
import com.snowcattle.game.service.net.pipeline.IServerPipeLine;

/**
 * Created by jiangwenping on 17/2/20.
 */
public class DefaultUdpServerPipelineFactory implements IServerPipelineFactory{
    @Override
    public IServerPipeLine createServerPipeLine() {
        return new DefaultUdpServerPipeLine();
    }
}
