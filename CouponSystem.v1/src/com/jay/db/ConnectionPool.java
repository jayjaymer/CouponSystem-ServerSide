package com.jay.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class ConnectionPool {

	// works like a gun magazine
	private Stack<Connection> connections = new Stack<>();
	
	// = new ConnectionPool instance
	private static ConnectionPool instance = null;
	
	// crafting the connections by checking the url + username + password of the DB
	private ConnectionPool() {
		for (int i = 1; i <= 10; i++) {
			System.out.println("Creating connection #" + i);
			try {
				Connection conn = DriverManager.getConnection(DatabaseManager.getUrl(), DatabaseManager.getUsername(),
						DatabaseManager.getPassword());
				connections.push(conn);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	// Creates a locked singleton connection pool FROM THIS CLASS
	public static ConnectionPool getInstance() {
		if (instance == null) {
			synchronized (ConnectionPool.class) {
				if (instance == null) {
					instance = new ConnectionPool();
				}
			}
		}
		return instance;
	}
	
	// checks if the stack is empty, if yes it will for a connection,
	// if not empty it will pop the top of the stack
	public Connection getConnection() throws InterruptedException {

		synchronized (connections) {

			if (connections.isEmpty()) {
				connections.wait();
			}

			return connections.pop();
		}
	}
	
	// will push a connection to the stack
	public void returnConnection(Connection conn) {

		synchronized (connections) {
			connections.push(conn);
			connections.notify();
		}
	}
	
	// will wait until all connections are poped and ONLY THAN will close the the connection
	public void closeAllConnection() throws InterruptedException {

		synchronized (connections) {

			while (connections.size() < 10) {
				connections.wait();
			}

			for (Connection conn : connections) {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
		}
	}
}
