package com.study.design.mode.samples.command;

/**
 * 
 * @Description: 命令模式
 * @author liguangsheng
 * @date 2018年11月25日
 *
 */
public class Receiver {

	//存放具体的命令实现
	private Map<String,Command> commands;
	
	//把具体的命令和对应的实现加入commands
	public void register(String strCommand,Command command) {
		commands.put(strCommand,command);
	}
	
	//使用者调用receive方法传入命令去执行
	public void receive(String command) {
		Command commandObj = commands.get(command);
		if(null != commandObj) {
			commandObj.exceute();
			return;
		}
		System.out.println("不支持此命令" + command);
	}
}
