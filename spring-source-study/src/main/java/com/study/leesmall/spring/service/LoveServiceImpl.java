package com.study.leesmall.spring.service;

import org.springframework.stereotype.Component;

@Component
public class LoveServiceImpl implements LoveService {

	private CombatService combatService;

	@Override
	public void doLove() {
		if (combatService != null) {
			combatService.doInit();
			combatService.combating();
			combatService.doClean();
		} else {
			System.out.println("没有 combatService，不能doLove!!");
		}
	}

	public CombatService getCombatService() {
		return combatService;
	}

	public void setCombatService(CombatService combatService) {
		this.combatService = combatService;
	}

}
