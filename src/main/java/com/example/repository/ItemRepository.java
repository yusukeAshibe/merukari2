package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

import com.example.domain.Item;
/**
 * アイテム情報を扱うレポシトリクラス.
 * @author ashibe
 *
 */
@Repository
public class ItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final  RowMapper<Item> ITEM_ROW_MAPPER =(rs,i)->{
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setPrice(rs.getDouble("price"));
		item.setCategory(rs.getInt("category"));
		item.setBrand(rs.getString("brand"));
		item.setCondition(rs.getInt("condition"));
		item.setShipping(rs.getInt("shipping"));
		item.setDescription(rs.getString("description"));	
		return item;
	};
	
	/**
	 * 商品一覧を取得.
	 * @return
	 */
	public List<Item> findAll(Integer offset){
		String sql = "SELECT * FROM items LIMIT 20 offset :offset ";
		MapSqlParameterSource param = new MapSqlParameterSource().addValue("offset", offset);

		List<Item> itemList = template.query(sql,param,ITEM_ROW_MAPPER );
		return itemList;
		
	}
	
	/**
	 * 
	 * itemテーブルからIDで1件検索します.
	 * 
	 * @param id ID
	 * @return 商品情報の詰まったオブジェクト.
	 */
	public Item load(Integer id) {
		String sql = "SELECT * from items WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Item item = template.queryForObject(sql, param, ITEM_ROW_MAPPER);
		return item;

	}
	
	
	public List<Item> itemList(String name,Integer offset){
		String sql = "SELECT * FROM items WHERE name LIKE :name LIMIT 20 offset :offset ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%"+name+"%").addValue("offset", offset);;
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;

	}
   
}
