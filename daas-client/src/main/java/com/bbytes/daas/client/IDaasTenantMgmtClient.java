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
package com.bbytes.daas.client;

import java.util.List;

import com.bbytes.daas.domain.Account;
import com.bbytes.daas.domain.DaasUser;

/**
 * 
 * 
 * @author Thanneer
 * 
 * @version
 */
public interface IDaasTenantMgmtClient extends IDaasAccountMgmtClient {

	/**
	 * Logs in to the DAAS Server
	 * 
	 * @param clientId
	 * @param clientSecret
	 * @return
	 * @throws DaasClientException
	 */
	public boolean login(String clientId, String clientSecret) throws DaasClientException;

	/**
	 * Is the logged in admin a super admin or not
	 * 
	 * @return
	 */
	public boolean isSuperAdmin();

	/**
	 * Creates an account with the account name passed and returns it
	 * 
	 * @param accName
	 * @return
	 * @throws DaasClientException
	 */
	public Account createAccount(String accName) throws DaasClientException;

	/**
	 * Activates or deactivates an account based on the boolean value passed
	 * 
	 * @param accountName
	 * @return
	 * @throws DaasClientException
	 */
	public boolean activateOrDeactivateAccount(String accountName, boolean activate) throws DaasClientException;

	/**
	 * Deletes an account. Returns true if successful
	 * 
	 * @param accName
	 * @return
	 * @throws DaasClientException
	 */
	public boolean deleteAccount(String accName) throws DaasClientException;

	/**
	 * Returns all the accounts in the Daas Server
	 * 
	 * @return
	 * @throws DaasClientException
	 */
	public List<Account> getAccounts() throws DaasClientException;

	/**
	 * Returns a specific account identified by the account name
	 * 
	 * @param accName
	 * @return
	 * @throws DaasClientException
	 */
	public Account getAccount(String accName) throws DaasClientException;

	/**
	 * Creates an Account level admin user
	 * 
	 * @param accName
	 * @param user
	 * @return
	 * @throws DaasClientException
	 */
	public DaasUser createAccountUser(String accName, DaasUser user) throws DaasClientException;

}