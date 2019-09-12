package com.example.repository;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.domain.OrderItem;

/**
 * オーダーアイテムのリポジトリ.
 * 
 * @author takahiro.araki
 *
 */
@Repository
public class OrderItemRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * 自動採番されたIDを持ってくる際に利用する変数.
	 */
	private SimpleJdbcInsert insert;
	
	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert=new SimpleJdbcInsert((JdbcTemplate)template.getJdbcOperations());
		//ordersテーブルのidカラムが自動採番されることをDIコンテナに教えてあげる
		SimpleJdbcInsert withTableName=simpleJdbcInsert.withTableName("order_items");
		insert=withTableName.usingGeneratedKeyColumns("id");
	}
	
	/**
	 * オーダーアイテムを追加する.
	 * @param orderItem オーダーアイテムオブジェクト
	 * @return orderItem idが割り当てられた、オーダーアイテムオブジェクト
	 */
	public OrderItem insert(OrderItem orderItem) {
		String sql="INSERT INTO order_items(item_id,order_id,quantity,size) VALUES (:itemId,:orderId,:quantity,:size)";
		SqlParameterSource param =new  BeanPropertySqlParameterSource(orderItem);
//		template.update(sql, param);
		Number key=insert.executeAndReturnKey(param);
		orderItem.setId(key.intValue());
		System.out.println(key+"が割り当てられました");
		return orderItem;
	}
	
	/**
	 * オーダーアイテムを主キーで削除する.
	 * @param id 
	 */
	public void delete(Integer id) {
		String sql="DELETE FROM order_items  WHERE id=:id";
		SqlParameterSource param=new MapSqlParameterSource().addValue("id",id);
		template.update(sql, param);
	}
	
	
	
}