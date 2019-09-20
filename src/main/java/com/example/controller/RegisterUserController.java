package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.RegisterUserForm;
import com.example.service.RegisterUserService;

/**
 * ユーザー情報登録コントローラー.
 * @author takahiro.araki
 *
 */
@Controller
@RequestMapping("/register")
public class RegisterUserController {
	
	@ModelAttribute
	public RegisterUserForm setRegisterUserForm() {
		return new RegisterUserForm();
	}
	
	
	@Autowired
	private RegisterUserService registerUserService;
	
	
	/**
	 * ユーザー登録画面を表示する.
	 * @return ユーザー登録画面
	 */
	@RequestMapping("")
	public String showRegister() {
		return "register";
	}
	
	
	/**
	 * ユーザー情報を登録する.
	 * @param form ユーザー情報フォーム
	 * @param result　
	 * @return
	 */
	@RequestMapping("/insertUser")
	public String insert(@Validated RegisterUserForm form,BindingResult result) {
		
		//登録がエラーになる場合の処理
		if(registerUserService.load(form.getEmail())!=null) {
			FieldError error=new FieldError(result.getObjectName(),"email","そのアドレスはすでに使われています");
			result.addError(error);
		}
		if(!form.getPassword().equals(form.getConfirmPassword())) {
			FieldError error=new FieldError(result.getObjectName(),"confirmPassword","パスワードは同じものを入力してください");
			result.addError(error);
		}
		if(result.hasErrors()) {
			return showRegister();
		}
		//登録ができた時の処理
		registerUserService.insert(form);
		return "forward:/";
	}
}