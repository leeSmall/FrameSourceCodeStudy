package com.sourcecodestudy.spring.beans;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @Description: bean定义BeanDefinition有了,bean定义注册BeanDefinitionRegistry也有了,
 * 就可以实现一个默认的bean工厂DefaultBeanFactory来创建bean实例了,DefaultBeanFactory除了需要实现BeanFactory外,
 * 还需要实现Bean定义注册接口BeanDefinitionRegistry,因为要把bean定义注册到bean工厂里面
 * @author leeSmall
 * @date 2018年11月29日
 *
 */
public class DefaultBeanFactory implements BeanFactory, BeanDefinitionRegistry, Closeable {

	private final Log logger = LogFactory.getLog(getClass());

	//用Map来存放bean定义信息
	private Map<String, BeanDefinition> beanDefintionMap = new ConcurrentHashMap<>(256);

	//用Map来存放创建的bean实例,注意这里只是存放单例bean,多实例每次都要创建新的,不需要存放
	private Map<String, Object> beanMap = new ConcurrentHashMap<>(256);

	//注册bean定义
	@Override
	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition)
			throws BeanDefinitionRegistException {
		//判断给入的beanName和beanDefinition不能为空
		Objects.requireNonNull(beanName, "注册bean需要给入beanName");
		Objects.requireNonNull(beanDefinition, "注册bean需要给入beanDefinition");

		// 校验给入的bean是否合法
		if (!beanDefinition.validate()) {
			throw new BeanDefinitionRegistException("名字为[" + beanName + "] 的bean定义不合法：" + beanDefinition);
		}

		//如果已存在bean定义就抛异常
		if (this.containsBeanDefinition(beanName)) {
			throw new BeanDefinitionRegistException(
					"名字为[" + beanName + "] 的bean定义已存在:" + this.getBeanDefinition(beanName));
		}

		//把bean定义放到Map里面
		this.beanDefintionMap.put(beanName, beanDefinition);
	}

	//根据bean的名字从Map里面获取bean定义
	@Override
	public BeanDefinition getBeanDefinition(String beanName) {
		return this.beanDefintionMap.get(beanName);
	}

	//根据bean的名字判断Map里面是否包含bean定义
	@Override
	public boolean containsBeanDefinition(String beanName) {

		return this.beanDefintionMap.containsKey(beanName);
	}

	//根据bean的名字获取bean实例,里面主要做的工作是创建bean实例和对bean实例进行初始化
	@Override
	public Object getBean(String name) throws Exception {
		return this.doGetBean(name);
	}

	//根据bean的名字获取bean实例,里面主要做的工作是创建bean实例和对bean实例进行初始化
	protected Object doGetBean(String beanName) throws Exception {
		//判断给入的bean名字不能为空
		Objects.requireNonNull(beanName, "beanName不能为空");

		//先从beanMap里面获取bean实例
		Object instance = beanMap.get(beanName);

		//如果beanMap里面已存在bean实例就直接返回,不需要走后面的流程了
		if (instance != null) {
			return instance;
		}

		//从beanDefintionMap里面获取bean定义信息
		BeanDefinition bd = this.getBeanDefinition(beanName);
		//bean定义信息不能为空
		Objects.requireNonNull(bd, "beanDefinition不能为空");

		//获取bean的类型
		Class<?> type = bd.getBeanClass();
		if (type != null) {
			//如果bean的类型不为空,并且工厂方法名为空,说明是使用构造方法的方式来创建bean实例
			if (StringUtils.isBlank(bd.getFactoryMethodName())) {
				// 构造方法来构造对象
				instance = this.createInstanceByConstructor(bd);
			} 
			//如果bean的类型不为空,并且工厂方法名不为空,说明是使用静态工厂方法的方式来创建bean实例
			else {
				// 静态工厂方法
				instance = this.createInstanceByStaticFactoryMethod(bd);
			}
		} 
		//如果bean的类型为空,说明是使用工厂bean的方式来创建bean实例
		else {
			// 工厂bean方式来构造对象
			instance = this.createInstanceByFactoryBean(bd);
		}

		// 执行初始化方法
		this.doInit(bd, instance);

		//存放单例的bean到beanMap
		if (bd.isSingleton()) {
			beanMap.put(beanName, instance);
		}

		return instance;
	}

	// 构造方法来构造对象 反射
	private Object createInstanceByConstructor(BeanDefinition bd)
			throws InstantiationException, IllegalAccessException {
		try {
			//拿到bean的类型,然后调用newInstance通过反射来创建bean实例
			return bd.getBeanClass().newInstance();
		} catch (SecurityException e1) {
			logger.error("创建bean的实例异常,beanDefinition：" + bd, e1);
			throw e1;
		}
	}

	// 静态工厂方法 反射
	private Object createInstanceByStaticFactoryMethod(BeanDefinition bd) throws Exception {
		//拿到bean的类型
		Class<?> type = bd.getBeanClass();
		//通过静态工厂方法方法的名字getFactoryMethodName反射出bean的方法m创建bean实例
		Method m = type.getMethod(bd.getFactoryMethodName(), null);
		return m.invoke(type, null);
	}

	// 工厂bean方式来构造对象 反射
	private Object createInstanceByFactoryBean(BeanDefinition bd) throws Exception {

		//通过bean定义信息中工厂bean的名字获取工厂bean的实例
		Object factoryBean = this.doGetBean(bd.getFactoryBeanName());

		//通过bean定义信息中工厂方法的名字反射出工厂bean的方法m创建bean实例
		Method m = factoryBean.getClass().getMethod(bd.getFactoryMethodName(), null);
		return m.invoke(factoryBean, null);
	}

	//执行初始化方法
	private void doInit(BeanDefinition bd, Object instance) throws Exception {
		// 获取bean定义中的初始化方法,如果存在初始化方法就通过反射去执行初始化方法
		if (StringUtils.isNotBlank(bd.getInitMethodName())) {
			Method m = instance.getClass().getMethod(bd.getInitMethodName(), null);
			m.invoke(instance, null);
		}
	}

	//销毁
	@Override
	public void close() throws IOException {
		// 执行单例实例的销毁方法
		for (Entry<String, BeanDefinition> e : this.beanDefintionMap.entrySet()) {
			String beanName = e.getKey();
			BeanDefinition bd = e.getValue();

			//获取bean定义中的销毁方法,如果存在销毁方法就通过反射去执行销毁方法
			if (bd.isSingleton() && StringUtils.isNotBlank(bd.getDestroyMethodName())) {
				Object instance = this.beanMap.get(beanName);
				try {
					Method m = instance.getClass().getMethod(bd.getDestroyMethodName(), null);
					m.invoke(instance, null);
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e1) {
					logger.error("执行bean[" + beanName + "] " + bd + " 的 销毁方法异常！", e1);
				}
			}
		}
	}
}
