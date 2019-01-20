package com.study.design.mode.samples.state;

/**
 * 
 * @Description: 没有支付状态
 * @author liguangsheng
 * @date 2018年11月25日
 *
 */
public class NoPayState implements State {

	private NewCoffeeMachine machine;

	public NoPayState(NewCoffeeMachine machine) {
		this.machine = machine;
	}

	@Override
	public void pay() {
		System.out.println("支付成功，请去确定购买咖啡。");
		this.machine.state = this.machine.PAY;
	}

	@Override
	public void refund() {
		System.out.println("你尚未支付，请不要乱按！");
	}

	@Override
	public void buy() {
		System.out.println("你尚未支付，请不要乱按！");
	}

	@Override
	public void getCoffee() {
		System.out.println("你尚未支付，请不要乱按！");
	}

}
