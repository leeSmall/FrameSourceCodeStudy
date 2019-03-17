package com.sourcecodestudy.spring.aop.advice;

import java.lang.reflect.Method;

/**
 * 
 * @Description: 前置增强接口
 * @author leeSmall
 * @date 2018年12月1日
 *
 */
public interface MethodBeforeAdvice extends Advice {

	/**
	 * 实现该方法进行前置增强
	 * 
	 * @param method 被增强的方法
	 *            
	 * @param args 被增强的方法的参数
	 *            
	 * @param target 被增强的目标对象(被增强的方法所在的类)
	 *            
	 * @throws Throwable 异常
	 */
	void before(Method method, Object[] args, Object target) throws Throwable;
}
