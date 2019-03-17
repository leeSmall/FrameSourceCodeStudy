package com.study.leesmall.spring.ext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
public class MyBeanDefinitonRegistryPostProcessor2 implements BeanDefinitionRegistryPostProcessor, Ordered {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("--- MyBeanDefinitonRegistryPostProcessor2.postProcessBeanFactory 被执行了。");

	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		System.out.println("--- MyBeanDefinitonRegistryPostProcessor2.postProcessBeanDefinitionRegistry 被执行了。");
	}

	@Override
	public int getOrder() {
		return 0;
	}

}
