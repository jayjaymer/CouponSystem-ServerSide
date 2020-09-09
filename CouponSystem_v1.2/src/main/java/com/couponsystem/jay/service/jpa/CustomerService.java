package com.couponsystem.jay.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.couponsystem.jay.beans.Coupon;
import com.couponsystem.jay.beans.Customer;
import com.couponsystem.jay.exceptions.NotFoundException;
import com.couponsystem.jay.repo.CustomerRepository;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository repo;
	
	//50%
	public void addCustomer(Customer customer) {
		repo.save(customer);
	}

	public void updateCustomer(Customer customer) {
		repo.saveAndFlush(customer);
	}

	public void deleteCustomer(int customerID) {
		repo.deleteById(customerID);

	}

	public List<Customer> getAllCustomer() {
		if ( repo.findAll() != null) {
		}
		return repo.findAll();
	}

	public Customer getOneCustomerByCustomerID(int customerID) throws NotFoundException {
		if (repo.getOne(customerID) != null) {
			return repo.getOne(customerID);
		}else {
			throw new NotFoundException("No customer found for this id!");
		}
	}
	
	//40%
	public boolean checkIfCustomer(String email, String password) {
		if (repo.findByEmailAndPassword(email, password) != null) {
			return true;
		}else {
			return false;
		}

	}
	public Customer findCustomerByID(int customerID) throws NotFoundException {
		if (repo.findById(customerID)!=null) {
			return repo.findById(customerID);
		}else {
			throw new NotFoundException("Customer id not found!");
		}
	}

	public Customer getOneCustomerByEmailAndPassword(String email, String password) {
		if (repo.findByEmailAndPassword(email, password)==null) {
		}
		return repo.findByEmailAndPassword(email, password);
	}
	
	//10%
	// get the customer coupons.
	public List<Coupon> getAllPurchasedCoupons(){
		return repo.getCustomersVsCoupons();
	}
	
}
