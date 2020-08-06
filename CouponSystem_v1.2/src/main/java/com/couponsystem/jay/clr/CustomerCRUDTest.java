package com.couponsystem.jay.clr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.couponsystem.jay.beans.Customer;
import com.couponsystem.jay.exceptions.NotFoundException;
import com.couponsystem.jay.service.CustomerService;
import com.couponsystem.jay.util.Print;

@Component
@Order(value = 3)
public class CustomerCRUDTest implements CommandLineRunner {
	@Autowired
	CustomerService customerService;

	@Override
	public void run(String... args) throws Exception {
		System.out.println();
		System.out.println();
		System.out.println();
		
		Print print = new Print();
		
		System.out.println();
		print.customerTEST(null);
		System.out.println();

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~CUSTOMER DUMMY TEST~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println();
		
		
		// ADD CUSTOMERS
		System.out.println("****Adding Customers****");

		Customer customer1 = Customer.builder()
				.firstName("Jay")
				.lastName("Mer")
				.email("jay@gmail.com")
				.password("123")
				.build();
		
		Customer customer2 = Customer.builder()
				.firstName("Shiran")
				.lastName("cohen")
				.email("shiran@gmail.com")
				.password("123")
				.build();
		
		Customer customer3 = Customer.builder()
				.firstName("Ora")
				.lastName("shemesh")
				.email("ora@gmail.com")
				.password("1234")
				.build();
		
		Customer customer4 = Customer.builder()
				.firstName("Kobe")
				.lastName("Bryant")
				.email("bryant@gmail.com")
				.password("123")
				.build();
		
		Customer customer5 = Customer.builder()
				.firstName("Lil")
				.lastName("Wayne")
				.email("wayne@gmail.com")
				.password("123")
				.build();
		Customer customer6 = Customer.builder()
				.firstName("Kendrick")
				.lastName("Lamar")
				.email("lamar@gmail.com")
				.password("123")
				.build();
		Customer customer7 = Customer.builder()
				.firstName("Test7")
				.lastName("test")
				.email("test7@gmail.com")
				.password("12347")
				.build();
		Customer customer8 = Customer.builder()
				.firstName("Test8")
				.lastName("test")
				.email("test8@gmail.com")
				.password("1234")
				.build();
		Customer customer9 = Customer.builder()
				.firstName("Test9")
				.lastName("test")
				.email("test9@gmail.com")
				.password("12349")
				.build();
		

		customerService.addCustomer(customer1);
		customerService.addCustomer(customer2);
		customerService.addCustomer(customer3);
		customerService.addCustomer(customer4);
		customerService.addCustomer(customer5);
		customerService.addCustomer(customer6);
		customerService.addCustomer(customer7);
		customerService.addCustomer(customer8);
		customerService.addCustomer(customer9);
		System.out.println("Customers are added.");
		System.out.println();

		// UPDATE CUSTOMERS
		System.out.println("****Update Customer****");
		System.out.println("Customer9 email address before update : "+ customer9.getEmail());
		customer9.setEmail("ahfafdshhafsd");
		customerService.updateCustomer(customer9);
		System.out.println("Customer9 email address after update : " + customer9.getEmail());
		System.out.println(customerService.getOneCustomerByCustomerID(9));
		System.out.println();
		
		// DELETE CUSTOMER
		System.out.println("****Customer DELETE****");
		customerService.deleteCustomer(9);
		System.out.println("Customer 9 Deleted");
		System.out.println();

		// GET ALL CUSTOMERS
		System.out.println("****Customers Registered****");
		System.out.println(customerService.getAllCustomer());
		System.out.println();

		// GET ONE CUSTOMER
		System.out.println("****Get one registerd Customer By ID****");
		System.out.println("real info test");
		System.out.println(customerService.findCustomerByID(1));
		System.out.println("fake info test");
		try {
			System.out.println(customerService.findCustomerByID(55));
		} catch (NotFoundException e) {
			System.out.println(e.getMessage());
		}
		System.out.println();
		
		// CHECK IF CUSTOMER EXISTS
		System.out.println("****Customer Existence ****");
		System.out.println("real info test");
		System.out.println(customerService.checkIfCustomer("jay@gmail.com", "123"));
		System.out.println("fake info test");
		System.out.println(customerService.checkIfCustomer("adshadshasdhdsa@gmail.com", "123"));
		System.out.println();
		
	}

}
