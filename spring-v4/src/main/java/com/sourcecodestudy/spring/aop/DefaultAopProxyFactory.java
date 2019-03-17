package com.sourcecodestudy.spring.aop;

import java.util.List;

import com.sourcecodestudy.spring.aop.advisor.Advisor;
import com.sourcecodestudy.spring.beans.BeanFactory;

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

	private boolean shouldUseJDKDynamicProxy(Object bean, String beanName) {
		// 如何判断？
		// 这样可以吗：有实现接口就用JDK,没有就用cglib？
		// 请同学们在读spring的源码时看spring中如何来判断的
		return false;
	}

}
