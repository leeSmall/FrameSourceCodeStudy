package com.study.leesmall.spring.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.study.leesmall.spring.ext.MyComponetAnno;

//@Componet
//@Service
@MyComponetAnno
public class Abean {

	@Autowired
	private ApplicationContext applicationContext;

	public Abean() {
		System.out.println("-----------------Abean 被实例化了。。。。。。。。。");
	}

	public void doSomething() {
		System.out.println(this + " do something .....leesmall.love="
				+ this.applicationContext.getEnvironment().getProperty("leesmall.love"));
		System.out
				.println("-----------leesmall.name=" + this.applicationContext.getMessage("leesmall.name", null, Locale.CHINA));
	}
}
