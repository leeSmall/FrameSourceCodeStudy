package com.sourcecodestudy.spring.beans;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * bean定义接口
 * 
 * @author 动脑学院.Mike老师 QQ:3266399810
 *
 *         VIP课程咨询加可可老师 QQ：2729772006
 */
public interface BeanDefinition {

	String SCOPE_SINGLETION = "singleton";

	String SCOPE_PROTOTYPE = "prototype";

	/**
	 * 类
	 */
	Class<?> getBeanClass();

	/**
	 * Scope
	 */
	String getScope();

	/**
	 * 是否单例
	 */
	boolean isSingleton();

	/**
	 * 是否原型
	 */
	boolean isPrototype();

	/**
	 * 工厂bean名
	 */
	String getFactoryBeanName();

	/**
	 * 工厂方法名
	 */
	String getFactoryMethodName();

	/**
	 * 初始化方法
	 */
	String getInitMethodName();

	/**
	 * 销毁方法
	 */
	String getDestroyMethodName();

	/* 下面的四个方法是供beanFactory中使用的 */

	public Constructor<?> getConstructor();

	public void setConstructor(Constructor<?> constructor);

	public Method getFactoryMethod();

	public void setFactoryMethod(Method factoryMethod);

	public Object[] getConstructorArgumentRealValues();

	public void setConstructorArgumentRealValues(Object[] values);

	/**
	 * 校验bean定义的合法性
	 */
	default boolean validate() {
		// 没定义class,工厂bean或工厂方法没指定，则不合法。
		if (this.getBeanClass() == null) {
			if (StringUtils.isBlank(getFactoryBeanName()) || StringUtils.isBlank(getFactoryMethodName())) {
				return false;
			}
		}

		// 定义了类，又定义工厂bean，不合法
		if (this.getBeanClass() != null && StringUtils.isNotBlank(getFactoryBeanName())) {
			return false;
		}

		return true;
	}

	/**
	 * 获得构造参数定义 <br>
	 * add in V2
	 */
	List<?> getConstructorArgumentValues();

	/**
	 * 属性依赖<br>
	 * add in V2
	 * 
	 * @return
	 */
	List<PropertyValue> getPropertyValues();

}
