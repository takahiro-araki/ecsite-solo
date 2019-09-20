package com.example.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * ログインユーザーフォーム.
 * 
 * @author takahiro.araki
 *
 */
public class LoginUserForm {

	//セキュリティコンフィグに任せるためコメントアウト
	/** パスワード */
	/*
	 * @NotBlank(message="パスワードは必須です") private String password;
	 *//** メールアドレス *//*
						 * @NotBlank(message="メールアドレスは必須です")
						 * 
						 * @Email(message="メールの形式で書いてください") private String email;
						 * 
						 * public String getPassword() { return password; } public void
						 * setPassword(String password) { this.password = password; } public String
						 * getEmail() { return email; } public void setEmail(String email) { this.email
						 * = email; }
						 * 
						 * @Override public String toString() { return "LoginUserForm [password=" +
						 * password + ", email=" + email + "]"; }
						 */

}
