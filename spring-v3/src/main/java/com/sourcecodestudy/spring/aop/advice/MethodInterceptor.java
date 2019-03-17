package com.sourcecodestudy.spring.aop.advice;

import java.lang.reflect.Method;

/**
 * 
 * @Description: 对方法进行环绕（前置、后置）增强、异常处理增强接口
 * @author leeSmall
 * @date 2018年12月1日
 *
 */
public interface MethodInterceptor extends Advice {
	/**
	 * 对方法进行环绕（前置、后置）增强、异常处理增强，方法实现中需调用目标方法。
	 * 
	 * @param method 被增强的方法
	 *            
	 * @param args 被增强的方法的参数
	 *            
	 * @param target 被增强的目标对象(被增强的方法所在的类)
	 *            
	 * @return Object 被增强的方法的返回值
	 * @throws Throwable
	 */
	Object invoke(Method method, Object[] args, Object target) throws Throwable;
}
