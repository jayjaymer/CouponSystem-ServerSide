package com.jay.facade;

import java.util.ArrayList;
import java.util.List;

import com.jay.beans.Category;
import com.jay.beans.Company;
import com.jay.beans.Coupon;
import com.jay.dao.CompaniesDAO;
import com.jay.dao.CouponsDAO;
import com.jay.dbdao.CompaniesDBDAO;
import com.jay.dbdao.CouponsDBDAO;
import com.jay.exceptions.CompanyNotFoundException;
import com.jay.exceptions.CouponPurchaseException;
import com.jay.exceptions.NoAccsessException;

public class CompanyFacade extends ClientFacade {

	CompaniesDAO compdbdao = new CompaniesDBDAO();
	CouponsDAO coupondbdao = new CouponsDBDAO();
	Company company;
	private int companyID;

	public CompanyFacade() {
	}

	@Override
	public boolean login(String email, String password) {
		List<Company> companies = companiesDAO.getAllCompanies();
		System.out.println(email + " " + password);
		for (Company company : companies) {
			if (company.getEmail().equalsIgnoreCase(email) && company.getPassword().equalsIgnoreCase(password)) {
				System.out.println("Company logged in");
				return true;
			}
		}
		System.out.println("Company not found.");
		return false;
	}

	/**
	 * on this method the company is creating new coupon ONLY IF : the new coupon is
	 * not used by other coupon from the same company ALLOWED - other company to add
	 * the same name of a coupon.
	 * 
	 * @param coupon - insert a coupon to Database
	 * @throws TitleUsedException - if condition not passed.
	 */
	public void createCoupon(Coupon coupon) throws NoAccsessException {
		// cant add exsiting name of a coupon from the SAME company.
		// can add coupon with the same name from OTHER company

		List<Coupon> coupons = coupondbdao.getCouponsByCompanyID(companyID);

		for (Coupon cpn : coupons) {
			if (coupon.getTitle().equalsIgnoreCase(cpn.getTitle())) {
				throw new NoAccsessException("title is used!");
			}
		}
		couponsDAO.addCoupon(coupon);
	}

	// cant update coupon id or company id
	public void updateCoupon(Coupon coupon) throws NoAccsessException {
		if (companyID != coupon.getCompanyID()) {
			throw new NoAccsessException("No access to change company ID");
		}

		coupondbdao.updateCoupon(coupon);

	}

	// to delete coupon most delete all connections to the coupon
	public void deleteCoupon(int couponID) {
		List<Coupon> coupons = coupondbdao.getAllCoupons();
		for (Coupon coupon : coupons) {
			if (coupon.getId() == couponID) {
				System.out.println("deleting purchase history ");
				couponsDAO.deleteCouponPurchase(coupon.getCompanyID(), couponID);
				System.out.println("deleted.");
			}
		}
		couponsDAO.deleteCoupon(couponID);
	}

	// get all the coupons of the connected company
	public List<Coupon> getCompanyCoupons() throws CouponPurchaseException {

		if (couponsDAO.getCouponsByCompanyID(companyID) != null) {
			return couponsDAO.getCouponsByCompanyID(companyID);
		} else {
			throw new CouponPurchaseException("no coupons found for this company!");
		}
	}

	// get all the coupons of the connected company by category
	public List<Coupon> getCompanyCouponsByCategory(Category category) {
		List<Coupon> result = new ArrayList<Coupon>();
		List<Coupon> coupons = couponsDAO.getAllCoupons();

		if (coupons != null) {
			for (Coupon coupon : coupons) {
				if (coupon.getCategory().equals(category) && coupon.getCompanyID() == companyID) {
					result.add(coupon);
				}
			}
			if (result.isEmpty() == true) {
				System.out.println("no coupons");
			}
		}
		return result;

	}

	// get all the coupons of the connected company by max price
	public List<Coupon> getCompanyCouponsByMaxPrice(double maxPrice) {
		List<Coupon> result = new ArrayList<Coupon>();
		List<Coupon> coupons = couponsDAO.getCouponsByCompanyID(companyID);
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
	public Company getCompanyDetails() throws CompanyNotFoundException, CouponPurchaseException {
		Company companies = companiesDAO.getOneCompany(companyID);
		companies.setCoupons(getCompanyCoupons());
		return companies;
	}

	// just a getter for client facade company login
	public int getCompanyID(String email, String password) throws CompanyNotFoundException {
		Company company = companiesDAO.getOneCompanyByEmailAndPassword(email, password);

		return company.getId();
	}

	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}

	public int getCompanyID() {
		return companyID;
	}

}
