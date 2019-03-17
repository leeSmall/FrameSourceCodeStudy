package com.sourcecodestudy.spring.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import com.sourcecodestudy.spring.aop.advisor.Advisor;
import com.sourcecodestudy.spring.aop.advisor.AdvisorRegistry;
import com.sourcecodestudy.spring.aop.advisor.PointcutAdvisor;
import com.sourcecodestudy.spring.aop.pointcut.Pointcut;
import com.sourcecodestudy.spring.beans.BeanFactory;
import com.sourcecodestudy.spring.beans.BeanFactoryAware;
import com.sourcecodestudy.spring.beans.BeanPostProcessor;

/**
 * 
 * @Description: AOP增强处理的观察者实现
 * @author leeSamll
 * @date 2018年12月2日
 *
 */
public class AdvisorAutoProxyCreator implements AdvisorRegistry, BeanPostProcessor, BeanFactoryAware {
	//通知者
	private List<Advisor> advisors;

	//bean工厂
	private BeanFactory beanFactory;

	public AdvisorAutoProxyCreator() {
		this.advisors = new ArrayList<>();
	}

	//注册通知者
	public void registAdvisor(Advisor ad) {
		this.advisors.add(ad);
	}

	//获取通知者
	public List<Advisor> getAdvisors() {
		return advisors;
	}

	@Override
	public void setBeanFactory(BeanFactory bf) {
		this.beanFactory = bf;
	}

	//后置增强
	public Object postProcessAfterInitialization(Object bean, String beanName) throws Throwable {

		// 在此判断bean是否需要进行切面增强
		List<Advisor> matchAdvisors = getMatchedAdvisors(bean, beanName);
		// 如需要就进行增强,再返回增强的对象。
		if (CollectionUtils.isNotEmpty(matchAdvisors)) {
			bean = this.createProxy(bean, beanName, matchAdvisors);
		}
		return bean;
	}

	//在此判断bean是否需要进行切面增强
	private List<Advisor> getMatchedAdvisors(Object bean, String beanName) {
		if (CollectionUtils.isEmpty(advisors)) {
			return null;
		}

		// 得到类、类的所有方法
		Class<?> beanClass = bean.getClass();
		List<Method> allMethods = this.getAllMethodForClass(beanClass);

		// 存放匹配的Advisor的list
		List<Advisor> matchAdvisors = new ArrayList<>();
		// 遍历Advisor来找匹配的
		for (Advisor ad : this.advisors) {
			if (ad instanceof PointcutAdvisor) {
				if (isPointcutMatchBean((PointcutAdvisor) ad, beanClass, allMethods)) {
					matchAdvisors.add(ad);
				}
			}
		}

		return matchAdvisors;
	}

	//获取类的所有方法,包括继承的父类和实现的接口里面的方法
	private List<Method> getAllMethodForClass(Class<?> beanClass) {
		List<Method> allMethods = new LinkedList<>();
		//获取beanClass的所有接口
		Set<Class<?>> classes = new LinkedHashSet<>(ClassUtils.getAllInterfacesForClassAsSet(beanClass));
		classes.add(beanClass);
		
		//遍历所有的类和接口反射获取到所有的方法
		for (Class<?> clazz : classes) {
			Method[] methods = ReflectionUtils.getAllDeclaredMethods(clazz);
			for (Method m : methods) {
				allMethods.add(m);
			}
		}

		return allMethods;
	}

	//判断类及类的方法是否和切面匹配
	private boolean isPointcutMatchBean(PointcutAdvisor pa, Class<?> beanClass, List<Method> methods) {
		Pointcut p = pa.getPointcut();

		// 首先判断类是否匹配
		if (!p.matchsClass(beanClass)) {
			return false;
		}

		// 再判断是否有方法匹配
		for (Method method : methods) {
			if (p.matchsMethod(method, beanClass)) {
				return true;
			}
		}
		return false;
	}

	//创建代理对象增强
	private Object createProxy(Object bean, String beanName, List<Advisor> matchAdvisors) throws Throwable {
		// 通过AopProxyFactory工厂去完成选择、和创建代理对象的工作。
		return AopProxyFactory.getDefaultAopProxyFactory().createAopProxy(bean, beanName, matchAdvisors, beanFactory)
				.getProxy();
	}

}
