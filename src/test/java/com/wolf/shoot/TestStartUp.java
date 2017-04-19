package com.wolf.shoot;

import com.wolf.shoot.common.util.BeanUtil;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.manager.spring.LocalSpringBeanManager;
import com.wolf.shoot.manager.spring.LocalSpringServiceManager;
import com.wolf.shoot.manager.spring.LocalSpringServicerAfterManager;

/**
 * Created by jiangwenping on 17/4/19.
 */
public class TestStartUp {
    public static void startUp(){
        LocalSpringServiceManager localSpringServiceManager = (LocalSpringServiceManager) BeanUtil.getBean("localSpringServiceManager");
        LocalSpringBeanManager localSpringBeanManager = (LocalSpringBeanManager) BeanUtil.getBean("localSpringBeanManager");
        LocalSpringServicerAfterManager localSpringServicerAfterManager  = (LocalSpringServicerAfterManager) BeanUtil.getBean("localSpringServicerAfterManager");
        LocalMananger.getInstance().setLocalSpringBeanManager(localSpringBeanManager);
        LocalMananger.getInstance().setLocalSpringServiceManager(localSpringServiceManager);
        LocalMananger.getInstance().setLocalSpringServicerAfterManager(localSpringServicerAfterManager);
        try {
            localSpringServiceManager.start();
            localSpringServicerAfterManager.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
