package com.couponsystem.jay.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.couponsystem.jay.beans.Category;
import com.couponsystem.jay.beans.Coupon;
import com.couponsystem.jay.exceptions.NotFoundException;
import com.couponsystem.jay.repo.CouponRepository;

@Service
public class CouponService {

	@Autowired
	private CouponRepository repo;

	// 50%

	public void addCoupon(Coupon coupon) {
		repo.save(coupon);
	}

	public void updateCoupon(Coupon coupon) {
		repo.saveAndFlush(coupon);
	}

	public void deleteCoupon(int couponID) {
		repo.deleteById(couponID);
	}

	public List<Coupon> getAllCoupons() {
		return repo.findAll();
	}

	public Coupon getOneCouponByID(int couponID) {
		return repo.getOne(couponID);
	}

	// 40%
	public boolean checkIfCoupon(int couponID) {

		return repo.findById(couponID) != null;
	}

	public Coupon findCouponByID(int couponID) throws NotFoundException {
		if (repo.findById(couponID) != null) {
			return repo.findById(couponID);
		} else {
			throw new NotFoundException("Coupon id not found");
		}

	}

	public List<Coupon> getCouponsByCompanyID(int companyID) throws NotFoundException {
		return repo.findByCompanyID(companyID);
	}

	public List<Coupon> getCouponsByCategoryAndCompanyID(Category category, int companyID) throws NotFoundException {
		if (repo.findByCategoryAndCompanyID(category, companyID) == null) {
			throw new NotFoundException("No coupons for this Category");
		}
		return repo.findByCategoryAndCompanyID(category, companyID);
	}

	public List<Coupon> getCouponsByPriceAndCompanyID(double price, int companyID) throws NotFoundException {
		if (repo.findByPriceLessThanAndCompanyID(price, companyID) == null) {
			throw new NotFoundException("No coupons for this Price");
		}
		return repo.findByPriceLessThanAndCompanyID(price, companyID);
	}

	// 10%

	public void addPurchaseCoupon(int customerID, int couponID) {
		repo.addCouponPurchase(customerID, couponID);
	}

	public void deletePurchaseCoupon(int couponID) {
		repo.deleteCouponPurchase(couponID);
	}

	public void deletePurchasedCouponByCouponID(int couponID) {
		repo.deleteCompanyCoupons(couponID);
	}

}
