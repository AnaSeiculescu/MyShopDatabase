package org.example;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

		// Register the PostmydatabasegreSQL driver

		try {
			Class.forName("org.postgresql.Driver");

			// Connect to the database
			connection = DriverManager.getConnection(jdbcUrl, username, password);
		} catch (Exception e) {
			System.out.println(e);
		}
		// Perform desired database operations

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
			ResultSet resultSet = statement.executeQuery("SELECT * FROM orders");
			while (resultSet.next())
			{
				Order order = new Order();
				int id = resultSet.getInt("id");
				Date date = resultSet.getDate("date");
				String address = resultSet.getString("address");
				order.setAddress(address);
				order.setDate(date);
				order.setId(id);
				orderList.add(order);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		closeConnection();
		System.out.println(orderList);
		return orderList;
	}
}
