package com.example.service;

import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.domain.Item;
import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.form.InsertItemForm;
import com.example.form.OrderShoppingCartForm;
import com.example.repository.ItemRepository;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;
import com.example.repository.OrderToppingRepository;

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

	@ModelAttribute
	public OrderShoppingCartForm setOrderShoppingCartForm() {
		return new OrderShoppingCartForm();
	};

	/**
	 * 商品をショッピングカートに追加する.
	 * 
	 * @param form 商品情報のフォーム
	 */
	public Order insertShoppingCart(InsertItemForm form, Integer userId) {
		// フォームのプロパティをオーダー商品ドメインに渡す
		OrderItem orderItem = new OrderItem();
		orderItem.setItemId(Integer.parseInt(form.getItemId()));
		orderItem.setQuantity(Integer.parseInt(form.getQuantity()));
		orderItem.setSize(form.getSize());
		// orderIdプロパティのみ、オーダードメインが既存かどうかで分岐
		Order order = null;
		// オーダー情報がある場合の処理
		if (orderRepository.loadById(userId).size() >= 1) {
			// オーダーの合計金額をセット
			List<Order> orderList = orderRepository.loadById(userId);
			order = orderList.get(0);
			int totalPrice=0;
			order.setTotalPrice(totalPrice);
			// オーダーアイテムをインサートする
			orderItem.setOrderId(order.getId());
			orderItemRepository.insert(orderItem);
			// オーダートッピングをインサートする。トッピングがない場合はインサートしない
			if (form.getToppingIdList() != null) {
				for (String toppingId : form.getToppingIdList()) {
					OrderTopping orderTopping = new OrderTopping();
					orderTopping.setOrderItemId(orderItem.getId());
					orderTopping.setToppingId(Integer.parseInt(toppingId));
					orderToppingRepository.insert(orderTopping);
				}
			} else {
				// トッピングなしなので、オーダートッピングにインサートしない
			}

		} else {// オーダー情報がない場合はオーダー、オーダーアイテム、オーダートッピングテーブルに情報をインサート
				// オーダーをインサート
			order = new Order();
			// ユーザーIDとステータスと合計金額だけセット
			order.setUserId(userId);
			order.setStatus(0);
			// 合計金額の計算
			int totalPrice = 0;
			order.setTotalPrice(totalPrice);
			orderRepository.insert(order);
			/**
			 * インサートしたオーダーテーブルから、自動採番されたIDを取得する
			 */
			// オーダーアイテムをインサートする
			orderItem.setOrderId(order.getId());
			orderItemRepository.insert(orderItem);
			// オーダートッピングをインサートする(リストにある回数分インサート)
			if (form.getToppingIdList() != null) {
				for (String toppingId : form.getToppingIdList()) {
					OrderTopping orderTopping = new OrderTopping();
					/**
					 * インサートしたオーダーアイテムテーブルから、自動採番されたIDを取得する
					 */
					orderTopping.setOrderItemId(orderItem.getId());
					orderTopping.setToppingId(Integer.parseInt(toppingId));
					orderToppingRepository.insert(orderTopping);
				}
			} else {
				// トッピングなしなので、オーダートッピングをインサートしない
			}
		}
		return orderRepository.findAllById(order.getId());
	}

	/**
	 * オーダー商品とトッピングを削除する.
	 * 
	 * @param オーダー商品id
	 */
	public void delete(String id) {
		orderToppingRepository.delete(Integer.parseInt(id));
		orderItemRepository.delete(Integer.parseInt(id));
	}

	/**
	 * オーダーテーブルの情報を主キー検索する.
	 * 
	 * @param オーダーid
	 * @return オーダー情報
	 */
	public Order findAllById(String id) {
		Order order = orderRepository.findAllById(Integer.parseInt(id));
		return order;
	}

	/**
	 * 注文情報を更新する.
	 * 
	 * @param form 注文情報フォーム
	 */
	public void updateOrder(OrderShoppingCartForm form,@AuthenticationPrincipal LoginUser loginUser) {
		Order order = new Order();
		BeanUtils.copyProperties(form, order);
		// status , totalPrice , orderDate , deliveryTime ,
		// paymentMethodはデータ型が違うので、詰めなおす
		order.setStatus(1);
		// 実験的にIDをセット
		order.setId(1);
		// 合計金額の計算
		Order totalPrice = new Order();
		totalPrice = orderRepository.findAllById(loginUser.getUserDomain().getId());
		order.setTotalPrice(totalPrice.getCalcTotalPrice());
		// orderDateをdate型へ変換、deliveryTimeをTimestamp型に変換
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(form.getOrderDate());
		stringBuilder.append("-");
		stringBuilder.append(form.getDeliveryTime());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh");
		String stringText = stringBuilder.toString();
		ParsePosition pos = new ParsePosition(0);
		Date orderDate = sdf.parse(stringText, pos);
		Timestamp deliveryTime = new Timestamp(orderDate.getTime());
		order.setOrderDate(orderDate);
		order.setDeliveryTime(deliveryTime);
		// paymentMethodを詰める
		order.setPaymentMethod(Integer.parseInt(form.getPaymentMethod()));
		orderRepository.uploadOrder(order);
	}

}
