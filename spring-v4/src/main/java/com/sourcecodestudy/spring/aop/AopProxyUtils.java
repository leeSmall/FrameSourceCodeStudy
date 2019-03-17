package com.sourcecodestudy.spring.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.sourcecodestudy.spring.aop.advisor.Advisor;
import com.sourcecodestudy.spring.aop.advisor.PointcutAdvisor;
import com.sourcecodestudy.spring.beans.BeanFactory;

public class AopProxyUtils {

	/**
	 * 对方法应用advices增强
	 * 
	 * @param target
	 * @param method
	 * @param args
	 * @param matchAdvisors
	 * @param proxy
	 * @param beanFactory
	 * @return
	 * @throws Throwable
	 */
	public static Object applyAdvices(Object target, Method method, Object[] args, List<Advisor> matchAdvisors,
			Object proxy, BeanFactory beanFactory) throws Throwable {
		// 这里要做什么？
		// 1、获取要对当前方法进行增强的advice
		List<Object> advices = AopProxyUtils.getShouldApplyAdvices(target.getClass(), method, matchAdvisors,
				beanFactory);
		// 2、如有增强的advice，责任链式增强执行
		if (CollectionUtils.isEmpty(advices)) {
			return method.invoke(target, args);
		} else {
			// 责任链式执行增强
			AopAdviceChainInvocation chain = new AopAdviceChainInvocation(proxy, target, method, args, advices);
			return chain.invoke();
		}
	}

	/**
	 * 获取与方法匹配的切面的advices
	 * 
	 * @param beanClass
	 * @param method
	 * @param matchAdvisors
	 * @param beanFactory
	 * @return
	 * @throws Exception
	 */
	public static List<Object> getShouldApplyAdvices(Class<?> beanClass, Method method, List<Advisor> matchAdvisors,
			BeanFactory beanFactory) throws Throwable {
		if (CollectionUtils.isEmpty(matchAdvisors)) {
			return null;
		}
		List<Object> advices = new ArrayList<>();
		for (Advisor ad : matchAdvisors) {
			if (ad instanceof PointcutAdvisor) {
				if (((PointcutAdvisor) ad).getPointcut().matchsMethod(method, beanClass)) {
					advices.add(beanFactory.getBean(ad.getAdviceBeanName()));
				}
			}
		}

		return advices;
	}

}
