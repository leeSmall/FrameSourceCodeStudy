package com.study.design.mode.samples.state;

/**
 * 
 * @Description: 普通的咖啡机: 没有使用状态模式的咖啡机
 * @author liguangsheng
 * @date 2018年11月25日
 *
 */
public class CoffeeMachine {

	final static int NO_PAY = 0;
	final static int PAY = 1;
	final static int SOLD = 2;
	final static int SOLD_OUT = 4;

	private int state = SOLD_OUT;
	private int store;

	public CoffeeMachine(int store) {
		this.store = store;
		if (this.store > 0) {
			this.state = NO_PAY;
		}
	}

	public void pay() {
		switch (this.state) {
		case NO_PAY:
			System.out.println("支付成功，请确定购买咖啡。");
			this.state = PAY;
			break;
		case PAY:
			System.out.println("已支付成功，请确定购买咖啡。");
			break;
		case SOLD:
			System.out.println("待取咖啡中，请稍后购买！");
			break;
		case SOLD_OUT:
			System.out.println("咖啡已售罄，不可购买！");
		}
	}

	public void refund() {
		switch (this.state) {
		case NO_PAY:
			System.out.println("你尚未支付，请不要乱按！");
			break;
		case PAY:
			System.out.println("退款成功！");
			this.state = NO_PAY;
			break;
		case SOLD:
			System.out.println("已购买，请取用！");
			break;
		case SOLD_OUT:
			System.out.println("咖啡已售罄，不可购买！");
		}
	}

	// 购买
	public void buy() {
		switch (this.state) {
		case NO_PAY:
			System.out.println("你尚未支付，请不要乱按！");
			break;
		case PAY:
			System.out.println("购买成功，请取用！");
			this.state = SOLD;
			break;
		case SOLD:
			System.out.println("已购买，请取用！");
			break;
		case SOLD_OUT:
			System.out.println("咖啡已售罄，不可购买！");
		}
	}

	// 取coffee
	public void getCoffee() {
		switch (this.state) {
		case NO_PAY:
			System.out.println("你尚未支付，请不要乱按！");
			break;
		case PAY:
			System.out.println("已购买，请取用！");
			break;
		case SOLD:
			System.out.println("请放好杯子，3秒后将出咖啡！");
			this.store--;
			if (this.store == 0) {
				this.state = SOLD_OUT;
			} else {
				this.state = NO_PAY;
			}
			break;
		case SOLD_OUT:
			System.out.println("咖啡已售罄，不可购买！");
		}
	}
}
