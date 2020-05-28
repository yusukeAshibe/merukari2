package com.example.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Category;
import com.example.domain.Item;
import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.form.AddItemForm;
import com.example.repository.ItemRepository;
import com.example.service.CategoryService;
import com.example.service.ItemService;
import com.example.service.UserService;
import com.zaxxer.hikari.util.SuspendResumeLock;

/**
 * アイテムの追加を行うコントローラー.
 * 
 * @author ashibe
 *
 */
@Controller
@RequestMapping("/addItem")
public class AddItemController {

	@ModelAttribute
	public AddItemForm setUpAddItemForm() {
		return new AddItemForm();
	}

	@Autowired
	private ItemService itemService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private UserService userService;

	/**
	 * 商品追加画面への遷移.
	 * 
	 * @return
	 */
	@RequestMapping("/showAddItem")
	public String showAddItem(Model model, @AuthenticationPrincipal LoginUser loginUser,AddItemForm form) {
		if (loginUser != null) {
			String userEmail = loginUser.getUser().getEmail();
			model.addAttribute("email", userEmail);
		}
		//初期画面表示にて大カテゴリを用意
		List<Category> parentCategoryList = new ArrayList<>();
		parentCategoryList = categoryService.parentCategoryList();
		model.addAttribute("parentCategoryList", parentCategoryList);

		return "add.html";

	}

	@RequestMapping("/changeCategory")
	public String changeCategory(Model model, @AuthenticationPrincipal LoginUser loginUser, AddItemForm form) {
		
		List<Category> parentCategoryList = new ArrayList<>();;
		List<Category> categoryList = new ArrayList<>();
		List<Category> childCategoryList = new ArrayList<>(); 
	
		//大カテゴリを初期表示にも出した場合
		if(Objects.equals(form.getParent(), 99)) {
			parentCategoryList = categoryService.parentCategoryList();
			model.addAttribute("parentCategoryList", parentCategoryList);
		}
		
//		//カテゴリ選択がされていない場合
//		if(form.getParent()!=null&&form.getParent().equals("")) {
//			parentCategoryList = categoryService.parentCategoryList();
//			model.addAttribute("parentCategoryList", parentCategoryList);
//		}
//		
		

		// 大カテゴリの値の変更
		if (form.getParent() != null && (form.getChuCategory().equals(""))) {
			parentCategoryList = categoryService.parentCategoryList();
			categoryList = categoryService.categoryList(Integer.parseInt(form.getParent()));
			model.addAttribute("parentCategoryList", parentCategoryList);
			model.addAttribute("categoryList", categoryList);
			
		}

		// 中カテゴリの値の変更
		if (form.getParent() != null && form.getChuCategory()!=null&& !(form.getChuCategory().equals(""))) {
			parentCategoryList = categoryService.parentCategoryList();
			categoryList = categoryService.categoryList(Integer.parseInt(form.getParent()));
			childCategoryList = categoryService.childCategoryList(Integer.parseInt(form.getChuCategory()));
			
			model.addAttribute("parentCategoryList", parentCategoryList);
			model.addAttribute("categoryList", categoryList);
			model.addAttribute("childCategoryList", childCategoryList);
		}

		return showAddItem(model, loginUser, form);

	}

	/**
	 * 商品の追加
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	public String addItem(Model model, @AuthenticationPrincipal LoginUser loginUser, @Validated AddItemForm form,BindingResult result) {

		//ログイン表示
			String userEmail = loginUser.getUser().getEmail();
			model.addAttribute("email", userEmail);
			
			if(result.hasErrors()) {
				return changeCategory(model, loginUser, form);
			}
		
		//アイテムの追加
		System.out.println(form);
		Item item = new Item();
		item.setName(form.getName());
		item.setPrice(Double.parseDouble(form.getPrice()));
		item.setCategory(Integer.parseInt(form.getCategory()));
		item.setBrand(form.getBrand());
		item.setCondition(Integer.parseInt(form.getCondition()));
		item.setShipping(0);  
		System.out.println(item.getShipping());
		item.setDescription(form.getDescription());
		
		//BeanUtils.copyProperties(form, item);
		itemService.addItem(item);
		return "addItemFinish.html";

	}
}
