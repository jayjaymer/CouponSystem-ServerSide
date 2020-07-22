package com.jay.dao;

import java.util.List;

import com.jay.beans.Company;
import com.jay.exceptions.CompanyExistsException;
import com.jay.exceptions.CompanyNotFoundException;

public interface CompaniesDAO {

	boolean isCompanyExists(String email) throws CompanyNotFoundException;

	void addCompany(Company company) throws CompanyExistsException;

	void updateCompany(Company company, int companyID);

	void deleteCompany(int companyID);

	List<Company> getAllCompanies();

	Company getOneCompany(int companyID) throws CompanyNotFoundException;

	Company getOneCompanyByEmailAndPassword(String email, String password) throws CompanyNotFoundException;

}
