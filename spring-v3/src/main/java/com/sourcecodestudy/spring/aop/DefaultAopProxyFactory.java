package com.sourcecodestudy.spring.aop;

import java.util.List;

import com.sourcecodestudy.spring.aop.advisor.Advisor;
import com.sourcecodestudy.spring.beans.BeanFactory;

/**
 * 
 * @Description: 工厂AopProxyFactory的默认实现   负责选择使用哪个动态代理
 * @author leeSamll
 * @date 2018年12月3日
 *
 */
public class DefaultAopProxyFactory implements AopProxyFactory {

	@Override
	public AopProxy createAopProxy(Object bean, String beanName, List<Advisor> matchAdvisors, BeanFactory beanFactory)
			throws Throwable {
		// 是该用jdk动态代理还是cglib？
		if (shouldUseJDKDynamicProxy(bean, beanName)) {
			return new JdkDynamicAopProxy(beanName, bean, matchAdvisors, beanFactory);
		} else {
			return new CglibDynamicAopProxy(beanName, bean, matchAdvisors, beanFactory);
		}
	}

	//默认使用cglib
	private boolean shouldUseJDKDynamicProxy(Object bean, String beanName) {
		// 如何判断？
		// 这样可以吗：有实现接口就用JDK,没有就用cglib？
		return false;
	}

}
