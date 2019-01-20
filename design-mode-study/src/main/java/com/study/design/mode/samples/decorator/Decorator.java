package com.study.design.mode.samples.decorator;

public class Decorator implements Component {

	protected Component component;

	public Decorator(Component component) {
		super();
		this.component = component;
	}

	public String methodA() {
		return this.component.methodA();
	}

	public int methodB() {
		return this.component.methodB();
	}
}
