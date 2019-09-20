package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ユーザーログインをするコントローラー.
 * 
 * @author takahiro.araki
 *
 */
@Controller
@RequestMapping("/")
public class LoginUserController {
	
	/*	セキュリティコンフィグでやる*//**
	/*
	 * @Autowired private LoginUserService loginUserService;
	 * 
	 * @ModelAttribute public LoginUserForm setLoginUserForm() {
	 * 
	 * return new LoginUserForm(); }
	 */

	/**
	 * ログイン画面を表示する.
	 * @param model リクエストパラメーター
	 * @param error　エラーメッセージ
	 * @return
	 */
	@RequestMapping("/")
	public String showLogin(Model model,@RequestParam(required=false) String error) {
		System.err.println("login error:"+error);
		if(error !=null) {
			System.err.println("login failed");
			model.addAttribute("errorMesssage","メールアドレスまたはパスワードが不正です。");
		}
		
		return "login";
	}
	
	
	

	/*	セキュリティコンフィグでやる*//**
			 * ログインする.
			 * 
			 * @param form 情報
			 * @return 商品一覧画面
			 *//*
				 * @RequestMapping("/find") public String login(@Validated LoginUserForm form,
				 * BindingResult result, Model model) { UserDomain user =
				 * loginUserService.login(form); // ログイン失敗時の処理 if
				 * (!(form.getEmail().equals(""))) { if (user== null) {
				 * result.rejectValue("email", "", "メールアドレスかパスワードが違います"); } else { if
				 * (!(user.getPassword().equals(form.getPassword()))) {
				 * result.rejectValue("password", "", "パスワードが違います"); } } } if
				 * (result.hasErrors()) { return "login"; } // ログイン成功時の処理 return
				 * "forward:/itemList/showItemList"; }
				 */
}