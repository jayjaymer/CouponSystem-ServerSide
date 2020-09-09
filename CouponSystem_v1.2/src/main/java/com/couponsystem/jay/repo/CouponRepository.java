package com.couponsystem.jay.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.couponsystem.jay.beans.Category;
import com.couponsystem.jay.beans.Coupon;


public interface CouponRepository extends JpaRepository<Coupon, Integer> {
	
	//40%
	
	Coupon findById(int couponID);
	
	List<Coupon> findByCompanyID(int companyID);
	
	List<Coupon> findByCategoryAndCompanyID(Category category, int companyID);
	
	List<Coupon> findByPriceLessThanAndCompanyID(double price, int companyID);
	
	
	// Company&CustomerVsCoupons
	//10%
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO customers_coupons (customer_id, coupons_id) VALUES (:customerID, :couponID)", nativeQuery = true)
	void addCouponPurchase(int customerID, int couponID);

	@Transactional
	@Modifying
	@Query(value = "DELETE from customers_coupons WHERE coupons_id=:couponID", nativeQuery = true)
	void deleteCouponPurchase(int couponID);

	
	@Transactional
    @Modifying
    @Query(value = "DELETE FROM companies_coupons WHERE coupons_id=:couponID",nativeQuery = true)
    void deleteCompanyCoupons(@Param("couponID") int couponID);
	
	
	
}
