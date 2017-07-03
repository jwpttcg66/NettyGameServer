package com.snowcattle.game.extend;

import com.snowcattle.game.common.util.BeanUtil;
import com.snowcattle.game.bootstrap.manager.GlobalManager;

/**
 * Created by jwp on 2017/5/6.
 * 用户拓展
 */
public class GlobalManagerEx extends GlobalManager{

    //拓展使用
    public void initGameManager() throws Exception {
        LocalSpringBeanGameManager localSpringBeanGameManager = (LocalSpringBeanGameManager) BeanUtil.getBean("localSpringBeanGameManager");
        GameManager.getInstance().setLocalSpringBeanGameManager(localSpringBeanGameManager);
        LocalSpringServiceGameManager localSpringServiceGameManager = (LocalSpringServiceGameManager) BeanUtil.getBean("localSpringServiceGameManager");
        GameManager.getInstance().setLocalSpringServiceGameManager(localSpringServiceGameManager);

    }

    public void startGameManager() throws Exception{
        LocalSpringServiceGameManager localSpringServiceGameManager = GameManager.getInstance().getLocalSpringServiceGameManager();
        localSpringServiceGameManager.start();
    }

    public void stopGameManager() throws Exception{
        LocalSpringServiceGameManager localSpringServiceGameManager = GameManager.getInstance().getLocalSpringServiceGameManager();
        localSpringServiceGameManager.stop();
    }


}
