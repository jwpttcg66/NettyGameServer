package com.wolf.shoot.service.jmx;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.*;
import org.springframework.jmx.support.ConnectorServerFactoryBean;
import org.springframework.jmx.support.MBeanServerConnectionFactoryBean;
import org.springframework.jmx.support.MBeanServerFactoryBean;
import org.springframework.remoting.rmi.RmiRegistryFactoryBean;

import javax.management.*;
import java.io.IOException;

/**
 * Created by jwp on 2017/3/8.
 */
@Configuration
@EnableMBeanExport
@ComponentScan("com.wolf.shoot.service.jmx")
public class ThreadPoolMonitorProvider {
    public final static String DELIMITER = ":";
    public final static String jmxPoolSizeMethod = "setPoolSize";
    public final static String jmxActiveCountMethod = "setActiveCount";
    public final static String jmxCorePoolSizeMethod = "setCorePoolSize";
    public final static String jmxMaximumPoolSizeMethod = "setMaximumPoolSize";
    public final static String jmxLargestPoolSizeMethod = "setLargestPoolSize";
    public final static String jmxTaskCountMethod = "setTaskCount";
    public final static String jmxCompletedTaskCountMethod = "setCompletedTaskCount";
    public static String url;

    @Bean
    public ThreadPoolStatus threadPoolStatus() {
        return new ThreadPoolStatus();
    }

    @Bean
    public MBeanServerFactoryBean mbeanServer() {
        return new MBeanServerFactoryBean();
    }

    @Bean
    public RmiRegistryFactoryBean registry() {
        return new RmiRegistryFactoryBean();
    }

    @Bean
    @DependsOn("registry")
    public ConnectorServerFactoryBean connectorServer() throws MalformedObjectNameException {
//        MessageRecvExecutor ref = MessageRecvExecutor.getInstance();
//        String ipAddr = StringUtils.isNotEmpty(ref.getServerAddress()) ? StringUtils.substringBeforeLast(ref.getServerAddress(), DELIMITER) : "localhost";
        String ipAddr ="localhost";
        url = "service:jmx:rmi://" + ipAddr + "/jndi/rmi://" + ipAddr + ":1099/nettyrpcstatus";
        System.out.println("NettyRPC JMX MonitorURL : [" + url + "]");
        ConnectorServerFactoryBean connectorServerFactoryBean = new ConnectorServerFactoryBean();
        connectorServerFactoryBean.setObjectName("connector:name=rmi");
        connectorServerFactoryBean.setServiceUrl(url);
        return connectorServerFactoryBean;
    }

    public static void monitor(ThreadPoolStatus status) throws IOException, MalformedObjectNameException, ReflectionException, MBeanException, InstanceNotFoundException {
        MBeanServerConnectionFactoryBean mBeanServerConnectionFactoryBean = new MBeanServerConnectionFactoryBean();
        mBeanServerConnectionFactoryBean.setServiceUrl(url);
        mBeanServerConnectionFactoryBean.afterPropertiesSet();
        MBeanServerConnection connection = mBeanServerConnectionFactoryBean.getObject();
        ObjectName objectName = new ObjectName("com.wolf.shoot.service.jmx:name=threadPoolStatus,type=ThreadPoolStatus");

        connection.invoke(objectName, jmxPoolSizeMethod, new Object[]{status.getPoolSize()}, new String[]{int.class.getName()});
        connection.invoke(objectName, jmxActiveCountMethod, new Object[]{status.getActiveCount()}, new String[]{int.class.getName()});
        connection.invoke(objectName, jmxCorePoolSizeMethod, new Object[]{status.getCorePoolSize()}, new String[]{int.class.getName()});
        connection.invoke(objectName, jmxMaximumPoolSizeMethod, new Object[]{status.getMaximumPoolSize()}, new String[]{int.class.getName()});
        connection.invoke(objectName, jmxLargestPoolSizeMethod, new Object[]{status.getLargestPoolSize()}, new String[]{int.class.getName()});
        connection.invoke(objectName, jmxTaskCountMethod, new Object[]{status.getTaskCount()}, new String[]{long.class.getName()});
        connection.invoke(objectName, jmxCompletedTaskCountMethod, new Object[]{status.getCompletedTaskCount()}, new String[]{long.class.getName()});
    }
}
