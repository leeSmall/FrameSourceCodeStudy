package com.study.design.mode.samples.proxy;

/**
 * 
 * @Description: 代理类实现Girl
 * @author leeSamll
 * @date 2018年11月24日
 *
 */
public class Tony implements Girl {

	//代理类持有被代理的目标对象TeacherCang（目标对象实现的超类或者接口）
	private Girl girl;

	public Girl getGirl() {
		return girl;
	}

	public void setGirl(Girl girl) {
		this.girl = girl;
	}

	//代理：控制、增强被代理对象的行为
	public boolean dating(float length) {
		// 前置增强
		doSomethingBefore();
		boolean res = this.girl.dating(length);
		// 后置增强
		doSomethingAfter();
		return res;
	}

	private void doSomethingBefore() {
		System.out.println("老板，这个我试过了，很不错，推荐给你！");
	}
	
	private void doSomethingAfter() {
		System.out.println("老板，你觉得怎样，欢迎下次再约！");
	}

}
