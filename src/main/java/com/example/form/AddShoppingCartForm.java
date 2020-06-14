package com.example.form;

/**
 * ショッピングカートに商品を追加する.
 * 
 * @author ashibe
 *
 */
public class AddShoppingCartForm {
	/**
	 * 数量
	 */
	private String quantity;
	/**
	 * 商品ID
	 */
	private String itemId;

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	@Override
	public String toString() {
		return "AddShoppingCartForm [quantity=" + quantity + ", itemId=" + itemId + "]";
	}

}
