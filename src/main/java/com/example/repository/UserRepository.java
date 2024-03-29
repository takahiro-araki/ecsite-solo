package com.example.repository;

import java.util.List;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.UserDomain;

/**
 * ユーザー情報レポジトリ
 * @author takahiro.araki
 *
 */
@Repository
public class UserRepository {
	
	private final static RowMapper<UserDomain>  USER_ROW_MAPPER=(rs,i)->{
		UserDomain user =new UserDomain();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setAddress(rs.getString("address"));
		user.setEmail(rs.getString("email"));
		user.setZipcode(rs.getString("zipcode"));
		user.setPassword(rs.getString("password"));
		user.setTelephone(rs.getString("telephone"));
		return user;
	};
	
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * ユーザー情報を新規登録する.
	 * @param user　ユーザー情報
	 */
	public void insert(UserDomain user)  {
		String sql="INSERT INTO users(name,email,password,zipcode,address,telephone) VALUES(:name,:email,:password,:zipcode,:address,:telephone)";
		SqlParameterSource param=new BeanPropertySqlParameterSource(user);
		template.update(sql, param);
	}
	
	/**
	 * ユーザー情報をメールアドレスから検索する.
	 * @param email メールアドレス
	 * @return　ユーザー情報
	 */
	public UserDomain load(String email) {
		String sql="SELECT id,name,email,password,zipcode,address,telephone FROM users WHERE email=:email";
		SqlParameterSource param=new MapSqlParameterSource().addValue("email",email);
		List<UserDomain> userList=template.query(sql, param, USER_ROW_MAPPER);
		UserDomain user;
		if(userList.size()==0) {
			return null;
		}
		return user=userList.get(0);
	}
	
	
}


