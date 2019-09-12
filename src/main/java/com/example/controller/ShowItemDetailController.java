package com.example.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.domain.Topping;
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
		Map<Integer,String>toppingMap=new LinkedHashMap<>();
		for (Topping topping:item.getToppingList()) {
			toppingMap.put(topping.getId(), topping.getName());
		}
		model.addAttribute("item",item);
		model.addAttribute("toppingMap",toppingMap);
		return "item_detail";
	}
	
	
}