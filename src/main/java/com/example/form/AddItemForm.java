package com.example.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 商品追加画面で入力された値を受け取るフォーム.
 * @author ashibe
 *
 */
public class AddItemForm {
	
	/**
	 *商品名 
	 */
//	@NotBlank(message="入力必須です")
//	@Pattern(regexp="^[ -~｡-ﾟ]{1,100}$" ,message="半角英数字で入力してください")
	@NotBlank(message="Required field")
	@Pattern(regexp="^[ -~｡-ﾟ]{1,100}$" ,message="type using half-width characters.")
	
	private String name;
	
	/**
	 * 状態
	 */
//	@NotBlank(message="入力必須です")
//	@Pattern(regexp="^[0-9]$", message="半角数字で入力してください")
	@NotBlank(message="Required field")
	@Pattern(regexp="^[0-9]$", message="type using half-width digit.")
	private String condition;
	
	/**
	 * 小カテゴリID
	 */
	//@NotBlank(message="小カテゴリまで選択必須です")
	@NotBlank(message="Please choose it till the last")
	
	private String category;
	
	/**
	 * ブランド
	 */
	//@Pattern(regexp="^[ -~｡-ﾟ]{0,100}$", message="半角英数字で入力してください")
	@Pattern(regexp="^[ -~｡-ﾟ]{0,100}$", message="type using half-width characters.")
	
	private String brand;
	
	/**
	 * 価格
	 */
	//@NotBlank(message="入力必須です")
	//@Pattern(regexp="^[0-9]{1,10}$" ,message="半角数字で入力してください")
	@NotBlank(message="Required field")
	@Pattern(regexp="^[0-9]{1,10}$" ,message="type using half-width digit")
	private String price;
	
	/**
	 * 
	 */
	private String shipping;
	
	/**
	 * 商品説明
	 */
	//@NotBlank(message="入力必須です")
	//@Pattern(regexp="^[ -~｡-ﾟ]{1,100000}$", message="半角英数字で入力してください")
	@NotBlank(message="Required field")
	@Pattern(regexp="^[ -~｡-ﾟ]{1,100000}$", message="type using half-width characters.")
	private String description;
	
	/**
	 * 大カテゴリID
	 */
	private String parent;

	/**
	 * 中カテゴリID
	 */
	private String chuCategory;
	
	

	@Override
	public String toString() {
		return "AddItemForm [name=" + name + ", condition=" + condition + ", category=" + category + ", brand=" + brand
				+ ", price=" + price + ", shipping=" + shipping + ", description=" + description + ", parent=" + parent
				+ ", chuCategory=" + chuCategory + "]";
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getShipping() {
		return shipping;
	}

	public void setShipping(String shipping) {
		this.shipping = shipping;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	
	
	

}
