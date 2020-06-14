package com.example.controller;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.service.ItemService;
import com.example.service.OrderService;

/**
 * 注文履歴を確認するコントローラー.
 * 
 * @author ashibe
 *
 */
@Controller
@RequestMapping("/orderHistory")
public class OrderHistoryController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private ItemService itemService;

	@RequestMapping("/showOrderHistory")
	public String showOrderHIstory(Model model, @AuthenticationPrincipal LoginUser loginUser) {

		// ユーザーのメルアドレスの表示
		String userEmail = loginUser.getUser().getEmail();
		model.addAttribute("email", userEmail);

		List<Order> orderHistoryList = orderService.showOrderHistory(loginUser.getUser().getId());

//		Double price=0.0;
//		Integer quantity=0;
//		Double total=0.0;
//		for (Order orderHistory : orderHistoryList) {
//			for(OrderItem orderList : orderHistory.getOrderItemList()) {
//				price=orderList.getItem().getPrice();
//				quantity=orderList.getQuantity();
//				total=price*quantity;
//				model.addAttribute("total",total);
//				System.out.println(total);
//				
//			}
//		}

		if (ObjectUtils.isEmpty(orderHistoryList)) {
			model.addAttribute("history", "No order history");
		}

		model.addAttribute("deliveryTime");
		model.addAttribute("orderList", orderHistoryList);

		return "orderHistory";

	}

	/**
	 * 詳細画面遷移.
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/showItemDetail")
	public String showItemDetail(Integer id, Model model, @AuthenticationPrincipal LoginUser loginUser) {
		String userEmail = loginUser.getUser().getEmail();
		model.addAttribute("email", userEmail);
		Item item = itemService.showDetail(id);
		model.addAttribute("item", item);
		// return "detail.html";
		// 一応作成したorderhistory用html
		return "orderHistoryDetail.html";

	}

}
