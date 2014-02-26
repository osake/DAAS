package com.bbytes.daas.client;

import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.Future;

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
 * Daas accn mgmt client
 * 
 * @author Thanneer
 * 
 * @version 0.0.1
 */
public class DaasAccountMgmtClient extends DaasClient implements IDaasAccountMgmtClient {

	/**
	 * Default constructor or Spring init
	 */
	public DaasAccountMgmtClient() {
		super();
	}

	public DaasAccountMgmtClient(String host, String port) {
		super(host, port);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bbytes.daas.client.IDaasAccountMgmtClient#login(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean login(String accountName, String clientId, String clientSecret) throws DaasClientException {
		DaasClientUtil.validateArg(accountName, "Account name cannot be null or empty");
		DaasClientUtil.validateArg(clientId, "clientId  cannot be null or empty");
		DaasClientUtil.validateArg(clientSecret, "clientSecret cannot be null or empty");

		this.accountName = accountName;

		// first verify if port and host name is correct using the ping url
		if (!pingSuccess())
			throw new DaasClientException("Not able to reach daas server on " + baseURL);

		token = DaasClientUtil.loginHelper(accountName, clientId, clientSecret, baseURL, asyncHttpClient, gson);
		if (token == null) {
			throw new DaasClientException("Not able to login to daas server on " + baseURL);
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bbytes.daas.client.IDaasManagementClient#createApplication(com.bbytes.daas.domain.Application
	 * )
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bbytes.daas.client.IDaasManagementClient#deleteApplication(java.lang.String,
	 * java.lang.String)
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bbytes.daas.client.IDaasManagementClient#getApplications(java.lang.String)
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bbytes.daas.client.IDaasManagementClient#getApplication(java.lang.String,
	 * java.lang.String)
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bbytes.daas.client.IDaasManagementClient#createApplicationUser(java.lang.String,
	 * java.lang.String, com.bbytes.daas.domain.DaasUser)
	 */
	@Override
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

	@Override
	public DaasUser getApplicationUser(String accName, String appName) throws DaasClientException {
		// TODO : after implementation in server side
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bbytes.daas.client.IDaasAccountMgmtClient#changePassword(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean changePassword(String accountName, String userid, String oldPassword, String newPassword)
			throws DaasClientException {

		DaasClientUtil.validateArg(accountName, "Account Name cannot be null or empty");
		DaasClientUtil.validateArg(userid, "User Id Name cannot be null or empty");
		DaasClientUtil.validateArg(oldPassword, "OldPassword Name cannot be null or empty");
		DaasClientUtil.validateArg(newPassword, "NewPassword Name cannot be null or empty");

		String url = baseURL + URLConstants.MANAGEMENT_CONTEXT + "/accounts/" + accountName + "/user/" + userid
				+ "/password/" + oldPassword + "/" + newPassword;
		try {
			Future<Response> f = buildRequest("get", url).execute();
			Response r = f.get();
			if (!HttpStatusUtil.isSuccess(r))
				throw new DaasClientException("Could not fetch all applications : " + r.getResponseBody());

			boolean result = gson.fromJson(r.getResponseBody(), Boolean.class);
			return result;

		} catch (Exception e) {
			throw new DaasClientException(e);
		}
	}

}
