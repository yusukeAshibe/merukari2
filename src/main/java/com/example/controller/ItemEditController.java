package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Category;
import com.example.domain.Item;
import com.example.domain.LoginUser;
import com.example.form.EditForm;
import com.example.service.CategoryService;
import com.example.service.ItemService;

/**
 * 商品編集を操作するコントローラー.
 * 
 * @author ashibe
 *
 */
@Controller
@RequestMapping("/edit")
public class ItemEditController {
	@Autowired
	private ItemService ItemService;
	@Autowired
	private CategoryService categoryService;

	@ModelAttribute
	private EditForm setUpEditForm() {
		return new EditForm();
	}

	/**
	 * 商品編集画面への遷移.
	 * 
	 * @param loginUser
	 * @param id
	 * @param model
	 * @param form
	 * @return
	 */
	@RequestMapping("/to-edit")
	public String toEdit(@AuthenticationPrincipal LoginUser loginUser, Integer id, Model model, EditForm form) {

		// メールアドレスの表示
		String userEmail = loginUser.getUser().getEmail();
		model.addAttribute("email", userEmail);

		// 編集するアイテムの要素の表示
		Item item = ItemService.showDetail(id);
		model.addAttribute("item", item);

		// カテゴリのリストの作成
		if (StringUtils.isEmpty(form.getParent())) {
			List<Category> parentCategoryList = new ArrayList<>();
			List<Category> categoryList = new ArrayList<>();
			List<Category> childCategoryList = new ArrayList<>();
			parentCategoryList = categoryService.parentCategoryList();
			categoryList = categoryService.categoryList(item.getDaiCategoryId());
			childCategoryList = categoryService.childCategoryList(item.getChuCategoryId());
			model.addAttribute("parentCategoryList", parentCategoryList);
			model.addAttribute("categoryList", categoryList);
			model.addAttribute("childCategoryList", childCategoryList);
		}

		// 初期表示時のみ
		if (form.getCondition() == null) {
			form.setName(item.getName());
			form.setPrice(String.valueOf(item.getPrice()));
			if (!StringUtils.isEmpty(item.getBrand())) {
				form.setBrand(item.getBrand());
			}
			form.setCategory(String.valueOf(item.getCategory()));
			form.setDescription(item.getDescription());
			form.setChuCategory(String.valueOf(item.getChuCategoryId()));
			form.setParent(String.valueOf(item.getDaiCategoryId()));

		}

		// conditionのcheckboxの初期表示
		String condition = String.valueOf(item.getCondition());
		model.addAttribute("condition", condition);

		return "edit.html";
	}

	/**
	 * カテゴリが変更になったときに呼ばれるメソッド.
	 * 
	 * @param loginUser
	 * @param form
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/changeCategory2")
	public String changeCategory2(@AuthenticationPrincipal LoginUser loginUser, EditForm form, Integer id,
			Model model) {

		// 空のカテゴリリストの作成
		List<Category> parentCategoryList = new ArrayList<>();
		List<Category> categoryList = new ArrayList<>();
		List<Category> childCategoryList = new ArrayList<>();

		// カテゴリ選択がされていない場合
		if (StringUtils.isEmpty(form.getParent())) {
			parentCategoryList = categoryService.parentCategoryList();
			model.addAttribute("parentCategoryList", parentCategoryList);
		}

		// 大カテゴリの値の変更
		if (!StringUtils.isEmpty(form.getParent()) && StringUtils.isEmpty(form.getChuCategory())) {
			parentCategoryList = categoryService.parentCategoryList();
			categoryList = categoryService.categoryList(Integer.parseInt(form.getParent()));

		}

		// 中カテゴリの値の変更
		if (form.getParent() != null && form.getChuCategory() != null && !(form.getChuCategory().equals(""))) {
			parentCategoryList = categoryService.parentCategoryList();
			categoryList = categoryService.categoryList(Integer.parseInt(form.getParent()));
			childCategoryList = categoryService.childCategoryList(Integer.parseInt(form.getChuCategory()));

		}

		model.addAttribute("parentCategoryList", parentCategoryList);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("childCategoryList", childCategoryList);
		return toEdit(loginUser, id, model, form);

	}

	/**
	 * 商品編集画面submitで遷移(商品編集完了)(入力エラーがある際は商品編集画面に帰る）..
	 * 
	 * @param model
	 * @param loginUser
	 * @param form
	 * @param result
	 * @param id
	 * @return
	 */
	@RequestMapping("/item-edit")
	public String itemEdit(Model model, @AuthenticationPrincipal LoginUser loginUser, @Validated EditForm form,
			BindingResult result, Integer id) {

		String userEmail = loginUser.getUser().getEmail();
		model.addAttribute("email", userEmail);

		if (result.hasErrors()) {
			return changeCategory2(loginUser, form, id, model);
		}

		// フォームの内容をアイテムにコピー
		Item item = new Item();

//		BeanUtils.copyProperties(form, item);

		item.setId(Integer.parseInt(form.getId()));
		item.setName(form.getName());
		item.setPrice(Double.parseDouble(form.getPrice()));
		item.setCategory(Integer.parseInt(form.getCategory()));
		item.setBrand(form.getBrand());
		item.setCondition(Integer.parseInt(form.getCondition()));
		item.setShipping(1);
		item.setDescription(form.getDescription());

		ItemService.EditItem(item);

		return "editItemFinish.html";

	}
}
