package com.jay.beans;

public class CustomerVsCoupon {

	private int customerID;
	private int couponID;

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public int getCouponID() {
		return couponID;
	}

	public void setCouponID(int couponID) {
		this.couponID = couponID;
	}

	@Override
	public String toString() {
		return "CustomerVsCoupon [customerID=" + customerID + ", couponID=" + couponID + "]";
	}

}
