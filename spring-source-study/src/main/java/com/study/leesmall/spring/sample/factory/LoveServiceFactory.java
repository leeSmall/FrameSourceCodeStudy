package com.study.leesmall.spring.sample.factory;

import com.study.leesmall.spring.service.CombatService;
import com.study.leesmall.spring.service.LoveService;
import com.study.leesmall.spring.service.LoveServiceImpl;

//工厂方式创建bean实例
public class LoveServiceFactory {

	//静态工厂方式创建bean实例
	public static LoveService getLoveServiceFromStaticFactoryMethod() {
		return new LoveServiceImpl();
	}

	//非静态工厂方式创建bean实例
	public CombatService getCombatServiceFromMemberFactoryMethod(int time) {
		return new CombatService(time);
	}
}
