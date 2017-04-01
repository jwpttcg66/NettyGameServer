package com.wolf.shoot.manager.spring;

import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.service.IService;
import com.wolf.shoot.service.rpc.client.RpcClientConnectService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

/**
 * Created by jiangwenping on 17/4/1.
 * 因为有些服务需要等待其它服务加载完成后，才可以加载，这里用来解决加载顺序
 */
@Service
public class LocalSpringServicerAfterManager {

    private Logger logger = Loggers.serverLogger;

    @Autowired
    private RpcClientConnectService rpcClientConnectService;

    public RpcClientConnectService getRpcClientConnectService() {
        return rpcClientConnectService;
    }

    public void setRpcClientConnectService(RpcClientConnectService rpcClientConnectService) {
        this.rpcClientConnectService = rpcClientConnectService;
    }

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
                    logger.info(iService.getId() + "shut down");
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
