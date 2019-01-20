package com.study.design.mode.service;

public abstract class Game {
	protected abstract void initialize();

	protected abstract void startPlay();

	protected abstract void endPlay();

	// 模板方法
	public final void play() {
		// 初始化游戏
		initialize();
		// 开始游戏
		startPlay();
		// 结束游戏
		endPlay();

	}
}