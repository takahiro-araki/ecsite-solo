package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.ShowItemListService;

/**
 * アイテム一覧を表示するコントローラー.
 * 
 * @author takahiro.araki
 *
 */
@RequestMapping("/itemList")
@Controller
public class ShowItemListController {
	
	@Autowired
	private ShowItemListService showItemListService;
	
	/**
	 * アイテム一覧を表示する.
	 * @param model リクエストパラメーター
	 * @return　アイテム一覧表示画面
	 */
	@RequestMapping("/showItemList")
	public String showItemList(Model model ) {
		List<Item> itemList=  showItemListService.showItem();
		model.addAttribute("itemList",itemList);
		return "item_list";
	} 
	
	@RequestMapping("/serchByName")
	public String serchByName(String name ,Model model) {
		List<Item> itemList=  showItemListService.serchByName(name);
		model.addAttribute("itemList",itemList);
		return "item_list";
	}
	
	

}
