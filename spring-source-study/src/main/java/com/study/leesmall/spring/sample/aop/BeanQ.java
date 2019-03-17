package com.study.leesmall.spring.sample.aop;

//被增强的目标对象
public class BeanQ {

	public void do1(String task, int time) {
		System.out.println("-------------do1 do " + task + " time:" + time);
	}

	public String service1(String name) {
		System.out.println("-------------servce1 do " + name);
		return name;
	}

	public String service2(String name) {
		System.out.println("-------------servce2 do " + name);
		/*if (!"s1".equals(name)) {
			throw new IllegalArgumentException("参数 name ！= s1, name=" + name);
		}*/

		return name + " hello!";
	}
}
