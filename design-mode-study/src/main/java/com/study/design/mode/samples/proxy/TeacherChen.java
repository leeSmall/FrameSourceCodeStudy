package com.study.design.mode.samples.proxy;

/**
 * 
 * @Description: 被代理的目标对象TeacherChen实现Boy
 * @author leeSamll
 * @date 2018年11月24日
 *
 */
public class TeacherChen implements Boy {

	public boolean dating(char cup) {
		if (cup == 'E') {
			System.out.println("这个女老板品德正好，可以约！");
			return true;
		}
		System.out.println("这个女老板品德不行，不可以约！");
		return false;
	}

	public void show() {
		System.out.println("开始进入拍摄模式。。。。。。。。");
	}

}
