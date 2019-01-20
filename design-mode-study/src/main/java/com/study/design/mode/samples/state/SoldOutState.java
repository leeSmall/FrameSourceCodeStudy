package com.study.design.mode.samples.state;

/**
 * 
 * @Description: 售出状态
 * @author liguangsheng
 * @date 2018年11月25日
 *
 */
public class SoldOutState implements State {

	private NewCoffeeMachine machine;

	public SoldOutState(NewCoffeeMachine machine) {
		this.machine = machine;
	}

	@Override
	public void pay() {
		System.out.println("当前状态为售出，请取咖啡！");

	}

	@Override
	public void refund() {
		System.out.println("当前状态为售出，不能退款！");

	}

	@Override
	public void buy() {
		System.out.println("当前状态为售出，请取咖啡！");

	}

	@Override
	public void getCoffee() {
		System.out.println("咖啡已出，请取咖啡！");

	}

}
