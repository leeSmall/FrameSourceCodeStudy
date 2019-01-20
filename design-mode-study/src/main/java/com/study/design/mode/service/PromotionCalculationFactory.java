package com.study.design.mode.service;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class PromotionCalculationFactory {

	private Map<String, PromotionCalculation> maps;

	public PromotionCalculation getPromotionCalculation(String promotion) {
		PromotionCalculation prom = maps.get(promotion);
		if (prom == null) {
			// 从配置的地方加载
			prom = getFromDb(promotion);
			if (prom != null)
				maps.put(promotion, prom);
		}

		return prom;
	}

	public void init() {
		// 第一次将所有的促销策略都加载到Map中
	}

	private PromotionCalculation getFromDb(String promotion) {
		// 从数据库中取到对应的类名
		//配置的格式： promotion1=com.study.dn.promotion.calculation.Promotion1
		String className = 从数据库（或其他配置源）中获得;
		//
		Class c = Class.forName(className);

		// 实例化
		
		// 返回
	}
}
