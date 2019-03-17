package com.sourcecodestudy.spring.beans;

/**
 * 
 * @Description: 属性值依赖实体
 * @author leeSmall
 * @date 2018年12月1日
 *
 */
public class PropertyValue {

	private String name;

	private Object value;

	public PropertyValue(String name, Object value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
