package org.example;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MyRepository {

//	query a database through JDBC:
//• load the JDBC driver (this is a class) - the class loader needs the class to be loaded
//• establish a connection to the DBMS• create a statement
//• execute it => obtain a ResultSet
//• process the ResultSet Object
//• close the connection

	private Connection connection;

	public void connect() {
		String jdbcUrl = "jdbc:postgresql://localhost:3050/myshop";
		String username = "postgres";
		String password = "anadb";

		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(jdbcUrl, username, password);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Order> getAllOrders() {
		List<Order> orderList = new ArrayList<>();
		try {
			connect();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM orders_products op\n" +
					"JOIN orders o ON o.id = op.order_id\n" +
					"JOIN products p ON p.id = op.product_id\n" +
					"ORDER BY o.address");
			while (resultSet.next()) {
				Order order;
				int id = resultSet.getInt("order_id");
				Optional<Order> orderOptional = orderList.stream()
						.filter(o -> o.getId() == id)
						.findAny();

//				if (orderOptional.isPresent()) {
//					order = orderOptional.get();
//				} else {
//					order = new Order();
//				}

				if (orderOptional.isPresent()) {
					Product product = new Product(resultSet.getInt("product_id"), resultSet.getString("name"), resultSet.getInt("quantity"));
					for (Order currentOrder : orderList) {
						if (currentOrder.getId() == id) {
							currentOrder.getProductList().add(product);
						}
					}
				} else {
					order = new Order();
					Date date = resultSet.getDate("date");
					String address = resultSet.getString("address");
					order.setAddress(address);
					order.setDate(date);
					order.setId(id);
					orderList.add(order);

					Product product = new Product(resultSet.getInt("product_id"), resultSet.getString("name"), resultSet.getInt("quantity"));
					order.getProductList().add(product);
				}
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		closeConnection();
		System.out.println(orderList);
		return orderList;
	}
}
