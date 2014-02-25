package com.bbytes.daas.client;

import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.Future;

import com.bbytes.daas.domain.Account;
import com.bbytes.daas.domain.Application;
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
 * Daas mgmt client
 * 
 * @author Thanneer
 * 
 * @version 0.0.1
 */
public class DaasManagementClient extends DaasClient {

	/**
	 * Default constructor or Spring init
	 */
	public DaasManagementClient() {
		super();
	}

	public DaasManagementClient(String host, String port) {
		super(host, port);
	}

	/**
	 * Logs in to the DAAS Server
	 * 
	 * @param clientId
	 * @param clientSecret
	 * @return
	 * @throws DaasClientException
	 */
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

	/**
	 * Creates an account with the account name passed and returns it
	 * 
	 * @param accName
	 * @return
	 * @throws DaasClientException
	 */
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

	/**
	 * Activates or deactivates an account based on the boolean value passed
	 * 
	 * @param accountName
	 * @return
	 * @throws DaasClientException
	 */
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

	/**
	 * Deletes an account. Returns true if successfull
	 * 
	 * @param accName
	 * @return
	 * @throws DaasClientException
	 */
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

	/**
	 * Returns all the accounts in the Daas Server
	 * 
	 * @return
	 * @throws DaasClientException
	 */
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

	/**
	 * Returns a specific account identified by the account name
	 * 
	 * @param accName
	 * @return
	 * @throws DaasClientException
	 */
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

	/**
	 * Creates an application
	 * 
	 * @param application
	 * @return
	 * @throws DaasClientException
	 */
	public Application createApplication(Application application) throws DaasClientException {

		try {
			String appName = URLEncoder.encode(application.getName(), "UTF-8");
			String accName = URLEncoder.encode(application.getAccountName(), "UTF-8");
			String url = baseURL + URLConstants.MANAGEMENT_CONTEXT
					+ String.format(URLConstants.CREATE_DELETE_APPLICATION, accName, appName);
			Future<Response> f = buildRequest("post", url).setBody(gson.toJson(application))
					.setHeader("Content-Type", "application/json").execute();
			Response r = f.get();
			if (!HttpStatusUtil.isSuccess(r))
				throw new DaasClientException("Application creation failed : " + r.getResponseBody());

			application = gson.fromJson(r.getResponseBody(), Application.class);
			return application;

		} catch (Exception e) {
			throw new DaasClientException(e);
		}
	}

	/**
	 * Deletes an application
	 * 
	 * @param accName
	 * @param appName
	 * @return
	 * @throws DaasClientException
	 */
	public boolean deleteApplication(String accName, String appName) throws DaasClientException {

		try {
			appName = URLEncoder.encode(appName, "UTF-8");
			String url = baseURL + URLConstants.MANAGEMENT_CONTEXT
					+ String.format(URLConstants.CREATE_DELETE_APPLICATION, accName, appName);
			Future<Response> f = buildRequest("delete", url).execute();
			Response r = f.get();
			if (!HttpStatusUtil.isSuccess(r))
				throw new DaasClientException("Application deletion failed : " + r.getResponseBody());

			return true;

		} catch (Exception e) {
			throw new DaasClientException(e);
		}

	}

	/**
	 * Returns all the applications under an account
	 * 
	 * @param accName
	 * @return
	 * @throws DaasClientException
	 */
	public List<Application> getApplications(String accName) throws DaasClientException {

		String url = baseURL + URLConstants.MANAGEMENT_CONTEXT
				+ String.format(URLConstants.GET_ALL_APPLICATIONS, accName);
		try {
			Future<Response> f = buildRequest("get", url).execute();
			Response r = f.get();
			if (!HttpStatusUtil.isSuccess(r))
				throw new DaasClientException("Could not fetch all applications : " + r.getResponseBody());

			List<Application> applications = gson.fromJson(r.getResponseBody(), new TypeToken<List<Application>>() {
			}.getType());
			return applications;

		} catch (Exception e) {
			throw new DaasClientException(e);
		}
	}

	/**
	 * Returns the application identified by the name
	 * 
	 * @param accName
	 * @param appName
	 * @return
	 * @throws DaasClientException
	 */
	public Application getApplication(String accName, String appName) throws DaasClientException {

		if (accName == null || accName.isEmpty()) {
			throw new DaasClientException("Account Name is null");
		}

		if (appName == null || appName.isEmpty()) {
			throw new DaasClientException("App Name is null");
		}

		String url = baseURL + URLConstants.MANAGEMENT_CONTEXT
				+ String.format(URLConstants.GET_APPLICATIONS, accName, appName);
		try {
			Future<Response> f = buildRequest("get", url).execute();
			Response r = f.get();
			if (!HttpStatusUtil.isSuccess(r))
				throw new DaasClientException("Could not fetch all applications : " + r.getResponseBody());

			Application applications = gson.fromJson(r.getResponseBody(), new TypeToken<Application>() {
			}.getType());
			return applications;

		} catch (Exception e) {
			throw new DaasClientException(e);
		}
	}

	/**
	 * Creates an Account level admin user
	 * 
	 * @param accName
	 * @param user
	 * @return
	 * @throws DaasClientException
	 */
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

	
	/**
	 * Creates an Application level user
	 * 
	 * @param accName
	 * @param appName
	 * @param user
	 * @return
	 * @throws DaasClientException
	 */
	public DaasUser createApplicationUser(String accName, String appName, DaasUser user) throws DaasClientException {

		try {
			accName = URLEncoder.encode(accName, "UTF-8");
			String url = baseURL + URLConstants.MANAGEMENT_CONTEXT
					+ String.format(URLConstants.CREATE_APPLICATION_USER, accName, appName);
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
}
