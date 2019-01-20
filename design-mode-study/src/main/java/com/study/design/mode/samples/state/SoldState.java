package com.study.design.mode.samples.state;

/**
 * 
 * @Description: 售罄状态
 * @author liguangsheng
 * @date 2018年11月25日
 *
 */
public class SoldState implements State {

	private NewCoffeeMachine machine;

	public SoldState(NewCoffeeMachine machine) {
		this.machine = machine;
	}

	@Override
	public void pay() {
		System.out.println("咖啡已卖完,不能支付!");

	}

	@Override
	public void refund() {
		System.out.println("不能退款!");

	}

	@Override
	public void buy() {
		System.out.println("咖啡已卖完,不能购买!");

	}

	@Override
	public void getCoffee() {
		System.out.println("咖啡已卖完!");

	}

}
