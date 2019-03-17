package com.study.leesmall.spring.sample.factory;

import org.springframework.beans.factory.FactoryBean;

import com.study.leesmall.spring.service.LoveService;
import com.study.leesmall.spring.service.LoveServiceImpl;

//实现FactoryBean的方式创建bean实例
public class LoveServiceFactoryBean implements FactoryBean<LoveService> {

	@Override
	public LoveService getObject() throws Exception {
		return new LoveServiceImpl();
	}

	@Override
	public Class<?> getObjectType() {
		return LoveService.class;
	}

}
