package com.study.design.mode.samples.responsibility;

/**
 * 
 * @Description: 具体的责任逻辑实现
 * @author leeSamll
 * @date 2018年11月25日
 *
 */
public class ResponsibilityA implements Responsibility {

	@Override
	public void process(Request request, ResponsibilityChain chain) {
		//前置增强
		System.out.println("Before Responsibility-A done something...");
		//ResponsibilityA处理完以后调用ResponsibilityChain的process方法交给下一个责任逻辑处理
		chain.process(request);
		//后置增强
	}

}
