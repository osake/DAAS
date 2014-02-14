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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The oauth token object representation of the json sent from server during authorization
 * 
 * @author Thanneer
 * 
 * @version 0.0.1
 */
public class OAuthToken {

	private String value;

	private Long expiration;

	private String tokenType;

	private List<String> scope = new ArrayList<String>();

	private Integer expiresIn;

	private Boolean expired;

	private Map<String, Object> additionalInformation = new HashMap<String, Object>();

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getExpiration() {
		return expiration;
	}

	public void setExpiration(Long expiration) {
		this.expiration = expiration;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public List<String> getScope() {
		return scope;
	}

	public void setScope(List<String> scope) {
		this.scope = scope;
	}

	/**
	 * @return the additionalInformation
	 */
	public Map<String, Object> getAdditionalInformation() {
		return additionalInformation;
	}

	/**
	 * @param additionalInformation
	 *            the additionalInformation to set
	 */
	public void setAdditionalInformation(Map<String, Object> additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public Integer getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}

	public Boolean getExpired() {
		return expired;
	}

	public void setExpired(Boolean expired) {
		this.expired = expired;
	}

	public boolean isExpired() {
		if (new Date().getTime() > getExpiration())
			return true;

		return false;
	}

}
