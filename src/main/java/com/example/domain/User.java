package com.example.domain;

/**
 * ユーザー情報を扱うドメイン.
 * @author ashibe
 *
 */
public class User {
	
	/**
	 * ID
	 */
	private Integer id;
	
	/**
	 * パスワード
	 */
	private String password;
	
	/**
	 * メールアドレス
	 */
	private String email;
	
	/**
	 * 名前
	 */
	private String name;
	
	/**
	 * 郵便番号
	 */
	private String zipCode;
	
	/**
	 * 住所
	 */
	private String address;
	
	/**
	 * 電話番号
	 */
	private String telephone;
	
	
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
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

	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", email=" + email + ", name=" + name + ", zipCode="
				+ zipCode + ", address=" + address + ", telephone=" + telephone + "]";
	}

	

	
	
	

}
