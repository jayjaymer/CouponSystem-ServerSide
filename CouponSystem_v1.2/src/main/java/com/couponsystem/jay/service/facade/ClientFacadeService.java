package com.couponsystem.jay.service.facade;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.couponsystem.jay.exceptions.LoginFailledException;
import com.couponsystem.jay.exceptions.NotFoundException;
import com.couponsystem.jay.service.jpa.CompanyService;
import com.couponsystem.jay.service.jpa.CouponService;
import com.couponsystem.jay.service.jpa.CustomerService;

import lombok.Getter;

@Service
@Getter
public abstract class ClientFacadeService {
	
	@Autowired
	protected CouponService couponService;
	@Autowired
	protected CompanyService companyService;
	@Autowired
	protected CustomerService customerService;
	
	public ClientFacadeService() {
		super();
	}
	
	
	public abstract boolean login (String email, String password) throws LoginFailledException, NotFoundException;
	
	

}
