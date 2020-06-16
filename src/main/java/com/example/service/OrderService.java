package com.example.service;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.repository.ItemRepository;
import com.example.repository.OrderRepository;

/**
 * オーダー情報を操作するサービス.
 * 
 * @author ashibe
 *
 */
@Transactional
@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	/**
	 * オーダー情報を新規作成.
	 * 
	 * @param order
	 * @return オーダーの主キー
	 */
	public Integer insert(Order order) {
		Order newOrder = orderRepository.insert(order);
		Integer id = newOrder.getId();
		return id;
	}

	/**
	 * オーダー情報が既に存在するか確認.
	 * 
	 * @param userId
	 * @return
	 */
	public Order FindOrderId(Integer userId) {
		Order order = orderRepository.FindOrderId(userId);
		return order;
	}

	/**
	 * 注文前にカートに入った状態の商品の取得.
	 * 
	 * @param userId
	 * @return
	 */
	public Order showOrder(Integer userId, Integer status) {
		List<Order> orderList = orderRepository.OrderingInformationFindByUserId(userId, status);
		if (!ObjectUtils.isEmpty(orderList)) {
			return orderList.get(0);
		} else {
			return null;
		}
	}

	/**
	 * カート内の商品の個数.
	 * 
	 * @param userId
	 * @return
	 */
	public Integer CountItemInShoppingCartByUserId(Integer userId) {
		Integer count = orderRepository.CountItemInShoppingCartByUserId(userId);
		return count;
	}

	/**
	 * 注文確定時に、orderテーブルの書き換え.
	 * 
	 * @param order
	 */
	public void Update(Order order) {
		orderRepository.updateOrder(order);

	}

	/**
	 * userIdで注文履歴商品を取得
	 * 
	 * @param userId
	 * @return
	 */
	public List<Order> showOrderHistory(Integer userId) {
		List<Order> orderHistoryList = orderRepository.OrderHistoryFindByUserId(userId);
		return orderHistoryList;
	}

	/**
	 * orderIdで注文履歴を取得.
	 * 
	 * @param orderId
	 * @return
	 */
	public Order orderHistoryDetail(Integer orderId) {
		Order order = orderRepository.OrderHistoryFindOrderId(orderId);
		return order;

	}
	
	/**
	 * ユーザーIdでユーザー情報を扱う.
	 * @param userId
	 * @return
	 */
	public List<Order> orderHistoryFindByUserId(Integer userId){
		List<Order>orderList = orderRepository.OrderHistoryFindByUserId(userId);
		return orderList;
	}

}
