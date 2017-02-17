package com.wolf.shoot.service.net.pipeline.factory;

import com.wolf.shoot.service.net.pipeline.DefaultTcpServerPipeLine;
import com.wolf.shoot.service.net.pipeline.IServerPipeLine;

/**
 * Created by jiangwenping on 17/2/14.
 */
public class DefaultTcpServerPipelineFactory implements IServerPipelineFactory {
    @Override
    public IServerPipeLine createServerPipeLine() {
        return new DefaultTcpServerPipeLine();
    }
}
