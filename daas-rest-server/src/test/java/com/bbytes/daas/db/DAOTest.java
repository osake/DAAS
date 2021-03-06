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
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
public class DAOTest extends BaseDBTest {

	private static final Logger LOG = Logger.getLogger(DAOTest.class);

	@Autowired
	private ApplicationDao applicationDao;
	
	@Autowired
	private AccountDao accountDao;

	private String uuid;
		

	@Before
	public void setUp() throws DaasPersistentException {

		Account org = new Account();
		org.setName(UUID.randomUUID().toString());

		accountDao.save(org);
		
		Account org2 = new Account();
		org2.setName(UUID.randomUUID().toString());

		org2 = accountDao.save(org2);
		System.out.println(org2);

		Application app1 = new Application();
		app1.setName(UUID.randomUUID().toString());

		app1 = applicationDao.save(app1);
		System.out.println(app1);

		for (int i = 0; i < 2; i++) {
			Application app = new Application();
			app.setAccountName(org.getName());
			app.setName(UUID.randomUUID().toString());
			app = applicationDao.save(app);
			uuid = app.getUuid();
		}

		LOG.debug("setup ended...");
	}
	
	

	@Test
	public void testDaoQueryAll() throws DaasPersistentException, DaasEntityNotFoundException {
		long start = Calendar.getInstance().getTimeInMillis();
		int size = applicationDao.findAll().size();
		System.out.println("application object size in DB " + size);
		assertTrue(size > 0);
		long stop = Calendar.getInstance().getTimeInMillis();

		LOG.debug("Time taken to fetch in sec " + (start - stop) / 1000);

	}

	@Test
	public void testDaoCount() throws DaasPersistentException {
		long start = Calendar.getInstance().getTimeInMillis();
		long size = applicationDao.count();
		LOG.debug("app object size " + size);
		assertTrue(size > 0);
		long stop = Calendar.getInstance().getTimeInMillis();

		LOG.debug("Time taken to fetch size in sec " + (start - stop) / 1000);

	}

	@Test
	public void testDaoFindByID() throws DaasPersistentException, DaasEntityNotFoundException {
		LOG.debug("testDaoFindByID started...");
		long start = Calendar.getInstance().getTimeInMillis();
		Application app = applicationDao.find(uuid);
		assertNotNull(app);
		long stop = Calendar.getInstance().getTimeInMillis();

		LOG.debug("testDaoFindByID ended...");

	}

	@Test
	public void testDaoIsAvailable() throws DaasPersistentException, DaasEntityNotFoundException {
		LOG.debug("isAvailable test...");

		Application app = applicationDao.find(uuid);
		assertNotNull(app);

		boolean available = applicationDao.findAny("uuid", uuid);
		assertTrue(available);

		available = applicationDao.findAny("uuid", "dummy uuid");
		assertTrue(!available);

		available = accountDao.findAny("name", "dummy org");
		assertTrue(!available);

	}

	

	
	@Test(expected=DaasPersistentException.class)
	public void testDuplicateAccount() throws DaasPersistentException {

		String accName = UUID.randomUUID().toString();
		
		Account org1 = new Account();
		org1.setName(accName);

		accountDao.save(org1);
		
		Account org2 = new Account();
		org2.setName(accName);

		accountDao.save(org2);
	}
	
	@Test(expected=DaasPersistentException.class)
	public void testDuplicateApp() throws DaasPersistentException {

		String orgName = UUID.randomUUID().toString();
		String appName = UUID.randomUUID().toString();
		
		Application app = new Application();
		app.setAccountName(orgName);
		app.setName(appName);
		app = applicationDao.save(app);
		
		
		Application app2 = new Application();
		app2.setAccountName(orgName);
		app2.setName(appName);
		app2 = applicationDao.save(app2);
		
	}
	
	

	
	
}
