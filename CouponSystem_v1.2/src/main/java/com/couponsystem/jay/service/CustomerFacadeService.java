package com.couponsystem.jay.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.couponsystem.jay.beans.Category;
import com.couponsystem.jay.beans.Coupon;
import com.couponsystem.jay.beans.Customer;
import com.couponsystem.jay.exceptions.AlreadyExistsException;
import com.couponsystem.jay.exceptions.LoginFailledException;
import com.couponsystem.jay.exceptions.NoAccessException;
import com.couponsystem.jay.exceptions.NotFoundException;

@Service
public class CustomerFacadeService extends ClientFacadeService {

	Customer customer;
	private int customerID;

	@Override
	public boolean login(String email, String password) throws LoginFailledException {
		System.out.println(email + " " + password);
		if (customerService.getOneCustomerByEmailAndPassword(email, password) == null) {
			throw new LoginFailledException();
		}
		System.out.println("Customer logged in.");
		return true;
	}

	/**
	 * on this method the customer is purchasing a coupon ONLY IF : the coupon is
	 * NOT purchased by the same customer the amount of the coupon is NOT 0 the
	 * coupon is NOT expired. AFTER A purchase is made, amount decreased by 1.
	 * 
	 * @param coupon - insert a coupon to Database
	 * @throws NotFoundException
	 * @throws CouponPurchaseException - if one of the conditions not passed
	 */

	public void purchaseCoupon(Coupon coupon) throws NoAccessException, AlreadyExistsException, NotFoundException {

		// not allowed to buy a purchased coupon.
		Customer customer = customerService.findCustomerByID(customerID);
		List<Coupon> coupons = customer.getCoupons();
		for (Coupon couponz : coupons) {
			if (couponz.getId() == coupon.getId()) {
				throw new AlreadyExistsException("Sorry, Coupon is already purchased once!");
			}
		}

		// cant purchase coupon when amount is 0
		Coupon couponpurchase = couponService.getOneCouponByID(coupon.getId());
		if (couponpurchase.getAmount() <= 0) {
			throw new NoAccessException("Coupon out of stock");
		}

		// if date is expired, coupon is not available.
		if (couponpurchase.getEndDate().before(new Date())) {
			throw new NoAccessException("Coupon is Expired!");
		}

		// decrease coupon amount by 1 if purchased.
		System.out.println("purchase coupon in proccess.... decreasing coupon amount.");
		System.out.println("amount before decrease : " + couponpurchase.getAmount());
		couponpurchase.setAmount(couponpurchase.getAmount() - 1);
		couponService.updateCoupon(couponpurchase);
		System.out.println("amount after decrease : " + couponpurchase.getAmount());
		couponService.addPurchaseCoupon(this.customerID, coupon.getId());
		System.out.println("coupon was purchased.");

	}

	// get all the coupons of the connected to the customer
	public List<Coupon> getCustomerCoupons() throws NotFoundException {
		return customerService.findCustomerByID(customerID).getCoupons();
	}

	// get all the coupons of the connected to the customer by category
	public List<Coupon> getCustoCouponsByCategory(Category category) throws NotFoundException {
		List<Coupon> getall = new ArrayList<Coupon>();
		List<Coupon> cVc = customerService.findCustomerByID(customerID).getCoupons();
		for (Coupon cVcS : cVc) {
			if (cVcS.getCategory() == category) {
				getall.add(cVcS);
			}
		}
		if (getall.isEmpty()) {
			throw new NotFoundException("No Coupons for this Category!");
		}
		return getall;
	}

	// get all the coupons of the connected to the customer by max price
	public List<Coupon> getCutomerCouponsByMaxPrice(double maxPrice) throws NotFoundException {
		List<Coupon> getall = new ArrayList<Coupon>();
		List<Coupon> cVc = customerService.findCustomerByID(customerID).getCoupons();
		for (Coupon cVcS : cVc) {
			if (cVcS.getPrice() <= maxPrice) {
				getall.add(cVcS);
			}
		}

		if (getall.isEmpty()) {
			throw new NotFoundException("No coupons for this price.");
		}
		return getall;
	}

	// get all details of customer including coupons purchased by him
	public Customer getCustomerDetails() throws NotFoundException {
		Customer customer = customerService.findCustomerByID(customerID);
		return customer;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

}
