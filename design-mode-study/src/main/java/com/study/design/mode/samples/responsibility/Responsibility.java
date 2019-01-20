package com.study.design.mode.samples.responsibility;

/**
 * 
 * @Description: 责任接口
 * @author leeSamll
 * @date 2018年11月25日
 *
 */
public interface Responsibility {

	void process(Request request, ResponsibilityChain chain);
}
