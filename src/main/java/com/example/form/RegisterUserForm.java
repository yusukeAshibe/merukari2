package com.example.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 入力された登録情報を受け取るフォーム.
 * @author ashibe
 *
 */
public class RegisterUserForm {
	
	/**
	 * Eメール
	 */
	@Email(message = "Email format is wrong" )
	@NotBlank(message = "Enter Email !!")
	private String email;
	/**
	 * パスワード
	 */
	@NotBlank(message="Enter Password !!")
	private String password;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "RegisterUserForm [email=" + email + ", password=" + password + "]";
	}
	
	
}
