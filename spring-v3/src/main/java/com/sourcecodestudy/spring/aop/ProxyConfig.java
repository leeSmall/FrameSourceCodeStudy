package com.sourcecodestudy.spring.aop;

import java.util.ArrayList;
import java.util.List;

import org.springframework.aop.Advisor;

import com.sourcecodestudy.spring.beans.BeanFactory;

public class ProxyConfig {

	/**
	 * 被代理的目标源对象
	 */
	private Object targetSource;

	private String beanName;

	/**
	 * 匹配的advsors
	 */
	private List<Advisor> advisors = new ArrayList<>();

	/**
	 * 要实现的接口
	 */
	private List<Class<?>> interfaces = new ArrayList<>();

	/**
	 * 要继承的类
	 */
	private Class<?> targetClass;

	private BeanFactory beanFactory;

	private boolean proxyTargetClass;

	public Object getTargetSource() {
		return targetSource;
	}

	public void setTargetSource(Object targetSource) {
		this.targetSource = targetSource;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public List<Advisor> getAdvisors() {
		return advisors;
	}

	public void setAdvisors(List<Advisor> advisors) {
		this.advisors = advisors;
	}

	public List<Class<?>> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(List<Class<?>> interfaces) {
		this.interfaces = interfaces;
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(Class<?> targetClass) {
		this.targetClass = targetClass;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public boolean isProxyTargetClass() {
		return proxyTargetClass;
	}

	public void setProxyTargetClass(boolean proxyTargetClass) {
		this.proxyTargetClass = proxyTargetClass;
	}

	public void addInterface(Class<?>... itf) {
		if (this.interfaces == null) {
			this.interfaces = new ArrayList<>();
		}
		for (Class<?> c : itf) {
			this.interfaces.add(c);
		}
	}

}
