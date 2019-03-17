package com.study.leesmall.spring.sample.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class AopMainUseAspectAnnotation {
	public static void main(String[] args) {
		ApplicationContext context = new GenericXmlApplicationContext(
				"classpath:com/study/leesmall/spring/sample/aop/application2.xml");

		BeanQ bq = context.getBean(BeanQ.class);
		bq.do1("task1", 20);
		System.out.println();

		bq.service1("service1");

	}
}
