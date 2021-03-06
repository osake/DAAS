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
import java.util.UUID;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bbytes.daas.domain.Account;

/**
 * 
 * 
 * @author Thanneer
 * 
 * @version
 */
public class DaasClientBasicTest extends DaasClientBaseTest {
	

	
	@Before
	public void SetUp() throws DaasClientException {
	}

	@Test
	public void daasClientTest() throws DaasClientException  {
		DaasClient daasClient = new DaasClient(host, port);
		boolean success = daasClient.login("testAccn","testApp","admin", "password");
	}
	
	
	@Test
	public void daasMgmtClientTest() throws DaasClientException  {
		DaasManagementClient daasManagementClient = new DaasManagementClient(host, port);
		boolean success = daasManagementClient.login("admin", "password");
		Assert.assertTrue(success);
	}
	
	@Test(expected=DaasClientException.class)
	public void daasMgmtClientIncorrectCredentialsTest() throws DaasClientException  {
		DaasManagementClient daasManagementClient = new DaasManagementClient(host, port);
		boolean success = daasManagementClient.login("dfdfdf", "fgfgfgfg");
	}
	

	@After
	public void cleanUp() {
		asyncHttpClient.close();
	}
}
