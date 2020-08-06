package com.couponsystem.jay.clr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.couponsystem.jay.beans.Company;
import com.couponsystem.jay.beans.Customer;
import com.couponsystem.jay.exceptions.AlreadyExistsException;
import com.couponsystem.jay.exceptions.NoAccessException;
import com.couponsystem.jay.login.ClientType;
import com.couponsystem.jay.login.LoginManager;
import com.couponsystem.jay.service.AdminFacadeService;
import com.couponsystem.jay.util.Print;

@Component
@Order(value = 4)
public class AdminFacadeTest implements CommandLineRunner {
	@Autowired
	private AdminFacadeService admin;
	@Autowired
	private LoginManager managerLogin;

	@Override
	public void run(String... args) throws Exception {
		System.out.println();
		System.out.println();
		System.out.println();

		Print print = new Print();
		System.out.println();
		print.facadeTest(null);
		System.out.println();

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~Admin Facade TEST~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println();
		AdminFacadeService adminAdmin = null;
		// Login test
		System.out.println("*******admin facade - login as admin*******");
		System.out.println("~~fake info test~~");
		try {
			adminAdmin = (AdminFacadeService) managerLogin.login("aadsgaas@admin.com", "aasdasddmin",
					ClientType.ADMINISTRATOR);
		} catch (NoAccessException e) {
			System.out.println(e.getMessage());
		}
		System.out.println();
		System.out.println("~~real info test~~");
		try {
			adminAdmin = (AdminFacadeService) managerLogin.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
		} catch (NoAccessException e) {
			System.out.println(e.getMessage());
		}

		System.out.println();
		// add existing company name
		System.out.println("*******admin facade - creating an existing company name or email*******");
		System.out.println("~~~trying to add used company name or email~~~");
		Company testCompany = Company.builder()
				.name("tesla")
				.email("Tesla@gmail.com")
				.password("1234")
				.build();
		try {
			adminAdmin.createCompany(testCompany);
		} catch (AlreadyExistsException e) {
			System.out.println(e.getMessage());
		}
		Company testCompany2 = Company.builder()
				.name("teslaaa")
				.email("Tesla@gmail.com")
				.password("1234")
				.build();
		try {
			adminAdmin.createCompany(testCompany2);
		} catch (AlreadyExistsException e) {
			System.out.println(e.getMessage());
		}
		System.out.println();
		System.out.println("~~~trying to add a unique customer~~~");
		Company testCompany1 = Company.builder()
				.name("BMW")
				.email("bmw@gmail.com")
				.password("1234")
				.build();
		try {
			adminAdmin.createCompany(testCompany1);
			System.out.println(testCompany1);
			System.out.println("company added.");

		} catch (AlreadyExistsException e) {
			System.out.println(e.getMessage());
		}
		System.out.println();

		// Cannot Update Company name and id
		System.out.println("*******admin facade - try update company information*******");
		System.out.println("~~~trying to change id and name~~~");
		admin.getOneCompany(testCompany1.getId());
		System.out.println("          *Company #10 before update attempt*");
		System.out.println(admin.findCompanyByID(testCompany1.getId()));
		try {
			testCompany1.setId(99);
			admin.updateCompany(testCompany1);
		} catch (NoAccessException e) {
			System.out.println(e.getMessage());
		}

		try {
			testCompany1.setName("sadgads");
			admin.updateCompany(testCompany1);
		} catch (NoAccessException e) {
			System.out.println(e.getMessage());
		}
		System.out.println();
		System.out.println("          *Company #10 after update attempt*");
		System.out.println(admin.findCompanyByID(testCompany1.getId()));

		System.out.println("~~~trying to change email and password~~~");
		Company testCompany3 = Company.builder()
				.name("Audi")
				.email("audi@gmail.com")
				.password("1234")
				.build();
		System.out.println("          *Company #11 before update attempt*");
		admin.createCompany(testCompany3);
		System.out.println(admin.findCompanyByID(testCompany3.getId()));
		try {
			testCompany3.setEmail("dasgasdgadsggasddasga");
			testCompany3.setPassword("asdhdsahadshadshdsahsdh");

			admin.updateCompany(testCompany3);
		} catch (NoAccessException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("          *Company #11 after update attempt*");
		System.out.println(admin.findCompanyByID(testCompany3.getId()));

		try {
			testCompany3.setEmail("audi@gmail.com");
			testCompany3.setPassword("1234");
			admin.updateCompany(testCompany3);
		} catch (Exception e) {
		}

		System.out.println("            *Retrun info to original.*");
		System.out.println(admin.findCompanyByID(testCompany3.getId()));

		// Delete Coupons Purchase history + delete available coupons from this company
		System.out.println("*******admin facade - remove FK and purchased coupons.*******");
		System.out.println("Removing A company.");
		admin.deleteCompany(11);
		System.out.println("Company 11 Deleted");
		System.out.println();

		// Return all companies
		System.out.println("*******admin facade - get all companies*******");
		System.out.println(admin.getallCompanies());
		System.out.println();

		// Return one Company
		System.out.println("*******admin facade - get one company*******");
		System.out.println(admin.getOneCompany(1));
		System.out.println();

		// Cannot add used customer email.
		System.out.println("*******admin facade - Cannot add a customer used email.*******");
		System.out.println("~~~trying to add used email~~~");
		Customer customertest = Customer.builder()
				.firstName("Drake")
				.lastName("champaigne")
				.email("jay@gmail.com")
				.password("123")
				.build();
		try {
			admin.addCustomer(customertest);
		} catch (AlreadyExistsException e) {
			System.out.println(e.getMessage());
		}
		System.out.println();

		// adding unique customer
		System.out.println("~~~trying to add a unique customer~~~");
		Customer customertest1 = Customer.builder()
				.firstName("Drake")
				.lastName("champaigne")
				.email("drizzy@gmail.com")
				.password("123")
				.build();
		try {
			admin.addCustomer(customertest1);
			System.out.println(customertest1);
			System.out.println("customer added.");
		} catch (AlreadyExistsException e) {
			System.out.println(e.getMessage());
		}
		System.out.println();

		// Cannot Update Customer ID
		System.out.println("*******admin facade - cant change customer information*******");
		System.out.println("~~~trying to change customer id~~~");
		Customer customer = admin.getOneCustomer(customertest1.getId());
		try {
			customer.setId(2000);
			admin.updateCustomer(customer, 10);
		} catch (NoAccessException e) {
			System.out.println(e.getMessage());
		}
		System.out.println();

		System.out.println("~~~trying to change customer info~~~");
		customer.setFirstName("asdgadsgasdgdsadsgf");
		customer.setLastName("asdfgdsadsgadsg");
		customer.setEmail("asddasgagsd");
		customer.setPassword("asdgasdadsg");
		try {
			admin.updateCustomer(customer, 10);
		} catch (NoAccessException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("customer after attempt");
		System.out.println(admin.getOneCustomer(10));

		System.out.println("~~~return to original customer info~~~");
		try {
			customer.setFirstName("Drake");
			customer.setLastName("champaigne");
			customer.setEmail("drizzy@gmail.com");
			customer.setPassword("123");
			admin.updateCustomer(customer, 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println(admin.getOneCustomer(10));
		System.out.println();

		// Delete customer coupon purchase
		System.out.println("*******admin facade - deleting customer purchased coupons.*******");
		System.out.println("Removing A customer.");
		admin.deleteCustomer(5);
		System.out.println("Customer 5 Deleted");
		System.out.println();

		// return all Customers.
		System.out.println("*******admin facade - get all customers*******");
		System.out.println(admin.getAllCustomers());
		System.out.println();

		// return one customer
		System.out.println("*******admin facade - get one customer*******");
		System.out.println(admin.getOneCustomer(1));
		System.out.println();

	}

}
