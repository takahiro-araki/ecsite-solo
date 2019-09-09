package com.example.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * ユーザー情報を登録するフォーム.
 * @author takahiro.araki
 *
 */
public class RegisterUserForm {
	
	/**名前*/
	@NotBlank(message="お名前は必須です")
	private String name;
	/**eメール*/
	@NotBlank(message="メールアドレスは必須です")
	@Email(message="Eメールの形式が不正です")
	private String email;
	/**郵便番号*/
	@NotBlank(message="郵便番号は必須です")
	private String zipcode;
	/**電話番号*/
	@NotBlank(message="電話番号は必須です")
	private String telephone;
	/**パスワード*/
	@NotBlank(message="パスワードは必須です")
	private String password;
	/**確認用パスワード*/
	@NotBlank(message="パスワードは必須です")
	private String confirmPassword;
	/**住所*/
	@NotBlank(message="住所は必須です")
	private String address;
	
	
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	@Override
	public String toString() {
		return "RegisterUserForm [name=" + name + ", email=" + email + ", zipcode=" + zipcode + ", telephone="
				+ telephone + ", password=" + password + ", confirmPassword=" + confirmPassword + ", address=" + address
				+ "]";
	}
	
	
	
	
	

}
