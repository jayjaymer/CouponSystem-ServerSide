package com.jay.dao;

import java.util.List;

import com.jay.beans.Customer;
import com.jay.beans.CustomerVsCoupon;
import com.jay.exceptions.CustomerNotFoundException;
import com.jay.exceptions.NoAccsessException;

public interface CustomersDAO {

	boolean isCustomerExists(String email, String password) throws CustomerNotFoundException;

	void addCustomer(Customer customer);

	void updateCustomer(int customerID, Customer customer) throws NoAccsessException;

	void deleteCustomer(int customerID);

	List<Customer> getAllCustomers();

	Customer getOneCustomer(int customerID) throws CustomerNotFoundException;

	List<CustomerVsCoupon> getAllCustomerVsCoupons();

	Customer getOneCustomerByEmailAndPassword(String email, String password) throws CustomerNotFoundException;
}
