package com.wolf.shoot.service.net.pipeline.factory;

import com.wolf.shoot.service.net.pipeline.DefaultUdpServerPipeLine;
import com.wolf.shoot.service.net.pipeline.IServerPipeLine;

/**
 * Created by jiangwenping on 17/2/20.
 */
public class DefaultUdpServerPipelineFactory implements IServerPipelineFactory{
    @Override
    public IServerPipeLine createServerPipeLine() {
        return new DefaultUdpServerPipeLine();
    }
}
