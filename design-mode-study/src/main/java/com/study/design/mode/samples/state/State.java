package com.study.design.mode.samples.state;

/**
 * 
 * @Description: 状态接口
 * @author liguangsheng
 * @date 2018年11月25日
 *
 */
public interface State {
	void pay();

	void refund();

	void buy();

	void getCoffee();
}
