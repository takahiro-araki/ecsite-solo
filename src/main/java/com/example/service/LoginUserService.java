package com.example.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.UserDomain;
import com.example.form.LoginUserForm;
import com.example.repository.UserRepository;

/**
 * ログイン認証を行う.
 * 
 * @author takahitro.araki
 *
 */
@Service
public class LoginUserService {

	@Autowired
	private UserRepository userRepository;

	/*	セキュリティコンフィグに任せる*/
	/**
			 * 登録した情報でログインをする.
			 * 
			 * @param form フォーム情報
			 *//*
				 * public UserDomain login(LoginUserForm form) { UserDomain user= new
				 * UserDomain(); BeanUtils.copyProperties(form, user); UserDomain
				 * userDomain=userRepository.load(user.getEmail()); return userDomain; }
				 */
}