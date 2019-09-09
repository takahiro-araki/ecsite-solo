package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;

/**
 * 商品レポジトリ.
 * @author takahiro.araki
 *
 */
@Repository
public class ItemRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private final static RowMapper<Item> ITEM_ROW_MAPPER=(rs,i)->{
		Item item=new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setDescription(rs.getString("description"));
		item.setPriceM(rs.getInt("price_m"));
		item.setPriceL(rs.getInt("price_l"));
		item.setDeleted(rs.getBoolean("deleted"));
		item.setImagePath(rs.getString("image_path"));
		return item;
	};
	
	
	/**
	 * 商品を全件検索する.
	 * @return 商品リスト
	 */
	public List<Item> findAll(){
		String sql="SELECT id,name,description,price_m,price_l,deleted,image_path FROM items";
		List<Item> itemList=template.query(sql, ITEM_ROW_MAPPER);
		return itemList;
		
		
	}
}