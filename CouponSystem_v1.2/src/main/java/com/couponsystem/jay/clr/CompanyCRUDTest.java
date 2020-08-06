package com.couponsystem.jay.clr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.couponsystem.jay.beans.Company;
import com.couponsystem.jay.exceptions.NotFoundException;
import com.couponsystem.jay.service.CompanyService;
import com.couponsystem.jay.util.Print;

@Component
@Order (value = 1)
public class CompanyCRUDTest implements CommandLineRunner {
	@Autowired
	CompanyService companyService;
	
	@Override
	public void run(String... args) throws Exception {
		
		
		System.out.println();
		System.out.println();
		System.out.println();
		
		Print print = new Print();
		System.out.println();
		print.companyTEST(null);
		System.out.println();
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~COMPANY DUMMY TEST~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println(); 
		System.out.println("****Adding Companies****");
		
		Company c1 = Company.builder()
				.name("Cola")
				.email("cola@gmail.com")
				.password("1234")
				.build();
				
		
		Company c2 = Company.builder()
				.name("Tesla")
				.email("tesla@gmail.com")
				.password("1234")
				.build();
		
		Company c3 = Company.builder()
				.name("Soda")
				.email("soda@gmail.com")
				.password("1234")
				.build();
		
		Company c4 = Company.builder()
				.name("Razer")
				.email("razer@gmail.com")
				.password("123")
				.build();
		
		Company c5 = Company.builder()
				.name("Toyota")
				.email("toyota@gmail.com")
				.password("123")
				.build();
		
		Company c6 = Company.builder()
				.name("Cafe Greg")
				.email("cafegreg@gmail.com")
				.password("1234")
				.build();
		
		Company c7 = Company.builder()
				.name("test7")
				.email("test7@gmail.com")
				.password("12347")
				.build();
		
		Company c8 = Company.builder()
				.name("test8")
				.email("test8@gmail.com")
				.password("12348")
				.build();
		
		Company c9 = Company.builder()
				.name("test9")
				.email("test9@gmail.com")
				.password("12349")
				.build();
		
		// ADD COMPANY
		companyService.addCompany(c1);
		companyService.addCompany(c2);
		companyService.addCompany(c3);
		companyService.addCompany(c4);
		companyService.addCompany(c5);
		companyService.addCompany(c6);
		companyService.addCompany(c7);
		companyService.addCompany(c8);
		companyService.addCompany(c9);
		System.out.println("*Companies are added.*");
		System.out.println();
		
		// UPDATE COMPANY
		System.out.println("****Updating Company****");
		System.out.print("Company9 name before update : "+c9.getName());
		c9.setName("asdhsdhafdshfdshsfdhsdf");
		companyService.updateCompany(c9);
		System.out.println();
		System.out.println("Company9 name after update : "+c9.getName());
		System.out.println(companyService.getOneCompanyByID(9));
		System.out.println();
		
		// DELETE COMPANY
		System.out.println("****Company DELETE****");
		System.out.println("Removing company ID 9");
		companyService.deleteCompany(9);
		System.out.println("Company  Deleted");
		System.out.println();
		
		// GET ALL COMPANIES
		System.out.println("****Companies Registered****");
		System.out.println(companyService.getAllCompanies());
		System.out.println();
		
		// GET ONE COMPANY
		System.out.println("****Get one registerd Company By ID****");
		System.out.println("real info test");
		System.out.println(companyService.findCompanyByID(2));
		System.out.println("fake info test");
		try {
			companyService.findCompanyByID(55);
		} catch (NotFoundException e) {
			System.out.println(e.getMessage());
		}
		System.out.println();
		
		// IS COMPANY EXISTS
		System.out.println("****Company Existence ****");
		System.out.println("real info test");
		System.out.println(companyService.checkIfCompany("cola@gmail.com", "1234"));
		System.out.println("fake info test");
		System.out.println(companyService.checkIfCompany("asdgadsgdsagasd@gmail.com", "1234"));
		System.out.println();
		
	

		
		
	}

}
