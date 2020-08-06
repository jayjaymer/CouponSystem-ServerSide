package com.couponsystem.jay.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.couponsystem.jay.beans.Company;


public interface CompanyRepository extends JpaRepository<Company, Integer> {
	
	//40%
	Company findByEmailAndPassword(String email, String password);
	Company findById(int companyID);
	
	
}
