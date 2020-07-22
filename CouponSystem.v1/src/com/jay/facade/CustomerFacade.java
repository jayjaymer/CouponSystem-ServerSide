package com.jay.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jay.beans.Category;
import com.jay.beans.Coupon;
import com.jay.beans.Customer;
import com.jay.beans.CustomerVsCoupon;
import com.jay.dao.CouponsDAO;
import com.jay.dao.CustomersDAO;
import com.jay.dbdao.CouponsDBDAO;
import com.jay.dbdao.CustomersDBDAO;
import com.jay.exceptions.CouponNotFoundException;
import com.jay.exceptions.CouponPurchaseException;
import com.jay.exceptions.CustomerNotFoundException;

public class CustomerFacade extends ClientFacade {

	CustomersDAO customerdao = new CustomersDBDAO();
	CouponsDAO coupondao = new CouponsDBDAO();
	Customer customer;

	private int customerID;

	public CustomerFacade() {
		super();
	}

	@Override
	public boolean login(String email, String password) throws CustomerNotFoundException {
		List<Customer> customers = customersDAO.getAllCustomers();
		for (Customer custum : customers) {
			if (custum.getEmail().equalsIgnoreCase(email) && custum.getPassword().equalsIgnoreCase(password)) {
				System.out.println("Customer logged in.");
				return true;
			}
		}
		System.out.println("Customer not found.");
		return false;

	}

	/**
	 * on this method the customer is purchasing a coupon ONLY IF : the coupon is
	 * NOT purchased by the same customer the amount of the coupon is NOT 0 the
	 * coupon is NOT expired. AFTER A purchase is made, amount decreased by 1.
	 * 
	 * @param coupon - insert a coupon to Database
	 * @throws CouponPurchaseException - if one of the conditions not passed
	 */
	public void purchaseCoupon(Coupon coupon) throws CouponPurchaseException {
		List<CustomerVsCoupon> customerVsCoupons = coupondao.getAllPurchased();
		// not allowed to buy a purchased coupon.
		for (CustomerVsCoupon customerVsCoupon : customerVsCoupons) {
			if (customerVsCoupon.getCustomerID() == this.customerID
					&& customerVsCoupon.getCouponID() == coupon.getId()) {
				throw new CouponPurchaseException("Coupon already Purchased once by customer " + customerID);

			}
		}

		// cant purchase coupon when amount is 0
		Coupon couponpurchase = couponsDAO.getOneCoupon(coupon.getId());
		if (couponpurchase.getAmount() <= 0) {
			throw new CouponPurchaseException("Coupon out of stock");
		}

		// if date is expired, coupon is not available.
		if (couponpurchase.getEndDate().before(new Date())) {
			throw new CouponPurchaseException("Coupon is Expired!");
		}

		// decrease coupon amount by 1 if purchased.
		System.out.println("purchase coupon in proccess.... decreasing coupon amount.");
		System.out.println("amount before decrease : " + couponpurchase.getAmount());
		couponpurchase.setAmount(couponpurchase.getAmount() - 1);
		couponsDAO.updateCoupon(couponpurchase);
		System.out.println("amount after decrease : " + couponpurchase.getAmount());
		coupondao.addCouponPurchase(this.customerID, coupon.getId());
		;
		System.out.println("coupon was purchased.");

	}

	// get all the coupons of the connected to the customer
	public List<Coupon> getCustomerCoupons() throws CouponPurchaseException {
		List<Coupon> getall = new ArrayList<>();
		List<CustomerVsCoupon> customerVsCoupons = coupondao.getAllPurchased();
		for (CustomerVsCoupon customerVsCoupon : customerVsCoupons) {
			if (customerVsCoupon.getCustomerID() == customerID) {
				getall.add(coupondao.getOneCoupon(customerVsCoupon.getCouponID()));
			}
		}
		return getall;
	}

	// get all the coupons of the connected to the customer by category
	public List<Coupon> getCustoCouponsByCategory(Category category) throws CouponNotFoundException {
		List<Coupon> getall = new ArrayList<Coupon>();
		List<CustomerVsCoupon> customerVsCoupons = coupondao.getAllPurchased();
		for (CustomerVsCoupon customerVsCoupon : customerVsCoupons) {
			if (customerVsCoupon.getCustomerID() == customerID) {
				getall.add(coupondao.getOneCoupon(customerVsCoupon.getCouponID()));
			}
		}
		List<Coupon> getallbyCate = new ArrayList<Coupon>();
		for (Coupon coupon : getall) {
			if (coupon.getCategory() == category) {
				getallbyCate.add(coupon);
			}
		}
		if (getallbyCate.isEmpty()) {
			throw new CouponNotFoundException("No coupons for this category.");
		}
		return getallbyCate;
	}

	// get all the coupons of the connected to the customer by max price
	public List<Coupon> getCutomerCouponsByMaxPrice(double maxPrice) throws CouponNotFoundException {
		List<Coupon> getall = new ArrayList<Coupon>();
		List<CustomerVsCoupon> customerVsCoupons = coupondao.getAllPurchased();
		for (CustomerVsCoupon customerVsCoupon : customerVsCoupons) {
			if (customerVsCoupon.getCustomerID() == customerID) {
				getall.add(coupondao.getOneCoupon(customerVsCoupon.getCouponID()));
			}
		}
		List<Coupon> getallbyMaxPrice = new ArrayList<Coupon>();
		for (Coupon coupon : getall) {
			if (coupon.getPrice() <= maxPrice) {
				getallbyMaxPrice.add(coupon);
			}
		}

		if (getallbyMaxPrice.isEmpty()) {
			throw new CouponNotFoundException("No coupons for this price.");
		}
		return getall;

	}

	// get all details of customer including coupons purchased by him
	public Customer getCustomerDetails() throws CustomerNotFoundException, CouponPurchaseException {
		Customer customer = customerdao.getOneCustomer(customerID);
		customer.setCoupons(getCustomerCoupons());
		return customer;
	}

	// just a getter for client facade customer login
	public int getCustomerID(String email, String password) throws CustomerNotFoundException {
		Customer customer = customersDAO.getOneCustomerByEmailAndPassword(email, password);
		return customer.getId();
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

}
