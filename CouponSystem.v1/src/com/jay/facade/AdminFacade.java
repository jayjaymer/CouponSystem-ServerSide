package com.jay.facade;

import java.util.List;

import com.jay.beans.Company;
import com.jay.beans.Coupon;
import com.jay.beans.Customer;
import com.jay.dao.CompaniesDAO;
import com.jay.dao.CouponsDAO;
import com.jay.dao.CustomersDAO;
import com.jay.dbdao.CompaniesDBDAO;
import com.jay.dbdao.CouponsDBDAO;
import com.jay.dbdao.CustomersDBDAO;
import com.jay.exceptions.CompanyExistsException;
import com.jay.exceptions.CompanyNotFoundException;
import com.jay.exceptions.CustomerNotFoundException;
import com.jay.exceptions.NoAccsessException;

public class AdminFacade extends ClientFacade {
	protected CompaniesDAO companiesDAO = null;
	protected CustomersDAO customersDAO = null;
	protected CouponsDAO couponsDAO = null;

	public AdminFacade() {

		companiesDAO = new CompaniesDBDAO();
		customersDAO = new CustomersDBDAO();
		couponsDAO = new CouponsDBDAO();

	}

	// LOGIN //
	// login allowed only with admin info

	@Override
	public boolean login(String email, String password) {
		System.out.println(email + " " + password);
		if (email.equals("admin@admin.com") && password.equals("admin")) {
			System.out.println("Admin logged in.");
			return true;
		}
		System.out.println("false, wrong info");
		return false;
	}

	// Companies facade //

	/**
	 * on this method the admin is creating new company ONLY IF : the company name
	 * is not used by other company the company email is not used by other company
	 * 
	 * @param company - insert a company to Database
	 * @throws CompanyExistsException - if one of the conditions not passed
	 */
	public void createCompany(Company company) throws CompanyExistsException {

		List<Company> companies = companiesDAO.getAllCompanies();
		for (Company comp : companies) {

			if (company.getName().equalsIgnoreCase(comp.getName())) {
				new CompanyExistsException("Sorry Company Name -" + company.getName() + "- is in use.");

			}
			if (comp.getEmail().equalsIgnoreCase(company.getEmail())) {
				new CompanyExistsException("Sorry Company Email -" + company.getEmail() + "- is in use.");
				return;
			}

		}
		companiesDAO.addCompany(company);
	}

	// updateCompany - can`t update company id and name
	public void updateCompany(Company company, int companyID) throws CompanyExistsException, NoAccsessException {
		List<Company> companies = companiesDAO.getAllCompanies();
		for (Company comp : companies) {
			if (comp.getId() == companyID && comp.getName().equalsIgnoreCase(company.getName())) {
				companiesDAO.updateCompany(company, companyID);
			} else {
				throw new NoAccsessException("No access");
			}
		}
	}

	// to delete company all coupons to the company most be deleted.
	public void deleteCompany(int companyID) {
		List<Coupon> coupons = couponsDAO.getAllCoupons();
		for (Coupon coupon : coupons) {
			if (coupon.getCompanyID() == companyID) {
				System.out.println("removing purchased coupons");
				couponsDAO.deleteCouponPurchase(companyID, coupon.getId());
				System.out.println("purchased coupons deleted.");
				System.out.println("removing company coupons");
				couponsDAO.deleteCoupon(coupon.getId());
				System.out.println("***COUPONS REMOVED***");
			}

		}
		companiesDAO.deleteCompany(companyID);

	}

	public List<Company> getallCompanies() throws CompanyExistsException {
		return companiesDAO.getAllCompanies();
	}

	public Company getOneCompany(int companyID) throws CompanyNotFoundException {
		return companiesDAO.getOneCompany(companyID);
	}

	// Customer Facade //

	// can`t add customer with same email as other customer
	public void addCustomer(Customer customer) throws NoAccsessException {
		List<Customer> customers = customersDAO.getAllCustomers();
		for (Customer cust : customers) {
			if (customer.getEmail().equalsIgnoreCase(cust.getEmail())) {
				throw new NoAccsessException("Email is already used.");
			}
		}
		customersDAO.addCustomer(customer);

	}

	// can`t update customer id
	public void updateCustomer(Customer customer, int customerID) throws NoAccsessException, CustomerNotFoundException {
		if (customersDAO.getOneCustomer(customerID) != null) {
			customersDAO.updateCustomer(customerID, customer);
		} else {
			throw new NoAccsessException("Cannot change customer id!");
		}
	}

	// to delete customer most delete coupons connected to the customer.
	public void deleteCustomer(int customerID) throws CustomerNotFoundException {
		Coupon coupon = new Coupon();
		if (customersDAO.getOneCustomer(customerID) != null) {
			System.out.println("removing purchased coupons");
			couponsDAO.deleteCouponPurchase(customerID, coupon.getId());
			System.out.println("purchased coupons deleted.");
			System.out.println("removing customer " + customerID);
			customersDAO.deleteCustomer(customerID);
			System.out.println("***CUSTOMER REMOVED***");
		}
	}

	public List<Customer> getAllCustomers() {
		return customersDAO.getAllCustomers();
	}

	public Customer getOneCustomer(int customerID) throws CustomerNotFoundException {
		return customersDAO.getOneCustomer(customerID);

	}

}
