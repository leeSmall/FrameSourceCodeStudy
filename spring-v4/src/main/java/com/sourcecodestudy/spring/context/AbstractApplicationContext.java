package com.sourcecodestudy.spring.context;

import com.sourcecodestudy.spring.beans.BeanFactory;
import com.sourcecodestudy.spring.beans.BeanPostProcessor;
import com.sourcecodestudy.spring.beans.PreBuildBeanFactory;

/**
 * 
 * @Description: 抽象XmlApplicationContext还是AnnotationApplicationContext
 * 无论是XmlApplicationContext还是AnnotationApplicationContext都要
 * 使用BeanFactory和BeanDefinitionRegistry,所以对他们再进行一次抽象
 * 
 * @author leeSmall
 * @date 2018年12月7日
 *
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

	protected BeanFactory beanFactory;

	public AbstractApplicationContext() {
		super();
		this.beanFactory = new PreBuildBeanFactory();
	}

	//获取bean定义对象
	@Override
	public Object getBean(String name) throws Throwable {
		return beanFactory.getBean(name);
	}

	//注册bean定义信息到bean工厂
	@Override
	public void registerBeanPostProcessor(BeanPostProcessor bpp) {
		this.beanFactory.registerBeanPostProcessor(bpp);
	}

}
