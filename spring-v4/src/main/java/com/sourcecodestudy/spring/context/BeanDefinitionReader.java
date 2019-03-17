package com.sourcecodestudy.spring.context;

/**
 * 
 * @Description: bean定义读取器接口
 * 
 * @author leeSmall
 * @date 2018年12月7日
 *
 */
public interface BeanDefinitionReader {

	//加载单个bean定义
	void loadBeanDefintions(Resource resource) throws Throwable;

	//加载多个bean定义
	void loadBeanDefintions(Resource... resource) throws Throwable;
}
