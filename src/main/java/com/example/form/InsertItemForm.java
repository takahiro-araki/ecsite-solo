package com.example.form;

import java.util.List;

/**
 * 商品をショッピングカートに追加する.
 * 
 * @author takahiro.araki
 *
 */
public class InsertItemForm {
	
	/**サイズ */
	private String size;
	/**数量*/
	private String quantity;
	/**商品ID */
	private String itemId;
	/**トッピングIDリスト*/
	private List<String> toppingIdList;
	/**ユーザーID*/
	private String userId;
	/**仮ユーザーID */
	private String preUserId;
	
	public String getSize() {
		return size;
	}
	
	public void setSize(String size) {
		this.size = size;
	}
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
	public List<String> getToppingIdList() {
		return toppingIdList;
	}
	public void setToppingIdList(List<String> toppingIdList) {
		this.toppingIdList = toppingIdList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPreUserId() {
		return preUserId;
	}

	public void setPreUserId(String preUserId) {
		this.preUserId = preUserId;
	}

	@Override
	public String toString() {
		return "InsertItemForm [size=" + size + ", quantity=" + quantity + ", itemId=" + itemId + ", toppingIdList="
				+ toppingIdList + ", userId=" + userId + ", preUserId=" + preUserId + "]";
	}
	
	
	
}
