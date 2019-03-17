package com.sourcecodestudy.spring.aop.advice;

import java.lang.reflect.Method;

/**
 * 
 * @Description: 后置增强接口
 * @author leeSmall
 * @date 2018年12月1日
 *
 */
public interface AfterReturningAdvice extends Advice {
	/**
	 * 实现该方法，提供后置增强
	 * 
	 * @param returnValue 被增强的方法的返回值
	 *            
	 * @param method 被增强的方法
	 *            
	 * @param args 被增强的方法的参数
	 *            
	 * @param target 被增强的目标对象(被增强的方法所在的类)
	 *            
	 * @throws Throwable 异常
	 */
	void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable;
}
