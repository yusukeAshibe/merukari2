package com.example.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
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
import com.example.domain.Page;
import com.example.domain.User;
import com.example.form.SearchForm;
import com.example.service.CategoryService;
import com.example.service.ItemService;
import com.example.service.UserService;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer.FromDecimalArguments;

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
	public String showItem(Model model, @Validated SearchForm form, BindingResult result,
			@AuthenticationPrincipal LoginUser loginUser) {// parentは親カテゴリのID
		
		
//		if(form.getPage()==null) {
//			form.setPage("1");
//		}
//		System.out.println(form);
		
//ページ要素に文字列が入ってきた場合のエラー回避	
		Integer page;
		try {
			page = Integer.parseInt(form.getPage());
		} catch (NumberFormatException e) {
			form.setPage("1");
			page = 1;
		}
		
	
		
		
//		try {
//		if(form.getPrevPage().equals("-1")) {
//			page--;
//		}
//		if(form.getNextPage().equals("1")) {
//			page++;
//		}
//		}catch(Exception e) {
//			page=1;
//		}
//		
//		System.out.println(page);

	       

		// ユーザーのメルアドレスの表示
		String userEmail = loginUser.getUser().getEmail();
		model.addAttribute("email", userEmail);

		// 初期の画面遷移
		if (form.getId() == null && form.getParent() == null && form.getChuCategory() == null
				&& form.getSyoCategory() == null && form.getName() == null && form.getName() == null
				&& form.getPage().equals("1")) {
			List<Category> parentCategoryList = categoryService.parentCategoryList();
			Integer count=itemService.countItem(form);
			Integer totalPage=count/20;
			model.addAttribute("parentCategoryList", parentCategoryList);
			List<Item> itemList = new ArrayList<>();
			form.setPage("1");
			itemList = itemService.showItem(page);
			model.addAttribute("itemList", itemList);
			model.addAttribute("page",page);
			model.addAttribute("form",form);
			model.addAttribute("count",count);
			model.addAttribute("totalPage",totalPage);
			//System.out.println("現在"+page+"ページ目");
			//System.out.println("初期表示");

			return "list.html";

		}

		// 大カテゴリが選択されていない場合
		if (form.getParent() != null && form.getParent().equals("") && form.getName() == null) {
			List<Item> itemList = new ArrayList<>();
			List<Category> parentCategoryList = categoryService.parentCategoryList();
			List<Category> categoryList = new ArrayList<>();
			List<Category> childCategoryList = new ArrayList<>();
			itemList = itemService.showItem(page);
			model.addAttribute("parentCategoryList", parentCategoryList);
			model.addAttribute("categoryList", categoryList);
			model.addAttribute("childCategoryList", childCategoryList);
			model.addAttribute("itemList", itemList);
			model.addAttribute("page",page);
			model.addAttribute("form",form);
			//System.out.println("現在"+page+"ページ目");
			System.out.println(" 何も選択されていない場合");
			return "list.html";

		}

		List<Item> itemList = new ArrayList<>();
		List<Category> parentCategoryList = categoryService.parentCategoryList();
		List<Category> categoryList = new ArrayList<>();
		List<Category> childCategoryList = new ArrayList<>();

//		itemList = itemService.showItem(form.parseIntpage());
//		parentCategoryList = categoryService.parentCategoryList();

// カテゴリの　プルダウン設定
		// 大カテゴリの選択がなくなった場合
		if (form.getName() != null && form.getParent().equals("")) {
			parentCategoryList = categoryService.parentCategoryList();
			model.addAttribute("parentCategoryList", parentCategoryList);
			System.out.println("大カテゴリの指定がなくなった場合");
		}

		// 中カテゴリの値の取得 大カテゴリのみが選択された場合(名前選択あり）
		if (!("").equals(form.getName()) && form.getParent() != null && (("").equals(form.getChuCategory()))) {
			parentCategoryList = categoryService.parentCategoryList();
			
			if (!form.getParent().equals("")) {
				categoryList = categoryService.categoryList(Integer.parseInt(form.getParent()));
				itemList = itemService.searchCategoryItem(Integer.parseInt(form.getParent()), page, form);	
			}
			model.addAttribute("parentCategoryList", parentCategoryList);
			model.addAttribute("categoryList", categoryList);
			System.out.println("大カテゴリのみが選択された場合（名前選択あり）");

			// 小カテゴリの値の取得 大中カテゴリが選ばれた場合（名前選択あり）
		} else if (!("").equals(form.getName()) && form.getParent() != null && !(("")).equals(form.getChuCategory())) {
			categoryList = categoryService.categoryList(Integer.parseInt(form.getParent()));
			childCategoryList = categoryService.childCategoryList(Integer.parseInt(form.getChuCategory()));
			model.addAttribute("categoryList", categoryList);
			model.addAttribute("childCategoryList", childCategoryList);
			System.out.println("2");
			System.out.println("大中（小）カテゴリが選ばれた場合（名前選択あり）");

			// 中カテゴリの値の取得 大カテゴリのみが選択された場合(名前選択なし）
		} else if (form.getParent() != null && (("").equals(form.getChuCategory()))) {
			parentCategoryList = categoryService.parentCategoryList();
			if (!form.getParent().equals("")) {
				categoryList = categoryService.categoryList(Integer.parseInt(form.getParent()));
			}

			if (form.getParent().equals("")) {
				itemList = itemService.showItem(page);
			} else {
				itemList = itemService.searchCategoryItem(Integer.parseInt(form.getParent()), page, form);
			}
			model.addAttribute("parentCategoryList", parentCategoryList);
			model.addAttribute("categoryList", categoryList);
			System.out.println("大カテゴリのみが選択された場合（名前選択なし）");

			// 小カテゴリの値の取得 大中カテゴリが選ばれた場合（名前選択なし）
		} else if (form.getParent() != null && !(("")).equals(form.getChuCategory())) {
			categoryList = categoryService.categoryList(Integer.parseInt(form.getParent()));
			childCategoryList = categoryService.childCategoryList(Integer.parseInt(form.getChuCategory()));
			model.addAttribute("categoryList", categoryList);
			model.addAttribute("childCategoryList", childCategoryList);
			System.out.println("大中カテゴリが選ばれた場合（名前選択なし）");

		}

		// 商品選択

		// 大中小すべてのカテゴリが選択
		if ((form.getParent() != null && (form.getChuCategory() != null && !form.getChuCategory().equals(""))
				&& form.getSyoCategory() != null && !form.getSyoCategory().equals(""))) {

			itemList = itemService.searchCategoryItem(Integer.parseInt(form.getParent()),
					Integer.parseInt(form.getChuCategory()), Integer.parseInt(form.getSyoCategory()), page, form);
			
			System.out.println("小カテゴリが選ばれた場合（商品表示）");

			// 大中カテゴリの選択をした場合
		} else if ((form.getParent() != null && (form.getChuCategory() != null && !form.getChuCategory().equals("")))) {
			itemList = itemService.searchCategoryItem(Integer.parseInt(form.getParent()),
					Integer.parseInt(form.getChuCategory()), page, form);
		
			System.out.println("中カテゴリが選ばれた場合（商品表示）");
			
			// 大カテゴリまでの検索時
		} else if ((form.getParent() != null && !form.getParent().equals(""))) {
			itemList = itemService.searchCategoryItem(Integer.parseInt(form.getParent()), page, form);
			System.out.println("大カテゴリが選ばれた場合（商品表示）");
			
		
		} else if (form.getName() != null && !("").equals(form.getName())) {
			itemList=itemService.searchItem(page, form);
			System.out.println("名前のみ検索");
		}
		if(("").equals(form.getName())&&("").equals(form.getParent())&&("").equals(form.getChuCategory())&&("").equals(form.getSyoCategory())&&("").equals(form.getName())) {
			itemList=itemService.searchItem(page, form);
			System.out.println("カテゴリのみ検索");
		}
		
		
		
		System.out.println(form);
		Integer count=itemService.countItem(form);
		System.out.println(count);
		Integer totalPage=count/20;
		if(totalPage<=0) {
			totalPage=1;
		}
		model.addAttribute("count",count);
		model.addAttribute("totalPage",totalPage);
		model.addAttribute("parentCategoryList", parentCategoryList);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("childCategoryList", childCategoryList);
		model.addAttribute("itemList", itemList);
		model.addAttribute("page",page);
		model.addAttribute("form",form);
	
		//System.out.println("現在"+page+"ページ目");
		

//		
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
	public String showItemDetail(Integer id, Model model,@AuthenticationPrincipal LoginUser loginUser) {
		String userEmail = loginUser.getUser().getEmail();
		model.addAttribute("email", userEmail);
		Item item = itemService.showDetail(id);
		model.addAttribute("item", item);

		return "detail.html";

	}

}
