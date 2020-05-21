package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.ItemService;

@Controller
@RequestMapping("/")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/")
	public String showItem(Model model,Integer page) {
		List<Item>itemList=itemService.showItem(page);
		model.addAttribute("itemList",itemList);
		return"list.html";
		
		
	}

}
