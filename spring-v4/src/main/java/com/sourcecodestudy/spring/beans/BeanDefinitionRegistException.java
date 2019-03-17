package com.sourcecodestudy.spring.beans;

public class BeanDefinitionRegistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6056374114834139330L;

	public BeanDefinitionRegistException(String mess) {
		super(mess);
	}

	public BeanDefinitionRegistException(String mess, Throwable e) {
		super(mess, e);
	}
}
