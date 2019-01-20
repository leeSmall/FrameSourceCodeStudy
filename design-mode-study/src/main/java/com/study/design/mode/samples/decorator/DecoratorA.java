package com.study.design.mode.samples.decorator;

public class DecoratorA extends Decorator {

	public DecoratorA(Component component) {
		super(component);
	}

	public String methodA() {
		return this.component.methodA() + " + A";
	}

	public int methodB() {
		return this.component.methodB() + 10;
	}
}
