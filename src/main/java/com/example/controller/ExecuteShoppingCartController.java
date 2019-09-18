package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.form.InsertItemForm;
import com.example.form.OrderShoppingCartForm;
import com.example.service.ExecuteShoppingCartService;

/**
 * ショッピングカートを操作するコントローラー.
 * 
 * @author takahiro.araki
 *
 */
@Controller
@RequestMapping("/Execute")
public class ExecuteShoppingCartController {
	
	@Autowired
	private ExecuteShoppingCartService executeShoppingCartService;
	
	@ModelAttribute
	public InsertItemForm setInsertItemForm() {
		return new InsertItemForm();	
	}
	
	/**
	 * 商品をショッピングカートに入れる.
	 * @param form インプット情報
	 * @return　ショッピングカート画面
	 */
	@RequestMapping("")
	public String insertShoppingCart(InsertItemForm form ,Model model) {
		executeShoppingCartService.insertShoppingCart(form);
		return findAllById("18",model);
	}
	
	/**
	 * オーダー商品の主キーでオーダー商品を削除する.
	 * @param id オーダー商品ID
	 * @return　ショッピングカート画面
	 */
	@RequestMapping("/delete")
	public String delete(String id,Model model) {
		executeShoppingCartService.delete(id);
		return findAllById("18",model);
	}
	
	/**
	 * オーダー情報を表示する.
	 * @param id オーダーID
	 * @param model リクエストパラメーター
	 * @return ショッピングカート画面
	 */
	@RequestMapping("/findOrder")
	public String findAllById(String id,Model model) {
		Order order=executeShoppingCartService.findAllById(id);
		model.addAttribute("order",order);
		return "cart_list";
	}
	
	
	/**
	 * 注文確認画面を表示する.
	 * @param id オーダーID
	 * @param model リクエストスコープ 
	 * @return　注文確認画面 
	 */
	@RequestMapping("/showOrderConfirm")
	public String showOrderConfirm(String id,Model model) {
		Order order=executeShoppingCartService.findAllById(id);
		model.addAttribute("order",order);
		return "order_confirm";
	}
	
	
	
	/**
	 * オーダー情報を更新する.
	 * @param form 更新情報フォーム
	 * @return 注文完了画面　
	 */
	@RequestMapping("/update")
	public String updateOrder(OrderShoppingCartForm form) {
		executeShoppingCartService.updateOrder(form);
		return"order_finished";
	}
}
