package com.study.design.mode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.design.mode.entity.Order;
import com.study.design.mode.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	/**
	 * 计算订单的促销金额
	 */
	@RequestMapping("prepare")
	public Order prepareOrder(Order order, String... promotion) {

		return this.orderService.prepareOrder(order, promotion);
	}
}
