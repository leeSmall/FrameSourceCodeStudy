package com.study.design.mode.samples.proxy;

/**
 * 
 * @Description: 被代理的目标对象TeacherCang实现Girl
 * @author leeSamll
 * @date 2018年11月24日
 *
 */
public class TeacherCang implements Girl {
	public boolean dating(float length) {
		if (length >= 1.7F) {
			System.out.println("身高可以，可以约！");
			return true;
		}
		System.out.println("身高不可以，不可约！");
		return false;
	}
}
