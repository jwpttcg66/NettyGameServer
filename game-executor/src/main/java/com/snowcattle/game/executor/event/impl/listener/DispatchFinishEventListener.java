package com.snowcattle.game.executor.event.impl.listener;

import com.snowcattle.game.executor.event.EventParam;
import com.snowcattle.game.executor.event.common.IEvent;
import com.snowcattle.game.executor.event.impl.event.FinishedEvent;
import com.snowcattle.game.executor.update.service.UpdateService;
import com.snowcattle.game.executor.update.thread.dispatch.DispatchThread;
import com.snowcattle.game.executor.update.entity.IUpdate;
import com.snowcattle.game.executor.common.utils.Constants;

/**
 * Created by jiangwenping on 17/1/11.
 */
public class DispatchFinishEventListener extends FinishEventListener {

    private UpdateService updateService;
    private DispatchThread dispatchThread;

    public DispatchFinishEventListener(DispatchThread dispatchThread, UpdateService updateService) {
        this.dispatchThread = dispatchThread;
        this.updateService = updateService;
    }

    public void fireEvent(IEvent event) {
        super.fireEvent(event);
        //提交更新服务器 执行完成调度
        EventParam[] eventParams = event.getParams();
        IUpdate iUpdate = (IUpdate) eventParams[0].getT();
        FinishedEvent finishedEvent = new FinishedEvent(Constants.EventTypeConstans.finishedEventType, iUpdate.getUpdateId(), event.getParams());
        this.updateService.addFinishedEvent(finishedEvent);
    }

}
