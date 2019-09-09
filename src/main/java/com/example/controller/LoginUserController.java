package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.User;
import com.example.form.LoginUserForm;
import com.example.service.LoginUserService;

/**
 * ユーザーログインをするコントローラー.
 * 
 * @author takahiro.araki
 *
 */
@Controller
@RequestMapping("/login")
public class LoginUserController {

	@Autowired
	private LoginUserService loginUserService;

	@ModelAttribute
	public LoginUserForm setLoginUserForm() {

		return new LoginUserForm();
	}

	@RequestMapping("")
	public String showLogin() {
		return "login";
	}

	/**
	 * ログインする.
	 * 
	 * @param form 情報
	 * @return 商品一覧画面
	 */
	@RequestMapping("/find")
	public String login(@Validated LoginUserForm form, BindingResult result) {
		List<User> userList = loginUserService.login(form);
		// ログイン失敗時の処理
		if (!(form.getEmail().equals(""))) {
			if (userList.size() <= 0) {
				result.rejectValue("email", "", "メールアドレスかパスワードが違います");
			}
			if (userList.size() > 0) {
				if (!(userList.get(0).getPassword().equals(form.getPassword()))) {
					result.rejectValue("password", "", "パスワードが違います");
				}
			}
		}
		if (result.hasErrors()) {
			return "login";
		}
		// ログイン成功時の処理
		return "item_list";
	}
}