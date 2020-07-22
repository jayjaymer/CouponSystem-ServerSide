package com.jay.Application;

import com.jay.load.CouponSystem;

public class Program {

	public static void main(String[] args) throws Exception {

		CouponSystem.getInstance(); // application singleton

		Test test = new Test();
		test.testALL(); // testing all method
		
		CouponSystem.getInstance().shutdownCouponSystem(); // shutdown of application + daily job

	}
}
