package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Category;
import com.example.repository.CategoryRepository;

/**
 * カテゴリー情報を扱うサービス.
 * @author ashibe
 *
 */
@Transactional
@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	/**
	 * 大カテゴリを取得
	 * 
	 * @return
	 */
	public List<Category> parentCategoryList() {
		List<Category> parentCategoryList = categoryRepository.parentCategoryList();
		return parentCategoryList;
	}

	/**
	 * 大カテゴリに対応する中カテゴリを取得
	 * 
	 * @param id
	 * @return
	 */
	public List<Category> categoryList(Integer parent) {
		List<Category> categoryList = categoryRepository.categoryList(parent);
		return categoryList;
	}

	/**
	 * 中カテゴリに対応する小カテゴリを取得
	 * @param parent
	 * @return
	 */
	public List<Category> childCategoryList(Integer parent) {
		List<Category> categoryList = categoryRepository.childCategoryList(parent);
		return categoryList;

	}
}
