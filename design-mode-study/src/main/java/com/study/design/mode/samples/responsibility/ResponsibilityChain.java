package com.study.design.mode.samples.responsibility;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Description: 责任链，所有的责任加到责任链里面进行处理
 * @author leeSamll
 * @date 2018年11月25日
 *
 */
public class ResponsibilityChain {

	//存放具体的责任逻辑
	private List<Responsibility> responsibilitys;

	private int index = 0;

	public ResponsibilityChain() {
		this.responsibilitys = new ArrayList<>();
	}

	//顺序调用加入的责任逻辑，一个处理完以后交给下一个继续处理，下一个处理完以后会通过this回调process看是否有下一个继续处理
	public void process(Request request) {
		if (this.index < this.responsibilitys.size()) {
			this.responsibilitys.get(index++).process(request, this);
		}
	}

	//加入具体的责任逻辑
	public void register(Responsibility res) {
		this.responsibilitys.add(res);
	}
}
