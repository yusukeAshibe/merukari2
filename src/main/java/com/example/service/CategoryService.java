package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Category;
import com.example.repository.CategoryRepository;

@Transactional
@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	/**
	 * 親カテゴリ
	 * 
	 * @return
	 */
	public List<Category> parentCategoryList() {
		List<Category> parentCategoryList = categoryRepository.parentCategoryList();
		return parentCategoryList;
	}

	/**
	 * 中カテゴリ
	 * 
	 * @param id
	 * @return
	 */
	public List<Category> categoryList(Integer parent) {
		List<Category> categoryList = categoryRepository.categoryList(parent);
		return categoryList;
	}

	public List<Category> childCategoryList(Integer parent) {
		List<Category> categoryList = categoryRepository.childCategoryList(parent);
		return categoryList;

	}
}
