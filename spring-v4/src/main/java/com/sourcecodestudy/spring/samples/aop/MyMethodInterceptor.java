package com.sourcecodestudy.spring.samples.aop;

import java.lang.reflect.Method;

import com.sourcecodestudy.spring.aop.advice.MethodInterceptor;

public class MyMethodInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(Method method, Object[] args, Object target) throws Throwable {
		System.out.println(this + "对 " + target + "进行了环绕--前增强");
		Object ret = method.invoke(target, args);
		System.out.println(this + "对 " + target + "进行了环绕 --后增强。方法的返回值为：" + ret);
		return ret;
	}

}
