package com.couponsystem.jay.schedule;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.couponsystem.jay.beans.Coupon;
import com.couponsystem.jay.service.facade.CompanyFacadeService;
import com.couponsystem.jay.service.jpa.CouponService;

@Component
public class ExpiredCouponsDailyThread implements Runnable{

		private boolean quit = false;
		
		@Autowired
		private CouponService couponService;
		@Autowired
		private CompanyFacadeService companyFacadeService;
		
	public ExpiredCouponsDailyThread() {
		super();
	}
	
	/**
	 * 
	 * This class is responsible for a daily job (once in 24hours), to delete of
	 * coupons that are expired.
	 * 
	 */
	
	@Scheduled(fixedDelay = 1000*10)
	@Override
	public void run() {
		while (!quit) {
			List<Coupon> coupons = couponService.getAllCoupons();
			for (Coupon coupon : coupons) {
				if (coupon.getEndDate().before(new Date(System.currentTimeMillis()))) {
					companyFacadeService.deleteCoupon(coupon.getId());
				}
			}
			try {
				
				Thread.sleep(1000*10);
			} catch (Exception e) {
			System.out.println(e.getMessage());
			}
		}
	}


}
