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
	 * 小カテゴリID
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
	 * 大カテゴリのid
	 */
	private Integer daiCategoryId;
	/**
	 * 中カテゴリのid
	 */
	private Integer chuCategoryId;
	
	/**
	 * 大カテゴリ名
	 */
	private String daiName;
	
	/**
	 * 中カテゴリ名
	 */
	private String chuName;
	
	/**
	 * 小カテゴリ名
	 */
	private String syoName;
	
	/**
	 *conditionの名前 
	 */
	private String conditionName;
	
	/**
	 * アイテムのカテゴリ名
	 */
	private String nameAll;
   
    

	public String getDaiName() {
		return daiName;
	}

	public void setDaiName(String daiName) {
		this.daiName = daiName;
	}

	public String getChuName() {
		return chuName;
	}

	public void setChuName(String chuName) {
		this.chuName = chuName;
	}

	public String getSyoName() {
		return syoName;
	}

	public void setSyoName(String syoName) {
		this.syoName = syoName;
	}

	

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

	public Integer getDaiCategoryId() {
		return daiCategoryId;
	}

	public void setDaiCategoryId(Integer daiCategoryId) {
		this.daiCategoryId = daiCategoryId;
	}

	public Integer getChuCategoryId() {
		return chuCategoryId;
	}

	public void setChuCategoryId(Integer chuCategoryId) {
		this.chuCategoryId = chuCategoryId;
	}

	public String getConditionName() {
		return conditionName;
	}

	public void setConditionName(String conditionName) {
		this.conditionName = conditionName;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", Condition=" + Condition + ", category=" + category + ", brand="
				+ brand + ", price=" + price + ", shipping=" + shipping + ", description=" + description
				+ ", daiCategoryId=" + daiCategoryId + ", chuCategoryId=" + chuCategoryId + ", daiName=" + daiName
				+ ", chuName=" + chuName + ", syoName=" + syoName + ", conditionName=" + conditionName + ", nameAll="
				+ nameAll + "]";
	}

	

	

	

	
	
	
}
