package com.example.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * ユーザーのログイン情報を格納するエンティティ.
 * 
 * @author takahiro.araki
 *
 */
public class LoginUser extends User {
	
	private static final long serialVersionUID=1L;
	
	private final UserDomain userDomain;
	
	/**
	 * 通常の管理者情報に加え、認可用ロールを設定する.
	 * 
	 * @param userDomain ユーザー情報
	 * @param authorityList　権限情報が入ったリスト
	 */
	public LoginUser (UserDomain userDomain,Collection<GrantedAuthority> authorityList) {
		super(userDomain.getEmail(),userDomain.getPassword(),authorityList);
		this.userDomain=userDomain;
	}
	
	public UserDomain getUserDomain() {
		return userDomain;
	}

}
