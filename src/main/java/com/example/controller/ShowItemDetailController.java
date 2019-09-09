package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.ShowItemDetailService;

/**
 * 商品詳細を表示するコントローラー.
 * @author takahiro.araki
 *
 */
@Controller
@RequestMapping("/detail")
public class ShowItemDetailController {
	
	@Autowired
	private ShowItemDetailService showItemDetailService;
	
	/**
	 * 商品詳細情報を表示.
	 * @param id 商品ID
	 * @param model リクエストスコープ
	 * @return 商品詳細画面
	 */
	@RequestMapping("")
	public String showItemDetail(String id,Model model) {
		Item item= showItemDetailService.loadItemDetail(id);
		model.addAttribute("item",item);
		return "item_detail";
	}
	
	

}
