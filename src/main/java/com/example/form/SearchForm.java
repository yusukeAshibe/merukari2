package com.example.form;

import javax.validation.constraints.Pattern;

/**
 * 入力された検索条件を受け取るフォーム,
 * 
 * @author ashibe
 *
 */
public class SearchForm {

	/**
	 * ID
	 */
	private String id;

	/**
	 * 商品名
	 */
	private String name;

	/**
	 * 大カテゴリID
	 */
	private String parent;

	/**
	 * 中カテゴリID
	 */
	private String chuCategory;

	/**
	 * 小カテゴリID
	 */
	private String syoCategory;

	/**
	 * 現在のページ
	 */

	private String page;

	/**
	 * ブランド名.
	 */
	private String brand;

//	/**
//	 * 前のページ
//	 */
//	private String prevPage;
//	
//	/**
//	 * 次のページ
//	 */
//	private String nextPage;
//	
	/**
	 * 並び替え
	 */
	private String sortPrice;

	private String sortCondition;

	/**
	 * 商品の状態
	 */
	private String condition;

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getSortPrice() {
		return sortPrice;
	}

	public void setSortPrice(String sortPrice) {
		this.sortPrice = sortPrice;
	}

	public String getSortCondition() {
		return sortCondition;
	}

	public void setSortCondition(String sortCondition) {
		this.sortCondition = sortCondition;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Integer parseInt(String parent) {
		return new Integer(parent);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getChuCategory() {
		return chuCategory;
	}

	public void setChuCategory(String chuCategory) {
		this.chuCategory = chuCategory;
	}

	public String getSyoCategory() {
		return syoCategory;
	}

	public void setSyoCategory(String syoCategory) {
		this.syoCategory = syoCategory;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	@Override
	public String toString() {
		return "SearchForm [id=" + id + ", name=" + name + ", parent=" + parent + ", chuCategory=" + chuCategory
				+ ", syoCategory=" + syoCategory + ", page=" + page + ", brand=" + brand + ", sortPrice=" + sortPrice
				+ ", sortCondition=" + sortCondition + ", condition=" + condition + "]";
	}

}
