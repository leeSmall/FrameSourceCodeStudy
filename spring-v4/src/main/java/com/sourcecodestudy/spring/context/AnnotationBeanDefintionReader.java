package com.sourcecodestudy.spring.context;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;

import org.apache.commons.lang3.StringUtils;

import com.sourcecodestudy.spring.beans.BeanDefinitionRegistry;
import com.sourcecodestudy.spring.beans.GenericBeanDefinition;
import com.sourcecodestudy.spring.context.config.annotation.Autowired;
import com.sourcecodestudy.spring.context.config.annotation.Component;

/**
 * 
 * @Description: 
 * 注解bean定义读取器,从Resource里面加载bean定义、创建bean定义、注册bean定义到bean工厂
 * 
 * @author leeSmall
 * @date 2018年12月7日
 *
 */
public class AnnotationBeanDefintionReader extends AbstractBeanDefinitionReader {

	public AnnotationBeanDefintionReader(BeanDefinitionRegistry registry) {
		super(registry);
	}

	//从Resource里面加载bean定义、创建bean定义、注册bean定义到bean工厂
	@Override
	public void loadBeanDefintions(Resource resource) throws Throwable {
		this.loadBeanDefintions(new Resource[] { resource });
	}

	//从Resource里面加载bean定义、创建bean定义、注册bean定义到bean工厂
	@Override
	public void loadBeanDefintions(Resource... resource) throws Throwable {
		if (resource != null && resource.length > 0) {
			for (Resource r : resource) {
				retriveAndRegistBeanDefinition(r);
			}
		}
	}

	//从Resource里面加载bean定义、创建bean定义、注册bean定义到bean工厂
	private void retriveAndRegistBeanDefinition(Resource resource) throws Throwable {
		if (resource != null && resource.getFile() != null) {
			String className = getClassNameFromFile(resource.getFile());
			try {
				//反射获取类上面的注解
				Class<?> clazz = Class.forName(className);
				Component component = clazz.getAnnotation(Component.class);
				//从注解里面获取bean定义信息、注册bean定义
				if (component != null) {// 标注了@Component注解
					//从注解里面获取bean定义信息
					GenericBeanDefinition bd = new GenericBeanDefinition();
					bd.setBeanClass(clazz);
					bd.setScope(component.scope());
					bd.setFactoryMethodName(component.factoryMethodName());
					bd.setFactoryBeanName(component.factoryBeanName());
					bd.setInitMethodName(component.initMethodName());
					bd.setDestroyMethodName(component.destroyMethodName());

					// 获得所有构造方法，在构造方法上找@Autowired注解，如有，将这个构造方法set到bd;
					this.handleConstructor(clazz, bd);

					// 处理工厂方法参数依赖
					if (StringUtils.isNotBlank(bd.getFactoryMethodName())) {
						this.handleFactoryMethodArgs(clazz, bd);
					}
					// 处理属性依赖
					this.handlePropertyDi(clazz, bd);

					String beanName = "".equals(component.value()) ? component.name() : null;
					if (StringUtils.isBlank(beanName)) {
						// TODO 应用名称生成规则生成beanName;
					}
					// 注册bean定义
					this.registry.registerBeanDefinition(beanName, bd);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private void handlePropertyDi(Class<?> clazz, GenericBeanDefinition bd) {
		// TODO Auto-generated method stub

	}

	private void handleFactoryMethodArgs(Class<?> clazz, GenericBeanDefinition bd) {
		// TODO Auto-generated method stub

	}

	private void handleConstructor(Class<?> clazz, GenericBeanDefinition bd) {
		// 获得所有构造方法，在构造方法上找@Autowired注解，如有，将这个构造方法set到bd;
		Constructor<?>[] cs = clazz.getConstructors();
		if (cs != null && cs.length > 0) {
			for (Constructor<?> c : cs) {
				if (c.getAnnotation(Autowired.class) != null) {
					bd.setConstructor(c);
					Parameter[] ps = c.getParameters();
					// TDDO 遍历获取参数上的注解，及创建参数依赖
					break;
				}
			}
		}
	}

	private int classPathAbsLength = AnnotationBeanDefintionReader.class.getResource("/").toString().length();

	private String getClassNameFromFile(File file) {
		String absPath = file.getAbsolutePath();
		String name = absPath.substring(classPathAbsLength + 1, absPath.indexOf('.'));
		return StringUtils.replace(name, File.separator, ".");
	}

}
