package com.example.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.example.domain.Credit;
import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.form.OrderConfirmForm;
import com.example.service.CreditService;
import com.example.service.OrderService;

@Controller
@RequestMapping("/confirm")
public class OrderConfirmController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private CreditService creditService;

	@Bean
	RestTemplate RestTemplate() {
		return new RestTemplate();
	}

	@ModelAttribute
	private OrderConfirmForm setUpOrderConfirmForm() {
		return new OrderConfirmForm();
	}

	/**
	 * 注文確認画面の表示.
	 * 
	 * @param loginUser
	 * @param model
	 * @param orderItemId
	 * @return
	 */
	@RequestMapping("/showOrderConfirm")
	public String showOrderConfirm(OrderConfirmForm form, @AuthenticationPrincipal LoginUser loginUser, Model model) {

		// ユーザー情報の表示
		String userEmail = loginUser.getUser().getEmail();
		String userName = loginUser.getUser().getName();
		String userAddress = loginUser.getUser().getAddress();
		String userZipCode = loginUser.getUser().getZipcode();
		String userTelephone = loginUser.getUser().getTelephone();
		if (StringUtils.isEmpty(form.getDestinationName())) {
			form.setDestinationName(userName);
		}
		if (StringUtils.isEmpty(form.getDestinationEmail())) {
			form.setDestinationEmail(userEmail);
		}
		if (StringUtils.isEmpty(form.getDestinationAddress())) {
			form.setDestinationAddress(userAddress);
		}
		if (StringUtils.isEmpty(form.getDestinationZipcode())) {
			form.setDestinationZipcode(userZipCode);
		}
		if (StringUtils.isEmpty(form.getDestinationTel())) {
			form.setDestinationTel(userTelephone);
		}
		// オーダー情報の取得
		Order order = orderService.showOrder(loginUser.getUser().getId(), 0);
		System.out.println(order);

		if (order != null) {
			// 商品が一つも入っていない場合、表示の変更
			if (order.getOrderItemList().size() != 0) {

				model.addAttribute("orderItemList", order.getOrderItemList());// オーダー情報の中に入っているカートに追加された商品情報の取得

				// 合計金額,個数の算出
				Double totalprice = 0.0;
				Integer totalQuantity = 0;// 初期値設定
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

		model.addAttribute("email", userEmail);

		return "orderConfirm.html";

	}

	@RequestMapping("/complete")
	public String CompleteOrder(@AuthenticationPrincipal LoginUser loginUser, Model model,
			@Validated OrderConfirmForm form, BindingResult result) {
		System.out.println(form);

		// エラー時の選択要素保持
		String deliveryTime = form.getDeliveryTime();
		String deliveryDate = form.getDeliveryDate();
		String paymentMethod = form.getPaymentMethod();
		model.addAttribute("deliveryTime", deliveryTime);
		model.addAttribute("deliveryDate", deliveryDate);
		model.addAttribute("paymentMethod", paymentMethod);
		model.addAttribute("card_exp_year",form.getCard_exp_year());
		model.addAttribute("card_exp_month",form.getCard_exp_month());

		// メールアドレスの表示
		String userEmail = loginUser.getUser().getEmail();
		model.addAttribute("email", userEmail);
		Timestamp deliveryTime2 = null;

		// 時間をtimeStamp型に変更
		try {
			// 注文時間をtimestamp型に変更
			deliveryTime2 = new Timestamp(new SimpleDateFormat("yyyy-MM-dd H")
					.parse(form.getDeliveryDate() + " " + form.getDeliveryTime()).getTime());
			// 現在時刻を取得しその一日後を取得する
			LocalDateTime nowTime = LocalDateTime.now();
			Timestamp nowPlusDay = Timestamp.valueOf(nowTime.plusDays(1));
			// 現在時刻から一日たっていない時間が配達時間に指定されたらエラー.
			if (nowPlusDay.after(deliveryTime2)) {
				result.rejectValue("deliveryDate", null, "24 hours or more after the current time");
			}

//			System.out.println("現在時刻 " + nowTime);
//			System.out.println("配達時間 " + deliveryTime);
//			System.out.println("現在時刻の一日後 " + nowPlusDay);
		} catch (Exception e) {
			return showOrderConfirm(form, loginUser, model);
		}

		// エラーがあれば注文画面に戻る

		if (result.hasErrors()) {
			return showOrderConfirm(form, loginUser, model);
		}

		// statusを切り替えて注文完了にする.

		Order order = orderService.showOrder(loginUser.getUser().getId(), 0);
		// 合計金額,個数の算出
		Double totalprice = 0.0;
		Integer totalQuantity = 0;
		for (OrderItem orderItemList : order.getOrderItemList()) {
			Integer quantity = orderItemList.getQuantity();// 数量
			Double price = orderItemList.getItem().getPrice();// 個数
			totalprice += price * quantity;
			totalQuantity += quantity;
		}

		// 入力情報をオーダーオブジェクトに入れる.
		// order.setStatus(9);
		order.setDestinationName(form.getDestinationName());
		order.setDestinationEmail(form.getDestinationEmail());
		order.setDestinationZipcode(form.getDestinationZipcode());
		order.setDestinationAddress(form.getDestinationAddress());
		order.setDestinationTel(form.getDestinationTel());
		order.setDeliveryTime(deliveryTime2);
		int newtotalprice = totalprice.intValue();
		order.setTotalPrice(newtotalprice);
		order.setOrderDate(new Date());
		// order.setStatus(Integer.parseInt(form.getPaymentMethod()));
		order.setStatus(9);

		if ("1".equals(form.getPaymentMethod())) {
			order.setPaymentMethod(Integer.valueOf(form.getPaymentMethod()));
		}
		if ("2".equals(form.getPaymentMethod())) {
			Credit credit = creditService.service(form);
			order.setPaymentMethod(Integer.valueOf(form.getPaymentMethod()));
			if ("error".equals(credit.getStatus())) {
				System.out.println("失敗");
				model.addAttribute("message", "Incorrect credit card information");
				return showOrderConfirm(form, loginUser, model);
			}
		}
		// 購入処理を行う（カートから商品を取り除く）(statusの変更）
		orderService.Update(order);

		//入力情報の保持
		model.addAttribute("order", order);
		model.addAttribute("orderItemList", order.getOrderItemList());
		model.addAttribute("totalQuantity", totalQuantity);
		model.addAttribute("totalPrice", totalprice);
		Date date = new Date(order.getDeliveryTime().getTime());
		model.addAttribute("deliveryTime", date);
		model.addAttribute("card_exp_year",form.getCard_exp_year());
		model.addAttribute("card_exp_month",form.getCard_exp_month());

		return "orderFinished.html";

	}

}
