package com.jay.Application;

import java.sql.Date;
import java.sql.SQLException;

import com.jay.beans.Category;
import com.jay.beans.Company;
import com.jay.beans.Coupon;
import com.jay.beans.Customer;
import com.jay.dao.CompaniesDAO;
import com.jay.dao.CouponsDAO;
import com.jay.dao.CustomersDAO;
import com.jay.dbdao.CompaniesDBDAO;
import com.jay.dbdao.CouponsDBDAO;
import com.jay.dbdao.CustomersDBDAO;
import com.jay.exceptions.ClientNotFoundException;
import com.jay.exceptions.CompanyExistsException;
import com.jay.exceptions.CompanyNotFoundException;
import com.jay.exceptions.CouponNotFoundException;
import com.jay.exceptions.CouponPurchaseException;
import com.jay.exceptions.CustomerNotFoundException;
import com.jay.exceptions.NoAccsessException;
import com.jay.facade.AdminFacade;
import com.jay.facade.CompanyFacade;
import com.jay.facade.CustomerFacade;
import com.jay.login.ClientType;
import com.jay.login.LoginManager;
import com.jay.util.Print;

public class Test {
	public void testALL() throws SQLException, NoAccsessException, CustomerNotFoundException, CompanyExistsException,
			CompanyNotFoundException, CouponNotFoundException, CouponPurchaseException, ClientNotFoundException,
			CouponPurchaseException {

		Print print = new Print();
		CustomersDAO customersDAO = new CustomersDBDAO();
		CompaniesDAO companiesDAO = new CompaniesDBDAO();
		CouponsDAO couponsDAO = new CouponsDBDAO();

		// Customers

		Customer customer1 = new Customer("Jay", "Mer", "jay@gmail.com", "123");
		Customer customer2 = new Customer("Shiran", "cohen", "shiran@gmail.com", "1234");
		Customer customer3 = new Customer("Ora", "shemesh", "ora@gmail.com", "123");
		Customer customer4 = new Customer("Test4", "test", "test@gmail.com", "1234");
		Customer customer5 = new Customer("Kobe", "Bryant", "bryant@gmail.com", "123");
		Customer customer6 = new Customer("Shaquille", "O'neal", "shaquille@gmail.com", "123");

		// Companies

		Company company1 = new Company("Cola", "cola@gmail.com", "1234");
		Company company2 = new Company("Tesla", "tesla@gmail.com", "1234");
		Company company3 = new Company("Soda", "soda@gmail.com", "1234");
		Company company4 = new Company("Test", "test@gmail.com", "123");
		Company company5 = new Company("Razer", "razer@gmail.com", "123");
		Company company6 = new Company("Toyota", "toyota@gmail.com", "123");
		Company company7 = new Company("Nike", "nike@gmail.com", "123");
		Company company8 = new Company("Logitech", "logitech@gmail.com", "123");

		// Coupons

		Coupon coupon1 = new Coupon(1, Category.FOOD, "Summer Sale - Zero", "5% Discount for diet coke and zero",
				new Date(2020, 01, 01), new Date(2020, 9, 10), 10, 4.99, "http://cokecola");
		Coupon coupon2 = new Coupon(2, Category.CARS, "Model X pre-order sale",
				"10% Discount on new version of model X", new Date(2020, 01, 01), new Date(2020, 10, 10), 100, 999.00,
				"http://tesla");
		Coupon coupon3 = new Coupon(1, Category.FOOD, "Soda deal", "buy 10 get 2 for free", new Date(2020, 01, 01),
				new Date(2020, 8, 8), 500, 49.99, "http://cokecola");
		Coupon coupon4 = new Coupon(6, Category.CARS, "Toyota supra 5% Discount", "Brand new 2020 supra discount",
				new Date(2020, 01, 01), new Date(2020, 9, 2), 5, 499, "http://toyota");
		Coupon coupon5 = new Coupon(7, Category.SHOES, "Nike Jordan 10% Discount", "10% off for any jordans",
				new Date(2020, 01, 01), new Date(2020, 9, 01), 30, 29.99, "http://nike");
		Coupon coupon6 = new Coupon(5, Category.ESPORTS, "Razer Headphones 15% Discount",
				"15% off for the new Kraken Headset", new Date(2020, 01, 01), new Date(2020, 10, 10), 100, 15,
				"http://razer");
		Coupon coupon7 = new Coupon(4, Category.ESPORTS, "test title", "test description", new Date(2020, 01, 01),
				new Date(2020, 9, 20), 10, 10, "http://test");

		// coupons for purchse test

		Coupon cPuchase1 = new Coupon(4, Category.ESPORTS, "test title", "test description", new Date(2020, 01, 01),
				new Date(2020, 10, 10), 10, 10, "http://test");

		Coupon cPuchase2 = new Coupon(4, Category.ESPORTS, "test title", "test description", new Date(2020, 01, 01),
				new Date(2020, 10, 10), 0, 10, "http://test");

		Coupon cPuchase3 = new Coupon(4, Category.ESPORTS, "test title", "test description", new Date(2020, 01, 01),
				new Date(2019, 01, 16), 10, 10, "http://test");
		
		// coupons for DAILY JOB
		
		Coupon cJob1 = new Coupon(4, Category.ESPORTS, "test title", "test description", new Date(2020, 01, 01),
				new Date(2019, 03, 10), 10, 10, "http://test");
		
		Coupon cJob2 = new Coupon(4, Category.ESPORTS, "test title", "test description", new Date(2020, 01, 01),
				new Date(2020, 03, 10), 10, 10, "http://test");
		
		Coupon cJob3 = new Coupon(4, Category.ESPORTS, "test title", "test description", new Date(2020, 01, 01),
				new Date(2019, 05, 1), 10, 10, "http://test");
		
		

		System.out.println();
		print.customerTEST(null);
		System.out.println();

		// ADD CUSTOMERS

		System.out.println("****Adding Customers****");
		customersDAO.addCustomer(customer1);
		customersDAO.addCustomer(customer2);
		customersDAO.addCustomer(customer3);
		customersDAO.addCustomer(customer4);
		customersDAO.addCustomer(customer5);
		customersDAO.addCustomer(customer6);
		System.out.println(customersDAO.getAllCustomers());
		System.out.println("Customers are added.");

		// UPDATE CUSTOMERS
		System.out.println();
		System.out.println("****Update Customer****");
		System.out.print("Customer6 email address before update : ");
		System.out.println(customer6.getEmail());
		System.out.println();
		customer6.setEmail("thisistest@gmail.com");
		customersDAO.updateCustomer(6, customer6);
		System.out.print("Customer6 email address after update : ");
		System.out.println(customer6.getEmail());
		System.out.println(customersDAO.getOneCustomer(6));
		System.out.println();

		// DELETE CUSTOMER
		System.out.println("****Customer DELETE****");
		customersDAO.deleteCustomer(6);
		System.out.println("Customer6 deleted.");
		System.out.println();

		// GET ALL CUSTOMERS
		System.out.println("****Customers Registered****");
		System.out.println(customersDAO.getAllCustomers());
		System.out.println();

		// GET ONE CUSTOMER
		System.out.println("****Get one registerd Customer By ID****");
		System.out.println("real info test");
		System.out.println(customersDAO.getOneCustomer(1));
		System.out.println("fake info test");
		System.out.println(customersDAO.getOneCustomer(55));
		System.out.println();

		// CHECK IF CUSTOMER EXISTS
		System.out.println("****Customer Existence ****");
		System.out.println("real info test");
		customersDAO.isCustomerExists("jay@gmail.com", "123");
		System.out.println("fake info test");
		customersDAO.isCustomerExists("thisistest@gmail.com", "1");
		System.out.println();

		// GET ONE CUSTOMER BY EMAIL AND PASSWORD
		System.out.println("****Get one Registered Customer By Email and Password****");
		System.out.println("real info test");
		customersDAO.getOneCustomerByEmailAndPassword("jay@gmail.com", "123");
		System.out.println("fake info test");
		customersDAO.getOneCustomerByEmailAndPassword("tfasdhfad", "1234");
		System.out.println();

		/*
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */

		print.companyTEST(null);
		System.out.println();

		// ADD COMPANY

		System.out.println("****Adding Companies****");
		companiesDAO.addCompany(company1);
		companiesDAO.addCompany(company2);
		companiesDAO.addCompany(company3);
		companiesDAO.addCompany(company4);
		companiesDAO.addCompany(company5);
		companiesDAO.addCompany(company6);
		companiesDAO.addCompany(company7);
		companiesDAO.addCompany(company8);
		System.out.println(companiesDAO.getAllCompanies());
		System.out.println("*Companies are added.*");
		System.out.println();

		// UPDATE COMPANY
		System.out.println("****Updating Company****");
		System.out.print("Company8 email address before update : ");
		System.out.println(company8.getEmail());
		company8.setEmail("badgashgasd");
		companiesDAO.updateCompany(company8, 8);
		System.out.print("Company8 email address after update : ");
		System.out.println(company8.getEmail());
		System.out.println();
		System.out.println(companiesDAO.getOneCompany(8));
		System.out.println();

		// DELETE COMPANY
		System.out.println("****Company DELETE****");
		System.out.println("Removing company ID 8");
		companiesDAO.deleteCompany(8);
		System.out.println("Company Deleted");
		System.out.println();

		// GET ALL COMPANIES
		System.out.println("****Companies Registered****");
		System.out.println(companiesDAO.getAllCompanies());
		System.out.println();

		// GET ONE COMPANY
		System.out.println("****Get one registerd Company By ID****");
		System.out.println("real info test");
		System.out.println(companiesDAO.getOneCompany(1));
		System.out.println("fake info test");
		System.out.println(companiesDAO.getOneCompany(55));
		System.out.println();

		// IS COMPANY EXISTS
		System.out.println("****Company Existence ****");
		System.out.println("real info test");
		companiesDAO.isCompanyExists("cola@gmail.com");
		System.out.println("fake info test");
		companiesDAO.isCompanyExists("test@gmail.commmmmmm");
		System.out.println();

		// GET ONE COMPANY BY EMAIL AND PASSWORD
		System.out.println("****Get one Registered Company By Email and Passaword****");
		System.out.println("real info test");
		companiesDAO.getOneCompanyByEmailAndPassword("cola@gmail.com", "1234");
		System.out.println("fake info test");
		companiesDAO.getOneCompanyByEmailAndPassword("dasgdg", "123");
		System.out.println();

		/*
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */

		print.couponTEST(null);
		System.out.println();

// ADD COUPON

		System.out.println("****Adding Coupons****");
		couponsDAO.addCoupon(coupon1);
		couponsDAO.addCoupon(coupon2);
		couponsDAO.addCoupon(coupon3);
		couponsDAO.addCoupon(coupon4);
		couponsDAO.addCoupon(coupon5);
		couponsDAO.addCoupon(coupon6);
		couponsDAO.addCoupon(coupon7);
		System.out.println(couponsDAO.getAllCoupons());
		System.out.println("Coupons are added.");
		System.out.println();

// UPDATE COUPON
		System.out.println("****Updating Coupon****");
		System.out.print("Coupon7 Title before update : ");
		System.out.println(coupon7.getTitle());
		coupon7.setTitle("testest123123");
		couponsDAO.updateCoupon(coupon7);
		System.out.print("Coupon7 Title after update : ");
		System.out.println(coupon7.getTitle());
		System.out.println();
		System.out.println(coupon7);
		System.out.println();

// DELETE COUPON
		System.out.println("****Coupon DELETE****");
		couponsDAO.deleteCoupon(7);
		System.out.println("Company7 Deleted");
		System.out.println();

// GET ALL COUPONS
		System.out.println("****Coupons Registered****");
		System.out.println(couponsDAO.getAllCoupons());
		System.out.println();

		// GET ONE COUPON
		System.out.println("****Get one registerd Coupon By ID****");
		System.out.println("real info test");
		System.out.println(couponsDAO.getOneCoupon(6));
		System.out.println("fake info test");
		System.out.println(couponsDAO.getOneCoupon(55));
		System.out.println();

// IS COUPON EXISTS
		System.out.println("****Coupon Existence ****");
		System.out.println("real info test");
		couponsDAO.isCouponExist(1);
		System.out.println("fake info test");
		couponsDAO.isCouponExist(15);
		System.out.println();

// GET ONE COUPON BY COMPANY ID
		System.out.println("****Get Coupons by company id****");
		System.out.println(couponsDAO.getCouponsByCompanyID(1));
		System.out.println();

		/*
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 	
		 */
		/*
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */

//  FACADE TEST
		print.facadeTest(null);
		System.out.println();

// FACADE ADMIN

		System.out.println(
				"*****************************************************Admin facade Testing*****************************************************");
		System.out.println();
		AdminFacade adminAdmin = null;
		try {
			// Login test
			System.out.println("*******admin facade - login as admin*******");
			System.out.println("fake info test");
			adminAdmin = (AdminFacade) LoginManager.getInstance().login("asfdh@admin.com", "adfdssdfmin",
					ClientType.ADMINISTRATOR);
			System.out.println();
		} catch (NoAccsessException msg) {
			System.out.println("Incorrent info");
		}
		System.out.println();
		try {
			System.out.println("real info test");
			adminAdmin = (AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin",
					ClientType.ADMINISTRATOR);
			System.out.println();

		} catch (NoAccsessException msg) {
			System.out.println("Incorrent info");
		}
		System.out.println();

		// add existing company name
		System.out.println();
		System.out.println("*******admin facade - creating an existing company name or email*******");
		Company testCompany = new Company("tesla", "Tesla@gmail.com", "1234");
		adminAdmin.createCompany(testCompany);
		System.out.println();
		System.out.println("*******admin facade - get all companies*******");
		System.out.println(adminAdmin.getallCompanies());

		// Cannot Update Company name and id
		System.out.println("*******admin facade - try update company`s name and id*******");
		System.out.println("~~~trying to change id, name and email~~~");
		Company company = adminAdmin.getOneCompany(1);
		System.out.println("Company 1 before attempt");
		System.out.println(adminAdmin.getOneCompany(1));

		try {
			company.setId(99);
			adminAdmin.updateCompany(company, 1);
		} catch (NoAccsessException e) {
			System.out.println("Cannot change company ID!");
		}

		try {
			company1.setName("sadgads");
			adminAdmin.updateCompany(company, 1);
		} catch (NoAccsessException e) {
			System.out.println("Cannot change company name!");
		}
		System.out.println();
		System.out.println("~~~trying to change email and password~~~");
		try {
			company.setEmail("dasgasdgadsggasddasga");
			company.setPassword("asdhdsahadshadshdsahsdh");
			adminAdmin.updateCompany(company, 1);
		} catch (Exception e) {
		}

		System.out.println("Company 1 after attempt");
		System.out.println(adminAdmin.getOneCompany(1));
		System.out.println("Retrun info to original.");
		try {
			company.setEmail("cola@gmail.com");
			company.setPassword("1234");
			adminAdmin.updateCompany(company, 1);
		} catch (Exception e) {
		}
		System.out.println(adminAdmin.getOneCompany(1));
		System.out.println();

		// Delete Coupons Purchase history + delete available coupons from this company
		System.out.println("*******admin facade - remove FK and purchased coupons.*******");
		System.out.println("Removing A company.");
		adminAdmin.deleteCompany(7);
		System.out.println();

		// Return all companies
		System.out.println("*******admin facade - get all companies*******");
		System.out.println(adminAdmin.getallCompanies());
		System.out.println();

		// Return one Company
		System.out.println("*******admin facade - get one company*******");
		System.out.println(adminAdmin.getOneCompany(1));
		System.out.println();

		// Cannot add used customer email.
		System.out.println("*******admin facade - Cannot add a customer used email.*******");
		System.out.println("trying to add used email");
		Customer customer68 = new Customer();
		try {
			customer68.setFirstName("Drake");
			customer68.setLastName("champaigne");
			customer68.setEmail("jay@gmail.com");
			customer68.setPassword("123");
			adminAdmin.addCustomer(customer68);
			System.out.println(customer68);
		} catch (NoAccsessException e) {
			System.out.println("Email already used, try other one!");
		}
		System.out.println();
		// adding unique customer
		System.out.println("trying to add a unique customer");
		try {
			Customer customer = new Customer("Drake", "champaigne", "drizzy@gmail.com", "123");
			adminAdmin.addCustomer(customer);
			System.out.println("customer added.");
		} catch (NoAccsessException e) {
			System.out.println("Email already used, try other one!");
		}
		System.out.println();

		// Cannot Update Customer ID
		System.out.println("*******admin facade - cant change customer id*******");
		System.out.println("~~~trying to change customer id~~~");
		Customer customer = customersDAO.getOneCustomer(1);
		try {
			customer.setId(2000);
			adminAdmin.updateCustomer(customer, 1);
		} catch (NoAccsessException e) {
			System.out.println("Cannot change customer id!");
		}
		System.out.println(adminAdmin.getOneCustomer(1));
		System.out.println("~~~trying to change customer info~~~");
		try {
			customer.setFirstName("asdgadsgasdgdsadsgf");
			customer.setLastName("asdfgdsadsgadsg");
			customer.setEmail("asddasgagsd");
			customer.setPassword("asdgasdadsg");
			adminAdmin.updateCustomer(customer, 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("customer after attempt");
		System.out.println(adminAdmin.getOneCustomer(1));
		System.out.println("return to original customer info");
		try {
			customer.setFirstName("Jay");
			customer.setLastName("Mer");
			customer.setEmail("jay@gmail.com");
			customer.setPassword("123");
			adminAdmin.updateCustomer(customer, 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println(adminAdmin.getOneCustomer(1));
		System.out.println();

		// Delete customer coupon purchase
		System.out.println("*******admin facade - deleting customer purchased coupons.*******");
		adminAdmin.deleteCustomer(7);
		System.out.println();

		// return all Customers.
		System.out.println("*******admin facade - get all customers*******");
		System.out.println(adminAdmin.getAllCustomers());
		System.out.println();

		// return one customer
		System.out.println("*******admin facade - get one customer*******");
		System.out.println(adminAdmin.getOneCustomer(1));

		System.out.println();

		/*
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */

		// COMPANY FACADE TEST
		CompanyFacade colaCompany = null;

		System.out.println(
				"**********************************************************************Company Facade test**********************************************************************");
		System.out.println();
		try {
			// LOGIN TEST
			System.out.println("*******Company facade - login as cola company*******");
			System.out.println();
			System.out.println("fake info test");
			colaCompany = (CompanyFacade) LoginManager.getInstance().login("asfdh", "asdfh", ClientType.COMPANY);
		} catch (NoAccsessException msg) {
			System.out.println("Incorrent info");
		}

		try {

			System.out.println();

			System.out.println("real info test");
			colaCompany = (CompanyFacade) LoginManager.getInstance().login("cola@gmail.com", "1234",
					ClientType.COMPANY);
			colaCompany.setCompanyID(1);
			System.out.println();
		} catch (NoAccsessException msg) {
			System.out.println("Incorrent info");
		}

		// add company if title is not the same as other coupons
		System.out.println("*******company facade - create coupon test*******");
		try {
			System.out.println();
			System.out.println("trying to add an existing title.");
			Coupon coupontest = new Coupon(1, Category.FOOD, "Summer Sale - Zero", "5% Discount for diet coke and zero",
					new Date(2020, 01, 01), new Date(2020, 01, 02), 10, 4.99, "http://cokecola");
			colaCompany.createCoupon(coupontest);
			System.out.println();
		} catch (NoAccsessException e) {
			System.out.println(e.getMessage());
		}

		try {
			System.out.println("adding unique coupon");
			Coupon coupontest = new Coupon(1, Category.FOOD, "TEST", "TEST", new Date(2020, 01, 01),
					new Date(2020, 01, 02), 10, 4.99, "TEST");
			colaCompany.createCoupon(coupontest);
			System.out.println("coupon added.");
			System.out.println();
		} catch (NoAccsessException e) {
			System.out.println(e.getMessage());
		}

		System.out.println(colaCompany.getCompanyCoupons());
		System.out.println();

		// updateCoupon method
		System.out.println("*******company facade - cant change company and coupon ID*******");
		System.out.println();
		try {
			System.out.println("trying to change id.");
			System.out.println(couponsDAO.getOneCoupon(1));
			Coupon coupon = couponsDAO.getOneCoupon(1);
			coupon.setId(21312321);
			coupon.setCompanyID(124);
			colaCompany.updateCoupon(coupon);
		} catch (NoAccsessException e) {
			System.out.println(e.getMessage());
		}
		System.out.println();
		try {
			System.out.println("trying to change title.");
			System.out.println("before");
			System.out.println(couponsDAO.getOneCoupon(1));
			Coupon coupon = couponsDAO.getOneCoupon(1);
			coupon.setTitle("sadfghsdagdasdsggdasadsgadsgasdg");
			colaCompany.updateCoupon(coupon);
			System.out.println(couponsDAO.getOneCoupon(1));
			System.out.println("Title Changed.");
			coupon.setTitle("Summer Sale - Zero");
			colaCompany.updateCoupon(coupon);
			System.out.println("Back to original title.");
			System.out.println(couponsDAO.getOneCoupon(1));

		} catch (NoAccsessException e) {
			System.out.println(e.getMessage());
		}

		// delete purchased coupons history
		System.out.println("*******company facade - delete coupon test*******");
		colaCompany.deleteCoupon(8);
		System.out.println();

		// get all coupons from current company login
		System.out.println("*******company facade - get all coupons by company*******");
		System.out.println(colaCompany.getCompanyCoupons());
		System.out.println();

		// get all coupons from specific category
		System.out.println("*******company facade - get all coupons by category*******");
		System.out.println("Available coupons :");
		System.out.println(colaCompany.getCompanyCouponsByCategory(Category.FOOD));
		System.out.println("None Available coupons :");
		System.out.println(colaCompany.getCompanyCouponsByCategory(Category.ESPORTS));
		System.out.println();

		// get all coupons of company till maxprice
		System.out.println("*******company facade - get all coupon by max price*******");
		System.out.println("Available price");
		System.out.println(colaCompany.getCompanyCouponsByMaxPrice(50));
		System.out.println();
		System.out.println("None Available price");
		System.out.println(colaCompany.getCompanyCouponsByMaxPrice(1.99));
		System.out.println();

		// get company info
		// FOR SOME REASON RETURNS ALSO DETAILS WITH NULL
		System.out.println("*******company facade - get company info*******");
		System.out.println(colaCompany.getCompanyDetails());
		System.out.println();

		/*
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */

		// Customer facade TEST
		CustomerFacade jayCustomer = null;
		System.out.println(
				"**********************************************************************Customer Facade test**********************************************************************");
		System.out.println();
		try {
			System.out.println("*******customer facade - login test*******");
			System.out.println();
			System.out.println("fake info test");
			jayCustomer = (CustomerFacade) LoginManager.getInstance().login("adsgasd", "12223", ClientType.CUSTOMER);
			System.out.println();
		} catch (NoAccsessException e) {
			System.out.println("Incorrect info!");
		}

		try {
			System.out.println("real info test");
			jayCustomer = (CustomerFacade) LoginManager.getInstance().login("jay@gmail.com", "123",
					ClientType.CUSTOMER);
			jayCustomer.setCustomerID(1);
		} catch (NoAccsessException e) {
			System.out.println("Incorrect info!");
		}
		System.out.println();
		
		/***
		 * 
		 * customer cannot purchase coupon if ( more than 1 time, if coupon amount is 0,
		 * if enddate passed,) THAN ~lower amount of coupon after purchase by 1~
		 * 
		 * 
		 */

		System.out.println("*******customer facade - purchase test*******");
		System.out.println();
		System.out.println("~~~~~~~~~~~coupon already purchased TEST~~~~~~~~~~~");
		couponsDAO.addCoupon(cPuchase1);
		// trying to purchase the same coupon twice!!!
		// ADDING FIRST TIME!
		Coupon purchasedCoupon1 = couponsDAO.getOneCoupon(9);
		try {
			jayCustomer.purchaseCoupon(purchasedCoupon1);
		} catch (CouponPurchaseException e) {
			System.out.println(e.getMessage());
		}

		// ADDING SECOND TIME TO SHOW ERROR
		System.out.println("!!Trying to purchase same coupon second time!!");
		try {
			jayCustomer.purchaseCoupon(purchasedCoupon1);
		} catch (CouponPurchaseException e) {
			System.out.println(e.getMessage());
		}

		System.out.println();

		System.out.println("~~~~~~~~~~~coupon is out of stock~~~~~~~~~~~");
		couponsDAO.addCoupon(cPuchase2);
		Coupon purchasedCoupon2 = couponsDAO.getOneCoupon(10);
		// ADDING AN EMPTY COUPON
		try {
			jayCustomer.purchaseCoupon(purchasedCoupon2);
		} catch (CouponPurchaseException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("~~~~~~~~~~~coupon is outdated~~~~~~~~~~~");
		couponsDAO.addCoupon(cPuchase3);
		Coupon purchasedCoupon3 = couponsDAO.getOneCoupon(11);
		System.out.println(purchasedCoupon3.getEndDate());
		try {
			jayCustomer.purchaseCoupon(purchasedCoupon3);
		} catch (CouponPurchaseException e) {
			System.out.println(e.getMessage());
		}
		System.out.println();

		System.out.println("~~~~~~~~~~~lowering amount by one~~~~~~~~~~~");
		Coupon purchasedCoupon = couponsDAO.getOneCoupon(6);
		try {
			jayCustomer.purchaseCoupon(purchasedCoupon);
		} catch (CouponPurchaseException e) {
			System.out.println(e.getMessage());
		}
		System.out.println();
		
		// adding one more purchased coupon by other customer,
		// just to prove the GET ALL methods work.
		couponsDAO.addCouponPurchase(2, 4);

		// Get all customer coupons
		System.out.println("*******customer facade - get all coupons*******");
		System.out.println(jayCustomer.getCustomerCoupons());
		System.out.println();

		// Get all coupons from specific category
		System.out.println("*******customer facade - get coupons by category*******");
		System.out.println("fake info test");
		try {
			System.out.println(jayCustomer.getCustoCouponsByCategory(Category.FOOD));
		} catch (CouponNotFoundException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("real info test");
		try {
			System.out.println(jayCustomer.getCustoCouponsByCategory(Category.ESPORTS));
		} catch (CouponNotFoundException e) {
			System.out.println(e.getMessage());
		}
		System.out.println();

		// get all purchased coupons maxprice
		System.out.println("*******customer facade - get coupons by maxprice*******");
		System.out.println();
		// testing price
		try {
			System.out.println("fake test info");
			System.out.println(jayCustomer.getCutomerCouponsByMaxPrice(2));
			System.out.println();
		} catch (CouponNotFoundException e) {
			System.out.println("No coupons for this price.");
		}
		System.out.println();
		System.out.println("real test info");
		System.out.println(jayCustomer.getCutomerCouponsByMaxPrice(50));
		System.out.println();

		// get all customer details.
		System.out.println("*******customer facade - get customer details*******");
		System.out.println(jayCustomer.getCustomerDetails());
		System.out.println();
		
		// adding expired coupons to test daily job thread.
		couponsDAO.addCoupon(cJob1);
		couponsDAO.addCoupon(cJob2);
		couponsDAO.addCoupon(cJob3);
		couponsDAO.addCouponPurchase(3, 12);
		couponsDAO.addCouponPurchase(4, 13);
		couponsDAO.addCouponPurchase(5, 14);

		
		print.endPrint(null);
	}

}
