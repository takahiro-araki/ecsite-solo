package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Item;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.form.InsertItemForm;
import com.example.repository.ItemRepository;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;
import com.example.repository.OrderToppingRepository;
import com.example.repository.ToppingRepository;

/**
 * ショッピングカートを操作する.
 * 
 * @author takahiro.araki
 *
 */
@Service
@Transactional
public class ExecuteShoppingCartService {

	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private OrderToppingRepository orderToppingRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ItemRepository itemRepository;
	

	/**
	 * 商品をショッピングカートに追加する.
	 * 
	 * @param form 商品情報のフォーム
	 */
	public void insertShoppingCart(InsertItemForm form) {
		// フォームのプロパティをオーダー商品ドメインに渡す
		OrderItem orderItem = new OrderItem();
		orderItem.setItemId(Integer.parseInt(form.getItemId()));
		orderItem.setQuantity(Integer.parseInt(form.getQuantity()));
		orderItem.setSize(form.getSize());
		// orderIdプロパティのみ、オーダードメインが既存かどうかで分岐
		Order order = null;
		// オーダー情報がある場合の処理
		if (orderRepository.loadById(1).size() >= 1) {
			// オーダーアイテムをインサートする
			List<Order> orderList = orderRepository.loadById(1);
			order = orderList.get(0);
			orderItem.setOrderId(order.getId());
			orderItemRepository.insert(orderItem);
			// オーダートッピングをインサートする。トッピングがない場合はインサートしない
			if(form.getToppingIdList().size()>=1) {
			for (String toppingId : form.getToppingIdList()) {
				OrderTopping orderTopping = new OrderTopping();
				orderTopping.setOrderItemId(orderItem.getId());
				orderTopping.setToppingId(Integer.parseInt(toppingId));
				orderToppingRepository.insert(orderTopping);
			}}else {
				
				//トッピングなしなので、オーダートッピングにインサートしない
			}
		} else {// オーダー情報がない場合はオーダー、オーダーアイテム、オーダートッピングテーブルに情報をインサート
				// オーダーをインサート
			order = new Order();
			// ユーザーIDとステータスと合計金額だけセット
			order.setUserId(1);
			order.setStatus(0);
			// 合計金額の計算
			int totalPrice = 0;
			if (form.getSize().equals("M")) {
				Item item = itemRepository.load(orderItem.getItemId());
				int priceM = item.getPriceM();
				int toppingPriceM = 200 * form.getToppingIdList().size();
				totalPrice = (priceM + toppingPriceM) * Integer.parseInt(form.getQuantity());
			} else {
				Item item = itemRepository.load(orderItem.getItemId());
				int priceL = item.getPriceL();
				int toppingPriceL = 300 * form.getToppingIdList().size();
				totalPrice = (priceL + toppingPriceL) * Integer.parseInt(form.getQuantity());
			}
			order.setTotalPrice(totalPrice);
			orderRepository.insert(order);
			/**
			 * インサートしたオーダーテーブルから、自動採番されたIDを取得する
			 */
			// オーダーアイテムをインサートする
			orderItem.setOrderId(order.getId());
			orderItemRepository.insert(orderItem);
			// オーダートッピングをインサートする(リストにある回数分インサート)
			if(form.getToppingIdList().size()>=1) {
			for (String toppingId : form.getToppingIdList()) {
				OrderTopping orderTopping = new OrderTopping();
				/**
				 * インサートしたオーダーアイテムテーブルから、自動採番されたIDを取得する
				 */
				orderTopping.setOrderItemId(orderItem.getId());
				orderTopping.setToppingId(Integer.parseInt(toppingId));
				orderToppingRepository.insert(orderTopping);
			}}else {
				
				//トッピングなしなので、オーダートッピングをインサートしない
				
			}
		}
	}
	
	/**
	 * オーダー商品とトッピングを削除する.
	 * @param id
	 */
	public void delete(String id) {
		orderToppingRepository.delete(Integer.parseInt(id));
		orderItemRepository.delete(Integer.parseInt(id));
	}
	
	/**
	 * オーダーテーブルの情報を主キー検索する.
	 * @param id
	 * @return オーダー情報
	 */
	public Order findAllById(String id) {
		Order order=orderRepository.findAllById(Integer.parseInt(id));
		return order;
	}







}
