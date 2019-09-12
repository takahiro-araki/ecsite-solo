package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.domain.Topping;

/**
 * オーダーレポジトリ.
 * 
 * @author takahiro.araki
 *
 */
@Repository
public class OrderRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 自動採番されたIDを持ってくる際に利用する変数.
	 */
	private SimpleJdbcInsert insert;
	
	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert=new SimpleJdbcInsert((JdbcTemplate)template.getJdbcOperations());
		//ordersテーブルのidカラムが自動採番されることをDIコンテナに教えてあげる
		SimpleJdbcInsert withTableName=simpleJdbcInsert.withTableName("orders");
		insert=withTableName.usingGeneratedKeyColumns("id");
	}
	
	// リゾルトセットエクストラクターを作成する。
	// ？オーダーリストにする必要あるか？
	private final static ResultSetExtractor<Order> ORDER_RS_EXTRACTOR = (rs) -> {
		// 最も大きなリストをニューする。その他は、変数定義のみを行い、Orderに追加される度にニューできるようにする
		Order order = new Order();
		List<OrderItem> orderItemList = null;
		List<OrderTopping> orderToppingList = null;
		int id = -1;
		int id2 = -1;
		while (rs.next()) {
			if (id != rs.getInt("o_id")) {
				// オーダーオブジェクトを作成する
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
				// リストも作成
				orderItemList = new ArrayList<>();
				order.setOrderItemList(orderItemList);
			}
			// オーダーアイテムのオブジェクトを作る。オーダーのIDがid変数と一致していても、
			// id2と異なる限り回り続ける
			if (rs.getInt("oi_id") != id2) {
				OrderItem orderItem = new OrderItem();
				orderItem.setId(rs.getInt("oi_id"));
				orderItem.setItemId(rs.getInt("oi_item_id"));
				orderItem.setOrderId(rs.getInt("oi_order_id"));
				orderItem.setQuantity(rs.getInt("oi_quantity"));
				orderItem.setSize(rs.getString("oi_size"));
				// アイテムオブジェクトも作成
				Item item = new Item();
				item.setId(rs.getInt("i_id"));
				item.setName(rs.getString("i_name"));
				item.setDescription(rs.getString("i_description"));
				item.setPriceM(rs.getInt("i_price_m"));
				item.setPriceL(rs.getInt("i_price_l"));
				item.setImagePath(rs.getString("i_image_path"));
				item.setDeleted(rs.getBoolean("i_deleted"));
				orderItem.setItem(item);
				// リストも作成
				orderToppingList = new ArrayList<>();
				orderItem.setToppingList(orderToppingList);
				orderItemList.add(orderItem);
			}
			// オーダートッピングのオブジェクトを作る。オーダーアイテムのIDがid２変数と一致していても、異なるIDになるまで回り続ける
			if (rs.getInt("ot_id") != 0) {
				OrderTopping orderTopping = new OrderTopping();
				orderTopping.setId(rs.getInt("ot_id"));
				orderTopping.setOrderItemId(rs.getInt("ot_order_item_id"));
				orderTopping.setToppingId(rs.getInt("ot_topping_id"));
				Topping topping = new Topping();
				topping.setId(rs.getInt("t_id"));
				topping.setName(rs.getString("t_name"));
				topping.setPriceM(rs.getInt("t_price_m"));
				topping.setPriceL(rs.getInt("t_price_l"));
				orderTopping.setTopping(topping);
				orderToppingList.add(orderTopping);
			}
			id = rs.getInt("o_id");
			id2 = rs.getInt("oi_id");
		}
		System.out.println("エクストラクター検証："+order.getId());
		return order;
	};

	private final static RowMapper<Order> ORDER_ROW_MAPPER = (rs, i) -> {
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

	/**
	 * オーダー情報と紐づけられた買うテーブルをユーザーIDで検索する.
	 * 
	 * @param id ID
	 * @return オーダー情報
	 */
	public Order findAllById(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT o.id as o_id ,o.user_id as o_user_id ,o.status as o_status,o.total_price as o_total_price,o.order_date as o_order_date, o.destination_name as o_destination_name ,o.destination_email as o_destination_email ,o.destination_zipcode as o_destination_zipcode ,o.destination_address as o_destination_address ,o.destination_tel as o_destination_tel ,o.delivery_time as o_delivery_time ,o.payment_method as o_payment_method,");
		sql.append(
				"oi.id as oi_id,oi.item_id as oi_item_id ,oi.order_id as oi_order_id,oi.quantity as oi_quantity,oi.size as oi_size,");
		sql.append(
				"i.id as i_id,i.name as i_name ,i.description as i_description,i.price_m as i_price_m,i.price_l as i_price_l,i.image_path as i_image_path,i.deleted as i_deleted,");
		sql.append("ot.id as ot_id,ot.topping_id as ot_topping_id ,ot.order_item_id as ot_order_item_id,");
		sql.append("t.id as t_id ,t.name as t_name,t.price_m as t_price_m,t.price_l as t_price_l ");
		sql.append("FROM order_items oi ");
		sql.append("FULL OUTER JOIN orders o ON o.id = oi.order_id ");
		sql.append("FULL OUTER JOIN items i ON oi.item_id=i.id ");
		sql.append("FULL OUTER JOIN order_toppings ot ON oi.id = ot.order_item_id ");
		sql.append("FULL OUTER JOIN toppings t ON t.id=ot.topping_id WHERE  o.id=:id;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		// queryメソッドは任意のロウマッパ―かリゾルトセットエクストラクターで返した任意のオブジェクトを返す
		Order order = template.query(sql.toString(), param, ORDER_RS_EXTRACTOR);
		System.out.println("レポジトリメソッド検証：ID="+order.getId());
		return order;
	}
	
	/**
	 * オーダー情報を追加する.
	 * 
	 * @param order オーダー情報
	 * @return order idが割り当てられた、オーダー情報
	 */
	public Order insert(Order order) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"INSERT INTO orders(user_id,status,total_price,order_date,destination_name,destination_email,destination_zipcode,destination_address,destination_tel,delivery_time,payment_method) ");
		sql.append(
				"VALUES (:userId,:status,:totalPrice,:orderDate,:destinationName,:destinationEmail,:destinationZipcode,:destinationAddress,:destinationTel,:deliveryTime,:paymentMethod)");
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
//		template.update(sql.toString(), param);
		Number key=insert.executeAndReturnKey(param);
		order.setId(key.intValue());
		System.out.println(key+"が割り当てられました");
		return order;
	}

	/**オーダー情報をユーザーIDで検索する.
	 * @param ユーザーID
	 * @return　オーダー情報リスト
	 */
	public List<Order> loadById(Integer id) {
		String sql = "SELECT id,user_id,status,total_price,order_date,destination_name,destination_email,destination_zipcode,destination_address,destination_tel,delivery_time,payment_method FROM orders WHERE user_id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		List<Order> orderList = template.query(sql, param, ORDER_ROW_MAPPER);
		return orderList;
	}

}