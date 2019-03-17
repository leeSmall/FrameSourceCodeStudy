package com.sourcecodestudy.spring.samples;

public class ABean {

	private String name;

	private CBean cb;

	public ABean(String name, CBean cb) {
		super();
		this.name = name;
		this.cb = cb;
		System.out.println("调用了含有CBean参数的构造方法");
	}

	public ABean(String name, CCBean cb) {
		super();
		this.name = name;
		this.cb = cb;
		System.out.println("调用了含有CCBean参数的构造方法");
	}

	public ABean(CBean cb) {
		super();
		this.cb = cb;
	}

	public void doSomthing() {
		System.out.println(System.currentTimeMillis() + " " + this.name + " cb.name=" + this.cb.getName());
	}

	public void init() {
		System.out.println("ABean.init() 执行了");
	}

	public void destroy() {
		System.out.println("ABean.destroy() 执行了");
	}
}
