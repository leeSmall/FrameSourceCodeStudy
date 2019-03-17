package com.sourcecodestudy.spring.samples;

/**
 * 
 * @Description: 创建bean实例ABean的工厂ABeanFactory
 * @author leeSmall
 * @date 2018年11月29日
 *
 */
public class ABeanFactory {

	//静态工厂方式
	public static ABean getABean() {
		return new ABean();
	}

	//工厂bean的方式
	public ABean getABean2() {
		return new ABean();
	}
}
