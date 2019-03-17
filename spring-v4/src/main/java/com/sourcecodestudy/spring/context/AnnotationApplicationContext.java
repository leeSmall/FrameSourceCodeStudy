package com.sourcecodestudy.spring.context;

import java.io.IOException;

import com.sourcecodestudy.spring.beans.BeanDefinitionRegistry;

/**
 * 
 * @Description: 
 * 扫描指定包下的类(包含子孙包下的类),得到Resource
 * 通过反射获取bean定义信息、创建bean定义对象、注册bean定义对象到bean工厂
 * 
 * @author leeSmall
 * @date 2018年12月7日
 *
 */
public class AnnotationApplicationContext extends AbstractApplicationContext {

	private ClassPathBeanDefinitionScanner scanner;

	public AnnotationApplicationContext(String... basePackages) throws Throwable {
		//扫描指定包下的类(包含子孙包下的类),
		//通过反射获取bean定义信息、创建bean定义对象、注册bean定义对象到bean工厂
		scanner = new ClassPathBeanDefinitionScanner((BeanDefinitionRegistry) this.beanFactory);
		scanner.scan(basePackages);
	}

	@Override
	public Resource getResource(String location) throws IOException {
		return null;
	}

}
