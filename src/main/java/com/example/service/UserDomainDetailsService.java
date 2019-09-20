package com.example.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.domain.LoginUser;
import com.example.domain.UserDomain;
import com.example.repository.UserRepository;

/**
 * ログイン後のユーザー情報に権限情報を付与するサービスクラス.
 * 
 * @author takahiro.araki
 *
 */
@Service
public class UserDomainDetailsService implements UserDetailsService {

	// ユーザー情報を取得するためのレポジトリ.
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println("検証　"+email);
		UserDomain userDomain = userRepository.load(email);
		System.out.println("検証2　"+userDomain);
		if (userDomain == null) {
			throw new UsernameNotFoundException("そのメールアドレスは登録されていません");
		}
		Collection<GrantedAuthority> authorityList = new ArrayList<>();
		authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));// ユーザー権限付与
		return new LoginUser(userDomain, authorityList);
	}

	

}