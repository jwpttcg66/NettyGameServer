package com.snowcattle.game.bootstrap.manager.spring;

import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.service.IService;
import org.slf4j.Logger;

import java.lang.reflect.Field;

/**
 * Created by jiangwenping on 17/4/5.
 * 抽象的spring启动
 */
public abstract class AbstractSpringStart {

    private Logger logger = Loggers.serverLogger;

    public  void start() throws Exception {
        // 获取对象obj的所有属性域
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            // 对于每个属性，获取属性名
            String varName = field.getName();
            try {
                boolean access = field.isAccessible();
                if (!access) field.setAccessible(true);

                //从obj中获取field变量
                Object object = field.get(this);
                if(object instanceof IService){
                    IService iService = (IService) object;
                    iService.startup();
                    logger.info(iService.getId() + " service start up");
                }else{
                    logger.info(object.getClass().getSimpleName() + " start up");
                }
                if (!access) field.setAccessible(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    public void stop() throws Exception{

        // 获取对象obj的所有属性域
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            // 对于每个属性，获取属性名
            String varName = field.getName();
            try {
                boolean access = field.isAccessible();
                if (!access) field.setAccessible(true);

                //从obj中获取field变量
                Object object = field.get(this);
                if(object instanceof IService){
                    IService iService = (IService) object;
                    iService.shutdown();
                    logger.info(iService.getId() + " shut down");
                }else{
                    logger.info(object.getClass().getSimpleName() + " shut down");
                }
                if (!access) field.setAccessible(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }
}
