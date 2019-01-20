package com.study.design.mode.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.design.mode.entity.Order;

@Service
public class OrderService {
	@Autowired
	private PromotionCalculationFactory promotionCalculationFactory;

	public Order prepareOrder(Order order, String... promotion) {

		/*
		 * for (String p : promotion) { order =
		 * promotionCalculationFactory.getPromotionCalculation(p).calculate(
		 * order); }
		 */
		return order;
	}
}
