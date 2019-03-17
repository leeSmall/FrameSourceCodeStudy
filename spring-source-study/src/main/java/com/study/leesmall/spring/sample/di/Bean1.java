package com.study.leesmall.spring.sample.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
// @Scope("prototype")
public class Bean1 {

	@Autowired
	private Bean2 b2;

	public void do1() {
		System.out.println("------------------do1");
	}

	public Bean2 getB2() {
		return b2;
	}

	public void setB2(Bean2 b2) {
		this.b2 = b2;
	}
}
