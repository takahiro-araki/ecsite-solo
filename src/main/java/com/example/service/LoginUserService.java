package com.example.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.User;
import com.example.form.LoginUserForm;
import com.example.repository.UserRepository;

/**
 * ログイン認証を行う.
 * @author takahitro.araki
 *
 */
@Service
public class LoginUserService {
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * 登録した情報でログインをする.
	 * @param form　フォーム情報
	 */
	public List<User> login(LoginUserForm form) {
		User user= new User();
		BeanUtils.copyProperties(form, user);
		List<User> userList=userRepository.load(user.getEmail());
		return userList;
	}
}