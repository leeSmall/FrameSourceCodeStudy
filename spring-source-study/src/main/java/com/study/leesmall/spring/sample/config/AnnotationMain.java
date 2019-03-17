package com.study.leesmall.spring.sample.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationMain {

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext("com.study.leesmall.spring.sample.config");

		BeanG bg = context.getBean(BeanG.class);
		bg.dog();
	}
}
