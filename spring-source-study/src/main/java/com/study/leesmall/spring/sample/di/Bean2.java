package com.study.leesmall.spring.sample.di;

import org.springframework.stereotype.Component;

@Component
// @Scope("prototype")
public class Bean2 {

	// @Autowired
	private Bean1 b1;

	public void do2() {
		b1.do1();
	}

	public Bean1 getB1() {
		return b1;
	}

	public void setB1(Bean1 b1) {
		this.b1 = b1;
	}
}
