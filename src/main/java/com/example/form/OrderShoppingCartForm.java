package com.example.form;

/**
 * 注文をするフォーム.
 * 
 * @author takahiro.araki
 *
 */
public class OrderShoppingCartForm {
	
	/**入金状態 */
	private String status;
	/**合計金額 */
	private String totalPrice;
	/**注文日 */
	private String orderDate;
	/**宛先氏名*/
	private String destinationName;
	/**宛先Eメール */
	private String destinationEmail;
	/**宛先郵便番号 */
	private String destinationZipcode;
	/**宛先住所 */
	private String destinationAddress;
	/**宛先電話番号 */
	private String destinationTel;
	/**配達日 */
	private String deliveryTime;
	/**支払い方法 */
	private String paymentMethod;
	
	/**ユーザーID */
	private String userId;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getDestinationEmail() {
		return destinationEmail;
	}

	public void setDestinationEmail(String destinationEmail) {
		this.destinationEmail = destinationEmail;
	}

	public String getDestinationZipcode() {
		return destinationZipcode;
	}

	public void setDestinationZipcode(String destinationZipcode) {
		this.destinationZipcode = destinationZipcode;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public String getDestinationTel() {
		return destinationTel;
	}

	public void setDestinationTel(String destinationTel) {
		this.destinationTel = destinationTel;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "OrderShoppingCartForm [status=" + status + ", totalPrice=" + totalPrice + ", orderDate=" + orderDate
				+ ", destinationName=" + destinationName + ", destinationEmail=" + destinationEmail
				+ ", destinationZipcode=" + destinationZipcode + ", destinationAddress=" + destinationAddress
				+ ", destinationTel=" + destinationTel + ", deliveryTime=" + deliveryTime + ", paymentMethod="
				+ paymentMethod + ", userId=" + userId + "]";
	}


	
	
	
	
	
}