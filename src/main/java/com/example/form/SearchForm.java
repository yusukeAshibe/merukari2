package com.example.form;

import javax.validation.constraints.Pattern;

/**
 * 入力された検索条件を受け取るフォーム,
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
	 * ページ数
	 */
	//@Pattern(regexp="^[0-9]$" ,message="エラー")
	private String page="1";

	public Integer parseInt(String parent) {
		return new Integer(parent);
	}
	
	public Integer parseIntPage() {
		return new Integer(this.page);
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
				+ ", syoCategory=" + syoCategory + ", page=" + page + "]";
	}



	

	

}
