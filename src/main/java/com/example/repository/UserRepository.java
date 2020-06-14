package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.User;

/**
 * ユーザー情報を扱うレポジトリー.
 * @author ashibe
 *
 */
@Repository
public class UserRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
		User user = new User();
		user.setId(rs.getInt("id"));	
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setZipcode(rs.getString("zipcode"));
		user.setTelephone(rs.getString("telephone"));
		user.setAddress(rs.getString("address"));
		user.setName(rs.getString("name"));
		return user;
	};
	
	/**
	 * ユーザー情報をDBに登録します.
	 * 
	 * @param user ユーザーの情報の入ったオブジェクト
	 */
	public void insert(User user) {
		String sql = "INSERT INTO users (email, password , name , address ,zipcode,telephone) VALUES (:email, :password,:name,:address,:zipcode,:telephone)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		template.update(sql, param);

	}
	
	/**
	 * メールアドレスからユーザー情報を取得します.
	 * 
	 * @param email メールアドレス
	 * @return ユーザーの情報の入ったオブジェクト
	 */
	public User findByEmail(String email) {
		String sql = "select id ,email, password,address,zipcode,name,telephone from  users where email=:email";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}

	
	

}
