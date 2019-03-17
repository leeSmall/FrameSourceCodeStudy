package com.sourcecodestudy.spring.aop.advice;

import java.lang.reflect.Method;

public interface AfterReturningAdvice extends Advice {
	/**
	 * 实现该方法，提供后置增强
	 * 
	 * @param returnValue
	 *            返回值
	 * @param method
	 *            被增强的方法
	 * @param args
	 *            方法的参数
	 * @param target
	 *            方法的所属对象
	 * @throws Throwable
	 */
	void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable;
}
