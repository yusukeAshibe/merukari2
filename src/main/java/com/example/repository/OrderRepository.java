package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;
import com.example.domain.Order;
import com.example.domain.OrderItem;

/**
 * オーダー情報を扱うレポシトリ.
 * 
 * @author ashibe
 *
 */
@Repository
public class OrderRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Order> ORDER_ROW_MAPPER = (rs, i) -> {
		Order order = new Order();
		order.setId(rs.getInt("id"));
		order.setUserId(rs.getInt("user_id"));
		order.setStatus(rs.getInt("status"));
		order.setTotalPrice(rs.getInt("total_price"));
		order.setOrderDate(rs.getDate("order_date"));
		order.setDestinationName(rs.getString("destination_name"));
		order.setDestinationEmail(rs.getString("destination_email"));
		order.setDestinationZipcode(rs.getString("destination_zipcode"));
		order.setDestinationAddress(rs.getString("destination_address"));
		order.setDestinationTel(rs.getString("destination_tel"));
		order.setDeliveryTime(rs.getTimestamp("delivery_time"));
		order.setPaymentMethod(rs.getInt("payment_method"));
		return order;
	};

	private static final RowMapper<Order> ORDER_ROW_MAPPER2 = (rs, i) -> {
		Order order = new Order();
		order.setId(rs.getInt("id"));
		return order;
	};

	private static final ResultSetExtractor<List<Order>> ORDER_RESULT_SET_EXTRACTOR = (rs) -> {
		Order order = new Order();
		List<Order> orderList = new ArrayList<>();
		List<OrderItem> orderItemList = new ArrayList<>();

		int beforeOrderId = 0;// 追加した商品のorderId

		while (rs.next()) {
			int nowOrderId = rs.getInt("o_id");// 今現在のオーダーID
			if (nowOrderId != beforeOrderId) {// 前回のordersテーブルのIdと今回のordersテーブルのIdを比較して違ったらオーダー情報を入れる.(同じならいれない）
				order = new Order();
				orderItemList = new ArrayList<>();
				order.setId(rs.getInt("o_id"));
				order.setUserId(rs.getInt("o_user_id"));
				order.setStatus(rs.getInt("o_status"));
				order.setTotalPrice(rs.getInt("o_total_price"));
				order.setOrderDate(rs.getDate("o_order_date"));
				order.setDestinationName(rs.getString("o_destination_name"));
				order.setDestinationEmail(rs.getString("o_destination_email"));
				order.setDestinationZipcode(rs.getString("o_destination_zipcode"));
				order.setDestinationAddress(rs.getString("o_destination_address"));
				order.setDestinationTel(rs.getString("o_destination_tel"));
				order.setDeliveryTime(rs.getTimestamp("o_delivery_time"));
				order.setPaymentMethod(rs.getInt("o_payment_method"));
				// order.setOrderItemList(orderItemList);
				orderList.add(order);
			}

			// orderItemの情報を入れる。
			OrderItem orderItem = new OrderItem();
			Item item = new Item();
			orderItem.setId(rs.getInt("oi_id"));
			orderItem.setItemId(rs.getInt("oi_item_id"));
			orderItem.setOrderId(rs.getInt("oi_order_id"));
			orderItem.setQuantity(rs.getInt("oi_quantity"));
			// orderItem.setItem(item);
			orderItemList.add(orderItem);
			order.setOrderItemList(orderItemList);

			// itemの情報を入れる
			item.setId(rs.getInt("i_id"));
			item.setName(rs.getString("i_name"));
			item.setPrice(rs.getDouble("i_price"));
			item.setCategory(rs.getInt("i_category"));
			item.setBrand(rs.getString("i_brand"));
			item.setCondition(rs.getInt("i_condition"));
			item.setShipping(rs.getInt("i_shipping"));
			item.setDescription(rs.getString("i_description"));
			orderItem.setItem(item);

			// 今追加したオーダー情報のID
			beforeOrderId = rs.getInt("o_id");

		}

		return orderList;
	};

	/**
	 * ユーザーIDで注文情報を取得するメソッド.
	 * 
	 * @param userId ユーザーID
	 * @return
	 */
	public List<Order> OrderingInformationFindByUserId(Integer userId, Integer status) {
		String sql = "select\r\n" + "o.id o_id,\r\n" + "o.user_id o_user_id,\r\n" + "o.status o_status,\r\n"
				+ "o.total_price o_total_price,\r\n" + "o.order_date o_order_date,\r\n"
				+ "o.destination_name o_destination_name,\r\n" + "o.destination_email o_destination_email,\r\n"
				+ "o.destination_zipcode o_destination_zipcode,\r\n"
				+ "o.destination_address o_destination_address,\r\n" + "o.destination_tel o_destination_tel,\r\n"
				+ "o.delivery_time o_delivery_time,\r\n" + "o.payment_method o_payment_method,\r\n" + "\r\n"
				+ "i.id i_id,\r\n" + "i.name i_name,\r\n" + "i.price i_price,\r\n" + "i.category i_category,\r\n"
				+ "i.brand i_brand,\r\n" + "i.condition i_condition,\r\n" + "i.shipping i_shipping,\r\n"
				+ "i.description i_description,\r\n" + "\r\n" + "oi.id oi_id, \r\n" + "oi.item_id oi_item_id,\r\n"
				+ "oi.order_id oi_order_id,\r\n" + "oi.quantity oi_quantity\r\n" + "\r\n" + "from orders o \r\n"
				+ "inner join order_items oi ON o.id = oi.order_id\r\n" + "left join items i ON oi.item_id = i.id\r\n"
				+ "\r\n" + "where o.user_id = :userId " + "and o.status = :status \r\n" + "order by oi.id ";

		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("status", status);
		List<Order> orderList = template.query(sql, param, ORDER_RESULT_SET_EXTRACTOR);
		return orderList;
	}

	/**
	 * 注文履歴を取得.
	 * 
	 * @param userId
	 * @return
	 */
	public List<Order> OrderHistoryFindByUserId(Integer userId) {
		String sql = "select o.id o_id,o.user_id o_user_id,o.status o_status,\r\n"
				+ "o.total_price o_total_price,o.order_date o_order_date,\r\n"
				+ "o.destination_name o_destination_name,\r\n" + "o.destination_email o_destination_email,\r\n"
				+ "o.destination_zipcode o_destination_zipcode,\r\n"
				+ "o.destination_address o_destination_address,o.destination_tel o_destination_tel,\r\n"
				+ "o.delivery_time o_delivery_time,o.payment_method o_payment_method,\r\n"
				+ "i.id i_id,i.name i_name,i.price i_price,i.category i_category,\r\n"
				+ "i.brand i_brand,i.condition i_condition,i.shipping i_shipping,\r\n"
				+ "i.description i_description,oi.id oi_id, oi.item_id oi_item_id,\r\n"
				+ "oi.order_id oi_order_id,oi.quantity oi_quantity from orders o \r\n"
				+ "inner join order_items oi ON o.id = oi.order_id left join items i ON oi.item_id = i.id\r\n"
				+ "where o.user_id =:userId and o.status != 0 order by oi.id ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<Order> orderHistoryList = template.query(sql, param, ORDER_RESULT_SET_EXTRACTOR);
		return orderHistoryList;

	}

	/**
	 * orderIdで注文履歴を取得.
	 * 
	 * @param userId
	 * @return
	 */
	public Order OrderHistoryFindOrderId(Integer orderId) {
		String sql = "select \r\n" + 
				"o.id o_id,\r\n" + 
				"o.user_id\r\n" + 
				"o_user_id,o.status o_status,\r\n" + 
				"o.total_price o_total_price,\r\n" + 
				"o.order_date o_order_date,\r\n" + 
				"o.destination_name o_destination_name,\r\n" + 
				"o.destination_email o_destination_email,\r\n" + 
				"o.destination_zipcode o_destination_zipcode,\r\n" + 
				"o.destination_address o_destination_address,\r\n" + 
				"o.destination_tel o_destination_tel,\r\n" + 
				"o.delivery_time o_delivery_time,\r\n" + 
				"o.payment_method o_payment_method,\r\n" + 
				"i.id i_id,i.name i_name,\r\n" + 
				"i.price i_price,i.category i_category,\r\n" + 
				"i.brand i_brand,i.condition i_condition,\r\n" + 
				"i.shipping i_shipping,\r\n" + 
				"i.description i_description,\r\n" + 
				"oi.id oi_id,\r\n" + 
				"oi.item_id oi_item_id,\r\n" + 
				"oi.order_id oi_order_id,\r\n" + 
				"oi.quantity oi_quantity \r\n" + 
				"from orders o \r\n" + 
				"inner join order_items oi ON o.id = oi.order_id \r\n" + 
				"left join items i ON oi.item_id = i.id\r\n" + 
				"where o.id = :orderId \r\n" + 
				"and o.status != 0 ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderId", orderId);
		List<Order> orderList = template.query(sql, param, ORDER_RESULT_SET_EXTRACTOR);
		//System.out.println("リスト：　　　"+orderList);
		Order order=orderList.get(0);
		//System.out.println("単品：　　　"+order);
		return order;
	}

	/**
	 * ordersテーブルに注文情報を挿入する.
	 * 
	 * @param order 注文情報
	 */
	public Order insert(Order order) {
		String sql = "insert into orders(\r\n" + "user_id,\r\n" + "status,\r\n" + "total_price,\r\n" + "order_date,\r\n"
				+ "destination_name,\r\n" + "destination_email,\r\n" + "destination_zipcode,\r\n"
				+ "destination_address,\r\n" + "destination_tel,\r\n" + "delivery_time,\r\n" + "payment_method\r\n"
				+ ") values(\r\n" + ":userId,\r\n" + "0,\r\n" + ":totalPrice,\r\n" + ":orderDate,\r\n"
				+ ":destinationName,\r\n" + ":destinationEmail,\r\n" + ":destinationZipcode,\r\n"
				+ ":destinationAddress,\r\n" + ":destinationTel,\r\n" + ":deliveryTime,\r\n" + ":paymentMethod)\r\n"
				+ "returning id";
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		Order newOrder = template.queryForObject(sql, param, ORDER_ROW_MAPPER2);

		return newOrder;

	}

	/**
	 * ordersテーブルからIDを取得
	 * 
	 * @return
	 */
	public Order FindOrderId(Integer userId) {
		String sql = "select * from orders\r\n" + "where user_id=:userId\r\n" + "and status = 0";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		try {
			Order order = template.queryForObject(sql, param, ORDER_ROW_MAPPER);
			return order;
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * カート内の商品の種類の検索.
	 * 
	 * @param userId
	 * @return
	 */
	public Integer CountItemInShoppingCartByUserId(Integer userId) {
		String sql = "select \r\n" + "count(*)\r\n" + "from orders o\r\n"
				+ "inner join order_items oi on o.id=oi.order_id\r\n" + "left join items i on oi.item_id=i.id\r\n"
				+ "where o.user_id=:userId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		Integer count = template.queryForObject(sql, param, Integer.class);
		return count;
	}

	/**
	 * 注文確定時にorderテーブルの書き換え.
	 * 
	 * @param order
	 */
	public void updateOrder(Order order) {
		String sql = "update orders set\r\n" + "status =:status,\r\n" + "total_price = :totalPrice, \r\n"
				+ "order_date = :orderDate, \r\n" + "destination_name = :destinationName,\r\n"
				+ "destination_email = :destinationEmail,\r\n" + "destination_zipcode = :destinationZipcode,\r\n"
				+ "destination_address = :destinationAddress,\r\n" + "destination_tel = :destinationTel, \r\n"
				+ "delivery_time = :deliveryTime,\r\n" + "payment_method = :paymentMethod \r\n" + "where id = :id\r\n"
				+ "and status = 0;";
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		template.update(sql, param);

	}
	
	/**
	 * 注文情報のListの取得(履歴表示.
	 * @param userId
	 * @return
	 */
	public List <Order> OrderListFindByOrderId(Integer userId){
		String sql="select * from orders where status !=0 and user_id=userId";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("userId", userId);
		List<Order> orderList=template.query(sql, param ,ORDER_ROW_MAPPER2);
		return orderList;
		
	}

}
