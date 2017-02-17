package com.wolf.shoot.service.net.pipeline.factory;


import com.wolf.shoot.service.net.pipeline.DefaultTcpServerPipeline;
import com.wolf.shoot.service.net.pipeline.IServerPipeline;

/**
 * Created by jiangwenping on 17/2/14.
 */
public class DefaultTcpServerPipelineFactory implements IServerPipelineFactory {
    @Override
    public IServerPipeline createServerPipeLine() {
        return new DefaultTcpServerPipeline();
    }
}
