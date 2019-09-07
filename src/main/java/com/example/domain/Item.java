package com.example.domain;

import java.util.List;

/**
 * 商品ドメイン.
 * 
 * @author takahiro.araki
 *
 */
public class Item {
	
    /**商品ID*/
	private Integer id;
	/**名前*/
	private String name;
	
	/**説明*/
	private String description;
	
	/**Mサイズ料金*/
	private Integer priceM;
	
	/**Lサイズ料金*/
	private Integer priceL;
	
	/**画像パス*/
	private String imagePath;
	
	/**削除フラグ*/
	private Boolean deleted;
	
	/**トッピングリスト*/
	private List toppingList;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Integer getPriceM() {
		return priceM;
	}

	public Integer getPriceL() {
		return priceL;
	}

	public String getImagePath() {
		return imagePath;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public List getToppingList() {
		return toppingList;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", description=" + description + ", priceM=" + priceM + ", priceL="
				+ priceL + ", imagePath=" + imagePath + ", deleted=" + deleted + ", toppingList=" + toppingList + "]";
	}
	
	
	
}
