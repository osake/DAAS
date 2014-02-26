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

import com.bbytes.daas.domain.Application;
import com.bbytes.daas.domain.DaasUser;

/**
 * Daas accn mgmt client
 * 
 * @author Thanneer
 * 
 * @version
 */
public interface IDaasAccountMgmtClient extends IDaasClient {

	/**
	 * Logs in to the DAAS Server as account admin
	 * 
	 * @param accountName
	 * @param clientId
	 * @param clientSecret
	 * @return
	 * @throws DaasClientException
	 */
	public boolean login(String accountName, String clientId, String clientSecret) throws DaasClientException;

	/**
	 * Change user password 
	 * @param accountName
	 * @param userid
	 * @param oldPassword
	 * @param newPassword
	 * @return true|false
	 * @throws DaasClientException
	 */
	public boolean changePassword(String accountName, String userid, String oldPassword, String newPassword)
			throws DaasClientException;

	/**
	 * Creates an application
	 * 
	 * @param application
	 * @return
	 * @throws DaasClientException
	 */
	public Application createApplication(Application application) throws DaasClientException;

	/**
	 * Deletes an application
	 * 
	 * @param accName
	 * @param appName
	 * @return
	 * @throws DaasClientException
	 */
	public boolean deleteApplication(String accName, String appName) throws DaasClientException;

	/**
	 * Returns all the applications under an account
	 * 
	 * @param accName
	 * @return
	 * @throws DaasClientException
	 */
	public List<Application> getApplications(String accName) throws DaasClientException;

	/**
	 * Returns the application identified by the name
	 * 
	 * @param accName
	 * @param appName
	 * @return
	 * @throws DaasClientException
	 */
	public Application getApplication(String accName, String appName) throws DaasClientException;

	/**
	 * Creates an Application level user
	 * 
	 * @param accName
	 * @param appName
	 * @param user
	 * @return
	 * @throws DaasClientException
	 */
	public DaasUser createApplicationUser(String accName, String appName, DaasUser user) throws DaasClientException;

	/**
	 * @param accName
	 * @param appName
	 * @return
	 * @throws DaasClientException
	 */
	public DaasUser getApplicationUser(String accName, String appName) throws DaasClientException;

}