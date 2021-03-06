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
package com.bbytes.daas.rest;

/**
 * Any exception related to Baas calls will be wrapped in this
 *
 * @author Thanneer
 *
 * @version 1.0.0
 */
public class DaasException extends Exception {

	private static final long serialVersionUID = 6289917997937599218L;

	public DaasException() {
		super();
	}

	public DaasException(String jsonResponse, String message, Throwable cause) {
		super(message, cause);
	}

	public DaasException(String message) {
		super(message);
	}

	public DaasException(Throwable cause) {
		super(cause);
	}

}
