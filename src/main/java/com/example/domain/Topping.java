package com.example.domain;

/**
 * トッピング情報.
 * @author takahiro.araki
 *
 */
public class Topping {
	
	/**トッピングID*/
	private Integer id;
	/**名前*/
	private String name;
	/**Mサイズ料金*/
	private Integer priceM;
	/**Lサイズ料金*/
	private Integer priceL;
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
	public Integer getPriceM() {
		return priceM;
	}
	public void setPriceM(Integer priceM) {
		this.priceM = priceM;
	}
	public Integer getPriceL() {
		return priceL;
	}
	public void setPriceL(Integer priceL) {
		this.priceL = priceL;
	}
	@Override
	public String toString() {
		return "Topping [id=" + id + ", name=" + name + ", priceM=" + priceM + ", priceL=" + priceL + "]";
	}
	

}
