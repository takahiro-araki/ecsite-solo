package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.form.ShowItemForm;
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
	 * アイテム一覧を表示、曖昧検索、並び替え、ページングもやる.
	 * 
	 * @param model リクエストパラメーター
	 * @return アイテム一覧表示画面
	 */
	@RequestMapping("/showItemList")
	public String showItemList(Model model, ShowItemForm form) {
		// ページング処理
		int startPage = 0;
		if (form.getPage() == null) {
			startPage = 1;
		} else {
			startPage = Integer.parseInt(form.getPage());
		}
		// SQLのOFFSETで使う際のスタートポイントを特定
		int startPoint;
		if (form.getPage() == null || Integer.parseInt(form.getPage()) <= 0) {
			startPoint = 0;
			startPage = 1;
		} else {
			startPoint = ((startPage - 1) * VIEW_SIZE);
		}
		// ページ数も残したいため、スコープに格納
		model.addAttribute("startPage", startPage);
		
		String name = form.getName();
		String order = form.getOrder();
		// nameとorderはnullの場合、あらかじめ値をセット
		if (name == null) {
			name = "";
		}
		if (order == null) {
			order = "id asc";
		}
		List<Item> itemList = showItemListService.serchByName(name, order, VIEW_SIZE, startPoint);
		// 商品リスト、あいまい検索の文字列と並び替え用の文字列を格納
		model.addAttribute("name", name);
		model.addAttribute("order", order);
		// 画面表示用のリストを作成
		List<List<Item>> totalList = new ArrayList<>();
		List<Item> list3 = new ArrayList<>();
		for (int i = 1; i <= itemList.size(); i++) {
			System.out.println(itemList.get(i - 1));
			list3.add(itemList.get(i - 1));
			if (i % 3 == 0) {
				totalList.add(list3);
				list3 = new ArrayList<>();
			}
		}
		totalList.add(list3);
		model.addAttribute("totalList", totalList);
		
		// オートコンプリート用の文字列を用意する.
		StringBuilder itemName = showItemListService.prepareAutocomplete(itemList);
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

}