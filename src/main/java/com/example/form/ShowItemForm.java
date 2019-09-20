package com.example.form;

/**
 * 商品一覧ページから情報を取得するときのフォーム.
 * @author takahiro.araki
 *
 */
public class ShowItemForm {
	
	/**あいまい検索の名前*/
	private String name;
	/**選択したページ */
	private String page;
	/**並び替えの順番*/
	private String order;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	@Override
	public String toString() {
		return "showItemForm [name=" + name + ", page=" + page + ", order=" + order + "]";
	}
	
	
	
	

}
