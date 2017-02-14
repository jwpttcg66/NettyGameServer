package com.wolf.shoot.service.net.pipeline.factory;

import com.wolf.shoot.service.net.pipeline.DefaultTcpServerPipeLine;
import com.wolf.shoot.service.net.pipeline.ITcpServerPipeLine;

/**
 * Created by jiangwenping on 17/2/14.
 */
public class DefaultTcpServerPipelineFactory implements ITcpServerPipelineFactory {
    @Override
    public ITcpServerPipeLine createServerPipeLine() {
        return new DefaultTcpServerPipeLine();
    }
}
