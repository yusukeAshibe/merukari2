package com.example.controller;

import java.util.ArrayList;
import java.util.List;

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

	@ModelAttribute
	public SearchForm setUpSearchForm() {
		return new SearchForm();
	}

	@RequestMapping("/")
	public String showItem(Model model, SearchForm form, Integer page, String parent) {// parentは親カテゴリのID
		List<Item> itemList=new ArrayList<>();
		 itemList=itemService.showItem(page);
		model.addAttribute("itemList", itemList);
		List<Category> parentCategoryList = categoryService.parentCategoryList();
		model.addAttribute("parentCategoryList", parentCategoryList);
		System.out.println(form);
		if(form.getName()!=null) {
			itemList=itemService.searchItem(form.getName(), page);
		}
		// 大カテゴリー0のプルダウンがちぇんじされ場合は
		// 中カテゴリーを検索して、modelにセットっとする

		if (form.getParent() != null && form.getName() == null) {
			System.out.println(form);
			List<Category> categoryList = categoryService.categoryList(Integer.parseInt(form.getParent()));
			List<Category> childCategoryList = new ArrayList<>();
			if (form.getChuCategory() != null) {
				// childCategoryList =
				// categoryService.childCategoryList(Integer.parseInt(form.getChuCategory()));
			}

			model.addAttribute("categoryList", categoryList);
			model.addAttribute("childCategoryList", childCategoryList);
			
			
		}
		

		return "list.html";

	}

	@RequestMapping("/showItemDetail")
	public String showItemDetail(Integer id, Model model) {
		Item item = itemService.showDetail(id);
		model.addAttribute("item", item);

		return "detail.html";

	}

}
