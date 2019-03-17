package com.study.leesmall.spring.sample.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

//定义包含 Advice 方法的 Bean 类
public class AspectAdviceBean {

	public void before1() {
		System.out.println("----------- AspectAdviceBean before1 增强 ");
	}

	//JoinPoint看具体哪个方法被增强了,JoinPoint一定要放在第一个参数
	public void before2(JoinPoint jp) {
		System.out.println("----------- AspectAdviceBean before2 增强 for " + jp);
	}

	//调用被增强的方法时传参数
	//<aop:before method="before3" pointcut="execution(* com.study.leesmall.spring.sample.aop.*.do*(..)) 
	//and args(tk,..)" arg-names=""/>
	//args(tk,..)有两个意思,第一个意思是被增强的方法的第一个参数的类型要和before3的参数tk的类型一样
	//第二个意思是被增强的方法的第一个参数tk要赋值给before3的参数tk
	//arg-names="" 当不能确定方法参数的顺序时可以用这个参数指定arg-names="param1,param2"
	public void before3(String tk) {
		System.out.println("----------- AspectAdviceBean before3  增强  参数tk= " + tk);
	}

	//调用被增强的方法时传参数
	public void before4(String tk, int ti) {
		System.out.println("----------- AspectAdviceBean before4  增强  参数tk= " + tk + " ti=" + ti);
	}

	//ProceedingJoinPoint正在处理的方法
	public Object arround1(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("----------- AspectAdviceBean arround1 环绕-前增强 for " + pjp);
		Object ret = pjp.proceed();
		System.out.println("----------- AspectAdviceBean arround1 环绕-后增强 for " + pjp);
		return ret;
	}

	public Object arround2(ProceedingJoinPoint pjp, String name) throws Throwable {
		System.out.println("--------- AspectAdviceBean arround2 参数 name=" + name);
		System.out.println("----------- AspectAdviceBean arround2 环绕-前增强 for " + pjp);
		Object ret = pjp.proceed();
		System.out.println("----------- AspectAdviceBean arround2 环绕-后增强 for " + pjp);
		return ret;
	}

	public void afterReturning(Object retValue) {
		System.out.println("----------- AspectAdviceBean afterReturning 增强 , 返回值为： " + retValue);
	}

	public void afterThrowing(JoinPoint jp, Exception e) {
		System.out.println("----------- AspectAdviceBean afterThrowing 增强  for " + jp);
		System.out.println("----------- AspectAdviceBean afterThrowing 增强  异常 ：" + e);
	}

	public void after(JoinPoint jp) {
		System.out.println("----------- AspectAdviceBean after 增强  for " + jp);
	}

}
