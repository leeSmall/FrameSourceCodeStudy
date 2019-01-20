package com.study.design.mode.samples;

import com.study.design.mode.samples.decorator.Component;
import com.study.design.mode.samples.decorator.ConcreteComponent;
import com.study.design.mode.samples.decorator.DecoratorA;

public class DecoratorSample {
	public static void main(String[] args) {
		Component cc = new ConcreteComponent();
		cc = new DecoratorA(cc);
		System.out.println(cc.methodA());
		System.out.println(cc.methodB());
	}
}
