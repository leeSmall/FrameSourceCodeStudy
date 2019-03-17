package com.sourcecodestudy.spring.beans;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
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

	//记录正在构造的bean
	private ThreadLocal<Set<String>> buildingBeans = new ThreadLocal<>();

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
	public Object getBean(String name) throws Exception {
		return this.doGetBean(name);
	}

	protected Object doGetBean(String beanName) throws Exception {
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

		// 检测循环依赖 因为如果已bean正在构造，这时发现有其他的bean依赖它，此时它又没有创建好久需要抛异常了
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

		// 执行初始化方法
		this.doInit(bd, instance);

		if (bd.isSingleton()) {
			beanMap.put(beanName, instance);
		}

		return instance;
	}

	//属性依赖实现
	private void setPropertyDIValues(BeanDefinition bd, Object instance) throws Exception {
		//bean定义中没有属性依赖就直接返回
		if (CollectionUtils.isEmpty(bd.getPropertyValues())) {
			return;
		}
		//如果bean定义中有属性依赖就设置值,设置方式和构造参数的设置一样
		for (PropertyValue pv : bd.getPropertyValues()) {
			//属性依赖没有给名字就直接跳过
			if (StringUtils.isBlank(pv.getName())) {
				continue;
			}
			//获取类对象
			Class<?> clazz = instance.getClass();
			//通过属性名获取类对象里面声明的字段
			Field p = clazz.getDeclaredField(pv.getName());

			//设置字段可访问
			p.setAccessible(true);

			//把属性值转化为真正的值,和构造参数一样
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

			//把真正的值v设置到属性p里面 即属性p依赖的值v
			p.set(instance, v);

		}
	}

	// 构造方法来构造对象
	private Object createInstanceByConstructor(BeanDefinition bd) throws Exception {
		try {
			//获取bean定义中的构造参数
			Object[] args = this.getConstructorArgumentValues(bd);
			//如果构造参数为空就使用无参构造函数
			if (args == null) {
				return bd.getBeanClass().newInstance();
			} 
			//查找有参构造函数并返回
			else {
				// 决定构造方法
				return this.determineConstructor(bd, args).newInstance(args);
			}
		} catch (SecurityException e1) {
			logger.error("创建bean的实例异常,beanDefinition：" + bd, e1);
			throw e1;
		}
	}

	//把bean定义中的构造参数引用转为真实的值
	private Object[] getConstructorArgumentValues(BeanDefinition bd) throws Exception {

		return this.getRealValues(bd.getConstructorArgumentValues());

	}

	//把bean定义中的构造参数引用转为真实的值
	private Object[] getRealValues(List<?> defs) throws Exception {
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

	//查找构造方法的方法
	private Constructor<?> determineConstructor(BeanDefinition bd, Object[] args) throws Exception {

		Constructor<?> ct = null;

		//如果参数为空,则返回无参的构造方法
		if (args == null) {
			return bd.getBeanClass().getConstructor(null);
		}

		// 对于原型bean,从第二次开始获取bean实例时，可直接获得第一次缓存的构造方法。
		ct = bd.getConstructor();
		if (ct != null) {
			return ct;
		}

		// 先根据参数类型获取精确匹配的构造方法
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

		//如果根据参数类型进行精确批次查找没有找到构造方法,就获得所有的构造方法遍历，通过参数数量过滤，再比对形参类型与实参类型
		if (ct == null) {

			// 没有精确参数类型匹配的，则遍历匹配所有的构造方法
			// 判断逻辑：先判断参数数量，再依次比对形参类型与实参类型
			outer: for (Constructor<?> ct0 : bd.getBeanClass().getConstructors()) {
				Class<?>[] paramterTypes = ct0.getParameterTypes();
				if (paramterTypes.length == args.length) {
					for (int i = 0; i < paramterTypes.length; i++) {
						//isAssignableFrom方法表示是否可以把args[i].getClass()赋值给paramterTypes[i]
						if (!paramterTypes[i].isAssignableFrom(args[i].getClass())) {
							continue outer;
						}
					}

					ct = ct0;
					break outer;
				}
			}
		}

		//如果找到构造方法了，并且是原型的就缓存起来
		if (ct != null) {
			// 对于原型bean,可以缓存找到的构造方法，方便下次构造实例对象。在BeanDefinfition中获取设置所用构造方法的方法。
			// 同时在上面增加从beanDefinition中获取的逻辑。
			if (bd.isPrototype()) {
				bd.setConstructor(ct);
			}
			return ct;
		} else {
			throw new Exception("不存在对应的构造方法！" + bd);
		}
	}

	//查找工厂方法的方法
	private Method determineFactoryMethod(BeanDefinition bd, Object[] args, Class<?> type) throws Exception {
		if (type == null) {
			type = bd.getBeanClass();
		}

		//获取bean定义中国工厂方法的名字
		String methodName = bd.getFactoryMethodName();

		//如果参数为空就返回无参的方法
		if (args == null) {
			return type.getMethod(methodName, null);
		}

		Method m = null;
		// 对于原型bean,从第二次开始获取bean实例时，可直接获得第一次缓存的构造方法。
		m = bd.getFactoryMethod();
		if (m != null) {
			return m;
		}

		// 先根据参数类型获取精确匹配的方法
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

		//如果根据参数类型进行精确批次查找没有找到工厂方法,就获得所有的构造方法遍历，通过参数数量过滤，再比对形参类型与实参类型
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
						//isAssignableFrom方法表示是否可以把args[i].getClass()赋值给paramterTypes[i]
						if (!paramterTypes[i].isAssignableFrom(args[i].getClass())) {
							continue outer;
						}
					}

					m = m0;
					break outer;
				}
			}
		}

		//如果找到构造方法了，并且是原型的就缓存起来
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
	private Object createInstanceByStaticFactoryMethod(BeanDefinition bd) throws Exception {

		Class<?> type = bd.getBeanClass();
		Object[] realArgs = this.getRealValues(bd.getConstructorArgumentValues());
		Method m = this.determineFactoryMethod(bd, realArgs, null);
		return m.invoke(type, realArgs);
	}

	// 工厂bean方式来构造对象
	private Object createInstanceByFactoryBean(BeanDefinition bd) throws Exception {

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
