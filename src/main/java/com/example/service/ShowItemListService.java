package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Item;
import com.example.repository.ItemRepository;

/**
 * アイテム一覧表示をするサービス.
 * @author takahiro.araki
 *
 */
@Service
public class ShowItemListService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	/**
	 * アイテム一覧を表示する.
	 * @return アイテムリスト
	 */
	public List<Item> showItem(){
		List<Item> itemList=itemRepository.findAll();
		return itemList;
	}
	
	/**
	 * 商品をあいまい検索し、並び替え検索も行う.
	 * @param name 商品名  order 順序
	 * @return　商品のリスト
	 */
	public List<Item>serchByName(String name,String order,Integer viewSize,Integer startPoint){
		List<Item> itemList=itemRepository.serchByName(name,order,viewSize,startPoint);
		return itemList;
	}
	
	/**
	 * オートコンプリート用の文字列を返す.
	 * @param 商品リスト
	 * @return オートコンプリート用文字列
	 */
	public StringBuilder prepareAutocomplete(List<Item>itemList) {
		StringBuilder autocompleteName= new StringBuilder();
		Item item;
		for (int i=0;i<itemList.size();i++) {
			if(i!=0) {
				autocompleteName.append(",");
			}
			item=itemList.get(i);
			autocompleteName.append("\"");
			autocompleteName.append(item.getName());
			autocompleteName.append("\"");
		}
		return autocompleteName;
	}
	
	
		
	
	
	
 
}
