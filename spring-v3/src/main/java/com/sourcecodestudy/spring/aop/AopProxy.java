package com.sourcecodestudy.spring.aop;

/**
 * 
 * @Description: JDK动态代理和cglib动态代理抽象出公共部分的接口去获取代理对象
 * @author leeSmall
 * @date 2018年12月2日
 *
 */
public interface AopProxy {
	
	//获取代理对象
	Object getProxy();

	//通过类加载器获取代理对象
	Object getProxy(ClassLoader classLoader);

}
