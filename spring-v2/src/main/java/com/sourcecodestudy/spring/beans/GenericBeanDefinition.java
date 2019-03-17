package com.sourcecodestudy.spring.beans;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @Description: bean定义有了就需要实现bean定义接口BeanDefinition实现一个通用的bean
 * 定义GenericBeanDefinition,用来承载数据
 * @author leeSmall
 * @date 2018年11月29日
 *
 */
public class GenericBeanDefinition implements BeanDefinition {

	// bean的名称
	private Class<?> beanClass;

	// scope 默认单例
	private String scope = BeanDefinition.SCOPE_SINGLETION;

	// 工厂bean名
	private String factoryBeanName;

	// 工厂方法名
	private String factoryMethodName;

	// 初始化方法
	private String initMethodName;

	// 销毁方法
	private String destroyMethodName;

	private Constructor<?> constructor;

	private Method factoryMethod;

	//存放构造参数值
	private List<?> constructorArgumentValues;

	//存放属性依赖
	private List<PropertyValue> propertyValues;

	public void setBeanClass(Class<?> beanClass) {
		this.beanClass = beanClass;
	}

	public void setScope(String scope) {
		if (StringUtils.isNotBlank(scope)) {
			this.scope = scope;
		}
	}

	public void setFactoryBeanName(String factoryBeanName) {
		this.factoryBeanName = factoryBeanName;
	}

	public void setFactoryMethodName(String factoryMethodName) {
		this.factoryMethodName = factoryMethodName;
	}

	public void setInitMethodName(String initMethodName) {
		this.initMethodName = initMethodName;
	}

	public void setDestroyMethodName(String destroyMethodName) {
		this.destroyMethodName = destroyMethodName;
	}

	@Override
	public Class<?> getBeanClass() {
		return this.beanClass;
	}

	@Override
	public String getScope() {
		return this.scope;
	}

	@Override
	public boolean isSingleton() {
		return BeanDefinition.SCOPE_SINGLETION.equals(this.scope);
	}

	@Override
	public boolean isPrototype() {
		return BeanDefinition.SCOPE_PROTOTYPE.equals(this.scope);
	}

	@Override
	public String getFactoryBeanName() {
		return this.factoryBeanName;
	}

	@Override
	public String getFactoryMethodName() {
		return this.factoryMethodName;
	}

	@Override
	public String getInitMethodName() {
		return this.initMethodName;
	}

	@Override
	public String getDestroyMethodName() {
		return this.destroyMethodName;
	}

	//获得构造参数值
	public List<?> getConstructorArgumentValues() {
		return constructorArgumentValues;
	}

	//设置构造参数值
	public void setConstructorArgumentValues(List<?> constructorArgumentValues) {
		this.constructorArgumentValues = constructorArgumentValues;
	}

	//获取属性依赖
	public List<PropertyValue> getPropertyValues() {
		return propertyValues;
	}

	//设置属性依赖
	public void setPropertyValues(List<PropertyValue> propertyValues) {
		this.propertyValues = propertyValues;
	}
	/* 下面的四个方法是供beanFactory中使用的 */
	//获取构造方法
	public Constructor<?> getConstructor() {
		return constructor;
	}

	//缓存构造方法
	public void setConstructor(Constructor<?> constructor) {
		this.constructor = constructor;
	}

	//获取工厂方法
	public Method getFactoryMethod() {
		return factoryMethod;
	}

	//缓存工厂方法
	public void setFactoryMethod(Method factoryMethod) {
		this.factoryMethod = factoryMethod;
	}

	@Override
	public String toString() {
		return "GenericBeanDefinition [beanClass=" + beanClass + ", scope=" + scope + ", factoryBeanName="
				+ factoryBeanName + ", factoryMethodName=" + factoryMethodName + ", initMethodName=" + initMethodName
				+ ", destroyMethodName=" + destroyMethodName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((beanClass == null) ? 0 : beanClass.hashCode());
		result = prime * result + ((destroyMethodName == null) ? 0 : destroyMethodName.hashCode());
		result = prime * result + ((factoryBeanName == null) ? 0 : factoryBeanName.hashCode());
		result = prime * result + ((factoryMethodName == null) ? 0 : factoryMethodName.hashCode());
		result = prime * result + ((initMethodName == null) ? 0 : initMethodName.hashCode());
		result = prime * result + ((scope == null) ? 0 : scope.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GenericBeanDefinition other = (GenericBeanDefinition) obj;
		if (beanClass == null) {
			if (other.beanClass != null)
				return false;
		} else if (!beanClass.equals(other.beanClass))
			return false;
		if (destroyMethodName == null) {
			if (other.destroyMethodName != null)
				return false;
		} else if (!destroyMethodName.equals(other.destroyMethodName))
			return false;
		if (factoryBeanName == null) {
			if (other.factoryBeanName != null)
				return false;
		} else if (!factoryBeanName.equals(other.factoryBeanName))
			return false;
		if (factoryMethodName == null) {
			if (other.factoryMethodName != null)
				return false;
		} else if (!factoryMethodName.equals(other.factoryMethodName))
			return false;
		if (initMethodName == null) {
			if (other.initMethodName != null)
				return false;
		} else if (!initMethodName.equals(other.initMethodName))
			return false;
		if (scope == null) {
			if (other.scope != null)
				return false;
		} else if (!scope.equals(other.scope))
			return false;
		return true;
	}

}
