package com.example.domain;

/**
 * カテゴリ情報を扱うためのドメイン.
 * @author ashibe
 *
 */
public class Category {
	
	/**
	 * ID
	 */
	private Integer id; 
	/**
	 * 名前
	 */
	private String name; 
	/**
	 * 親のカテゴリID
	 */
	private Integer parent_id; 
	/**
	 * 
	 */
	private String nameAll;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getParent_id() {
		return parent_id;
	}
	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}
	public String getNameAll() {
		return nameAll;
	}
	public void setNameAll(String nameAll) {
		this.nameAll = nameAll;
	}
	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", parent_id=" + parent_id + ", nameAll=" + nameAll + "]";
	}
	
	

}
