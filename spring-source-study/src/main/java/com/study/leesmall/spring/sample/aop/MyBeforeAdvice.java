package com.study.leesmall.spring.sample.aop;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

//前置增强
public class MyBeforeAdvice implements MethodBeforeAdvice {

	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		System.out.println("------ MyBeforeAdvice before 增强 " + target + " " + method);
	}

}
