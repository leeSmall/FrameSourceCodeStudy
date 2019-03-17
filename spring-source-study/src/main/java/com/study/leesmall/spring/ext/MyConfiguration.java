package com.study.leesmall.spring.ext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

//国际化 给入messageSource的bean实例到bean工厂
@Configuration
@PropertySource("classpath:/application.properties")
public class MyConfiguration {

	@Bean("messageSource")
	public ReloadableResourceBundleMessageSource getReloadableResourceBundleMessageSource() {
		ReloadableResourceBundleMessageSource rms = new ReloadableResourceBundleMessageSource();
		rms.setBasename("message");
		return rms;
	}
}
