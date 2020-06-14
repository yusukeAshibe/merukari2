package com.example.service;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.repository.OrderRepository;

@Service
@Transactional
public class OrderConfirmServoce {

	@Autowired
	private OrderRepository orderRepository;

	public Order showOrderConfirm(Integer userId, Integer status) {
		List<Order> orderList = orderRepository.OrderingInformationFindByUserId(userId, status);
		if (!ObjectUtils.isEmpty(orderList)) {
			return orderList.get(0);
		} else {
			return null;
		}
	}

}
