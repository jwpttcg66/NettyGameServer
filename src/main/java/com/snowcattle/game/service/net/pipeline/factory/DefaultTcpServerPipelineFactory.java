package com.snowcattle.game.service.net.pipeline.factory;


import com.snowcattle.game.service.net.pipeline.DefaultTcpServerPipeLine;
import com.snowcattle.game.service.net.pipeline.IServerPipeLine;

/**
 * Created by jiangwenping on 17/2/14.
 */
public class DefaultTcpServerPipelineFactory implements IServerPipelineFactory {
    @Override
    public IServerPipeLine createServerPipeLine() {
        return new DefaultTcpServerPipeLine();
    }
}
