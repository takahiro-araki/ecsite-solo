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
	 * 商品をあいまい検索する.
	 * @param name 商品名
	 * @return　商品のリスト
	 */
	public List<Item>serchByName(String name){
		List<Item> itemList=itemRepository.serchByName(name);
		return itemList;
	}
	
	
 
}
