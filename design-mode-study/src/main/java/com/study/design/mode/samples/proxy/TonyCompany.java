package com.study.design.mode.samples.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 
 * @Description: JDK动态代理
 * @author leeSamll
 * @date 2018年11月24日
 *
 */
public class TonyCompany {

	//动态生成代理对象 传入target的是被代理的类
	public static Object proxy(Object target) {
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
				new MyInvationHandler(target));
	}

	//特定的功能增强实现
	private static class MyInvationHandler implements InvocationHandler {

		//被被代理的目标对象
		private Object target;

		public MyInvationHandler(Object target) {
			super();
			this.target = target;
		}

		public Object getTarget() {
			return target;
		}

		public void setTarget(Object target) {
			this.target = target;
		}

		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			// 前置增强
			doSomethingBefore();

			// 调用被代理对象的方法
			Object res = method.invoke(target, args);

			// 后置增强
			doSomethingAfter();

			return res;
		}

		private void doSomethingAfter() {
			System.out.println("老板，你觉得怎样，欢迎下次再约！");
		}

		private void doSomethingBefore() {
			System.out.println("老板，这个我试过了，很不错，推荐给你！");
		}

	}
}
