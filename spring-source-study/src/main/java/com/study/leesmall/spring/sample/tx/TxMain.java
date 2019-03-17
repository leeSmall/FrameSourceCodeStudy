package com.study.leesmall.spring.sample.tx;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.study.leesmall.spring.sample.tx.entity.User;
import com.study.leesmall.spring.sample.tx.service.UserService;

@Configuration
@ComponentScan("com.study.leesmall.spring.sample.tx")
@ImportResource("classpath:com/study/leesmall/spring/sample/tx/application.xml")
@EnableTransactionManagement
public class TxMain {

	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TxMain.class);) {
			User user = new User();
			user.setId("1234565");
			user.setUserName("leesmall-666666666");

			UserService userService = context.getBean(UserService.class);
			userService.insertUser(user);
		}
	}
}
