package com.bbytes.daas.client;

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

/**
 * URL constants
 *
 * @author Thanneer
 *
 * @version 
 */
public class URLConstants {

	
	public static final String LOGIN_OAUTH = "/oauth/token";
	
	public static final String SERVER_CONTEXT="/daas-rest-server";
	
	public static final String MANAGEMENT_CONTEXT="/management";
	
	public static final String CREATE_DELETE_ACCOUNT="/accounts/%s";
	
	public static final String CREATE_DELETE_APPLICATION="/accounts/%s/applications/%s";
	
	public static final String GET_ALL_ACCOUNT="/accounts";
	
	public static final String GET_ALL_APPLICATIONS="/accounts/%s/applications";
	
	public static final String CREATE_ACCOUNT_USER="/accounts/%s/user";
	
	
}
