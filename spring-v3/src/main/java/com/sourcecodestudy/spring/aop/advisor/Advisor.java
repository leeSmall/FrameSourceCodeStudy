package com.sourcecodestudy.spring.aop.advisor;

/**
 * 
 * @Description: Advisor（通知者）接口
 * @author leeSmall
 * @date 2018年12月2日
 *
 */
public interface Advisor {

	//获取配置的Advice的bean的名字
	String getAdviceBeanName();

	//获取切入点表达式
	String getExpression();
}
