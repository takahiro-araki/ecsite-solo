package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Topping;

/**
 * トッピング情報のレポジトリ.
 * 
 * @author takahiro.araki
 *
 */
@Repository
public class ToppingRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private final static RowMapper<Topping> TOPPING_ROW_MAPPER=(rs,i)->{
		Topping topping =new Topping();
		topping.setId(rs.getInt("id"));
		topping.setName(rs.getString("name"));
		topping.setPriceM(rs.getInt("price_m"));
		topping.setPriceL(rs.getInt("price_l"));
		
		return topping;
	};

	
	/**
	 * トッピング情報を全件検索する.
	 * @return トッピング情報リスト
	 */
	public List<Topping> findAll(){
		String sql="SELECT id,name,price_m,price_l FROM toppings";
		List<Topping> toppingList=template.query(sql, TOPPING_ROW_MAPPER);
		return toppingList;
	}
	
	/**
	 *idでトッピングを検索する.
	 * @param id
	 * @return　トッピング情報をリターン
	 */
	public Topping load(Integer id) {
		String sql="SELECT id,name,price_m,price_l FROM toppings WHERE id=:id";
		SqlParameterSource param=new MapSqlParameterSource().addValue("id",id);
		Topping topping=template.queryForObject(sql, param,TOPPING_ROW_MAPPER);
		return topping;
	}
	
	
	
	
	
}
