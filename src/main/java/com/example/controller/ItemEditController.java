package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.service.ItemService;

@Controller
@RequestMapping("/edit")
public class ItemEditController {
	@Autowired
	private ItemService ItemService;
	
	@RequestMapping("/to-edit")
	public String toEdit() {
//		ItemService.showDetail(id);
	return "edit.html";
	}
	
	
	@RequestMapping("/item-edit")
	public String itemEdit() {
		return"";
		
		
	}
}
