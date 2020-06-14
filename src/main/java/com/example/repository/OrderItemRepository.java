package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.OrderItem;

/**
 * 注文商品テーブルを扱うレポジトリー.
 * 
 * @author ashibe
 *
 */
@Repository
public class OrderItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<OrderItem> ORDERITEM_ROW_MAPPER = (rs, i) -> {
		OrderItem orderItem = new OrderItem();
		orderItem.setId(rs.getInt("id"));
		orderItem.setItemId(rs.getInt("item_id"));
		orderItem.setOrderId(rs.getInt("order_id"));
		orderItem.setQuantity(rs.getInt("quantity"));
		return orderItem;
	};

	/**
	 * order_itemsテーブルに注文商品を挿入する.
	 * 
	 * @param orderItem
	 */
	public void insertItem(OrderItem orderItem) {
		String sql = "insert into order_items(item_id, order_id, quantity)values(:itemId, :orderId, :quantity);";
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderItem);
		template.update(sql, param);
	}
	
	/**
	 * idが一致する商品を削除するメソッド.
	 * 
	 * @param orderItemId 
	 */
	public void deleteItem(Integer orderItemId) {
		String sql = "DELETE FROM order_items WHERE id = :orderItemId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderItemId", orderItemId);
		template.update(sql, param);
	}
	
	

}
