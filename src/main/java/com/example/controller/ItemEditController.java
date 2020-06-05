package com.example.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Category;
import com.example.domain.Item;
import com.example.domain.LoginUser;
import com.example.form.EditForm;
import com.example.service.CategoryService;
import com.example.service.ItemService;
import com.example.service.UserService;

@Controller
@RequestMapping("/edit")
public class ItemEditController {
	@Autowired
	private ItemService ItemService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private UserService userService;
	
	@ModelAttribute
	private EditForm setUpEditForm() {
		return new EditForm();
	}
	

	@RequestMapping("/to-edit")
	public String toEdit(@AuthenticationPrincipal LoginUser loginUser, Integer id, Model model ,EditForm form) {
		//メールアドレスの表示
		String userEmail = loginUser.getUser().getEmail();
		model.addAttribute("email", userEmail);
		//編集するアイテムの要素の表示
		Item item = ItemService.showDetail(id);
		model.addAttribute("item",item);
		System.out.println(item);
		
		//カテゴリのリストの作成
		List<Category> parentCategoryList = new ArrayList<>();
		parentCategoryList = categoryService.parentCategoryList();
		model.addAttribute("parentCategoryList", parentCategoryList);
		
		
//		form.setName(item.getName());
//		form.setPrice(String.valueOf(item.getPrice()));
		
		return "edit.html";
	}
	
	

	@RequestMapping("/changeCategory")
	public String changeCategory(@AuthenticationPrincipal LoginUser loginUser,EditForm form,Integer id,Model model) {
		//空のカテゴリリストの作成
		List<Category> parentCategoryList = new ArrayList<>();
		List<Category> categoryList = new ArrayList<>();
		List<Category> childCategoryList = new ArrayList<>();

		// 大カテゴリを初期表示にも出した場合
		if (Objects.equals(form.getParent(), 99)) {
			parentCategoryList = categoryService.parentCategoryList();
			model.addAttribute("parentCategoryList", parentCategoryList);
		}
		
		// 大カテゴリの値の変更
		if (form.getParent() != null && (form.getChuCategory().equals(""))) {
			parentCategoryList = categoryService.parentCategoryList();
			categoryList = categoryService.categoryList(Integer.parseInt(form.getParent()));
			model.addAttribute("parentCategoryList", parentCategoryList);
			model.addAttribute("categoryList", categoryList);

		}

		// 中カテゴリの値の変更
		if (form.getParent() != null && form.getChuCategory() != null && !(form.getChuCategory().equals(""))) {
			parentCategoryList = categoryService.parentCategoryList();
			categoryList = categoryService.categoryList(Integer.parseInt(form.getParent()));
			childCategoryList = categoryService.childCategoryList(Integer.parseInt(form.getChuCategory()));

			model.addAttribute("parentCategoryList", parentCategoryList);
			model.addAttribute("categoryList", categoryList);
			model.addAttribute("childCategoryList", childCategoryList);
		}

		return toEdit(loginUser, id, model, form);

	}

	@RequestMapping("/item-edit")
	public String itemEdit() {

		return "";

	}
}
