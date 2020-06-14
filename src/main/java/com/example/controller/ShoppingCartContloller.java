package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.form.AddShoppingCartForm;
import com.example.service.OrderItemService;
import com.example.service.OrderService;

/**
 * ショッピングカートを操作するコントローラー.
 * 
 * @author ashibe
 *
 */
@Controller
@RequestMapping("/cart")
public class ShoppingCartContloller {

	@Autowired
	private OrderItemService orderItemService;

	@Autowired
	private OrderService orderService;

	@ModelAttribute
	private AddShoppingCartForm setUpShoppingCartForm() {
		return new AddShoppingCartForm();
	}

	/**
	 * カートに商品の追加（orderテーブルに追加）、最初に商品を追加するときはorder情報も作成.
	 * 
	 * @param form
	 * @param loginUser
	 * @return
	 */
	@RequestMapping("/addcart")
	public String addcart(AddShoppingCartForm form, @AuthenticationPrincipal LoginUser loginUser, Model model,
			String orderItemId, String quantity) {

		// ユーザーのメルアドレスの表示
		String userEmail = loginUser.getUser().getEmail();
		model.addAttribute("email", userEmail);

		// 既にオーダーがあるか確認
		Order order = orderService.FindOrderId(loginUser.getUser().getId());

		// （なければオーダー作成）
		Integer orderId = null;
		if (StringUtils.isEmpty(order)) {
			order = new Order();
			order.setUserId(loginUser.getUser().getId());
			order.setStatus(0);
			order.setTotalPrice(0);
			orderId = orderService.insert(order);// IDの新規作成
//			orderService.insert(order);// IDの新規作成
		} else {
			orderId = order.getId(); // あるならオーダーIdを取得
		}

		// 注文された商品情報のインサート
		OrderItem orderItem = new OrderItem();

		orderItem.setQuantity(Integer.parseInt(quantity));
		orderItem.setItemId(Integer.parseInt(form.getItemId()));
		orderItem.setOrderId(orderId);// オーダーテーブルの主キー
		orderItemService.insertItem(orderItem);

		return "addCartFinish.html";
	}

	/**
	 * ショッピングカートの表示
	 * 
	 * @param model
	 * @param loginUser
	 * @return
	 */
	@RequestMapping("/showCart")
	public String showCart(Model model, @AuthenticationPrincipal LoginUser loginUser) {

		// ユーザーのメルアドレスの表示
		String userEmail = loginUser.getUser().getEmail();
		model.addAttribute("email", userEmail);

		// カートに商品情報を表示する
		Order order = orderService.showOrder(loginUser.getUser().getId(), 0);// オーダー情報の取得（引数0は購入完了前のもの）

		if (order != null) {
			// 商品が一つも入っていない場合、表示の変更
			if (order.getOrderItemList().size() != 0) {

				model.addAttribute("orderItemList", order.getOrderItemList());// オーダー情報の中に入っているカートに追加された商品情報の取得

				// 合計金額、個数の算出
				Double totalprice = 0.0;
				Integer totalQuantity = 0;
				// 初期値設定
				for (OrderItem orderItemList : order.getOrderItemList()) {
					Integer quantity = orderItemList.getQuantity();// 数量
					Double price = orderItemList.getItem().getPrice();// 個数
					totalprice += price * quantity;
					totalQuantity += quantity;
					model.addAttribute("quantity", totalQuantity);
					model.addAttribute("totalprice", totalprice);
				}
			}
		}
		return "cart.html";
	}

	/**
	 * カートの中の商品の削除
	 * 
	 * @param model
	 * @param loginUser
	 * @param orderItemId
	 * @return
	 */
	@RequestMapping("/deleteItem")
	public String deleteItem(Model model, @AuthenticationPrincipal LoginUser loginUser, String orderItemId) {
		// 対応するorderItemIdの値の商品を削除
		orderItemService.deleteItem(Integer.parseInt(orderItemId));
		return showCart(model, loginUser);
	}

}
