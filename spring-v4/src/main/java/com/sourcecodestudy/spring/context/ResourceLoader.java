package com.sourcecodestudy.spring.context;

import java.io.IOException;

/**
 * 
 * @Description: 加载xml配置文件的行为ResourceLoader接口 在ApplicationContext里面实现
 * 				   工厂模式:根据不同的字符串创建不同的Resource对象 
 * 
 * @author leeSmall
 * @date 2018年12月7日
 *
 */
public interface ResourceLoader {

	Resource getResource(String location) throws IOException;
}
