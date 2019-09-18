package com.example.controller;

import java.util.ArrayList;
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

	// １ページあたりのアイテム数
	private static final int VIEW_SIZE = 6;

	@Autowired
	private ShowItemListService showItemListService;

	/**
	 * アイテム一覧を表示する.
	 * 
	 * @param model リクエストパラメーター
	 * @return アイテム一覧表示画面
	 */
	@RequestMapping("/showItemList")
	public String showItemList(Model model) {
		List<Item> itemList = showItemListService.showItem();
		StringBuilder itemName = showItemListService.prepareAutocomplete(itemList);
		model.addAttribute("itemList", itemList);
		model.addAttribute("itemName", itemName);

		// ページングのリンクを作成
		// ナンバリングができなそうなので保留
		/*
		 * int page; if (itemList.size() % VIEW_SIZE != 0) { page = (itemList.size() /
		 * VIEW_SIZE) + 1; } else { page = itemList.size() / VIEW_SIZE; }
		 * model.addAttribute("page", page);
		 */

		return "item_list";
	}

	/**
	 * 商品をあいまい検索し、かつ並び替えを行う.
	 * 
	 * @param name  検索名前 order順序
	 * @param model
	 * @return 商品一覧画面
	 */
	@RequestMapping("/serchByName")
	public String serchByName(String name, String order, String getPage, Model model) {
		
		// どこから始まるかを特定
		int page = 0;
		if (getPage == null) {
			page = 1;
		} else {
			page = Integer.parseInt(getPage);
		}
		// ページ数も残したいため、スコープに格納
		model.addAttribute("page", page);
		// SQLのOFFSETで使う際のスタートポイントを特定
		int startPoint;
		if (getPage == null) {
			startPoint = 0;
		} else {
			startPoint =( (page - 1) * VIEW_SIZE) ;
		}
		// nameとorderはnullの場合、あらかじめ値をセット
		if (name == null) {
			name = "";
		}
		if (order == null) {
			order = "id asc";
		}
		List<Item> itemList = showItemListService.serchByName(name, order, VIEW_SIZE, startPoint);
		model.addAttribute("itemList", itemList);

		// ナンバリングは保留するのでコメントアウト
		/*
		 * int subLink; if (itemList.size() % VIEW_SIZE != 0) { subLink =
		 * (itemList.size() / VIEW_SIZE) + 1; } else { subLink = itemList.size() /
		 * VIEW_SIZE; } List<Integer> pageList = new ArrayList<>(); for (int i = 1; i <=
		 * subLink; i++) { pageList.add(i); } model.addAttribute("pageList", pageList);
		 */

		// ページング後も、並び替え検索やあいまい検索ができるように
		model.addAttribute("name", name);
		model.addAttribute("order", order);
		return "item_list";
	}

}