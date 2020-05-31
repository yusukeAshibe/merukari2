package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.thymeleaf.util.StringUtils;

import com.example.domain.Item;
import com.example.form.SearchForm;

/**
 * アイテム情報を扱うレポシトリクラス.
 * 
 * @author ashibe
 *
 */
@Repository
public class ItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	@ModelAttribute
	public SearchForm setUpsearchForm() {
		return new SearchForm();
	}

	private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {
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

	private static final RowMapper<Item> ITEM_ROW_MAPPER2 = (rs, i) -> {
		Item item = new Item();
		item.setId(rs.getInt("i_id"));
		item.setName(rs.getString("i_name"));
		item.setPrice(rs.getDouble("i_price"));
		item.setCategory(rs.getInt("i_category"));
		item.setBrand(rs.getString("i_brand"));
		item.setCondition(rs.getInt("i_condition"));
		item.setShipping(rs.getInt("i_shipping"));
		item.setDescription(rs.getString("i_description"));
		item.setNameAll(rs.getString("c1_name_all"));
		item.setDaiCategoryId(rs.getInt("c2_parent"));
		item.setChuCategoryId(rs.getInt("c2_id"));
		item.setDaiName(rs.getString("c3_name"));
		item.setChuName(rs.getString("c2_name"));
		item.setSyoName(rs.getString("c1_name"));

		return item;
	};

	/**
	 * 商品一覧を取得.
	 * 
	 * @return
	 */
	public List<Item> findAll(Integer offset) {

		String sql = "SELECT  \r\n" + "i.id i_id,\r\n" + "i.name i_name ,\r\n" + "i.condition i_condition, \r\n"
				+ "i.category i_category, \r\n" + "i.brand i_brand,\r\n" + "i.price i_price,\r\n"
				+ "i.shipping i_shipping,\r\n" + "i.description i_description,\r\n" + "c1.id c1_id ,\r\n"
				+ "c2.id c2_id,\r\n" + "c2.parent c2_parent ,\r\n" + "c1.name_all c1_name_all ,\r\n"
				+ "c1.name c1_name,\r\n" + "c2.name c2_name,\r\n" + "c3.id c3_id ,\r\n" + "c3.name c3_name\r\n"
				+ "from items i\r\n" + "left join category c1 on c1.id = i.category\r\n"
				+ "left join category c2 on c1.parent = c2.id \r\n" + "left join category c3 on c2.parent = c3.id\r\n"
				+ "order by i.id LIMIT 20 offset :offset";

//
//		String sql = "SELECT  i.id i_id, i.name i_name , i.condition i_condition, i.category i_category, i.brand i_brand,i.price i_price,i.shipping i_shipping,i.description i_description,c1.id c1_id,c2.id c2_id ,c2.parent c2_parent ,c1.name_all c1_name_all,c1.name c1_name,c2.name c2_name ,c3.id c3_id ,\r\n" + 
//				"c3.name c3_name from items i left join category c1 on c1.id = i.category  left join category c2 on c1.id = c2.parent left join category c3 on c2.parent = c3.id  LIMIT 20 offset :offset ";
		MapSqlParameterSource param = new MapSqlParameterSource().addValue("offset", offset);
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER2);
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

		String sql = "SELECT  \r\n" + "i.id i_id,\r\n" + "i.name i_name ,\r\n" + "i.condition i_condition, \r\n"
				+ "i.category i_category, \r\n" + "i.brand i_brand,\r\n" + "i.price i_price,\r\n"
				+ "i.shipping i_shipping,\r\n" + "i.description i_description,\r\n" + "c1.id c1_id ,\r\n"
				+ "c2.id c2_id,\r\n" + "c2.parent c2_parent ,\r\n" + "c1.name_all c1_name_all ,\r\n"
				+ "c1.name c1_name,\r\n" + "c2.name c2_name,\r\n" + "c3.id c3_id ,\r\n" + "c3.name c3_name\r\n"
				+ "from items i\r\n" + "left join category c1 on c1.id = i.category\r\n"
				+ "left join category c2 on c1.parent = c2.id \r\n" + "left join category c3 on c2.parent = c3.id\r\n"
				+ "where i.id=:id ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Item item = template.queryForObject(sql, param, ITEM_ROW_MAPPER2);
		return item;

	}

	/**
	 * 大中小カテゴリ検索
	 * 
	 * @param dai
	 * @param chu
	 * @param sho
	 * @param offset
	 * @return
	 */
	public List<Item> selectCategory(Integer dai, Integer chu, Integer sho, Integer offset, SearchForm form) {

//		String sql = "SELECT \r\n" + 
//				"i.id i_id,\r\n" + 
//				"i.name i_name , \r\n" + 
//				"i.condition i_condition, \r\n" + 
//				"i.category i_category, \r\n" + 
//				"i.brand i_brand,\r\n" + 
//				"i.price i_price,\r\n" + 
//				"i.shipping i_shipping,\r\n" + 
//				"i.description i_description,\r\n" + 
//				"c1.id syo_id ,\r\n" + 
//				"c1.parent sho_parent ,\r\n" + 
//				"c2.id chu_id,\r\n" + 
//				"c2.parent chu_parent ,\r\n" + 
//				"c1.name_all c1_name_all \r\n" + 
//				"from items i\r\n" + 
//				"left join category c1 on c1.id = i.category  left join category c2 on c1.parent = c2.id "+
//				" where 1=1";

		String sql = "SELECT  \r\n" + "i.id i_id,\r\n" + "i.name i_name ,\r\n" + "i.condition i_condition, \r\n"
				+ "i.category i_category, \r\n" + "i.brand i_brand,\r\n" + "i.price i_price,\r\n"
				+ "i.shipping i_shipping,\r\n" + "i.description i_description,\r\n" + "c1.id c1_id ,\r\n"
				+ "c2.id c2_id,\r\n" + "c2.parent c2_parent ,\r\n" + "c1.name_all c1_name_all ,\r\n"
				+ "c1.name c1_name,\r\n" + "c2.name c2_name,\r\n" + "c3.id c3_id ,\r\n" + "c3.name c3_name\r\n"
				+ "from items i\r\n" + "left join category c1 on c1.id = i.category\r\n"
				+ "left join category c2 on c1.parent = c2.id \r\n" + "left join category c3 on c2.parent = c3.id\r\n"
				+ "where 1=1";

		MapSqlParameterSource param = new MapSqlParameterSource();
		if (sho != null) {
			sql += " AND c1.id = :sho";
			param.addValue("sho", sho);// 小カテゴリ
		} else if (chu != null) {
			sql += " AND  c2.id = :chu";
			param.addValue("chu", chu);// 中カテゴリ
		} else if (dai != null) {
			sql += " AND c2.parent = :dai";
			param.addValue("dai", dai);// 大カテゴリ
		}

		
		
		if (!StringUtils.isEmpty(form.getName())) {
			sql += " AND i.name LIKE :name";
			param.addValue("name", "%" + form.getName() + "%");
		}
	
		if (!StringUtils.isEmpty(form.getBrand())) {
			sql += " AND i.brand = :brand";
			param.addValue("brand",form.getBrand());
		}

		sql += " LIMIT 20 offset :offset";
		param.addValue("offset", offset);

		System.out.println(sql);
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER2);
		
		System.out.println(itemList);
	
		return itemList;

	}

	/**
	 * 大中カテゴリ検索
	 * 
	 * @param dai
	 * @param chu
	 * @param offset
	 * @return
	 */
	public List<Item> selectCategory(Integer dai, Integer chu, Integer offset, SearchForm form) {

		String sql = "SELECT  \r\n" + "i.id i_id,\r\n" + "i.name i_name ,\r\n" + "i.condition i_condition, \r\n"
				+ "i.category i_category, \r\n" + "i.brand i_brand,\r\n" + "i.price i_price,\r\n"
				+ "i.shipping i_shipping,\r\n" + "i.description i_description,\r\n" + "c1.id c1_id ,\r\n"
				+ "c2.id c2_id,\r\n" + "c2.parent c2_parent ,\r\n" + "c1.name_all c1_name_all ,\r\n"
				+ "c1.name c1_name,\r\n" + "c2.name c2_name,\r\n" + "c3.id c3_id ,\r\n" + "c3.name c3_name\r\n"
				+ "from items i\r\n" + "left join category c1 on c1.id = i.category\r\n"
				+ "left join category c2 on c1.parent = c2.id \r\n" + "left join category c3 on c2.parent = c3.id\r\n"
				+ " where 1=1";

		MapSqlParameterSource param = new MapSqlParameterSource();

		if (chu != null) {
			sql += " AND  c2.id = :chu";
			param.addValue("chu", chu);// 中カテゴリ
		} else if (dai != null) {
			sql += " AND c2.parent = :dai";
			param.addValue("dai", dai);// 大カテゴリ
		}
		if (!StringUtils.isEmpty(form.getName())) {
			sql += " AND i.name LIKE :name";
			param.addValue("name", "%" + form.getName() + "%");
		}
		
		if (!StringUtils.isEmpty(form.getBrand())) {
			sql += " AND i.brand = :brand";
			param.addValue("brand", form.getBrand());
		}

		sql += " LIMIT 20 offset :offset";
		param.addValue("offset", offset);

		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER2);
		return itemList;

	}

	/**
	 * 大カテゴリ検索
	 * 
	 * @param dai
	 * @param offset
	 * @return
	 */
	public List<Item> selectCategory(Integer dai, Integer offset, SearchForm form) {

		String sql = "SELECT  \r\n" + "i.id i_id,\r\n" + "i.name i_name ,\r\n" + "i.condition i_condition, \r\n"
				+ "i.category i_category, \r\n" + "i.brand i_brand,\r\n" + "i.price i_price,\r\n"
				+ "i.shipping i_shipping,\r\n" + "i.description i_description,\r\n" + "c1.id c1_id ,\r\n"
				+ "c2.id c2_id,\r\n" + "c2.parent c2_parent ,\r\n" + "c1.name_all c1_name_all ,\r\n"
				+ "c1.name c1_name,\r\n" + "c2.name c2_name,\r\n" + "c3.id c3_id ,\r\n" + "c3.name c3_name\r\n"
				+ "from items i\r\n" + "left join category c1 on c1.id = i.category\r\n"
				+ "left join category c2 on c1.parent = c2.id \r\n" + "left join category c3 on c2.parent = c3.id\r\n"
				+ "where 1=1";

		MapSqlParameterSource param = new MapSqlParameterSource();

		if (dai != null) {
			sql += " AND c2.parent = :dai";
			param.addValue("dai", dai);// 大カテゴリ
		}

		// 商品名（あいまい検索）
		if (!StringUtils.isEmpty(form.getName())) {
			sql += " AND i.name LIKE :name";
			param.addValue("name", "%" + form.getName() + "%");
		}
		// ブランド名
		if (!StringUtils.isEmpty(form.getBrand())) {
			sql += " AND i.brand = :brand";
			param.addValue("brand", form.getBrand());
		}

		sql += " LIMIT 20 offset :offset";
		param.addValue("offset", offset);

		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER2);
		return itemList;

	}

	
	/**
	 * カテゴリ選択なしの検索.
	 * @param name
	 * @param offset
	 * @param form
	 * @return
	 */
	public List<Item> itemList(Integer offset, SearchForm form) {
//		String sql = "SELECT  \r\n" + "i.id i_id,\r\n" + "i.name i_name ,\r\n" + "i.condition i_condition, \r\n"
//				+ "i.category i_category, \r\n" + "i.brand i_brand,\r\n" + "i.price i_price,\r\n"
//				+ "i.shipping i_shipping,\r\n" + "i.description i_description,\r\n" + "c1.id c1_id ,\r\n"
//				+ "c2.id c2_id,\r\n" + "c2.parent c2_parent ,\r\n" + "c1.name_all c1_name_all ,\r\n"
//				+ "c1.name c1_name,\r\n" + "c2.name c2_name,\r\n" + "c3.id c3_id ,\r\n" + "c3.name c3_name\r\n"
//				+ "from items i\r\n" + "left join category c1 on c1.id = i.category\r\n"
//				+ "left join category c2 on c1.parent = c2.id \r\n" + "left join category c3 on c2.parent = c3.id\r\n"
//				+ "WHERE 1=1";
//		
		String sql = "SELECT  \r\n" + "i.id i_id,\r\n" + "i.name i_name ,\r\n" + "i.condition i_condition, \r\n"
				+ "i.category i_category, \r\n" + "i.brand i_brand,\r\n" + "i.price i_price,\r\n"
				+ "i.shipping i_shipping,\r\n" + "i.description i_description,\r\n" + "c1.id c1_id ,\r\n"
				+ "c2.id c2_id,\r\n" + "c2.parent c2_parent ,\r\n" + "c1.name_all c1_name_all ,\r\n"
				+ "c1.name c1_name,\r\n" + "c2.name c2_name,\r\n" + "c3.id c3_id ,\r\n" + "c3.name c3_name\r\n"
				+ "from items i\r\n" + "left join category c1 on c1.id = i.category\r\n"
				+ "left join category c2 on c1.parent = c2.id \r\n" + "left join category c3 on c2.parent = c3.id\r\n"
				+ "where 1=1 AND i.name Like :name";

		MapSqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + form.getName() + "%");
			
				if (!StringUtils.isEmpty(form.getBrand())) {
					sql += " AND i.brand = :brand";
					param.addValue("brand", form.getBrand());
				}
				
				sql += " LIMIT 20 offset :offset";
				param.addValue("offset", offset);
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER2);
		
		return itemList;

	}

	/**
	 * 商品の追加を行う.
	 * 
	 * @param item
	 */
	public void addItem(Item item) {
		String sql = "insert into items (name,condition,category,brand,price,shipping,description)values(:name,:condition,:category,:brand,:price,:shipping,:description);";
		SqlParameterSource param = new BeanPropertySqlParameterSource(item);
		template.update(sql, param);
	}

}
