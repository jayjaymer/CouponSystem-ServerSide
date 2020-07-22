package com.jay.dao;

import java.util.List;

import com.jay.beans.Coupon;
import com.jay.beans.CustomerVsCoupon;
import com.jay.exceptions.CouponNotFoundException;

public interface CouponsDAO {

	void addCoupon(Coupon coupon);

	void updateCoupon(Coupon coupon);

	void deleteCoupon(int couponID);

	List<Coupon> getAllCoupons();

	Coupon getOneCoupon(int couponID);

	List<Coupon> getCouponsByCompanyID(int companyID);

	boolean isCouponExist(int couponID) throws CouponNotFoundException;

	// Purchased Coupons

	void addCouponPurchase(int customerID, int couponID);

	void deleteCouponPurchase(int customerID, int couponID);
	
	void deleteCouponPurchaseByCouponID(int couponID);

	List<CustomerVsCoupon> getAllPurchased();

}
