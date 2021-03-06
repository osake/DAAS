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
package com.bbytes.daas.db;

import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.bbytes.daas.dao.AccountDao;
import com.bbytes.daas.dao.ApplicationDao;
import com.bbytes.daas.dao.UserDao;
import com.bbytes.daas.domain.Account;
import com.bbytes.daas.domain.Application;
import com.bbytes.daas.domain.DaasUser;
import com.bbytes.daas.domain.Role;
import com.bbytes.daas.rest.DaasEntityNotFoundException;
import com.bbytes.daas.rest.DaasPersistentException;

/**
 * 
 * 
 * @author Thanneer
 * 
 * @version
 */
@Transactional
public class UserDAOTest extends BaseDBTest {

	private static final Logger LOG = Logger.getLogger(UserDAOTest.class);

	@Autowired
	private ApplicationDao applicationDao;
	
	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private UserDao userDao;

	private String uuid;
		

	@Before
	public void setUp() throws DaasPersistentException {

		Account org = new Account();
		org.setName(UUID.randomUUID().toString());

		accountDao.save(org);
		

		uuid = UUID.randomUUID().toString();
		Application app1 = new Application();
		app1.setAccountName(org.getName());
		app1.setName(uuid);

		app1 = applicationDao.save(app1);
		
		DaasUser user = new DaasUser();
		user.setEmail("test1@test1.com");
		user.setAccountName(app1.getAccountName());
		userDao.saveAccountUser(user);
		
		DaasUser user2 = new DaasUser();
		user2.setEmail("test2@test2.com");
		user2.setAccountName(app1.getAccountName());
		user2.setApplicationName(app1.getName());
		userDao.saveAppUser(user2);

		LOG.debug("setup ended...");
	}
	


	
	@Test
	public void testFindAccountUser() throws DaasPersistentException, DaasEntityNotFoundException {
		LOG.debug("testFindAccountUser started...");
		Application app = applicationDao.find("name", uuid).get(0);
		assertNotNull(app);
		
		List<DaasUser> users = userDao.findUserByRole(app.getAccountName(), Role.ROLE_ACCOUNT_ADMIN);
		Assert.notEmpty(users);
	
		LOG.debug("testDaoFindByID ended...");

	}

	@Test
	public void testFindAppUser() throws DaasPersistentException, DaasEntityNotFoundException {
		LOG.debug("testFindAppUser started...");
		Application app = applicationDao.find("name", uuid).get(0);
		assertNotNull(app);
		
		List<DaasUser> users = userDao.findUserByRole(app.getAccountName(), app.getName(),Role.ROLE_APPLICATION_USER);
		Assert.notEmpty(users);
	
		LOG.debug("testFindAppUser ended...");

	}
	
	

	
	
}
