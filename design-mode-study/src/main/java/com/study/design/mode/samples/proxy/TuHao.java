package com.study.design.mode.samples.proxy;

/**
 * 
 * @Description: 使用者
 * @author leeSamll
 * @date 2018年11月24日
 *
 */
public class TuHao {

	private float length;

	public TuHao(float length) {
		super();
		this.length = length;
	}

	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	//约会
	public void dating(Girl g) {
		g.dating(length);
	}

}
