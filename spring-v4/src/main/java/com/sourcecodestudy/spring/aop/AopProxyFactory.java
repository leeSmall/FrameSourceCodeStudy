package com.sourcecodestudy.spring.aop;

import java.util.List;

import com.sourcecodestudy.spring.aop.advisor.Advisor;
import com.sourcecodestudy.spring.beans.BeanFactory;

public interface AopProxyFactory {

	AopProxy createAopProxy(Object bean, String beanName, List<Advisor> matchAdvisors, BeanFactory beanFactory)
			throws Throwable;

	/**
	 * 获得默认的AopProxyFactory实例
	 * 
	 * @return AopProxyFactory
	 */
	static AopProxyFactory getDefaultAopProxyFactory() {
		return new DefaultAopProxyFactory();
	}
}
