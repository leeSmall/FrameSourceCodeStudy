package com.sourcecodestudy.spring.context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.sourcecodestudy.spring.beans.BeanDefinitionRegistry;

/**
 * 
 * @Description: 
 * 加载xml配置文件得到Resource,
 * 然后bean定义读取器BeanDefinitionReader从Resource里面加载bean定义注册到bean工厂
 * 
 * @author leeSmall
 * @date 2018年12月7日
 *
 */
public class XmlApplicationContext extends AbstractApplicationContext {

	//加载xml配置文件的产物Resource
	private List<Resource> resources;

	//bean定义读取器
	private BeanDefinitionReader reader;

	public XmlApplicationContext(String... location) throws Throwable {
		super();
		//加载resources
		load(location);
		//bean定义读取器从Resource里面加载bean定义
		this.reader = new XmlBeanDefinitionReader((BeanDefinitionRegistry) this.beanFactory);
		reader.loadBeanDefintions((Resource[]) resources.toArray());
	}

	//根据不同的字符串创建不同的Resource对象 
	@Override
	public Resource getResource(String location) throws IOException {
		if (StringUtils.isNotBlank(location)) {
			if (location.startsWith(Resource.CLASS_PATH_PREFIX)) {
				return new ClassPathResource(location.substring(Resource.CLASS_PATH_PREFIX.length()));
			} else if (location.startsWith(Resource.File_SYSTEM_PREFIX)) {
				return new FileSystemResource(location.substring(Resource.File_SYSTEM_PREFIX.length()));
			} else {
				return new UrlResource(location);
			}
		}
		return null;
	}

	//加载不同的Resource
	private void load(String... location) throws IOException {
		if (resources == null) {
			resources = new ArrayList<>();
		}
		// 完成加载，创建好 Resource
		if (location != null && location.length > 0) {
			for (String lo : location) {
				Resource re = this.getResource(lo);
				if (re != null) {
					this.resources.add(re);
				}
			}
		}
	}
}
