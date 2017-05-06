package com.wolf.shoot.extend;

import com.wolf.shoot.common.util.BeanUtil;
import com.wolf.shoot.manager.GlobalManager;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.manager.spring.LocalSpringBeanManager;
import com.wolf.shoot.manager.spring.LocalSpringServiceManager;
import com.wolf.shoot.manager.spring.LocalSpringServicerAfterManager;

/**
 * Created by jwp on 2017/5/6.
 */
public class GlobalManagerEx extends GlobalManager{

    //拓展使用
    public void initGameManager() throws Exception {
        LocalSpringBeanGameManager localSpringBeanGameManager = (LocalSpringBeanGameManager) BeanUtil.getBean("localSpringBeanGameManager");
        GameManager.getInstance().setLocalSpringBeanGameManager(localSpringBeanGameManager);
        LocalSpringServiceGameManager localSpringServiceGameManager = (LocalSpringServiceGameManager) BeanUtil.getBean("localSpringServiceGameManager");
        GameManager.getInstance().setLocalSpringServiceGameManager(localSpringServiceGameManager);
        localSpringServiceGameManager.start();

    }

    public void startGameManager() throws Exception{

    }

    public void stopGameManager() throws Exception{

    }


}
