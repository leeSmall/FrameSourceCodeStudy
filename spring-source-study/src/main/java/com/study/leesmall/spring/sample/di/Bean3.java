package com.study.leesmall.spring.sample.di;

import org.springframework.stereotype.Component;

@Component
public class Bean3 {
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
