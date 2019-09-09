package com.example.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.User;
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
		
	/**
	 * ユーザー情報を登録する.
	 * @param form ユーザー情報フォーム
	 */
	public void insert(RegisterUserForm form) {
		User user =new User();
 		BeanUtils.copyProperties(form, user);
 		userRepository.insert(user); 		
	}
	
	
	/**
	 * ユーザー情報をメールアドレスで検索する.
	 * @param email
	 * @return ユーザー情報
	 */
	public List<User> load(String email) {
		List<User> userList=userRepository.load(email);
		return userList;
	}
	
}