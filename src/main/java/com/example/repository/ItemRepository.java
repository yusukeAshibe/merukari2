package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

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
	public List<Item> findAll(){
		String sql = "SELECT * FROM items  ";
		List<Item> itemList = template.query(sql,ITEM_ROW_MAPPER );
		return itemList;
		
	}
}
