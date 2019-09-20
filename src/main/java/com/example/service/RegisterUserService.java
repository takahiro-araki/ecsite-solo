package com.example.service;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.domain.UserDomain;
import com.example.form.RegisterUserForm;
import com.example.repository.UserRepository;

/**
 * ユーザー登録サービス.
 * 
 * @author takahiro.araki
 *
 */
@Service
public class RegisterUserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
		
	/**
	 * ユーザー情報を登録する.
	 * @param form ユーザー情報フォーム
	 */
	public void insert(RegisterUserForm form) {
		UserDomain user =new UserDomain();
 		BeanUtils.copyProperties(form, user);
 		//パスワードをハッシュ化
 		String encodePassword=passwordEncoder.encode(user.getPassword());
 		user.setPassword(encodePassword);
 		userRepository.insert(user); 		
	}
	
	
	/**
	 * ユーザー情報をメールアドレスで検索する.
	 * @param email
	 * @return ユーザー情報
	 */
	public UserDomain load(String email) {
		UserDomain user=userRepository.load(email);
		return user;
	}
	
}