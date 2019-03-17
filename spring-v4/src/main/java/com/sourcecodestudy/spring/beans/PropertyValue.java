package com.sourcecodestudy.spring.beans;

/**
 * 属性值依赖定义
 * 
 * @author 动脑学院.Mike老师 QQ:3266399810
 *
 *         VIP课程咨询加可可老师 QQ：2729772006
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
