package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.OrderItem;
import com.example.repository.OrderItemRepository;

/**
 * 注文商品情報を扱うサービス.
 * @author ashibe
 *
 */
@Service
@Transactional
public class OrderItemService {

	@Autowired
	private OrderItemRepository orderItemRepository;

	/**
	 * 商品をカートに追加する,
	 * @param orderItem
	 */
	public void insertItem(OrderItem orderItem) {
		orderItemRepository.insertItem(orderItem);
	}
	
	/**
	 * 商品をカートから削除する.
	 * @param orderItemId
	 */
	public void deleteItem(Integer orderItemId) {
		orderItemRepository.deleteItem(orderItemId);
	}

}
