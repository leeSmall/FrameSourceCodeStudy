package com.sourcecodestudy.spring.context;

import com.sourcecodestudy.spring.beans.BeanFactory;

/**
 * 
 * @Description: 加载xml配置文件和扫描包的接口 
 * 在XmlApplicationContext和AnnotationApplicationContext里面实现
 * 
 * @author leeSmall
 * @date 2018年12月7日
 *
 */
public interface ApplicationContext extends ResourceLoader, BeanFactory {

}
