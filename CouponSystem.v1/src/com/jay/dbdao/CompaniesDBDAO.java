package com.jay.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.jay.beans.Company;
import com.jay.dao.CompaniesDAO;
import com.jay.db.ConnectionPool;
import com.jay.exceptions.CompanyExistsException;
import com.jay.exceptions.CompanyNotFoundException;

public class CompaniesDBDAO implements CompaniesDAO {
	// Connection
	private Connection connection = null;
	// QUERY of company
	private static final String ADD_COMPANY_QUERY = " INSERT INTO `coupon_system`.`companies` (`NAME`, `EMAIL`, `PASSWORD`) VALUES (?, ?, ?);";
	private static final String UPDATE_COMPANY_QUERY = "UPDATE  `coupon_system`.`companies` SET `NAME` = ? , `EMAIL` = ? , `PASSWORD` = ? WHERE (`id` = ?);";
	private static final String DELETE_COMPANY_QUERY = "DELETE FROM `coupon_system`.`companies` WHERE ID = ? ;";
	private static final String GETALL_COMPANY_QUERY = "SELECT * FROM `coupon_system`.`companies`;";
	private static final String GET_ONE_COMPANY_QUERY = "SELECT * FROM coupon_system.companies WHERE ID = ?;"; // update
	private static final String CHECK_IF_COMPANY_EXISTS_QUERY = "SELECT * FROM `coupon_system`.`companies` WHERE email = ?;";
	private static final String GET_ONE_COMPANY_BY_EMAIL_PASSOWRD_QUERY = "SELECT * FROM coupon_system.companies WHERE EMAIL = ? AND PASSWORD = ?;";

	@Override
	public void addCompany(Company company) throws CompanyExistsException {
		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = ADD_COMPANY_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, company.getName());
			statement.setString(2, company.getEmail());
			statement.setString(3, company.getPassword());
			statement.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	public void updateCompany(Company company, int companyID) {
		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = UPDATE_COMPANY_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, company.getName());
			statement.setString(2, company.getEmail());
			statement.setString(3, company.getPassword());
			statement.setInt(4, companyID);
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
	public void deleteCompany(int companyID) {
		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = DELETE_COMPANY_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, companyID);

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
	public List<Company> getAllCompanies() {
		List<Company> companies = new ArrayList<>();
		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = GETALL_COMPANY_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Company company = new Company();
				company.setId(resultSet.getInt(1));
				company.setName(resultSet.getString(2));
				company.setEmail(resultSet.getString(3));
				company.setPassword(resultSet.getString(4));

				companies.add(company);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

		return companies;
	}

	@Override
	public Company getOneCompany(int companyID) throws CompanyNotFoundException {
		Company company = new Company();
		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = GET_ONE_COMPANY_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, companyID);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				company.setId(resultSet.getInt(1));
				company.setName(resultSet.getString(2));
				company.setEmail(resultSet.getString(3));
				company.setPassword(resultSet.getString(4));
				return company;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
		System.out.println("Company Not Found");
		return company;

	}

	@Override
	public boolean isCompanyExists(String email) throws CompanyNotFoundException {
		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = CHECK_IF_COMPANY_EXISTS_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				System.out.println("true");
				return true;
			} else {
				throw new CompanyNotFoundException("Company not exists.");
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
	public Company getOneCompanyByEmailAndPassword(String email, String password) throws CompanyNotFoundException {
		Company company = new Company();
		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			// STEP 3

			String sql = GET_ONE_COMPANY_BY_EMAIL_PASSOWRD_QUERY;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				company.setId(resultSet.getInt(1));
				company.setName(resultSet.getString(2));
				company.setEmail(resultSet.getString(3));
				company.setPassword(resultSet.getString(4));

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
		System.out.println(company);
		return company;
	}

}
