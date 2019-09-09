package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Item;
import com.example.domain.Topping;
import com.example.repository.ItemRepository;
import com.example.repository.ToppingRepository;

/**
 * 商品詳細画面を表示するサービス.
 * 
 * @author takahiro.araki
 *
 */
@Service
@Transactional
public class ShowItemDetailService {
	
	@Autowired
	private ToppingRepository toppingRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	
	/**
	 * 主キーで検索した商品とトッピング情報を結びつける.
	 * @param id 商品ID
	 * @return　商品情報
	 */
	public Item loadItemDetail(String id) {
		
		//Itemドメインを作る
		Item item = itemRepository.load(Integer.parseInt(id));
		List<Topping> toppingList=toppingRepository.findAll();
		item.setToppingList(toppingList);
		return item;
	}
	

}
