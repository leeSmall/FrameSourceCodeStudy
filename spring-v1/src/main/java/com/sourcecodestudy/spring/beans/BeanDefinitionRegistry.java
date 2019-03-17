package com.sourcecodestudy.spring.beans;

/**
 * 
 * @Description: bean定义BeanDefinition定义好了就需要告诉IOC容器(bean工厂):
 * 通过bean定义注册接口BeanDefinitionRegistry把bean定义BeanDefinition注册到IOC容器(bean工厂)BeanFactory
 * @author leeSmall
 * @date 2018年11月29日
 *
 */
public interface BeanDefinitionRegistry {

	//注册bean定义
	void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeanDefinitionRegistException;

	//获取bean定义
	BeanDefinition getBeanDefinition(String beanName);

	//判断是否包含bean定义
	boolean containsBeanDefinition(String beanName);

}
