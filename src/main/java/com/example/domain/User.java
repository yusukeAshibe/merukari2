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

	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", email=" + email + "]";
	}

	
	
	

}
