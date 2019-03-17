package com.sourcecodestudy.spring.beans;

/**
 * 
 * @Description: AOP增强处理接口
 * @author leeSamll
 * @date 2018年12月2日
 *
 */
public interface BeanPostProcessor {

	//bean初始化前增强
	default Object postProcessBeforeInitialization(Object bean, String beanName) throws Throwable {
		return bean;
	}

	//bean初始化后增强
	default Object postProcessAfterInitialization(Object bean, String beanName) throws Throwable {
		return bean;
	}
}
