package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.form.InsertItemForm;
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
		return findAllById("16",model);
	}
	
	/**
	 * オーダー商品の主キーでオーダー商品を削除する.
	 * @param id オーダー商品ID
	 * @return　ショッピングカート画面
	 */
	@RequestMapping("/delete")
	public String delete(String id,Model model) {
		executeShoppingCartService.delete(id);
		return findAllById("16",model);
	}
	
	/**
	 * オーダー情報を表示する.
	 * @param id
	 * @param model リクエストパラメーター
	 * @return ショッピングカート画面
	 */
	@RequestMapping("/findOrder")
	public String findAllById(String id,Model model) {
		Order order=executeShoppingCartService.findAllById(id);
		model.addAttribute("order",order);
		return "cart_list";
	}

}
