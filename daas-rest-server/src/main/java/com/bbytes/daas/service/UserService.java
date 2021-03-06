/*
 * Copyright (C) 2013 The Daas Open Source Project 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */
package com.bbytes.daas.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.bbytes.daas.domain.DaasUser;
import com.bbytes.daas.rest.DaasEntityNotFoundException;
import com.bbytes.daas.rest.DaasException;
import com.bbytes.daas.rest.DaasPersistentException;

/**
 * 
 *
 * @author Thanneer
 *
 * @version 
 */
public interface UserService {

	@PreAuthorize("hasRole('ROLE_TENENT_ADMIN')")
	public DaasUser createAccountUser(String accountName, DaasUser user) throws DaasPersistentException;
	
	@PreAuthorize("hasRole('ROLE_TENENT_ADMIN')")
	public List<DaasUser> getAccountUsers(String accountName) throws DaasEntityNotFoundException;
	
	@PreAuthorize("hasAnyRole('ROLE_TENENT_ADMIN','ROLE_ACCOUNT_ADMIN')")
	public DaasUser createApplicationUser(String accountName, String applicationName,DaasUser user) throws DaasPersistentException;
	
	@PreAuthorize("hasAnyRole('ROLE_TENENT_ADMIN','ROLE_ACCOUNT_ADMIN')")
	public List<DaasUser> getApplicationUsers(String accountName, String applicationName) throws DaasEntityNotFoundException;
	
	
	@PreAuthorize("hasAnyRole('ROLE_TENENT_ADMIN','ROLE_ACCOUNT_ADMIN')")
	public DaasUser updateUserPassword(String oldPassword,String newPassword, String userId) throws DaasPersistentException, DaasEntityNotFoundException, DaasException;
	
}
