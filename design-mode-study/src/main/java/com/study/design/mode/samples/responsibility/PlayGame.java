package com.study.design.mode.samples.responsibility;

/**
 * 
 * @Description: 调用者调用示例
 * @author leeSamll
 * @date 2018年11月25日
 *
 */
public class PlayGame {
	public static void main(String[] args) {
		//创建一个责任链
		ResponsibilityChain chain = new ResponsibilityChain();
		//往责任链里面加入具体的责任逻辑
		chain.register(new ResponsibilityA());
		chain.register(new ResponsibilityB());

		//开始处理
		chain.process(new Request() {
		});
	}
}
