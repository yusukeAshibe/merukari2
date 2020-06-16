package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 注文を操作するコントローラー.
 * 
 * @author ashibe
 *
 */
@Controller
@RequestMapping("/orderItem")
public class OrderItemController {

	@RequestMapping("/showCart")
	public String showCart() {
//		List<OrderItem> orderItemList = new ArrayList<>();
		return "cart.html";
	}

}
