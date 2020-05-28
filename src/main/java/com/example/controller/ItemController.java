package com.example.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Category;
import com.example.domain.Item;
import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.form.SearchForm;
import com.example.service.CategoryService;
import com.example.service.ItemService;
import com.example.service.UserService;

/**
 * アイテム関連の操作をするコントローラ.
 * 
 * @author ashibe
 *
 */
@Controller
@RequestMapping("/")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private UserService userService;

	@ModelAttribute
	public SearchForm setUpSearchForm() {
		return new SearchForm();
	}

	/**
	 * アイテム一覧画面内での挙動関連.
	 * 
	 * @param model
	 * @param form
	 * @param page
	 * @return
	 */
	@RequestMapping("/")
	public String showItem(Model model, SearchForm form, @AuthenticationPrincipal LoginUser loginUser, Integer page) {// parentは親カテゴリのID
		

//		//ページ関連
//		Integer page;
//		if (form.getPage().equals("")) {
//			page =1 ;
//		} else if(form.getPage()!=null) {
//			page = Integer.parseInt(form.getPage());
//		}else {
//			page=1;
//		}
		// メールアドレスの表示
		if (loginUser != null) {
			String userEmail = loginUser.getUser().getEmail();
			model.addAttribute("email", userEmail);
		}

		// 初期の画面遷移
		if (form.getId() == null && form.getChuCategory() == null && form.getSyoCategory() == null
				&& form.getName() == null) {
			
			List<Category> parentCategoryList = categoryService.parentCategoryList();
			model.addAttribute("parentCategoryList", parentCategoryList);
			List<Item> itemList = new ArrayList<>();
			itemList = itemService.showItem(page);
			model.addAttribute("itemList", itemList);
			return "list.html";

		}

		// 大カテゴリが選択されていない場合
		if (form.getParent() != null && form.getParent().equals("")) {
			List<Item> itemList = new ArrayList<>();
			List<Category> parentCategoryList = categoryService.parentCategoryList();
			List<Category> categoryList = new ArrayList<>();
			List<Category> childCategoryList = new ArrayList<>();
			itemList = itemService.showItem(page);
			model.addAttribute("parentCategoryList", parentCategoryList);
			model.addAttribute("categoryList", categoryList);
			model.addAttribute("childCategoryList", childCategoryList);
			model.addAttribute("itemList", itemList);
			return "list.html";

		}

		List<Item> itemList = new ArrayList<>();
		List<Category> parentCategoryList = categoryService.parentCategoryList();
		List<Category> categoryList = new ArrayList<>();
		List<Category> childCategoryList = new ArrayList<>();
		itemList = itemService.showItem(page);

		parentCategoryList = categoryService.parentCategoryList();

		// 中カテゴリの値の変更
		if (form.getParent() != null && (form.getChuCategory().equals(""))) {
			System.out.println(form);
			parentCategoryList = categoryService.parentCategoryList();
			categoryList = categoryService.categoryList(Integer.parseInt(form.getParent()));
			model.addAttribute("parentCategoryList", parentCategoryList);
			model.addAttribute("categoryList", categoryList);
			
		}
		// 小カテゴリの値の変更
		if (form.getParent() != null && !(form.getChuCategory().equals(""))) {
			categoryList = categoryService.categoryList(Integer.parseInt(form.getParent()));
			childCategoryList = categoryService.childCategoryList(Integer.parseInt(form.getChuCategory()));
			model.addAttribute("categoryList", categoryList);
			model.addAttribute("childCategoryList", childCategoryList);

		}

		// 大中小すべてのカテゴリが選択
		if ((form.getParent() != null && (form.getChuCategory() != null && !form.getChuCategory().equals(""))
				&& form.getSyoCategory() != null && !form.getSyoCategory().equals(""))) {

			itemList = itemService.searchCategoryItem(Integer.parseInt(form.getParent()),
					Integer.parseInt(form.getChuCategory()), Integer.parseInt(form.getSyoCategory()), page);

			// 大中カテゴリの選択をした場合
		} else if ((form.getParent() != null && (form.getChuCategory() != null && !form.getChuCategory().equals("")))) {
			itemList = itemService.searchCategoryItem(Integer.parseInt(form.getParent()),
					Integer.parseInt(form.getChuCategory()), page);

			// 大カテゴリまでの検索時
		} else if ((form.getParent() != null && !form.getParent().equals(""))) {
			itemList = itemService.searchCategoryItem(Integer.parseInt(form.getParent()), page);
		}

		model.addAttribute("parentCategoryList", parentCategoryList);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("childCategoryList", childCategoryList);
		model.addAttribute("itemList", itemList);
		return "list.html";

	}

	/**
	 * 詳細画面遷移.
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/showItemDetail")
	public String showItemDetail(Integer id, Model model) {
		Item item = itemService.showDetail(id);
		model.addAttribute("item", item);

		return "detail.html";

	}

}
