package com.example.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.domain.OrderItem;
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

		
		//
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
		System.out.println(orderHistoryList);

		if (ObjectUtils.isEmpty(orderHistoryList)) {
			model.addAttribute("history", "No order history");
		} else {

			for (Order order : orderHistoryList) {
				model.addAttribute("order", order);
				System.out.println(order);
			}
		}
		List<Order> orderList = orderService.orderHistoryFindByUserId(loginUser.getUser().getId());
		System.out.println(orderList);
		model.addAttribute("order2", orderList);
		model.addAttribute("deliveryTime");
		model.addAttribute("orderList", orderHistoryList);

		return "orderHistory.html";

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
		
		return "orderHistoryDetail.html";

	}

	@RequestMapping("/orderHistoryDetail")
	public String showOrderHistoryDetail(@AuthenticationPrincipal LoginUser loginUser, Model model, Integer id) {
		String userEmail = loginUser.getUser().getEmail();
		model.addAttribute("email", userEmail);

		
		Order order = orderService.orderHistoryDetail(id);//

		// 合計金額,個数の算出
		Double totalPrice = 0.0;
		Integer totalQuantity = 0;
		for (OrderItem orderItemList : order.getOrderItemList()) {
			Integer quantity = orderItemList.getQuantity();// 数量
			Double price = orderItemList.getItem().getPrice();// 個数
			totalPrice += price * quantity;
			totalQuantity += quantity;
		}

		//htmlの表示に利用
		model.addAttribute("totalPrice", order.getTotalPrice());
		model.addAttribute("totalQuantity", totalQuantity);
		model.addAttribute("order", order);
		model.addAttribute("orderItemList", order.getOrderItemList());
		Date date = new Date(order.getDeliveryTime().getTime());
		model.addAttribute("deliveryTime", date);

		return "orderHistoryDetail2.html";

	}

}
