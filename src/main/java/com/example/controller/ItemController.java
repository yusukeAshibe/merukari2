package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Category;
import com.example.domain.Item;
import com.example.form.SearchForm;
import com.example.service.CategoryService;
import com.example.service.ItemService;

@Controller
@RequestMapping("/")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private HttpSession session;
	
	@ModelAttribute
	public SearchForm setUpSearchForm() {
		return new SearchForm();
	}
	
	@RequestMapping("/")
	public String showItem(Model model,SearchForm form, Integer page,String parent) {//parentは親カテゴリのID
		System.out.println(form);
		List<Item>itemList=itemService.showItem(page);
		model.addAttribute("itemList",itemList);
		List<Category> parentCategoryList = categoryService.parentCategoryList();
		model.addAttribute("parentCategoryList", parentCategoryList);
		// 大カテゴリー0のプルダウンがちぇんじされ場合は
		// 中カテゴリーを検索して、modelにセットっとする
		
		if (form.getParent() != null && form.getName()==null) {
			List<Category>categoryList = categoryService.categoryList(Integer.parseInt(form.getParent()));
			model.addAttribute("categoryList",categoryList);
			System.out.println(categoryList);
		} 
		if(form.getParent()!=null &&form.getName()==null&&form.getChuCategory()!=null ) {
			 List<Category>childCategoryList= categoryService.childCategoryList(Integer.parseInt(form.getChuCategory()));
			System.out.println(childCategoryList);
		    model.addAttribute("childCategoryList",childCategoryList);
		}
		
	    return"list.html";

	   
	    
	}
	
	@RequestMapping("/showItemDetail")
	public String showItemDetail(Integer id, Model model ) {
		Item item = itemService.showDetail(id);
		model.addAttribute("item", item);
		
		return "detail.html"; 
		
	}

}
