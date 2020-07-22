package com.jay.load;

import java.sql.SQLException;

import com.jay.db.ConnectionPool;
import com.jay.db.DatabaseManager;

import schedule.CouponTerminatorDailyJob;

public class CouponSystem {

	/**
	 * this class is responsible for a full application boot - ~crating a singleton
	 * for the coupon system ~loading the SQL driver for the workbench ~drop and
	 * create tables in the data base ~getting a singleton of connection(10
	 * connections max) ~startup the daily job that is deleting the expired coupons
	 * ~SHUTDOWN of the daily job and coupon system singleton!
	 */

	private static CouponSystem instance = null;
	private Thread doTheWork = null;

	private CouponSystem() throws ClassNotFoundException, SQLException {
		System.out.println("START");
		System.out.println();

		// 1 - driver load.
		Class.forName("com.mysql.cj.jdbc.Driver");

		// 2 - drop&create database
		createDatabase();

		// 3 - Create connection pool.
		ConnectionPool.getInstance();
		System.out.println();
		System.out.println();
		System.out.println(
				"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ CONNECTION CREATED! ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println();

		// 4 - activate daily thread
	StartupThread();
	}
	
	// 
	private void StartupThread() {
		// this thread will run daily
		doTheWork = new Thread(new CouponTerminatorDailyJob());
		doTheWork.start();
	}

	private void terminateThread() {
		doTheWork.stop();
	}

	public static CouponSystem getInstance() throws SQLException {
		if (instance == null) {
			synchronized (CouponSystem.class) {
				if (instance == null) {
					try {
						instance = new CouponSystem();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return instance;
	}

	public void shutdownCouponSystem() throws InterruptedException {
		// for log off coupon system i need to close the connection pool + terminate
		// thread
		terminateThread();
		ConnectionPool.getInstance().closeAllConnection();
		try {
			ConnectionPool.getInstance().closeAllConnection();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println();
		System.out.println("END - Couponsystem Connection Closed.");
	}

	private void createDatabase() throws SQLException {

		DatabaseManager.dropAllTables();
		DatabaseManager.createAllTables();
		DatabaseManager.insertTestdata();
	}

}
