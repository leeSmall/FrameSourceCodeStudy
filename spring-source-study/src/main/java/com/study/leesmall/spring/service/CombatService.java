package com.study.leesmall.spring.service;

public class CombatService {
	// 战斗时长
	private int time;

	public CombatService(int time) {
		super();
		this.time = time;
	}

	public boolean doInit() {
		System.out.println("-----为战斗精心准备.......");
		return true;
	}

	public void combating() {
		System.out.println("-----战斗中....时长" + this.time + ",  好过瘾！");
	}

	public void doClean() {
		System.out.println("-----还得打扫战场，千万小心，不能留下痕迹！");
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
}
