package com.zhaolei.material.admin.common.tools;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 通过实现ApplicationContextAware接口来获取spring上下文
 * 非spring管理的类如果想获取spring管理实例，有以下方式：
 * 一、new ClasspathXmlApplicationContext(配置文件)，也可以用不同方式加载配置文件;会造成对象重复加载
 * 二、利用spring的生命周期，实现不同的Aware接口来获取不同的信息
 * @author ZHAOLEI
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext applicationCOntext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationCOntext = applicationContext;
    }

    public static ApplicationContext getContext(){
        return applicationCOntext;
    }
}
