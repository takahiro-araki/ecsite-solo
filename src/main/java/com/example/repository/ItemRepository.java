package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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
	
	/**
	 * 商品情報を主キー検索する.
	 * @param id　商品ID
	 * @return　商品情報ドメイン
	 */
	public Item load(Integer id) {
		String sql="SELECT id,name,description,price_m,price_l,deleted,image_path FROM items WHERE id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id",id);
		Item item=template.queryForObject(sql, param, ITEM_ROW_MAPPER);
		return item;
	}
	
	/**
	 * 商品名であいまい検索をする.
	 * @param name 商品名
	 * @return　商品リスト
	 */
	public List<Item> serchByName(String name){
		String sql="SELECT id,name,description,price_m,price_l,deleted,image_path FROM items WHERE name LIKE :name";
		SqlParameterSource param=new MapSqlParameterSource().addValue("name","%"+name+"%");
		List<Item> itemList=template.query(sql, param,ITEM_ROW_MAPPER);
		return itemList;
	}
	
}