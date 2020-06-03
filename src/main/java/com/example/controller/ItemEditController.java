package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.ItemService;

@Controller
@RequestMapping("/edit")
public class ItemEditController {
	@Autowired
	private ItemService ItemService;
	
	@RequestMapping("/to-edit")
	public String toEdit(Integer id,Model model) {
	Item item=ItemService.showDetail(id);
	model.addAttribute("item",item);
	return "edit.html";
	}
	
	
	@RequestMapping("/item-edit")
	public String itemEdit() {
		return"";
		
		
	}
}
