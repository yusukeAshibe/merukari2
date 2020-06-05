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
		item.setConditionName(rs.getString("co_name"));

		return item;
	};

	/**
	 * 商品一覧を取得.
	 * 
	 * @return
	 */
	public List<Item> findAll(Integer offset, SearchForm form) {

		String sql = "SELECT  \r\n" + "i.id i_id,\r\n" + "i.name i_name ,\r\n" + "i.condition i_condition, \r\n"
				+ "i.category i_category, \r\n" + "i.brand i_brand,\r\n" + "i.price i_price,\r\n"
				+ "i.shipping i_shipping,\r\n" + "i.description i_description,\r\n" + "c1.id c1_id ,\r\n"
				+ "c2.id c2_id,\r\n" + "c2.parent c2_parent ,\r\n" + "c1.name_all c1_name_all ,\r\n"
				+ "c1.name c1_name,\r\n" + "c2.name c2_name,\r\n" + "c3.id c3_id ,\r\n"
				+ "c3.name c3_name ,co.name co_name\r\n" + "from items i\r\n"
				+ "left join category c1 on c1.id = i.category\r\n" + "left join category c2 on c1.parent = c2.id \r\n"
				+ "left join category c3 on c2.parent = c3.id\r\n"
				+ "left join condition co on co.id=i.condition where 1=1";
		MapSqlParameterSource param = new MapSqlParameterSource().addValue("offset", offset);

		if (form.getCondition() == null) {
			sql += "";
		} else if (("").equals(form.getCondition())) {
			sql += "";
		} else if (form.getCondition().equals("brandNew")) {
			sql += " AND i.condition=1";
		} else if (form.getCondition().equals("secondHand")) {
			sql += " AND i.condition=2 ";
		} else if (form.getCondition().equals("unknown")) {
			sql += " AND i.condition=3 ";
		}

		if (form.getSortPrice() == null) {
			sql += " order by i.price,i.name";
		} else if (("").equals(form.getSortPrice()) && ("").equals(form.getSortCondition())) {
			sql += " order by i.price,i.name";
		} else if (form.getSortPrice().equals("Ascending")) {
			sql += " order by i.price ,i.name";
		} else if (form.getSortPrice().equals("Descending")) {
			sql += " order by i.price desc ,i.name";
		}
//		}else if (form.getSortCondition().equals("brandNew")) {
//			sql += " order by i.condition ,i.name";
//		} else if (form.getSortCondition().equals("secondHand")) {
//			sql += " order by i.condition=2 desc ,i.condition asc ,i.name";
//		} else if (form.getSortCondition().equals("unknown")) {
//			sql += " order by i.condition=3 desc,i.condition asc ,i.name";
//		}

		if (!StringUtils.isEmpty(form.getSortCondition())) {
			sql += " order by i.condition=:condition desc,i.condition asc,i.name";
			param.addValue("condition", Integer.parseInt(form.getSortCondition()));
		}

		sql += " LIMIT 20 offset :offset";
		System.out.println(sql);
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER2);
		return itemList;

	}

	/**
	 * カテゴリ選択なしの検索.
	 * 
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
				+ "c1.name c1_name,\r\n" + "c2.name c2_name,\r\n" + "c3.id c3_id ,\r\n"
				+ "c3.name c3_name  ,co.name co_name\r\n" + "from items i\r\n"
				+ "left join category c1 on c1.id = i.category\r\n" + "left join category c2 on c1.parent = c2.id \r\n"
				+ "left join category c3 on c2.parent = c3.id left join condition co on co.id=i.condition\r\n"
				+ "where 1=1 AND i.name Like :name";

		MapSqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + form.getName() + "%");

		System.out.println("カテゴリなし");
		if (!StringUtils.isEmpty(form.getBrand())) {
			sql += " AND i.brand = :brand";
			param.addValue("brand", form.getBrand());
		}
		if (form.getCondition() == null) {
			sql += "";
		} else if (("").equals(form.getCondition())) {
			sql += "";
		} else if (form.getCondition().equals("brandNew")) {
			sql += " AND i.condition=1";
		} else if (form.getCondition().equals("secondHand")) {
			sql += " AND i.condition=2 desc ";
		} else if (form.getCondition().equals("unknown")) {
			sql += " AND i.condition=3 ";
		}

		if (form.getSortPrice() == null) {
			sql += " order by i.price,i.name";
		} else if (("").equals(form.getSortPrice()) && ("").equals(form.getSortCondition())) {
			sql += " order by i.price,i.name";
		} else if (form.getSortPrice().equals("Ascending")) {
			sql += " order by i.price ,i.name";
		} else if (form.getSortPrice().equals("Descending")) {
			sql += " order by i.price desc ,i.name";
		}	
		if (!StringUtils.isEmpty(form.getSortCondition())) {
				sql += " order by i.condition=:condition desc,i.condition asc,i.name";
				param.addValue("condition", Integer.parseInt(form.getSortCondition()));
			}
//		} else if (form.getSortCondition().equals("brandNew")) {
//			sql += " order by i.condition=1 ,i.name";
//		} else if (form.getSortCondition().equals("secondHand")) {
//			sql += " order by i.condition=2 desc ,i.condition asc ,i.name";
//		} else if (form.getSortCondition().equals("unknown")) {
//			sql += " order by i.condition=3 desc,i.condition asc ,i.name";
//		}

		sql += " LIMIT 20 offset :offset";
		param.addValue("offset", offset);
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER2);
		System.out.println(sql);
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
				+ "c1.name c1_name,\r\n" + "c2.name c2_name,\r\n" + "c3.id c3_id ,\r\n"
				+ "c3.name c3_name ,co.name co_name\r\n" + "from items i\r\n"
				+ "left join category c1 on c1.id = i.category\r\n" + "left join category c2 on c1.parent = c2.id \r\n"
				+ "left join category c3 on c2.parent = c3.id left join condition co on co.id=i.condition\r\n"
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
				+ "c1.name c1_name,\r\n" + "c2.name c2_name,\r\n" + "c3.id c3_id ,\r\n"
				+ "c3.name c3_name  ,co.name co_name\r\n" + "from items i\r\n"
				+ "left join category c1 on c1.id = i.category\r\n" + "left join category c2 on c1.parent = c2.id \r\n"
				+ "left join category c3 on c2.parent = c3.id left join condition co on co.id=i.condition\r\n"
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
			param.addValue("brand", form.getBrand());
		}

		if (form.getCondition() == null) {
			sql += "";
		} else if (("").equals(form.getCondition())) {
			sql += "";
		} else if (form.getCondition().equals("brandNew")) {
			sql += " AND i.condition=1";
		} else if (form.getCondition().equals("secondHand")) {
			sql += " AND i.condition=2 ";
		} else if (form.getCondition().equals("unknown")) {
			sql += " AND i.condition=3 ";
		}

		if (form.getSortPrice() == null) {
			sql += " order by i.price,i.name";
		} else if (("").equals(form.getSortPrice()) && ("").equals(form.getSortCondition())) {
			sql += " order by i.price,i.name";
		} else if (form.getSortPrice().equals("Ascending")) {
			sql += " order by i.price ,i.name";
		} else if (form.getSortPrice().equals("Descending")) {
			sql += " order by i.price desc ,i.name";
		}
		
		if (!StringUtils.isEmpty(form.getSortCondition())) {
			sql += " order by i.condition=:condition desc,i.condition asc,i.name";
			param.addValue("condition", Integer.parseInt(form.getSortCondition()));
		}
//		
//		else if (form.getSortCondition().equals("brandNew")) {
//			sql += " order by i.condition ,i.name";
//		} else if (form.getSortCondition().equals("secondHand")) {
//			sql += " order by i.condition=2 desc ,i.condition asc ,i.name";
//		} else if (form.getSortCondition().equals("unknown")) {
//			sql += " order by i.condition=3 desc,i.condition asc ,i.name";
//		}

		sql += " LIMIT 20 offset :offset";
		param.addValue("offset", offset);
		System.out.println("大中小");
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER2);

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
				+ "c1.name c1_name,\r\n" + "c2.name c2_name,\r\n" + "c3.id c3_id ,\r\n"
				+ "c3.name c3_name  ,co.name co_name\r\n" + "from items i\r\n"
				+ "left join category c1 on c1.id = i.category\r\n" + "left join category c2 on c1.parent = c2.id \r\n"
				+ "left join category c3 on c2.parent = c3.id left join condition co on co.id=i.condition\r\n"
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

		if (form.getCondition() == null) {
			sql += "";
		} else if (("").equals(form.getCondition())) {
			sql += "";
		} else if (form.getCondition().equals("brandNew")) {
			sql += " AND  i.condition=1";
		} else if (form.getCondition().equals("secondHand")) {
			sql += " AND i.condition=2 ";
		} else if (form.getCondition().equals("unknown")) {
			sql += " AND i.condition=3 ";
		}

		if (form.getSortPrice() == null) {
			sql += " order by i.price,i.name";
		} else if (("").equals(form.getSortPrice()) && ("").equals(form.getSortCondition())) {
			sql += " order by i.price,i.name";
		} else if (form.getSortPrice().equals("Ascending")) {
			sql += " order by i.price ,i.name";
		} else if (form.getSortPrice().equals("Descending")) {
			sql += " order by i.price desc ,i.name";
		} else if (form.getSortCondition().equals("brandNew")) {
			sql += " order by i.condition ,i.name";	
		} 
		if (!StringUtils.isEmpty(form.getSortCondition())) {
			sql += " order by i.condition=:condition desc,i.condition asc,i.name";
			param.addValue("condition", Integer.parseInt(form.getSortCondition()));
		}
//		else if (form.getSortCondition().equals("secondHand")) {
//			sql += " order by i.condition=2 desc ,i.condition asc ,i.name";
//		} else if (form.getSortCondition().equals("unknown")) {
//			sql += " order by i.condition=3 desc,i.condition asc ,i.name";
//		}

		sql += " LIMIT 20 offset :offset";
		param.addValue("offset", offset);
		System.out.println("大中");
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
				+ "c1.name c1_name,\r\n" + "c2.name c2_name,\r\n" + "c3.id c3_id ,\r\n"
				+ "c3.name c3_name  ,co.name co_name\r\n" + "from items i\r\n"
				+ "left join category c1 on c1.id = i.category\r\n" + "left join category c2 on c1.parent = c2.id \r\n"
				+ "left join category c3 on c2.parent = c3.id left join condition co on co.id=i.condition\r\n"
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
		if (form.getCondition() == null) {
			sql += "";
		} else if (("").equals(form.getCondition())) {
			sql += "";
		} else if (form.getCondition().equals("brandNew")) {
			sql += " AND i.condition=1";
		} else if (form.getCondition().equals("secondHand")) {
			sql += " AND i.condition=2 ";
		} else if (form.getCondition().equals("unknown")) {
			sql += " AND i.condition=3 ";
		}

		if (form.getSortPrice() == null) {
			sql += " order by i.price,i.name";
		} else if (("").equals(form.getSortPrice()) && ("").equals(form.getSortCondition())) {
			sql += " order by i.price,i.name";
		} else if (form.getSortPrice().equals("Ascending")) {
			sql += " order by i.price ,i.name";
		} else if (form.getSortPrice().equals("Descending")) {
			sql += " order by i.price desc ,i.name";
		} 
		
		if (!StringUtils.isEmpty(form.getSortCondition())) {
			sql += " order by i.condition=:condition desc,i.condition asc,i.name";
			param.addValue("condition", Integer.parseInt(form.getSortCondition()));
		}
		
		
//		else if (form.getSortCondition().equals("brandNew")) {
//			sql += " order by i.condition ,i.name";
//		} else if (form.getSortCondition().equals("secondHand")) {
//			sql += " order by i.condition=2 desc ,i.condition asc ,i.name";
//		} else if (form.getSortCondition().equals("unknown")) {
//			sql += " order by i.condition=3 desc,i.condition asc ,i.name";
//		}
		sql += " LIMIT 20 offset :offset";
		param.addValue("offset", offset);
		System.out.println("大");
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

	/**
	 * 検索された商品の数量を数える
	 * 
	 * @param form
	 */
	public Integer countItem(SearchForm form) {
		String sql = "select count(*) from items i \r\n" + "left join category c1 on c1.id = i.category \r\n"
				+ "left join category c2 on c1.parent = c2.id \r\n"
				+ "left join category c3 on c2.parent = c3.id where 1=1";

		MapSqlParameterSource param = new MapSqlParameterSource();

		if (!(StringUtils.isEmpty(form.getSyoCategory()))) {
			System.out.println("小カテゴリの選択" + form);
			sql += " AND c1.id = :sho";
			param.addValue("sho", Integer.parseInt(form.getSyoCategory()));// 小カテゴリ
		} else if (!(StringUtils.isEmpty(form.getChuCategory()))) {
			sql += " AND  c2.id = :chu";
			param.addValue("chu", Integer.parseInt(form.getChuCategory()));// 中カテゴリ
		} else if (!(StringUtils.isEmpty(form.getParent()))) {
			sql += " AND c2.parent = :dai";
			param.addValue("dai", Integer.parseInt(form.getParent()));// 大カテゴリ
		}

		// 商品名（あいまい検索）
		if (!StringUtils.isEmpty(form.getName())) {
			sql += " AND i.name LIKE :name";
			param.addValue("name", "%" + form.getName() + "%");
		}
		// ブランド名
		if (!StringUtils.isEmpty(form.getBrand())) {
			sql += " AND brand = :brand";
			param.addValue("brand", form.getBrand());
		}
		if (form.getCondition() == null) {
			sql += "";
		} else if (("").equals(form.getCondition())) {
			sql += "";
		} else if (form.getCondition().equals("brandNew")) {
			sql += " AND i.condition=1";
		} else if (form.getCondition().equals("secondHand")) {
			sql += " AND i.condition=2 ";
		} else if (form.getCondition().equals("unknown")) {
			sql += " AND i.condition=3 ";
		}
		Integer count = template.queryForObject(sql, param, Integer.class);
		return count;
	}

}
