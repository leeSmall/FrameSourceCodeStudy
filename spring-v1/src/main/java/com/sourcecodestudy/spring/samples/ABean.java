package com.sourcecodestudy.spring.samples;

/**
 * 
 * @Description: 需要创建的bean实例ABean
 * @author leeSmall
 * @date 2018年11月29日
 *
 */
public class ABean {

	public void doSomthing() {
		System.out.println(System.currentTimeMillis() + " " + this);
	}

	public void init() {
		System.out.println("ABean.init() 执行了");
	}

	public void destroy() {
		System.out.println("ABean.destroy() 执行了");
	}
}
