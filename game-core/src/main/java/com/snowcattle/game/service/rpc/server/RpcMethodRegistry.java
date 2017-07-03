package com.snowcattle.game.service.rpc.server;

import com.snowcattle.game.common.annotation.RpcServiceAnnotation;
import com.snowcattle.game.common.constant.GlobalConstants;
import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.common.constant.ServiceName;
import com.snowcattle.game.common.scanner.ClassScanner;
import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.service.IService;
import com.snowcattle.game.service.Reloadable;
import com.snowcattle.game.service.config.GameServerConfigService;
import com.snowcattle.game.service.rpc.serialize.protostuff.ProtostuffSerializeI;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jwp on 2017/3/7.
 */
@Service
public class RpcMethodRegistry implements Reloadable, IService {

    public static Logger logger = Loggers.serverLogger;

    public ClassScanner classScanner = new ClassScanner();

    private ConcurrentHashMap<String, Object> registryMap = new ConcurrentHashMap<String, Object>();

    @Override
    public String getId() {
        return ServiceName.RpcMethodRegistry;
    }

    @Override
    public void startup() throws Exception {
        reload();
    }

    @Override
    public void shutdown() throws Exception {

    }

    @Override
    public void reload() throws Exception {
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        String packageName = gameServerConfigService.getGameServerConfig().getRpcServicePackage();
        loadPackage(gameServerConfigService.getGameServerConfig().getRpcServicePackage(),
                GlobalConstants.FileExtendConstants.Ext);
    }

    public void loadPackage(String namespace, String ext) throws Exception {
        String[] fileNames = classScanner.scannerPackage(namespace, ext);
        // 加载class,获取协议命令
        if(fileNames != null) {
            for (String fileName : fileNames) {
                String realClass = namespace
                        + "."
                        + fileName.subSequence(0, fileName.length()
                        - (ext.length()));
                Class<?> messageClass = Class.forName(realClass);

                logger.info("rpc load:" + messageClass);
                RpcServiceAnnotation rpcServiceAnnotation = messageClass.getAnnotation(RpcServiceAnnotation.class);
                if(rpcServiceAnnotation != null) {
                    String interfaceName = messageClass.getAnnotation(RpcServiceAnnotation.class).value().getName();
                    ProtostuffSerializeI rpcSerialize = LocalMananger.getInstance().getLocalSpringBeanManager().getProtostuffSerialize();
                    Object serviceBean = (Object) rpcSerialize.newInstance(messageClass);
                    registryMap.put(interfaceName, serviceBean);
                    logger.info("rpc register:" + messageClass);
                }
            }
        }
    }

    public Object getServiceBean(String className){
        return registryMap.get(className);
    }

}
