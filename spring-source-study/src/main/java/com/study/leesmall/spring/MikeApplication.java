package com.study.leesmall.spring;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.study.leesmall.spring.ext.MyTypeFilter;
import com.study.leesmall.spring.service.Abean;
import com.study.leesmall.spring.service.CombatService;

@Configuration
@ComponentScan(includeFilters = { @Filter(type = FilterType.CUSTOM, classes = MyTypeFilter.class) })
public class MikeApplication {

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");

		CombatService cs = context.getBean(CombatService.class);
		cs.doInit();
		cs.combating();

		ApplicationContext context1 = new FileSystemXmlApplicationContext("e:/study/application.xml");
		cs = context1.getBean(CombatService.class);
		cs.doInit();
		cs.combating();

		context1 = new GenericXmlApplicationContext("file:e:/study/application.xml");
		cs = context1.getBean(CombatService.class);
		cs.doInit();
		cs.combating();

		// 注解的方式
		ApplicationContext context2 = new AnnotationConfigApplicationContext(MikeApplication.class);
		CombatService cs2 = context2.getBean(CombatService.class);
		cs2.combating();

		System.out.println("------------------------------------------------------");
		GenericApplicationContext context3 = new GenericApplicationContext();
		new XmlBeanDefinitionReader(context3).loadBeanDefinitions("classpath:application.xml");
		new ClassPathBeanDefinitionScanner(context3).scan("com.study.leesmall.spring.service");
		// 一定要刷新
		context3.refresh();
		cs2 = context3.getBean(CombatService.class);
		cs2.combating();
		Abean ab = context3.getBean(Abean.class);
		ab.doSomething();
	}

	@Bean
	public CombatService getCombatService() {
		return new CombatService(120);
	}
}
