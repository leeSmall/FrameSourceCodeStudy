package com.study.design.mode.samples.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 
 * @Description: cglib动态代理
 * @author leeSamll
 * @date 2018年11月24日
 *
 */
public class CglibDemo {

	// 特定的功能增强的实现
	static class MyMethodInterceptor implements MethodInterceptor {
		//特定的功能增强实现MyMethodInterceptor里面持有被代理的类或者接口target
		private Object target;

		public MyMethodInterceptor(Object target) {
			this.target = target;
		}

		//在intercept方法进行调用被代理类或者接口的方法之前进行拦截实现前置、后置、环绕、异常处理等功能的增强
		public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

			System.out.println("**************** " + method.getName());
			// 前置增强
			doSomethingBefore();
			// 返回值
			Object res = null;
			// 这里可以调用父类的该方法，当是生成接口的代理时不可调用。
			// Object res = methodProxy.invokeSuper(proxy, args);
			// 通过method来调用被代理对象的方法
			if (this.target != null) {
				res = method.invoke(target, args);
			}
			// 后置增强
			doSomethingAfter();
			return res;
		}

		private void doSomethingBefore() {
			System.out.println("老板你好，这个我试过了，很不错，推荐给你！");
		}

		private void doSomethingAfter() {
			System.out.println("老板你觉得怎样？ 欢迎下次.....");
		}
	};

	
	public static void main(String[] args) {
		//创建Enhancer对象用来生成代理类
		Enhancer e = new Enhancer();
		//创建需要被代理的类TeacherCang
		TeacherCang tc = new TeacherCang();
		// 设置增强回调
		e.setCallback(new MyMethodInterceptor(tc));
		
		//对接口生成代理对象
		System.out.println("--------------------cglib动态代理：对接口Girl进行代理----------------------");
		//设置要代理的接口
		e.setInterfaces(new Class[] { Girl.class });
		//生成代理的接口的动态代理对象
		Girl g = (Girl) e.create();
		//调用被代理的接口的dating方法
		g.dating(1.8f);

		// 对类生成代理对象
		System.out.println("--------------------cglib动态代理：对类TeacherCang进行代理----------------------");
		//设置要代理的类
		e.setSuperclass(TeacherCang.class);
		//把前面的设置的接口Girl置为空
		e.setInterfaces(null);
		//当有多个callback时，需要通过callbackFilter来指定被代理方法使用第几个callback
		/* e.setCallbackFilter(new CallbackFilter() {
			@Override
			public int accept(Method method) {
				return 0;
			}
		});*/

		//生成代理的类TeacherCang的动态代理对象
		TeacherCang proxy = (TeacherCang) e.create();
		//调用代理的类TeacherCang的dating方法
		proxy.dating(1.8f);
	}
}
