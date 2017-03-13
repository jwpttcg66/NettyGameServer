package com.wolf.shoot.service.rpc;

import com.wolf.shoot.common.config.GameServerConfigService;
import com.wolf.shoot.common.constant.GlobalConstants;
import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.common.constant.ServiceName;
import com.wolf.shoot.common.util.FileUtil;
import com.wolf.shoot.common.util.JdomUtils;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.IService;
import com.wolf.shoot.service.rpc.client.ConnectManage;
import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jwp on 2017/3/8.
 * rpc的服务发现
 */
@Service
public class RpcServiceDiscovery implements IService{

    private static final Logger LOGGER = Loggers.rpcLogger;

    private String registryAddress;


    protected Object lock = new Object();

    protected List<SdServer> sdWorldServers;
    protected List<SdServer> sdGameServers;

    public void updateConnectedServer(List<SdServer> sdServers){
        ConnectManage.getInstance().updateConnectedServer(sdServers);
    }

    public void updateOnlineConnectedServer(){
        ConnectManage.getInstance().updateConnectedServer(sdWorldServers);
    }

    @Override
    public String getId() {
        return ServiceName.RpcServiceDiscovery;
    }

    @Override
    public void startup() throws Exception {
        init();
//        updateConnectedServer();
    }

    @Override
    public void shutdown() throws Exception {
        ConnectManage.getInstance().stop();
    }

    @SuppressWarnings("unchecked")
    public void init() throws DataConversionException {

        GameServerConfigService gameServerConfigServiceEx = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        Element rootElement = JdomUtils.getRootElemet(FileUtil.getConfigURL(GlobalConstants.ConfigFile.RPC_SERVER_CONFIG).getFile());
        List<SdServer> sdWorldServers = new ArrayList<SdServer>();
        Element element = rootElement.getChild("online");
        List<Element> childrenElements = element.getChildren("server");
        for(Element childElement: childrenElements){
            int serverId = childElement.getAttribute("serverId").getIntValue();
            String domain = childElement.getAttributeValue("domain");
            int domainPort = childElement.getAttribute("domainPort").getIntValue();
            String ip = childElement.getAttributeValue("ip");
            int port = childElement.getAttribute("port").getIntValue();
            int weight = childElement.getAttribute("weight").getIntValue();
            int maxNumber = childElement.getAttribute("maxNumber").getIntValue();
            int communicationPort = childElement.getAttribute("communicationPort").getIntValue();
            int communicationNumber = childElement.getAttribute("communicationNumber").getIntValue();
            SdServer sdServer = new SdServer(serverId, domain, domainPort, ip, port, weight, maxNumber, communicationPort,communicationNumber);
            sdWorldServers.add(sdServer);

        }

        List<SdServer> sdGameServers = new ArrayList<SdServer>();
        element = rootElement.getChild("room");
        childrenElements = element.getChildren("server");
        for(Element childElement: childrenElements){
            int serverId = childElement.getAttribute("serverId").getIntValue();
            String domain = childElement.getAttributeValue("domain");
            int domainPort = childElement.getAttribute("domainPort").getIntValue();
            String ip = childElement.getAttributeValue("ip");
            int port = childElement.getAttribute("port").getIntValue();
            int weight = childElement.getAttribute("weight").getIntValue();
            int maxNumber = childElement.getAttribute("maxNumber").getIntValue();
            int communicationPort = childElement.getAttribute("communicationPort").getIntValue();
            int communicationNumber = childElement.getAttribute("communicationNumber").getIntValue();

            SdServer sdServer = new SdServer(serverId, domain, domainPort, ip, port, weight, maxNumber, communicationPort, communicationNumber);
            sdGameServers.add(sdServer);
        }

        synchronized (this.lock) {
            this.sdWorldServers = sdWorldServers;
            this.sdGameServers = sdGameServers;
        }
    }
}

