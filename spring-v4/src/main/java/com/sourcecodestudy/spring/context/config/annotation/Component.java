package com.sourcecodestudy.spring.context.config.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.sourcecodestudy.spring.beans.BeanDefinition;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {

	String value() default "";

	String name() default "";

	String scope() default BeanDefinition.SCOPE_SINGLETION;

	String factoryMethodName() default "";

	String factoryBeanName() default "";

	String initMethodName() default "";

	String destroyMethodName() default "";
}
