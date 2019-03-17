package com.sourcecodestudy.spring.beans;

/**
 * 用于依赖注入中描述bean依赖
 * 
 * @author 动脑学院.Mike老师 QQ:3266399810
 *
 *         VIP课程咨询加可可老师 QQ：2729772006
 */
public class BeanReference {

	private String beanName;

	public BeanReference(String beanName) {
		super();
		this.beanName = beanName;
	}

	/**
	 * 获得引用的beanName
	 * 
	 * @return
	 */
	public String getBeanName() {
		return this.beanName;
	}
}
