package com.example.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 入力された登録情報を受け取るフォーム.
 * 
 * @author ashibe
 *
 */
public class RegisterUserForm {

	/**
	 * Eメール
	 */
	@NotBlank(message = "input Email")
	@Email(message = "this Email format is wrong")
	private String email;
	/**
	 * パスワード
	 */

	@NotBlank(message = "input password")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,16}$", message = "this password is in the wrong.input using half-width English numbers and letters, using more than 8 characters but less than 16.")
	private String password;

	/**
	 * 確認用パスワード
	 */
	private String confirmationPassword;

	/**
	 * 名前
	 */
	@NotBlank(message = "input name")
	private String name;
	/**
	 * 郵便番号
	 */

	@Pattern(regexp = "^[0-9]{7}$", message = "zipCode format is wrong")
	@NotBlank(message = "input zipCode")
	private String zipcode;
	/**
	 * 電話番号
	 */
	@Pattern(regexp = "^[0-9]{2,4}[0-9]{2,4}[0-9]{3,4}$", message = "telephone formmat is wrong")
	@NotBlank(message = "input telephone")
	private String telephone;
	/**
	 * 住所
	 */
	@NotBlank(message = "input address")
	private String address;

//	/** 名前 */
//	@NotBlank(message = "名前を入力してください。")
//	private String name;
//	/** メールアドレス */
//	@Email(message = "メールアドレスの形式ではありません。")
//	@NotBlank(message = "メールアドレスを入力してください。")
	// @NotBlank(message="パスワードを入力してください")
	// @Pattern(regexp="^[ -~｡-ﾟ]{1,100}$",message="半角英数字で入力してください")
	// @Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,16}$",
	// message="パスワードの形が間違っています。8文字以上16文字以内の半角英数字で入力してください")
//	private String email;
//	/** 郵便番号 */
//	@Pattern(regexp = "^[0-9]{7}$", message = "郵便番号が不正です。")
//	@NotBlank(message = "郵便番号を入力してください。")
//	private String zipcode;
//	/** 住所 */
//	@NotBlank(message = "住所を入力してください。")
//	private String address;
//	/** 電話 */
//	@Pattern(regexp = "^[0-9]{2,4}[0-9]{2,4}[0-9]{3,4}$", message = "電話番号が不正です。")
//	@NotBlank(message = "電話番号を入力してください。")
//	private String telephone;
//	/** パスワード */
////	@NotBlank(message="パスワードを入力してください。")
//	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,16}$", message = "パスワードが不正です。")
//	private String password;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getConfirmationPassword() {
		return confirmationPassword;
	}

	public void setConfirmationPassword(String confirmationPassword) {
		this.confirmationPassword = confirmationPassword;
	}

	@Override
	public String toString() {
		return "RegisterUserForm [email=" + email + ", password=" + password + ", confirmationPassword="
				+ confirmationPassword + ", name=" + name + ", zipcode=" + zipcode + ", telephone=" + telephone
				+ ", address=" + address + "]";
	}

}
