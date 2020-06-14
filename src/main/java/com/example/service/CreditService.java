package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.example.domain.Credit;
import com.example.form.CreditForm;
import com.example.form.OrderConfirmForm;

@Service
@Transactional
public class CreditService {

	@Autowired
	RestTemplate restTemplate;
													//werファイル/requestmapping("/")
	private static final String URL = "http://192.168.0.10:8080/sample-credit-card-web-api/credit-card/payment";

	public Credit service(OrderConfirmForm form) {

		return restTemplate.postForObject(URL, form, Credit.class);
	}
}
