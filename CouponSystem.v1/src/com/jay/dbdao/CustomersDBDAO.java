package com.jay.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.jay.beans.Customer;
import com.jay.beans.CustomerVsCoupon;
import com.jay.dao.CustomersDAO;
import com.jay.db.ConnectionPool;
import com.jay.exceptions.CustomerNotFoundException;

public class CustomersDBDAO implements CustomersDAO {
	// Connection
	private Connection connection = null;

	// QUERY off customers
	private static final String ADD_CUSTOMER_QUERY = " INSERT INTO `coupon_system`.`customers` (`FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`) VALUES (?, ?, ?, ?);";
	private static final String UPDATE_CUSTOMER_QUERY = "UPDATE  `coupon_system`.`customers` SET `FIRST_NAME` = ? , `LAST_NAME` = ?, `EMAIL` = ? , `PASSWORD` = ? WHERE (`id` = ?)";
	private static final String DELETE_CUSTOMER_QUERY = "DELETE FROM `coupon_system`.`customers` WHERE ID = ? ;";
	private static final String GETALL_CUSTOMERS_QUERY = "SELECT * FROM `coupon_system`.`customers`;";
	private static final String GET_ONE_CUSTOMER_QUERY = "SELECT * FROM `coupon_system`.`customers` WHERE ID = ?;";
	private static final String CHECK_CUSTOMER_EXISTS_QUERY = "SELECT * FROM `coupon_system`.`customers` WHERE email = ? AND password=?;";
	private static final String GET_ONE_CUSTOMER_BY_EMAIL_PASSOWRD_QUERY = "SELECT * FROM coupon_system.customers\r\n"
			+ "			WHERE EMAIL = ? AND PASSWORD = ?;";
	private static final String GET_ALL_CUSTOMERSVSCOUPONS = "SELECT * FROM `coupon_system`.`customers_vs_coupons` WHERE CUSTOMER_ID = ? ;";

	@Override
	public void addCustomer(Customer customer) {
		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = ADD_CUSTOMER_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
			statement.setString(3, customer.getEmail());
			statement.setString(4, customer.getPassword());
			statement.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	@Override
	public void updateCustomer(int customerID, Customer customer) {

		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = UPDATE_CUSTOMER_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
			statement.setString(3, customer.getEmail());
			statement.setString(4, customer.getPassword());
			statement.setInt(5, customerID);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	@Override
	public void deleteCustomer(int customerID) {
		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = DELETE_CUSTOMER_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customerID);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	@Override
	public List<Customer> getAllCustomers() {
		List<Customer> customers = new ArrayList<>();
		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = GETALL_CUSTOMERS_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Customer customer = new Customer();
				customer.setId(resultSet.getInt(1));
				customer.setFirstName(resultSet.getString(2));
				customer.setLastName(resultSet.getString(3));
				customer.setEmail(resultSet.getString(4));
				customer.setPassword(resultSet.getString(5));
				customers.add(customer);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

		return customers;
	}

	@Override
	public Customer getOneCustomer(int customerID) throws CustomerNotFoundException { //
		Customer customer = new Customer();
		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = GET_ONE_CUSTOMER_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customerID);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				customer.setId(resultSet.getInt(1));
				customer.setFirstName((resultSet.getString(2)));
				customer.setLastName(resultSet.getString(3));
				customer.setEmail(resultSet.getString(4));
				customer.setPassword(resultSet.getString(5));
				return customer;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
		System.out.println("Customer Not Found");
		return customer;

	}

	public boolean isCustomerExists(String email, String password) throws CustomerNotFoundException {
		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = CHECK_CUSTOMER_EXISTS_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				System.out.println("true");
				return true;

			} else {
				throw new CustomerNotFoundException("Customer not exists.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
		return false;
	}

	@Override
	public Customer getOneCustomerByEmailAndPassword(String email, String password) throws CustomerNotFoundException {
		Customer customer = new Customer();

		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = GET_ONE_CUSTOMER_BY_EMAIL_PASSOWRD_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				customer.setId(resultSet.getInt(1));
				customer.setFirstName((resultSet.getString(2)));
				customer.setLastName(resultSet.getString(3));
				customer.setEmail(resultSet.getString(4));
				customer.setPassword(resultSet.getString(5));
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
		System.out.println(customer);
		return customer;
	}

//
//
//

	@Override
	public List<CustomerVsCoupon> getAllCustomerVsCoupons() {
		List<CustomerVsCoupon> customerVsCoupons = new ArrayList<CustomerVsCoupon>();
		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = GET_ALL_CUSTOMERSVSCOUPONS;
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				CustomerVsCoupon customerVsCoupon = new CustomerVsCoupon();
				customerVsCoupon.setCustomerID(resultSet.getInt(1));
				customerVsCoupon.setCouponID(resultSet.getInt(2));
				customerVsCoupons.add(customerVsCoupon);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

		return customerVsCoupons;
	}

}
