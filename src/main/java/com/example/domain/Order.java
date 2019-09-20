package com.example.domain;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**オーダードメイン
 * 
 * @author takahiro.araki
 * 
 * 
 *
 */
public class Order {
	
	/**オーダーID*/
	private Integer id;
	/**ユーザーID*/
	private Integer userId;
	/**ステータス*/
	private Integer status;
	/**合計金額*/
	private Integer totalPrice;
	/**注文日*/
	private Date orderDate;
	/**宛先氏名*/
	private String destinationName;
	/**宛先Eメール*/
	private String destinationEmail;
	/**宛先郵便番号*/
	private String destinationZipcode;
	/**宛先住所*/
	private String destinationAddress;
	/**宛先電話番号*/
	private String destinationTel;
	/**配達時間*/
	private Timestamp deliveryTime;
	/**支払方法*/
	private Integer paymentMethod;
	/**ユーザー情報*/
	private UserDomain user;
	/**オーダー商品リスト */
	private List<OrderItem> orderItemList;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
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
	public Timestamp getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(Timestamp deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public Integer getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public UserDomain getUser() {
		return user;
	}
	public void setUser(UserDomain user) {
		this.user = user;
	}
	
	
	
	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}
	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}
	/**
	 * 消費税を算出する.
	 * @return 消費税金額
	 */
	public int getTax() {
		int price=0;
		int tax=0;
		for(OrderItem orderItem:this.getOrderItemList()) {
			int subTotal=orderItem.getSubTotal();
			price+=subTotal;
		}
		return tax=(int)(price*0.08);
	}
	
	/**
	 * 消費税込みの合計金額(TotalPrice)を算出する.
	 * @return 消費税込み合計金額
	 */
	public int getCalcTotalPrice() {
		
		//オーダーアイテムリストの中にあるオーダーアイテムの金額をひとつひとつ出して、合計する。
		int price=0;
		int totalProce=0;
		for(OrderItem orderItem:this.getOrderItemList()) {
			int subTotal=orderItem.getSubTotal();
			price+=subTotal;
		}
		//オーダーアイテムリストの中にある合計金額に消費税を込みして計算
		return totalPrice=price+this.getTax();
		
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", userId=" + userId + ", status=" + status + ", totalPrice=" + totalPrice
				+ ", orderDate=" + orderDate + ", destinationName=" + destinationName + ", destinationEmail="
				+ destinationEmail + ", destinationZipcode=" + destinationZipcode + ", destinationAddress="
				+ destinationAddress + ", destinationTel=" + destinationTel + ", deliveryTime=" + deliveryTime
				+ ", paymentMethod=" + paymentMethod + ", user=" + user + ", orderItemList=" + orderItemList + "]";
	}
	

	


}
