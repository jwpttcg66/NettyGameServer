package com.wolf.shoot.manager.extend.factory;

import com.wolf.shoot.common.util.BeanUtil;
import com.wolf.shoot.manager.extend.LocalExtendSpringBeanManager;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Repository;

/**
 * Created by jwp on 2017/5/5.
 */
@Repository
public class LocalExtendSpringBeanManagerFactoryBean implements FactoryBean<LocalExtendSpringBeanManager> {

    @Override
    public LocalExtendSpringBeanManager getObject() throws Exception {
        return (LocalExtendSpringBeanManager) BeanUtil.getBean("localExtendSpringBeanManager");
    }

    @Override
    public Class<?> getObjectType() {
        return LocalExtendSpringBeanManager.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
