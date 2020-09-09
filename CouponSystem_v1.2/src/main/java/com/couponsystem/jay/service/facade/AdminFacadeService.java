package com.couponsystem.jay.service.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.couponsystem.jay.beans.Company;
import com.couponsystem.jay.beans.Coupon;
import com.couponsystem.jay.beans.Customer;
import com.couponsystem.jay.exceptions.AlreadyExistsException;
import com.couponsystem.jay.exceptions.NoAccessException;
import com.couponsystem.jay.exceptions.NotFoundException;
import com.couponsystem.jay.service.jpa.CompanyService;
import com.couponsystem.jay.service.jpa.CouponService;
import com.couponsystem.jay.service.jpa.CustomerService;

@Service
public class AdminFacadeService extends ClientFacadeService {
	@Autowired
	protected CompanyService companyService = new CompanyService();
	@Autowired
	protected CustomerService customerService = new CustomerService();
	@Autowired
	protected CouponService couponservice = new CouponService();
	
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

	public void createCompany(Company company) throws AlreadyExistsException {

		List<Company> companies = companyService.getAllCompanies();
		for (Company comp : companies) {

			if (company.getName().equalsIgnoreCase(comp.getName()))
				throw new AlreadyExistsException("Sorry Company Name -" + company.getName() + "- is in use.");
			if (comp.getEmail().equalsIgnoreCase(company.getEmail()))
				throw new AlreadyExistsException("Sorry Company Email -" + company.getEmail() + "- is in use.");

		}
		companyService.addCompany(company);
	}

	// updateCompany - can`t update company id and name
	public void updateCompany(Company company) throws NoAccessException, NotFoundException {

		if (companyService.findCompanyByID(company.getId()) == null) {
			throw new NotFoundException("Company does not exists");
		}
		Company comp = companyService.getOneCompanyByID(company.getId());
		if (!company.getName().equalsIgnoreCase(comp.getName())) {
			throw new NoAccessException("Cannot change company name!");
		}
		companyService.updateCompany(company);

	}

	// try to delete company all coupons to the company most be deleted.
	public void deleteCompany(int companyID) throws NotFoundException {
		Company comp = companyService.getOneCompanyByID(companyID);
		if (comp.getCoupons().size() > 0) {
			for (Coupon coupon : comp.getCoupons()) {
				couponService.deletePurchasedCouponByCouponID(coupon.getId());
			}

		}
		companyService.deleteCompany(comp.getId());
	}

	public List<Company> getallCompanies() {
		return companyService.getAllCompanies();
	}

	public Company getOneCompany(int companyID) throws NotFoundException {
		return companyService.getOneCompanyByID(companyID);
	}

	public Company findCompanyByID(int companyID) throws NotFoundException {
		return companyService.findCompanyByID(companyID);
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

	// Customer Facade //

	// can`t add customer with same email as other customer
	public void addCustomer(Customer customer) throws AlreadyExistsException {
		List<Customer> customers = customerService.getAllCustomer();
		for (Customer cust : customers) {
			if (customer.getEmail().equalsIgnoreCase(cust.getEmail())) {
				throw new AlreadyExistsException("Email is already used.");
			}
		}
		customerService.addCustomer(customer);

	}

	// can`t update customer id
	public void updateCustomer(Customer customer, int customerID) throws NoAccessException, NotFoundException {
		if (customerService.getOneCustomerByCustomerID(customer.getId()) == null) {
			throw new NoAccessException("ID is not found");
		}
		customerService.updateCustomer(customer);
	}

	// to delete customer most delete coupons connected to the customer.
	public void deleteCustomer(int customerID) throws NotFoundException {
		Customer cust = customerService.getOneCustomerByCustomerID(customerID);
		if (cust.getCoupons().size() > 0) {
			for (Coupon coupon : cust.getCoupons()) {
				couponService.deletePurchasedCouponByCouponID(coupon.getId());
			}
		}
		customerService.deleteCustomer(cust.getId());
	}

	public List<Customer> getAllCustomers() {
		return customerService.getAllCustomer();
	}

	public Customer getOneCustomer(int customerID) throws NotFoundException {
		return customerService.getOneCustomerByCustomerID(customerID);

	}

	public Customer findCustomerById(int customerID) throws NotFoundException {
		return customerService.findCustomerByID(customerID);
	}
	
	// Admin manual coupon add
	
	public void insertCouponManual (int customerID, int couponID) {
		couponservice.addPurchaseCoupon(customerID, couponID);
	}

}
