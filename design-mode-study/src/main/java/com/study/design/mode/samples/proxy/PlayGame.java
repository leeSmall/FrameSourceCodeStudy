package com.study.design.mode.samples.proxy;

/**
 * 
 * @Description: 调用示例
 * @author leeSamll
 * @date 2018年11月24日
 *
 */
public class PlayGame {

	public static void main(String[] args) {
		System.out.println("----------------1.静态代理TeacherCang-----------------------");
		//创建土豪(使用者)、苍老师(目标对象)、tony(代理)三个对象
		TuHao th = new TuHao(1.7F);
		Girl tc = new TeacherCang();
		Tony tony = new Tony();
		//tony对苍老师进行代理
		tony.setGirl(tc);
		//土豪和tony约
		th.dating(tony);

		System.out.println("----------------2.JDK动态代理TeacherCang-----------------------");
		//生成代理类$Proxy0
		Girl tony1 = (Girl) TonyCompany.proxy(tc);
		//土豪直接和代理tony约
		th.dating(tony1);

		System.out.println("----------------3.JDK动态代理TeacherChen，横向纵向扩展：代理更多的类和方法-----------------------");
		//代理另外一个目标对象TeacherChen
		Boy tcc = new TeacherChen();
		//生成代理类$Proxy0
		Boy tony2 = (Boy) TonyCompany.proxy(tcc);
		//tony2约TeacherChen 纵向扩展：增强更多的方法
		System.out.println("----------------3.1 JDK动态代理TeacherChen，调用TeacherChen的dating方法-----------------------");
		tony2.dating('E');
		System.out.println("----------------3.2 JDK动态代理TeacherChen，调用TeacherChen的show方法-----------------------");
		tony2.show();

		ProxyUtils.generateClassFile(Boy.class, tony2.getClass().getName());
	}

}
