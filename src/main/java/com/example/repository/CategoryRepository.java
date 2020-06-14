package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Category;

/**
 * カテゴリー情報を扱うレポジトリー.
 * 
 * @author ashibe
 *
 */
@Repository
public class CategoryRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Category> ITEM_ROW_MAPPER = (rs, i) -> {
		Category category = new Category();
		category.setId(rs.getInt("id"));
		category.setParent(rs.getInt("parent"));
		category.setName(rs.getString("name"));
		category.setNameAll(rs.getString("name_all"));

		return category;

	};

	/**
	 * 親カテゴリの抽出
	 * 
	 * @return 親カテゴリ
	 */
	public List<Category> parentCategoryList() {
		String sql = " select * from category where parent is null order by name ";
		List<Category> parentCategoryList = template.query(sql, ITEM_ROW_MAPPER);
		return parentCategoryList;
	}

	/**
	 * 親カテゴリに対応する中カテゴリの抽出
	 * 
	 * @param id
	 * @return 中カテゴリ
	 */
	public List<Category> categoryList(Integer parent) {
		String sql = "select * from category where parent=:parent and name_all is null order by name";
		SqlParameterSource param = new MapSqlParameterSource().addValue("parent", parent);
		List<Category> categoryList = template.query(sql, param, ITEM_ROW_MAPPER);

		return categoryList;
	}

	/**
	 * 小カテゴリの抽出.
	 * 
	 * @param parent
	 * @return
	 */
	public List<Category> childCategoryList(Integer parent) {
		String sql = "select * from category where parent=:parent and name_all is not null order by name";
		SqlParameterSource param = new MapSqlParameterSource().addValue("parent", parent);
		List<Category> categoryList = template.query(sql, param, ITEM_ROW_MAPPER);
		// List<Category> categoryList = template.query(sql, ITEM_ROW_MAPPER);
		return categoryList;
	}

}
