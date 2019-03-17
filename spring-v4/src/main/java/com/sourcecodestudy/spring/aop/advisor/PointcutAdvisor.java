package com.sourcecodestudy.spring.aop.advisor;

import com.sourcecodestudy.spring.aop.pointcut.Pointcut;

public interface PointcutAdvisor extends Advisor {

	Pointcut getPointcut();
}
