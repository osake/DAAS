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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bbytes.daas.domain.DaasUser;

/**
 * 
 * 
 * @author Thanneer
 * 
 * @version
 */
public class DaasClientChangePasswdTest extends DaasClientBaseTest {

	IDaasTenantMgmtClient daasManagementClient = new DaasTenantMgmtClient(host, port);

	@Before
	public void SetUp() throws DaasClientException {
		boolean success = daasManagementClient.login("admin", "password");
		Assert.assertTrue(success);
		try {
			if (daasManagementClient.getAccount("testAccn") == null)
				daasManagementClient.createAccount("testAccn");
		} catch (Exception e) {
			daasManagementClient.createAccount("testAccn");
		}

	}

	@Test
	public void daasMgmtClientChangePasswdTest() throws DaasClientException {

		DaasUser user = new DaasUser();
		user.setAccountName("testAccn");
		user.setEmail("randonName@test.com");
		user.setName("testRandomuser");
		user.setPassword("testpassword");
		user.setUserName("testRandomuser");
		DaasUser returnedUser = daasManagementClient.createAccountUser("testAccn", user);
		// Assert.assertNotNull(returnedUser);

		daasManagementClient.changePassword("testAccn", returnedUser.getUuid(), "testpassword", "testNewpassword");

		List<DaasUser> daasUsers = daasManagementClient.getAccountUsers("testAccn");
		for (DaasUser daasUser : daasUsers) {
			if (daasUser.getUuid().equals(returnedUser.getUuid()))
				Assert.assertEquals("testNewpassword", daasUser.getPassword());
		}

	}

	@After
	public void cleanUp() throws DaasClientException {
		daasManagementClient.deleteAccount("testAccn");
		asyncHttpClient.close();
	}
}
