package com.study.leesmall.spring.sample.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class AopMain {
	public static void main(String[] args) {
		ApplicationContext context = new GenericXmlApplicationContext(
				"classpath:com/study/leesmall/spring/sample/aop/application.xml");

		BeanQ bq = context.getBean(BeanQ.class);
		bq.do1("task1", 20);
		System.out.println();

		bq.service1("service1");

		System.out.println();
		bq.service2("ssss");
	}
}
