package com.example.domain;

/**
 * 注文商品を扱うドメイン.
 * 
 * @author ashibe
 *
 */
public class OrderItem {
	/**
	 * ID
	 **/
	private Integer id;
	/**
	 * 商品ID
	 **/
	private Integer itemId;
	/**
	 * 注文ID
	 **/
	private Integer orderId;
	/**
	 * 数量
	 **/
	private Integer quantity;

	/**
	 * 商品情報
	 **/
	private Item item;



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", itemId=" + itemId + ", orderId=" + orderId + ", quantity=" + quantity
				+ ", item=" + item + "]";

	}
}
