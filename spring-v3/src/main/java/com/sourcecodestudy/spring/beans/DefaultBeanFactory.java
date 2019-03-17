package com.sourcecodestudy.spring.beans;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DefaultBeanFactory implements BeanFactory, BeanDefinitionRegistry, Closeable {

	private final Log logger = LogFactory.getLog(getClass());

	private Map<String, BeanDefinition> beanDefintionMap = new ConcurrentHashMap<>(256);

	private Map<String, Object> beanMap = new ConcurrentHashMap<>(256);

	private ThreadLocal<Set<String>> buildingBeans = new ThreadLocal<>();

	private List<BeanPostProcessor> beanPostProcessors = Collections.synchronizedList(new ArrayList<>());

	@Override
	public void registerBeanPostProcessor(BeanPostProcessor bpp) {
		this.beanPostProcessors.add(bpp);
		if (bpp instanceof BeanFactoryAware) {
			((BeanFactoryAware) bpp).setBeanFactory(this);
		}
	}

	@Override
	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition)
			throws BeanDefinitionRegistException {
		Objects.requireNonNull(beanName, "注册bean需要给入beanName");
		Objects.requireNonNull(beanDefinition, "注册bean需要给入beanDefinition");

		// 校验给入的bean是否合法
		if (!beanDefinition.validate()) {
			throw new BeanDefinitionRegistException("名字为[" + beanName + "] 的bean定义不合法：" + beanDefinition);
		}

		if (this.containsBeanDefinition(beanName)) {
			throw new BeanDefinitionRegistException(
					"名字为[" + beanName + "] 的bean定义已存在:" + this.getBeanDefinition(beanName));
		}

		this.beanDefintionMap.put(beanName, beanDefinition);
	}

	@Override
	public BeanDefinition getBeanDefinition(String beanName) {
		return this.beanDefintionMap.get(beanName);
	}

	@Override
	public boolean containsBeanDefinition(String beanName) {

		return this.beanDefintionMap.containsKey(beanName);
	}

	@Override
	public Object getBean(String name) throws Throwable {
		return this.doGetBean(name);
	}

	protected Object doGetBean(String beanName) throws Throwable {
		Objects.requireNonNull(beanName, "beanName不能为空");

		Object instance = beanMap.get(beanName);

		if (instance != null) {
			return instance;
		}

		BeanDefinition bd = this.getBeanDefinition(beanName);
		Objects.requireNonNull(bd, "不存在name为：" + beanName + "beean 定义！");

		// 记录正在创建的Bean
		Set<String> ingBeans = this.buildingBeans.get();
		if (ingBeans == null) {
			ingBeans = new HashSet<>();
			this.buildingBeans.set(ingBeans);
		}

		// 检测循环依赖
		if (ingBeans.contains(beanName)) {
			throw new Exception(beanName + " 循环依赖！" + ingBeans);
		}

		// 记录正在创建的Bean
		ingBeans.add(beanName);

		Class<?> type = bd.getBeanClass();
		if (type != null) {
			if (StringUtils.isBlank(bd.getFactoryMethodName())) {
				// 构造方法来构造对象
				instance = this.createInstanceByConstructor(bd);
			} else {
				// 静态工厂方法
				instance = this.createInstanceByStaticFactoryMethod(bd);
			}
		} else {
			// 工厂bean方式来构造对象
			instance = this.createInstanceByFactoryBean(bd);
		}

		// 创建好实例后，移除创建中记录
		ingBeans.remove(beanName);

		// 给入属性依赖
		this.setPropertyDIValues(bd, instance);

		// 应用bean初始化前的处理
		instance = this.applyPostProcessBeforeInitialization(instance, beanName);

		// 执行初始化方法
		this.doInit(bd, instance);

		// 应用bean初始化后的处理
		instance = this.applyPostProcessAfterInitialization(instance, beanName);

		if (bd.isSingleton()) {
			beanMap.put(beanName, instance);
		}

		return instance;
	}

	// 应用bean初始化前的处理
	private Object applyPostProcessBeforeInitialization(Object bean, String beanName) throws Throwable {
		for (BeanPostProcessor bpp : this.beanPostProcessors) {
			bean = bpp.postProcessBeforeInitialization(bean, beanName);
		}
		return bean;
	}

	// 应用bean初始化后的处理
	private Object applyPostProcessAfterInitialization(Object bean, String beanName) throws Throwable {
		for (BeanPostProcessor bpp : this.beanPostProcessors) {
			bean = bpp.postProcessAfterInitialization(bean, beanName);
		}
		return bean;
	}

	private void setPropertyDIValues(BeanDefinition bd, Object instance) throws Throwable {
		if (CollectionUtils.isEmpty(bd.getPropertyValues())) {
			return;
		}
		for (PropertyValue pv : bd.getPropertyValues()) {
			if (StringUtils.isBlank(pv.getName())) {
				continue;
			}
			Class<?> clazz = instance.getClass();
			Field p = clazz.getDeclaredField(pv.getName());

			p.setAccessible(true);

			Object rv = pv.getValue();
			Object v = null;
			if (rv == null) {
				v = null;
			} else if (rv instanceof BeanReference) {
				v = this.doGetBean(((BeanReference) rv).getBeanName());
			} else if (rv instanceof Object[]) {
				// TODO 处理集合中的bean引用
			} else if (rv instanceof Collection) {
				// TODO 处理集合中的bean引用
			} else if (rv instanceof Properties) {
				// TODO 处理properties中的bean引用
			} else if (rv instanceof Map) {
				// TODO 处理Map中的bean引用
			} else {
				v = rv;
			}

			p.set(instance, v);

		}
	}

	// 构造方法来构造对象
	private Object createInstanceByConstructor(BeanDefinition bd) throws Throwable {
		try {
			Object[] args = this.getConstructorArgumentValues(bd);
			if (args == null) {
				return bd.getBeanClass().newInstance();
			} else {
				bd.setConstructorArgumentRealValues(args);
				// 决定构造方法
				Constructor<?> constructor = this.determineConstructor(bd, args);
				// 缓存构造函数由determineConstructor 中移到了这里，无论原型否都缓存，因为后面AOP需要用
				bd.setConstructor(constructor);
				return constructor.newInstance(args);
			}
		} catch (SecurityException e1) {
			logger.error("创建bean的实例异常,beanDefinition：" + bd, e1);
			throw e1;
		}
	}

	private Object[] getConstructorArgumentValues(BeanDefinition bd) throws Throwable {

		return this.getRealValues(bd.getConstructorArgumentValues());

	}

	private Object[] getRealValues(List<?> defs) throws Throwable {
		if (CollectionUtils.isEmpty(defs)) {
			return null;
		}

		Object[] values = new Object[defs.size()];
		int i = 0;
		Object v = null;
		for (Object rv : defs) {
			if (rv == null) {
				v = null;
			} else if (rv instanceof BeanReference) {
				v = this.doGetBean(((BeanReference) rv).getBeanName());
			} else if (rv instanceof Object[]) {
				// TODO 处理集合中的bean引用
			} else if (rv instanceof Collection) {
				// TODO 处理集合中的bean引用
			} else if (rv instanceof Properties) {
				// TODO 处理properties中的bean引用
			} else if (rv instanceof Map) {
				// TODO 处理Map中的bean引用
			} else {
				v = rv;
			}

			values[i++] = v;
		}

		return values;
	}

	private Constructor<?> determineConstructor(BeanDefinition bd, Object[] args) throws Exception {

		Constructor<?> ct = null;

		if (args == null) {
			return bd.getBeanClass().getConstructor(null);
		}

		// 对于原型bean,从第二次开始获取bean实例时，可直接获得第一次缓存的构造方法。
		ct = bd.getConstructor();
		if (ct != null) {
			return ct;
		}

		// 根据参数类型获取精确匹配的构造方法
		Class<?>[] paramTypes = new Class[args.length];
		int j = 0;
		for (Object p : args) {
			paramTypes[j++] = p.getClass();
		}
		try {
			ct = bd.getBeanClass().getConstructor(paramTypes);
		} catch (Exception e) {
			// 这个异常不需要处理
		}

		if (ct == null) {

			// 没有精确参数类型匹配的，则遍历匹配所有的构造方法
			// 判断逻辑：先判断参数数量，再依次比对形参类型与实参类型
			outer: for (Constructor<?> ct0 : bd.getBeanClass().getConstructors()) {
				Class<?>[] paramterTypes = ct0.getParameterTypes();
				if (paramterTypes.length == args.length) {
					for (int i = 0; i < paramterTypes.length; i++) {
						if (!paramterTypes[i].isAssignableFrom(args[i].getClass())) {
							continue outer;
						}
					}

					ct = ct0;
					break outer;
				}
			}
		}

		if (ct != null) {
			return ct;
		} else {
			throw new Exception("不存在对应的构造方法！" + bd);
		}
	}

	private Method determineFactoryMethod(BeanDefinition bd, Object[] args, Class<?> type) throws Exception {
		if (type == null) {
			type = bd.getBeanClass();
		}

		String methodName = bd.getFactoryMethodName();

		if (args == null) {
			return type.getMethod(methodName, null);
		}

		Method m = null;
		// 对于原型bean,从第二次开始获取bean实例时，可直接获得第一次缓存的构造方法。
		m = bd.getFactoryMethod();
		if (m != null) {
			return m;
		}

		// 根据参数类型获取精确匹配的方法
		Class[] paramTypes = new Class[args.length];
		int j = 0;
		for (Object p : args) {
			paramTypes[j++] = p.getClass();
		}
		try {
			m = type.getMethod(methodName, paramTypes);
		} catch (Exception e) {
			// 这个异常不需要处理
		}

		if (m == null) {

			// 没有精确参数类型匹配的，则遍历匹配所有的方法
			// 判断逻辑：先判断参数数量，再依次比对形参类型与实参类型
			outer: for (Method m0 : type.getMethods()) {
				if (!m0.getName().equals(methodName)) {
					continue;
				}
				Class<?>[] paramterTypes = m.getParameterTypes();
				if (paramterTypes.length == args.length) {
					for (int i = 0; i < paramterTypes.length; i++) {
						if (!paramterTypes[i].isAssignableFrom(args[i].getClass())) {
							continue outer;
						}
					}

					m = m0;
					break outer;
				}
			}
		}

		if (m != null) {
			// 对于原型bean,可以缓存找到的方法，方便下次构造实例对象。在BeanDefinfition中获取设置所用方法的方法。
			// 同时在上面增加从beanDefinition中获取的逻辑。
			if (bd.isPrototype()) {
				bd.setFactoryMethod(m);
			}
			return m;
		} else {
			throw new Exception("不存在对应的构造方法！" + bd);
		}
	}

	// 静态工厂方法
	private Object createInstanceByStaticFactoryMethod(BeanDefinition bd) throws Throwable {

		Class<?> type = bd.getBeanClass();
		Object[] realArgs = this.getRealValues(bd.getConstructorArgumentValues());
		Method m = this.determineFactoryMethod(bd, realArgs, null);
		return m.invoke(type, realArgs);
	}

	// 工厂bean方式来构造对象
	private Object createInstanceByFactoryBean(BeanDefinition bd) throws Throwable {

		Object factoryBean = this.doGetBean(bd.getFactoryBeanName());
		Object[] realArgs = this.getRealValues(bd.getConstructorArgumentValues());
		Method m = this.determineFactoryMethod(bd, realArgs, factoryBean.getClass());

		return m.invoke(factoryBean, realArgs);
	}

	/**
	 * 执行初始化方法
	 * 
	 * @param bd
	 * @param instance
	 * @throws Exception
	 */
	private void doInit(BeanDefinition bd, Object instance) throws Exception {
		// 执行初始化方法
		if (StringUtils.isNotBlank(bd.getInitMethodName())) {
			Method m = instance.getClass().getMethod(bd.getInitMethodName(), null);
			m.invoke(instance, null);
		}
	}

	@Override
	public void close() throws IOException {
		// 执行单例实例的销毁方法
		for (Entry<String, BeanDefinition> e : this.beanDefintionMap.entrySet()) {
			String beanName = e.getKey();
			BeanDefinition bd = e.getValue();

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
