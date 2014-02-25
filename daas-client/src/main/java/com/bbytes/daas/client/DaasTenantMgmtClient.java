package com.bbytes.daas.client;

import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.Future;

import com.bbytes.daas.domain.Account;
import com.bbytes.daas.domain.DaasUser;
import com.google.gson.reflect.TypeToken;
import com.ning.http.client.Response;

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

/**
 * Daas tenant mgmt client
 * 
 * @author Thanneer
 * 
 * @version 0.0.1
 */
public class DaasTenantMgmtClient extends DaasAccountMgmtClient implements IDaasTenantMgmtClient {

	/**
	 * Default constructor or Spring init
	 */
	public DaasTenantMgmtClient() {
		super();
	}

	public DaasTenantMgmtClient(String host, String port) {
		super(host, port);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bbytes.daas.client.IDaasManagementClient#login(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean login(String clientId, String clientSecret) throws DaasClientException {
		// first verify if port and host name is correct using the ping url
		if (!pingSuccess())
			throw new DaasClientException("Not able to reach daas server on" + baseURL);

		token = DaasClientUtil.loginHelper(clientId, clientSecret, baseURL, asyncHttpClient, gson);
		if (token == null) {
			throw new DaasClientException("Not able to login to daas server on" + baseURL);
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bbytes.daas.client.IDaasManagementClient#createAccount(java.lang.String)
	 */
	@Override
	public Account createAccount(String accName) throws DaasClientException {

		try {
			accName = URLEncoder.encode(accName, "UTF-8");
			String url = baseURL + URLConstants.MANAGEMENT_CONTEXT
					+ String.format(URLConstants.CREATE_DELETE_ACCOUNT, accName);
			Future<Response> f = buildRequest("post", url).execute();
			Response r = f.get();
			if (!HttpStatusUtil.isSuccess(r))
				throw new DaasClientException("Account creation failed : " + r.getResponseBody());

			Account account = gson.fromJson(r.getResponseBody(), Account.class);
			return account;

		} catch (Exception e) {
			throw new DaasClientException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bbytes.daas.client.IDaasManagementClient#activateOrDeactivateAccount(java.lang.String,
	 * boolean)
	 */
	@Override
	public boolean activateOrDeactivateAccount(String accountName, boolean activate) throws DaasClientException {
		try {
			accountName = URLEncoder.encode(accountName, "UTF-8");
			String url = baseURL + URLConstants.MANAGEMENT_CONTEXT
					+ String.format(URLConstants.CREATE_DELETE_ACCOUNT, accountName) + "/" + activate;
			Future<Response> f = buildRequest("put", url).execute();
			Response r = f.get();
			if (!HttpStatusUtil.isSuccess(r))
				throw new DaasClientException("Account creation failed : " + r.getResponseBody());
			return true;

		} catch (Exception e) {
			throw new DaasClientException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bbytes.daas.client.IDaasManagementClient#deleteAccount(java.lang.String)
	 */
	@Override
	public boolean deleteAccount(String accName) throws DaasClientException {
		try {
			accName = URLEncoder.encode(accName, "UTF-8");
			String url = baseURL + URLConstants.MANAGEMENT_CONTEXT
					+ String.format(URLConstants.CREATE_DELETE_ACCOUNT, accName);
			Future<Response> f = buildRequest("delete", url).execute();
			Response r = f.get();
			if (!HttpStatusUtil.isSuccess(r))
				throw new DaasClientException("Account deletion failed : " + r.getResponseBody());

			return true;

		} catch (Exception e) {
			throw new DaasClientException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bbytes.daas.client.IDaasManagementClient#getAccounts()
	 */
	@Override
	public List<Account> getAccounts() throws DaasClientException {
		String url = baseURL + URLConstants.MANAGEMENT_CONTEXT + URLConstants.GET_ALL_ACCOUNT;
		try {
			Future<Response> f = buildRequest("get", url).execute();
			Response r = f.get();
			if (!HttpStatusUtil.isSuccess(r))
				throw new DaasClientException("Could not fetch all accounts : " + r.getResponseBody());

			List<Account> accounts = gson.fromJson(r.getResponseBody(), new TypeToken<List<Account>>() {
			}.getType());
			return accounts;

		} catch (Exception e) {
			throw new DaasClientException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bbytes.daas.client.IDaasManagementClient#getAccount(java.lang.String)
	 */
	@Override
	public Account getAccount(String accName) throws DaasClientException {
		if (accName == null || accName.isEmpty()) {
			throw new DaasClientException("Account Name is null");
		}
		String url = baseURL + URLConstants.MANAGEMENT_CONTEXT + String.format(URLConstants.GET_ACCOUNT, accName);
		try {
			Future<Response> f = buildRequest("get", url).execute();
			Response r = f.get();
			if (!HttpStatusUtil.isSuccess(r))
				throw new DaasClientException("Could not fetch all accounts : " + r.getResponseBody());

			Account accounts = gson.fromJson(r.getResponseBody(), new TypeToken<Account>() {
			}.getType());
			return accounts;

		} catch (Exception e) {
			throw new DaasClientException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bbytes.daas.client.IDaasManagementClient#createAccountUser(java.lang.String,
	 * com.bbytes.daas.domain.DaasUser)
	 */
	@Override
	public DaasUser createAccountUser(String accName, DaasUser user) throws DaasClientException {

		try {
			accName = URLEncoder.encode(accName, "UTF-8");
			String url = baseURL + URLConstants.MANAGEMENT_CONTEXT
					+ String.format(URLConstants.CREATE_ACCOUNT_USER, accName);
			Future<Response> f = buildRequest("post", url).setBody(gson.toJson(user))
					.setHeader("Content-Type", "application/json").execute();
			Response r = f.get();
			if (!HttpStatusUtil.isSuccess(r))
				throw new DaasClientException("Account user creation failed : " + r.getResponseBody());

			DaasUser accountUser = gson.fromJson(r.getResponseBody(), DaasUser.class);
			return accountUser;

		} catch (Exception e) {
			throw new DaasClientException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bbytes.daas.client.IDaasManagementClient#isSuperAdmin()
	 */
	@Override
	public boolean isSuperAdmin() {
		if (token != null && token.getAdditionalInformation() != null) {
			Object isSuperAdmin = token.getAdditionalInformation().get("isSuperAdmin");
			if(isSuperAdmin == null) {
				return true;
			}
			else {
				return (boolean) isSuperAdmin;
			}
		}
		return false;
	}


	@Override
	public DaasUser getAccountUser(String accName) throws DaasClientException {
		try {
			accName = URLEncoder.encode(accName, "UTF-8");
			String url = baseURL + URLConstants.MANAGEMENT_CONTEXT
					+ String.format(URLConstants.CREATE_ACCOUNT_USER, accName);
			Future<Response> f = buildRequest("get", url).execute();
			Response r = f.get();
			if (!HttpStatusUtil.isSuccess(r))
				throw new DaasClientException("Account user retrieval failed : " + r.getResponseBody());

			DaasUser accountUser = gson.fromJson(r.getResponseBody(), DaasUser.class);
			return accountUser;

		} catch (Exception e) {
			throw new DaasClientException(e);
		}
	}

}
