package com.example.domain;

import java.util.List;

/**
 * オーダー商品ドメイン
 * 
 * @author takahiro.araki
 * 
 *         getSubTotalメソッド未実装
 *
 */
public class OrderItem {
	/** オーダー商品ID */
	private Integer id;
	/** 商品ID */
	private Integer itemId;
	/** オーダーID */
	private Integer orderId;
	/** 数量 */
	private Integer quantity;
	/** サイズ */
	private String size;
	/** 商品 */
	private Item item;
	/** トッピングリスト */
	private List toppingList;

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

	public List getToppingList() {
		return toppingList;
	}

	public void setToppingList(List toppingList) {
		this.toppingList = toppingList;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * オーダー商品の合計金額を算出する.
	 * @return　オーダー商品の合計金額
	 */
	public int getSubTotal() {
		int subTotal;
		if (this.getSize().equals("M")) {
			int sizePrice = item.getPriceM();
			int toppingPrice = 200;
			int totalToppingPrice=toppingPrice*this.getToppingList().size();
			subTotal=(sizePrice+totalToppingPrice)*this.getQuantity();
		}else {
			int sizePrice = item.getPriceL();
			int toppingPrice = 300;
			int totalToppingPrice=toppingPrice*this.getToppingList().size();
			subTotal=(sizePrice+totalToppingPrice)*this.getQuantity();
		}
		return subTotal;

	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", itemId=" + itemId + ", orderId=" + orderId + ", quantity=" + quantity
				+ ", size=" + size + ", item=" + item + ", toppingList=" + toppingList + "]";
	}

}
