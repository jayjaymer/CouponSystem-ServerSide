package com.jay.facade;

import com.jay.dao.CompaniesDAO;
import com.jay.dao.CouponsDAO;
import com.jay.dao.CustomersDAO;
import com.jay.dbdao.CompaniesDBDAO;
import com.jay.dbdao.CouponsDBDAO;
import com.jay.dbdao.CustomersDBDAO;
import com.jay.exceptions.ClientNotFoundException;
import com.jay.exceptions.CompanyExistsException;
import com.jay.exceptions.CustomerNotFoundException;

public abstract class ClientFacade {

	// client facade managing all my facades
	// giving access to the DAO/DBDAO

	protected CompaniesDAO companiesDAO = null;
	protected CustomersDAO customersDAO = null;
	protected CouponsDAO couponsDAO = null;

	// throw exception if login failed
	public abstract boolean login(String email, String password)
			throws ClientNotFoundException, CompanyExistsException, CustomerNotFoundException;

	public ClientFacade() {
		companiesDAO = new CompaniesDBDAO();
		customersDAO = new CustomersDBDAO();
		couponsDAO = new CouponsDBDAO();
	}

}
