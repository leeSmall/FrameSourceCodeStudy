package com.sourcecodestudy.spring.beans;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class GenericBeanDefinition implements BeanDefinition {

	private Class<?> beanClass;

	private String scope = BeanDefinition.SCOPE_SINGLETION;

	private String factoryBeanName;

	private String factoryMethodName;

	private String initMethodName;

	private String destroyMethodName;

	private Constructor<?> constructor;

	private Method factoryMethod;

	private List<?> constructorArgumentValues;

	private List<PropertyValue> propertyValues;

	private ThreadLocal<Object[]> realConstructorArgumentValues = new ThreadLocal<>();

	@Override
	public Object[] getConstructorArgumentRealValues() {
		return realConstructorArgumentValues.get();
	}

	@Override
	public void setConstructorArgumentRealValues(Object[] values) {
		realConstructorArgumentValues.set(values);
	}

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

	public List<?> getConstructorArgumentValues() {
		return constructorArgumentValues;
	}

	public void setConstructorArgumentValues(List<?> constructorArgumentValues) {
		this.constructorArgumentValues = constructorArgumentValues;
	}

	public List<PropertyValue> getPropertyValues() {
		return propertyValues;
	}

	public void setPropertyValues(List<PropertyValue> propertyValues) {
		this.propertyValues = propertyValues;
	}

	public Constructor<?> getConstructor() {
		return constructor;
	}

	public void setConstructor(Constructor<?> constructor) {
		this.constructor = constructor;
	}

	public Method getFactoryMethod() {
		return factoryMethod;
	}

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
