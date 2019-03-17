package com.sourcecodestudy.spring.context;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @Description: 从指定的类路径下加载xml配置文件得到InputStream
 * 
 * 
 * @author leeSmall
 * @date 2018年12月7日
 *
 */
public class ClassPathResource implements Resource {

	//xml配置文件的路径
	private String path;
	
	//类
	private Class<?> clazz;
	
	//类加载器
	private ClassLoader classLoader;

	public ClassPathResource(String path) {
		this(path, null);
	}

	public ClassPathResource(String path, Class<?> clazz) {
		this(path, clazz, null);
	}

	public ClassPathResource(String path, Class<?> clazz, ClassLoader classLoader) {
		super();
		this.path = path;
		this.clazz = clazz;
		this.classLoader = classLoader;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public ClassLoader getClassLoader() {
		return classLoader;
	}

	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	//根据指定的xml配置文件的类路径加载xml得到InputStream
	@Override
	public InputStream getInputStream() throws IOException {
		if (StringUtils.isNotBlank(path)) {
			if (this.clazz != null) {
				return this.clazz.getResourceAsStream(path);
			}

			if (this.classLoader != null) {
				return this.classLoader.getResourceAsStream(path.startsWith("/") ? path.substring(1) : path);
			}

			return this.getClass().getResourceAsStream(path);
		}
		return null;
	}

	@Override
	public boolean exists() {
		if (StringUtils.isNotBlank(path)) {
			if (this.clazz != null) {
				return this.clazz.getResource(path) != null;
			}

			if (this.classLoader != null) {
				return this.classLoader.getResource(path.startsWith("/") ? path.substring(1) : path) != null;
			}

			return this.getClass().getResource(path) != null;
		}
		return false;
	}

	@Override
	public boolean isReadable() {
		return exists();
	}

	@Override
	public boolean isOpen() {
		return false;
	}

	@Override
	public File getFile() {
		return null;
	}

}
