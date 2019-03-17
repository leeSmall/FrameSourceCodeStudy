package com.study.leesmall.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@Lazy
public class Bbean {

	@Autowired
	private Abean abean;

	public void sayHello() {
		System.out.println(this + " 大哥，进来玩会！");
	}
}
