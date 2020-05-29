package com.example.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 入力された登録情報を受け取るフォーム.
 * @author ashibe
 *
 */
public class RegisterUserForm {
	
	/**
	 * Eメール
	 */
	@Email(message = "Emailの形で入力してください「" )
	@NotBlank(message = "メールアドレスを入力してください")
	private String email;
	/**
	 * パスワード
	 */
	@NotBlank(message="パスワードを入力してください")
	//@Pattern(regexp="^[ -~｡-ﾟ]{1,100}$",message="半角英数字で入力してください")
	@Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,16}$", message="パスワードの形が間違っています。8文字以上16文字以内の半角英数字で入力してください")
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
