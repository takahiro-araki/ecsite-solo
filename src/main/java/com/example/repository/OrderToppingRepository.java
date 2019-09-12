package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.OrderTopping;

/**
 * 注文商品のトッピング情報のレポジトリ.
 * 
 * @author takahiro.araki
 *
 */
@Repository
public class OrderToppingRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	
	
	
	/**
	 * オーダートッピング情報を追加する.
	 * @param orderTopping オーダートッピングオブジェクト
	 */
	public void insert(OrderTopping orderTopping) {
		String sql="INSERT INTO order_toppings(topping_id,order_item_id) VALUES(:toppingId,:orderItemId)";
		SqlParameterSource param=new BeanPropertySqlParameterSource(orderTopping);
		template.update(sql, param);
	}
	
	
	/**
	 * オーダートッピングをアイテムIDで削除する.
	 * @param id
	 */
	public void delete(Integer id) {
		String sql="DELETE FROM order_toppings WHERE order_item_id=:id";
		SqlParameterSource param=new MapSqlParameterSource().addValue("id",id);
		template.update(sql, param);
		
		
	}
}