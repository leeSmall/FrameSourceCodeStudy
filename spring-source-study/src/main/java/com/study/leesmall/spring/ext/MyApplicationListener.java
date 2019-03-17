package com.study.leesmall.spring.ext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

//如果你想对容器工作过程中发生的节点事件进行一些处理，比如容器要刷新、容器要关闭了，那么你就可以实现ApplicationListener
@Component
public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		System.out.println("-----收到应用事件：" + event);
	}
}
