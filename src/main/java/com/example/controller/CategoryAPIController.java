package com.example.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Category;
import com.example.service.CategoryService;
@RestController
@RequestMapping(value = "/categoryCheck")
public class CategoryAPIController {
	
	@Autowired
    private CategoryService categoryService; 
	
//	@RequestMapping(value = "/", method = RequestMethod.POST)
//	public List<Category>showCategoyList(String parent) {
//	//		List<Category>categoryList = categoryService.categoryList(parent);
//			return categoryList;
//	
//	}
//	
  //Modelに詰めて返す方法もある？

	
}
