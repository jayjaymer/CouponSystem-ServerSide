package com.couponsystem.jay.service.facade;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.couponsystem.jay.beans.Category;
import com.couponsystem.jay.beans.Company;
import com.couponsystem.jay.beans.Coupon;
import com.couponsystem.jay.exceptions.AlreadyExistsException;
import com.couponsystem.jay.exceptions.LoginFailledException;
import com.couponsystem.jay.exceptions.NoAccessException;
import com.couponsystem.jay.exceptions.NotFoundException;

import lombok.Setter;

@Service
@Scope("prototype")
@Setter
public class CompanyFacadeService extends ClientFacadeService {

	private int companyID;

	@Override
	public boolean login(String email, String password) throws LoginFailledException, NotFoundException {
		System.out.println(email + " " + password);
		if (companyService.findCompanyByEmailAndPassword(email, password) == null) {
			throw new LoginFailledException();
		}
		System.out.println("Company logged in");
		return true;
	}

	/**
	 * on this method the company is creating new coupon ONLY IF : the new coupon is
	 * not used by other coupon from the same company ALLOWED - other company to add
	 * the same name of a coupon.
	 * 
	 * @param coupon - insert a coupon to Database
	 * @throws TitleUsedException - if condition not passed.
	 */
	public void createCoupon(Coupon coupon) throws AlreadyExistsException {
		// cant add exsiting name of a coupon from the SAME company.
		// can add coupon with the same name from OTHER company

		List<Coupon> coupons = couponService.getAllCoupons();

		for (Coupon cpn : coupons) {
			if (coupon.getTitle().equalsIgnoreCase(cpn.getTitle())) {
				throw new AlreadyExistsException("title is used!");
			}
		}
		couponService.addCoupon(coupon);
	}

	// cant update coupon id or company id
	public void updateCoupon(Coupon coupon) throws NoAccessException, NotFoundException {
		int companyCoupon = couponService.getOneCouponByID(coupon.getId()).getCompanyID();

		if (companyCoupon != coupon.getCompanyID()) {
			throw new NoAccessException("No access to change company ID");
		}

		couponService.updateCoupon(coupon);

	}

	// to delete coupon most delete all connections to the coupon
	public void deleteCoupon(int couponID) {
		List<Coupon> coupons = couponService.getAllCoupons();
		for (Coupon coupon : coupons) {
			if (coupon.getId() == couponID) {
				System.out.println("deleting purchase history ");
				couponService.deletePurchaseCoupon(couponID);
				System.out.println("deleted.");
			}
		}
		couponService.deleteCoupon(couponID);
	}

	// get all the coupons of the connected company
	public List<Coupon> getCompanyCoupons() throws NotFoundException {

		return companyService.findCompanyByID(companyID).getCoupons();
	}

	// get all the coupons of the connected company by category
	public List<Coupon> getCompanyCouponsByCategory(Category category) throws NotFoundException {
		List<Coupon> result = new ArrayList<Coupon>();
		List<Coupon> coupons = companyService.findCompanyByID(companyID).getCoupons();

		if (coupons != null) {
			for (Coupon coupon : coupons) {
				if (coupon.getCategory().equals(category)) {
					result.add(coupon);
				}
			}
			if (result.isEmpty() == true) {
				System.out.println("No Coupons for this Category!");
			}
		}
		return result;

	}

	// get all the coupons of the connected company by max price
	public List<Coupon> getCompanyCouponsByMaxPrice(double maxPrice) throws NotFoundException {
		List<Coupon> result = new ArrayList<Coupon>();
		List<Coupon> coupons = companyService.findCompanyByID(companyID).getCoupons();
		for (Coupon coupon : coupons) {
			if (coupon.getPrice() <= maxPrice) {
				result.add(coupon);

			}
		}
		if (result.isEmpty() == true) {
			System.out.println("no coupons for this price");
		}
		return result;
	}

	// get all company info including coupons
	public Company getCompanyDetails() throws NotFoundException {
		Company companies = companyService.findCompanyByID(companyID);
		return companies;
	}


}
