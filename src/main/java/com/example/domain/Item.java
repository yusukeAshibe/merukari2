package com.example.domain;

/**
 *  アイテム情報を扱うためのドメイン.
 * @author ashibe
 *
 */
public class Item {

	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 商品名
	 */
	private String name;
	/**
	 * アイテムの状態
	 */
	private Integer Condition;
	/**
	 * カテゴリID
	 */
	private Integer category;
	/**
	 * ブランド
	 */
	private String brand;
	/**
	 * 価格
	 */
	private Double price;
	/**
	 * 
	 */
	private Integer shipping;
	/**
	 * アイテムの削除
	 */
	private String description;

	/**
	 * アイテムのカテゴリ名
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

	public Integer getCondition() {
		return Condition;
	}

	public void setCondition(Integer condition) {
		Condition = condition;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getShipping() {
		return shipping;
	}

	public void setShipping(Integer shipping) {
		this.shipping = shipping;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNameAll() {
		return nameAll;
	}

	public void setNameAll(String nameAll) {
		this.nameAll = nameAll;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", Condition=" + Condition + ", category=" + category + ", brand="
				+ brand + ", price=" + price + ", shipping=" + shipping + ", description=" + description + ", nameAll="
				+ nameAll + "]";
	}
	
	
}
