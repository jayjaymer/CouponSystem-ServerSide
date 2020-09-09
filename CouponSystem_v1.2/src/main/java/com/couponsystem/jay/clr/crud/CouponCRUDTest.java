package com.couponsystem.jay.clr.crud;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.couponsystem.jay.beans.Category;
import com.couponsystem.jay.beans.Coupon;
import com.couponsystem.jay.exceptions.NotFoundException;
import com.couponsystem.jay.service.jpa.CouponService;
import com.couponsystem.jay.util.DateUtil;
import com.couponsystem.jay.util.Print;


@Component
@Order(value = 2)
public class CouponCRUDTest implements CommandLineRunner {
	
	@Autowired
	CouponService couponService;
	
	@Override
	public void run(String... args) throws Exception {
		
		
		System.out.println();
		System.out.println();
		System.out.println();
		Print print = new Print();
		
		System.out.println();
		print.couponTEST(null);
		System.out.println();
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~COUPON DUMMY TEST~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println();
		// ADD COUPON
		System.out.println("****Adding Coupons****");
		Coupon coupon1 = Coupon.builder()
				.companyID(1)
				.category(Category.RESTAURANTS)
				.title("Summer Sale - Zero")
				.description("5% Discount for diet coke and zero")
				.startDate(DateUtil.changeDateType(new Date(2020,1,1)))
				.endDate(DateUtil.changeDateType(new Date(2020, 9, 10)))
				.amount(10)
				.price(4.99)
				.image("http://cokecola")
				.build();
		
		Coupon coupon2 = Coupon.builder()
				.companyID(2)
				.category(Category.CARS)
				.title("Model X pre-order sale")
				.description("10% Discount on new version of model X")
				.startDate(DateUtil.changeDateType(new Date(2020,01,01)))
				.endDate(DateUtil.changeDateType(new Date(2020, 10, 10)))
				.amount(5)
				.price(499)
				.image("http://tesla")
				.build();
		
		Coupon coupon3 = Coupon.builder()
				.companyID(3)
				.category(Category.RESTAURANTS)
				.title("Soda deal")
				.description("buy 10 get 2 for free")
				.startDate(DateUtil.changeDateType(new Date(2020,01,01)))
				.endDate(DateUtil.changeDateType(new Date(2020, 8, 8)))
				.amount(500)
				.price(49.99)
				.image("http://cokecola")
				.build();
		
	
				
		Coupon coupon4 = Coupon.builder()
				.companyID(5)
				.category(Category.CARS)
				.title("Toyota supra 5% Discount")
				.description("Brand new 2020 supra discount")
				.startDate(DateUtil.changeDateType(new Date(2020,01,01)))
				.endDate(DateUtil.changeDateType(new Date(2020,9,1)))
				.amount(5)
				.price(499)
				.image("http://toyota")
				.build();

		
		Coupon coupon5 = Coupon.builder()
				.companyID(4)
				.category(Category.ESPORTS)
				.title("Razer Headphones 15% Discount")
				.description("15% off for the new Kraken Headset")
				.startDate(DateUtil.changeDateType(new Date(2020,01,01)))
				.endDate(DateUtil.changeDateType(new Date(2020,10,10)))
				.amount(100)
				.price(15)
				.image("http://razer")
				.build();
		
		Coupon coupon6 = Coupon.builder()
				.companyID(6)
				.category(Category.RESTAURANTS)
				.title("20% Discount for Breakfast")
				.description("Delicious luxory breakfast available until 11:00 AM")
				.startDate(DateUtil.changeDateType(new Date(2020,01,01)))
				.endDate(DateUtil.changeDateType(new Date(2020,12,12)))
				.amount(500)
				.price(15)
				.image("http://greg")
				.build();
		
		Coupon coupon7 = Coupon.builder()
				.companyID(7)
				.category(Category.VACATION)
				.title("Test title 7")
				.description("0 amount coupons test")
				.startDate(DateUtil.changeDateType(new Date(2020,01,01)))
				.endDate(DateUtil.changeDateType(new Date(2020,11,11)))
				.amount(0)
				.price(7)
				.image("http://test7")
				.build();
		
		Coupon coupon8 = Coupon.builder()
				.companyID(8)
				.category(Category.VACATION)
				.title("Test title 8")
				.description("Expired Coupon test ")
				.startDate(DateUtil.changeDateType(new Date(2020,01,01)))
				.endDate(DateUtil.changeDateType(new Date(2020,5,5)))
				.amount(8)
				.price(8)
				.image("http://test8")
				.build();
		
		Coupon coupon9 = Coupon.builder()
				.companyID(4)
				.category(Category.VACATION)
				.title("Test title 9")
				.description("Test dec 9")
				.startDate(DateUtil.changeDateType(new Date(2020,01,01)))
				.endDate(DateUtil.changeDateType(new Date(2020,11,5)))
				.amount(9)
				.price(9)
				.image("http://test9")
				.build();
				
		couponService.addCoupon(coupon1);
		couponService.addCoupon(coupon2);
		couponService.addCoupon(coupon3);
		couponService.addCoupon(coupon4);
		couponService.addCoupon(coupon5);
		couponService.addCoupon(coupon6);
		couponService.addCoupon(coupon7);
		couponService.addCoupon(coupon8);
		couponService.addCoupon(coupon9);
		System.out.println("Coupons are added.");
		System.out.println();
		
		// UPDATE COUPON
		System.out.println("****Updating Coupon****");
		System.out.println("Coupon9 Title before update : "+coupon9.getTitle());
		coupon9.setTitle("asdfhsdfhdfshsdfdfshfds");
		couponService.updateCoupon(coupon9);
		System.out.println("Coupon9 Title after update : "+coupon9.getTitle());
		System.out.println(coupon9);
		System.out.println();
		
		// DELETE COUPON
		System.out.println("****Coupon DELETE****");
		couponService.deleteCoupon(9);
		System.out.println("Coupon 9 Deleted");
		System.out.println();
		
		// GET ALL COUPONS
		System.out.println("****Coupons Registered****");
		System.out.println(couponService.getAllCoupons());
		System.out.println();
		
		// GET ONE COUPON
		System.out.println("****Get one registerd Coupon By ID****");
		System.out.println("real info test");
		System.out.println(couponService.findCouponByID(1));
		System.out.println("fake info test");
		try {
			System.out.println(couponService.findCouponByID(55));
		} catch (NotFoundException e) {
			System.out.println(e.getMessage());
		}
		System.out.println();
		
		// IS COUPON EXISTS
		System.out.println("****Coupon Existence ****");
		System.out.println("real info test");
		System.out.println(couponService.checkIfCoupon(3));
		System.out.println("fake info test");
		System.out.println(couponService.checkIfCoupon(55));
		System.out.println();
		
	}

}
