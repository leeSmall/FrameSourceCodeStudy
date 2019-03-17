package com.sourcecodestudy.spring.beans;

public interface BeanFactoryAware extends Aware {

	void setBeanFactory(BeanFactory bf);
}
