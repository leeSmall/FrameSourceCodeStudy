package com.sourcecodestudy.spring.aop.pointcut;

import java.lang.reflect.Method;

/**
 * 
 * @Description: Pointcut标准接口
 * @author leeSmall
 * @date 2018年12月2日
 *
 */
public interface Pointcut {

	//匹配类
	boolean matchsClass(Class<?> targetClass);

	//匹配方法
	boolean matchsMethod(Method method, Class<?> targetClass);
}
