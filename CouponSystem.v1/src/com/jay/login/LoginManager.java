package com.jay.login;

import com.jay.exceptions.ClientNotFoundException;
import com.jay.exceptions.CompanyExistsException;
import com.jay.exceptions.CompanyNotFoundException;
import com.jay.exceptions.CustomerNotFoundException;
import com.jay.exceptions.NoAccsessException;
import com.jay.facade.AdminFacade;
import com.jay.facade.ClientFacade;
import com.jay.facade.CompanyFacade;
import com.jay.facade.CustomerFacade;

public class LoginManager {

	private static LoginManager instance = null;
	private ClientFacade clientFacade;

	private LoginManager() {
	}

	// LOGIN SINGLETON
	public static LoginManager getInstance() {
		if (instance == null) {
			synchronized (LoginManager.class) {
				if (instance == null) {
					instance = new LoginManager();
				}
			}
		}

		return instance;
	}

	// this method is checking if login info is available WITH the type of the
	// client.
	public ClientFacade login(String email, String password, ClientType clientType) throws ClientNotFoundException,
			CompanyExistsException, CustomerNotFoundException, CompanyNotFoundException, NoAccsessException {
		switch (clientType) {
		case ADMINISTRATOR:
			clientFacade = (ClientFacade) new AdminFacade();
			if (clientFacade.login(email, password)) {
				return clientFacade;
			} else {
				return null;
			}
		case COMPANY:
			clientFacade = (ClientFacade) new CompanyFacade();
			if (clientFacade.login(email, password)) {
				int companyID = ((CompanyFacade) clientFacade).getCompanyID(email, password);
				((CompanyFacade) clientFacade).setCompanyID(companyID);
				return clientFacade;
			} else {
				return null;
			}
		case CUSTOMER:
			clientFacade = (ClientFacade) new CustomerFacade();
			if (clientFacade.login(email, password)) {
				int customerID = ((CustomerFacade) clientFacade).getCustomerID(email, password);
				((CustomerFacade) clientFacade).setCustomerID(customerID);
				return clientFacade;
			}
		default:
			clientFacade = null;
			break;
		}
		return null;
	}

}
