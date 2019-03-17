package com.study.leesmall.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class SingleSpringStart {

	public static void main(String[] args) {
        //类路径下加载xml配置文件创建bean定义
        //ApplicationContext context1 = new ClassPathXmlApplicationContext("application.xml");
		
		//通用的xml方式加载xml配置文件创建bean定义
		//ApplicationContext context3 = new GenericXmlApplicationContext("file:e:/study/application.xml");
		
		// 扫描注解的方式创建bean定义
		AnnotationConfigApplicationContext context4 = new AnnotationConfigApplicationContext("com.study.leesmall.spring.service");
		context4.close();
	}
}

/*
 * GenericXmlApplicationContext context1 = new
 * GenericXmlApplicationContext("file:e:/study/application.xml");
 * context1.close();
 */