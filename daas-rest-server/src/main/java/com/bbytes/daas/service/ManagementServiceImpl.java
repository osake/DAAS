/*
 * Copyright (C) 2013 The Daas Open Source Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.bbytes.daas.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbytes.daas.dao.AccountDao;
import com.bbytes.daas.dao.ApplicationDao;
import com.bbytes.daas.domain.Account;
import com.bbytes.daas.domain.Application;
import com.bbytes.daas.rest.DaasEntityNotFoundException;
import com.bbytes.daas.rest.DaasPersistentException;

/**
 * 
 * 
 * @author Thanneer
 * 
 * @version
 */
@Service("ManagementService")
public class ManagementServiceImpl implements ManagementService {

	@Autowired
	private AccountDao accountDao;

	@Autowired
	private ApplicationDao applicationDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bbytes.daas.service.ManagementService#getAllAccounts()
	 */
	@Override
	public List<Account> getAllAccounts() throws DaasPersistentException, DaasEntityNotFoundException {
		try {
			return accountDao.findAll();
		} catch (DaasEntityNotFoundException e) {
			return new ArrayList<Account>();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.bbytes.daas.service.ManagementService#getAccountCount()
	 */
	@Override
	public long getAccountCount() throws DaasPersistentException {
		return accountDao.count();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bbytes.daas.service.ManagementService#createAccount(java.lang.String)
	 */
	@Override
	public Account createAccount(String accountName) throws DaasPersistentException {
		Account account = new Account();
		account.setName(accountName);
		return accountDao.save(account);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bbytes.daas.service.ManagementService#createApplication(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Application createApplication(String accountName, String applicationName, String applicationType, String applicationSubType, String applicationFullName) throws DaasPersistentException {
		Application app = new Application();
		app.setAccountName(accountName);
		app.setName(applicationName);
		app.setApplicationType(applicationType);
		app.setApplicationSubType(applicationSubType);
		app.setFullName(applicationFullName);
		return applicationDao.save(app);
	}

	
	@Override
	public Application editApplication(String accountName, String applicationName, String applicationType,
			String applicationSubType, String applicationFullName) throws DaasPersistentException {
		List<Application> apps;
		try {
			apps = applicationDao.find("name", applicationName);
			if (apps != null && apps.size() > 0) {
				// account name is unique so get the only one in the list
				Application app = apps.get(0);
				app.setApplicationSubType(applicationSubType);
				app.setApplicationType(applicationType);
				app.setFullName(applicationFullName);
				return applicationDao.update(app);
			}
		} catch (DaasEntityNotFoundException e) {
			throw new DaasPersistentException(e);
		}
		return null;
	}

	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bbytes.daas.service.ManagementService#getAllApplications(java.lang.String)
	 */
	@Override
	public List<Application> getAllApplications(String accountName) throws DaasPersistentException,
			DaasEntityNotFoundException {
		try {
			// we dont have to filter based on account name as we have separate database per account
			return applicationDao.findAll();
		} catch (DaasEntityNotFoundException e) {
			return new ArrayList<Application>();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bbytes.daas.service.ManagementService#deleteAccount(java.lang.String)
	 */
	@Override
	public boolean deleteAccount(String accountName) throws DaasPersistentException, DaasEntityNotFoundException {
		List<Account> accns = accountDao.find("name", accountName);
		if (accns != null && accns.size() > 0) {
			// account name is unique so get the only one in the list
			Account accn = accns.get(0);
			accountDao.remove(accn);
		}

		
		return true;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bbytes.daas.service.ManagementService#deleteApplication(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean deleteApplication(String accountName, String applicationName) throws DaasPersistentException,
			DaasEntityNotFoundException {
		List<Application> apps = applicationDao.find("name", applicationName);
		if (apps != null && apps.size() > 0) {
			// account name is unique so get the only one in the list
			Application app = apps.get(0);
			applicationDao.remove(app);
		}

		return true;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bbytes.daas.service.ManagementService#getAccount(java.lang.String)
	 */
	@Override
	public Account getAccount(String accountName) throws DaasPersistentException, DaasEntityNotFoundException {
		List<Account> accns = accountDao.find("name", accountName);
		if (accns != null && accns.size() > 0) {
			// account name is unique so get the only one in the list
			return accns.get(0);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bbytes.daas.service.ManagementService#getApplication(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Application getApplication(String accountName, String applicationName) throws DaasPersistentException,
			DaasEntityNotFoundException {
		List<Application> apps = applicationDao.find("name", applicationName);
		if (apps != null && apps.size() > 0) {
			// app name is unique so get the only one in the list
			return apps.get(0);
		}
		return null;
	}



}
