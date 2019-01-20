package com.study.design.mode.samples.state;

/**
 * 
 * @Description: 使用了状态模式的咖啡机
 * @author liguangsheng
 * @date 2018年11月25日
 *
 */
public class NewCoffeeMachine {

	final State NO_PAY, PAY, SOLD, SOLD_OUT;
	State state;
	int store;

	//初始化状态
	public NewCoffeeMachine(int store) {
		NO_PAY = new NoPayState(this);
		PAY = new PayState(this);
		SOLD = new SoldState(this);
		SOLD_OUT = new SoldOutState(this);

		this.store = store;
		if (this.store > 0) {
			this.state = NO_PAY;
		}
	}

	//支付行为委托给当前状态实例
	public void pay() {
		this.state.pay();
	}

	//退款行为委托给当前状态实例
	public void refund() {
		this.state.refund();
	}

	//买咖啡行为委托给当前状态实例
	public void buy() {
		this.state.buy();
	}

	//取咖啡行为委托给当前状态实例
	public void getCoffee() {
		this.state.getCoffee();
	}
}
