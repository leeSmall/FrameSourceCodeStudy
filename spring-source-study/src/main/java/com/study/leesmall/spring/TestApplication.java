package com.study.leesmall.spring;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.study.leesmall.spring.service.Abean;
import com.study.leesmall.spring.service.CombatService;

//Spring不同的方式创建bean实例使用代码示例
@Configuration
public class TestApplication {

	public static void main(String[] args) {

		//类路径下加载xml配置文件创建bean定义
		ApplicationContext context1 = new ClassPathXmlApplicationContext("application.xml");
		CombatService cs = context1.getBean(CombatService.class);
		cs.doInit();
		cs.combating();

		//文件系统下加载xml配置文件创建bean定义
		ApplicationContext context2 = new FileSystemXmlApplicationContext("e:/study/application.xml");
		cs = context2.getBean(CombatService.class);
		cs.doInit();
		cs.combating();

		//通用的xml方式加载xml配置文件创建bean定义
		ApplicationContext context3 = new GenericXmlApplicationContext("file:e:/study/application.xml");
		cs = context3.getBean(CombatService.class);
		cs.doInit();
		cs.combating();

		// 扫描注解的方式创建bean定义
		ApplicationContext context4 = new AnnotationConfigApplicationContext(TestApplication.class);
		CombatService cs2 = context4.getBean(CombatService.class);
		cs2.combating();
		
		//通用的方式加载xml配置文件或者扫描指定包下的类创建bean定义
		System.out.println("------------------------------------------------------");
		GenericApplicationContext context5 = new GenericApplicationContext();
		new XmlBeanDefinitionReader(context5).loadBeanDefinitions("classpath:application.xml");
		new ClassPathBeanDefinitionScanner(context5).scan("com.study.leesmall.spring.service");
		// 一定要刷新
		context5.refresh();
		cs2 = context5.getBean(CombatService.class);
		cs2.combating();
		Abean ab = context5.getBean(Abean.class);
		ab.doSomething();
	}

	@Bean
	public CombatService getCombatService() {
		return new CombatService(120);
	}
}
