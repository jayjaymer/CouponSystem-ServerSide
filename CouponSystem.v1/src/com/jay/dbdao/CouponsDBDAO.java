package com.jay.dbdao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.jay.beans.Category;
import com.jay.beans.Coupon;
import com.jay.beans.CustomerVsCoupon;
import com.jay.dao.CouponsDAO;
import com.jay.db.ConnectionPool;
import com.jay.exceptions.CouponNotFoundException;
import com.jay.util.DateUtils;

public class CouponsDBDAO implements CouponsDAO {
	// Connection
	private Connection connection = null;

	// QUERY of coupons

	private static final String ADD_COUPON_QUERY = "INSERT INTO `coupon_system`.`coupons` (`COMPANY_ID`, `CATEGORY_ID`, `TITLE`, `DESCRIPTION`, `START_DATE`, `END_DATE`, `AMOUNT`, `PRICE`, `IMAGE`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);\r\n";
	private static final String GET_ONE_COUPON_QUERY = "SELECT * FROM coupon_system.coupons WHERE ID = ?;";
	private static final String UPDATE_COUPON_QUERY = "UPDATE `coupon_system`.`coupons` SET `TITLE` = ?, `DESCRIPTION` = ?, `START_DATE` = ?, `END_DATE` = ?, `AMOUNT` = ?, `PRICE` = ?, `IMAGE` = ? WHERE (`ID` = ?);";
	private static final String DELETE_COUPON_QUERY = "DELETE FROM `coupon_system`.`coupons` WHERE ID = ? ;";
	private static final String GETALL_COUPONS_QUERY = "SELECT * FROM `coupon_system`.`coupons`;";
	private static final String CHECK_IF_COUPON_EXISTS_QUERY = "SELECT * FROM `coupon_system`.`coupons` WHERE ID = ?;";
	private static final String GET_ALL_COUPONS_BY_COMPANY = "SELECT * FROM `coupon_system`.`coupons` WHERE COMPANY_ID =? ;";
	private static final String ADD__PURCHASED_COUPON = " INSERT INTO `coupon_system`.`customers_vs_coupons` (`CUSTOMER_ID`, `COUPON_ID`) VALUES (?, ?);";
	private static final String DELETE_PURCHASED_COUPON = "DELETE FROM `coupon_system`.`customers_vs_coupons` WHERE CUSTOMER_ID = ? AND COUPON_ID = ?;";
	private static final String DELETE_PURCHASED_COUPON_BYID = "DELETE FROM `coupon_system`.`customers_vs_coupons` WHERE COUPON_ID = ?;";
	private static final String GET_ALL_PURCHASED_COUPONS_QUERY = "SELECT * FROM `coupon_system`.`customers_vs_coupons`;";

	@Override
	public void addCoupon(Coupon coupon) {
		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = ADD_COUPON_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, coupon.getCompanyID());
			statement.setInt(2, coupon.getCategory().ordinal() + 1);
			statement.setString(3, coupon.getTitle());
			statement.setString(4, coupon.getDescription());
			statement.setDate(5, (DateUtils.changeDateType(coupon.getStartDate())));
			statement.setDate(6, (DateUtils.changeDateType(coupon.getEndDate())));
			statement.setInt(7, coupon.getAmount());
			statement.setDouble(8, coupon.getPrice());
			statement.setString(9, coupon.getImage());
			statement.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	public Coupon getOneCoupon(int couponID) {
		Coupon coupon = new Coupon();
		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = GET_ONE_COUPON_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, couponID);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {

				coupon.setId(resultSet.getInt(1));
				coupon.setCompanyID(resultSet.getInt(2));
				coupon.setCategory(Category.values()[resultSet.getInt(3) - 1]);
				coupon.setTitle(resultSet.getString(4));
				coupon.setDescription(resultSet.getString(5));
				coupon.setStartDate(resultSet.getDate(6));
				coupon.setEndDate(resultSet.getDate(7));
				coupon.setAmount(resultSet.getInt(8));
				coupon.setPrice(resultSet.getDouble(9));
				coupon.setImage(resultSet.getString(10));
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
		return coupon;

	}

	@Override
	public void updateCoupon(Coupon coupon) {
		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = UPDATE_COUPON_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setInt(1, coupon.getCompanyID());
			statement.setInt(2, coupon.getCategory().ordinal() + 1);
			statement.setString(1, coupon.getTitle());
			statement.setString(2, coupon.getDescription());
			statement.setDate(3, (Date) (coupon.getStartDate()));
			statement.setDate(4, (Date) (coupon.getEndDate()));
			statement.setInt(5, coupon.getAmount());
			statement.setDouble(6, coupon.getPrice());
			statement.setString(7, coupon.getImage());
			statement.setInt(8, coupon.getId());
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
	public void deleteCoupon(int couponID) {
		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = DELETE_COUPON_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, couponID);

			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	public List<Coupon> getAllCoupons() {
		List<Coupon> coupons = new ArrayList<>();
		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = GETALL_COUPONS_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(resultSet.getInt(1));
				coupon.setCompanyID(resultSet.getInt(2));
				coupon.setCategory(Category.values()[resultSet.getInt(3) - 1]);
				coupon.setTitle(resultSet.getString(4));
				coupon.setDescription(resultSet.getString(5));
				coupon.setStartDate(resultSet.getDate(6));
				coupon.setEndDate(resultSet.getDate(7));
				coupon.setAmount(resultSet.getInt(8));
				coupon.setPrice(resultSet.getDouble(9));
				coupon.setImage(resultSet.getString(10));
				coupons.add(coupon);

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
		return coupons;
	}

	public boolean isCouponExist(int couponID) throws CouponNotFoundException {
		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = CHECK_IF_COUPON_EXISTS_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, couponID);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				System.out.println("true");
				return true;
			} else {
				throw new CouponNotFoundException("false");
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
	public List<Coupon> getCouponsByCompanyID(int companyID) {
		List<Coupon> coupons = new ArrayList<>();
		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = GET_ALL_COUPONS_BY_COMPANY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, companyID);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				companyID = resultSet.getInt(2);
				Category category = (Category.values()[resultSet.getInt(3) - 1]);
				String title = resultSet.getString(4);
				String description = resultSet.getString(5);
				Date startDate = resultSet.getDate(6);
				Date endDate = resultSet.getDate(7);
				int amount = resultSet.getInt(8);
				double price = resultSet.getDouble(9);
				String image = resultSet.getString(10);
				coupons.add(new Coupon(id, companyID, category, title, description, startDate, endDate, amount, price,
						image));

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

		return coupons;
	}

	// Purchased Coupons

	@Override
	public void addCouponPurchase(int customerID, int couponID) {

		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = ADD__PURCHASED_COUPON;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customerID);
			statement.setInt(2, couponID);
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
	public void deleteCouponPurchase(int customerID, int couponID) {

		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = DELETE_PURCHASED_COUPON;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customerID);
			statement.setInt(2, couponID);
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
	public void deleteCouponPurchaseByCouponID(int couponID) {

		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = DELETE_PURCHASED_COUPON_BYID;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, couponID);
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
	public List<CustomerVsCoupon> getAllPurchased() {

		List<CustomerVsCoupon> customerVsCoupons = new ArrayList<>();
		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = GET_ALL_PURCHASED_COUPONS_QUERY;
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
