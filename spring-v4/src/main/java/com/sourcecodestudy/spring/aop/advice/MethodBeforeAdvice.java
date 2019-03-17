package com.sourcecodestudy.spring.aop.advice;

import java.lang.reflect.Method;

public interface MethodBeforeAdvice extends Advice {

	/**
	 * 实现该方法进行前置增强
	 * 
	 * @param method
	 *            被增强的方法
	 * @param args
	 *            方法的参数
	 * @param target
	 *            被增强的目标对象
	 * @throws Throwable
	 */
	void before(Method method, Object[] args, Object target) throws Throwable;
}
