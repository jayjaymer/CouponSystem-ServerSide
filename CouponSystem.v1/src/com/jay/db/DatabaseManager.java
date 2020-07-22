package com.jay.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

	// Connection
	private static Connection connection = null;

	// SQL login
	private static final String url = "jdbc:mysql://localhost:3306/?user=root&serverTimezone=UTC";
	private static final String username = "root";
	private static final String password = "1325897";

	// CREATE QUERY FOR ALL TABLES
	private static final String CREATE_SCHEMA = "CREATE SCHEMA coupon_system ;";
	private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE `coupon_system`.`categories` (\r\n"
			+ "  `ID` INT NOT NULL,\r\n" + "  `NAME` VARCHAR(45) NOT NULL,\r\n" + "  PRIMARY KEY (`ID`));";
	private static final String CREATE_TABLE_COMPANIES = "CREATE TABLE `coupon_system`.`companies` (\r\n"
			+ "  `ID` INT NOT NULL AUTO_INCREMENT,\r\n" + "  `NAME` VARCHAR(45) NOT NULL,\r\n"
			+ "  `EMAIL` VARCHAR(45) NOT NULL,\r\n" + "  `PASSWORD` VARCHAR(45) NOT NULL,\r\n"
			+ "  PRIMARY KEY (`ID`));\r\n";

	private static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE `coupon_system`.`customers` (\r\n"
			+ "  `ID` INT NOT NULL AUTO_INCREMENT,\r\n" + "  `FIRST_NAME` VARCHAR(45) NOT NULL,\r\n"
			+ "  `LAST_NAME` VARCHAR(45) NOT NULL,\r\n" + "  `EMAIL` VARCHAR(45) NOT NULL,\r\n"
			+ "  `PASSWORD` VARCHAR(45) NOT NULL,\r\n" + "  PRIMARY KEY (`ID`));\r\n" + "";
	private static final String CREATE_TABLE_COUPONS = "CREATE TABLE `coupon_system`.`coupons` (\r\n"
			+ "  `ID` INT NOT NULL AUTO_INCREMENT,\r\n" + "  `COMPANY_ID` INT NOT NULL,\r\n"
			+ "  `CATEGORY_ID` INT NOT NULL,\r\n" + "  `TITLE` VARCHAR(45) NOT NULL,\r\n"
			+ "  `DESCRIPTION` VARCHAR(45) NOT NULL,\r\n" + "  `START_DATE` DATE NOT NULL,\r\n"
			+ "  `END_DATE` DATE NOT NULL,\r\n" + "  `AMOUNT` INT NOT NULL,\r\n" + "  `PRICE` DOUBLE NOT NULL,\r\n"
			+ "  `IMAGE` VARCHAR(45) NOT NULL,\r\n" + "  PRIMARY KEY (`ID`),\r\n"
			+ "  INDEX `category_id_idx` (`CATEGORY_ID` ASC) VISIBLE,\r\n"
			+ "  INDEX `company_id_idx` (`COMPANY_ID` ASC) VISIBLE,\r\n" + "  CONSTRAINT `category_id`\r\n"
			+ "    FOREIGN KEY (`CATEGORY_ID`)\r\n" + "    REFERENCES `coupon_system`.`categories` (`ID`)\r\n"
			+ "    ON DELETE NO ACTION\r\n" + "    ON UPDATE NO ACTION,\r\n" + "  CONSTRAINT `company_id`\r\n"
			+ "    FOREIGN KEY (`COMPANY_ID`)\r\n" + "    REFERENCES `coupon_system`.`companies` (`ID`)\r\n"
			+ "    ON DELETE NO ACTION\r\n" + "    ON UPDATE NO ACTION);\r\n" + "";
	private static final String CREATE_TABLE_CUSTOMERS_VS_COUPONS = "CREATE TABLE `coupon_system`.`customers_vs_coupons` (\r\n"
			+ "  `CUSTOMER_ID` INT NOT NULL,\r\n" + "  `COUPON_ID` INT NOT NULL,\r\n"
			+ "  PRIMARY KEY (`CUSTOMER_ID`, `COUPON_ID`),\r\n"
			+ "  INDEX `coupon_id_idx` (`COUPON_ID` ASC) VISIBLE,\r\n" + "  CONSTRAINT `customer_id`\r\n"
			+ "    FOREIGN KEY (`CUSTOMER_ID`)\r\n" + "    REFERENCES `coupon_system`.`customers` (`ID`)\r\n"
			+ "    ON DELETE NO ACTION\r\n" + "    ON UPDATE NO ACTION,\r\n" + "  CONSTRAINT `coupon_id`\r\n"
			+ "    FOREIGN KEY (`COUPON_ID`)\r\n" + "    REFERENCES `coupon_system`.`coupons` (`ID`)\r\n"
			+ "    ON DELETE NO ACTION\r\n" + "    ON UPDATE NO ACTION);\r\n" + " ";

	// DELETE QUERY FOR ALL TABLES
	private static final String DELETE_SCHEMA = "DROP SCHEMA coupon_system;";
	private static final String DROP_TABLE_CATEGORIES = "DROP TABLE `coupon_system`.`categories`;";
	private static final String DROP_TABLE_COMPANIES = "DROP TABLE `coupon_system`.`companies`;";
	private static final String DROP_TABLE_CUSTOMERS = "DROP TABLE `coupon_system`.`customers`;";
	private static final String DROP_TABLE_COUPONS = "DROP TABLE `coupon_system`.`coupons`;";
	private static final String DROP_TABLE_CUSTOMERS_VS_COUPONS = "DROP TABLE `coupon_system`.`customers_vs_coupons`;";

	public static void createAllTables() throws SQLException {

		createScheme();
		createTableCategories();
		createTableCompanies();
		createTableCustomers();
		createTableCoupons();
		createTableCustomersVsCoupons();

	}

	public static void dropAllTables() throws SQLException {

		dropTableCustomersVsCoupons();
		dropTableCoupons();
		dropTableCustomers();
		dropTableCategories();
		dropTableCompanies();
		deleteScheme();

	}

	public static void deleteScheme() {

		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = DELETE_SCHEMA;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	/**
	 * Insert permanent categories info DB
	 */

	public static void insertTestdata() {

		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3
			Statement statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO `coupon_system`.`categories` (`ID`, `NAME`) VALUES ('1', 'SHOES')");
			statement.executeUpdate("INSERT INTO `coupon_system`.`categories` (`ID`, `NAME`) VALUES ('2', 'ESPORTS')");
			statement.executeUpdate("INSERT INTO `coupon_system`.`categories` (`ID`, `NAME`) VALUES ('3', 'FOOD')");
			statement.executeUpdate("INSERT INTO `coupon_system`.`categories` (`ID`, `NAME`) VALUES ('4', 'CARS')");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	public static void createScheme() throws SQLException {

		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = CREATE_SCHEMA;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	private static void createTableCustomersVsCoupons() throws SQLException {
		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = CREATE_TABLE_CUSTOMERS_VS_COUPONS;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	private static void createTableCoupons() throws SQLException {
		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = CREATE_TABLE_COUPONS;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	private static void createTableCustomers() throws SQLException {
		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = CREATE_TABLE_CUSTOMERS;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	private static void createTableCompanies() throws SQLException {

		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = CREATE_TABLE_COMPANIES;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	private static void createTableCategories() throws SQLException {
		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = CREATE_TABLE_CATEGORIES;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	private static void dropTableCustomersVsCoupons() throws SQLException {
		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = DROP_TABLE_CUSTOMERS_VS_COUPONS;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	private static void dropTableCoupons() throws SQLException {
		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = DROP_TABLE_COUPONS;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	private static void dropTableCustomers() throws SQLException {
		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = DROP_TABLE_CUSTOMERS;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	private static void dropTableCompanies() throws SQLException {
		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = DROP_TABLE_COMPANIES;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	private static void dropTableCategories() throws SQLException {
		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = DROP_TABLE_CATEGORIES;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	public static String getUrl() {
		return url;
	}

	public static String getUsername() {
		return username;
	}

	public static String getPassword() {
		return password;
	}
}