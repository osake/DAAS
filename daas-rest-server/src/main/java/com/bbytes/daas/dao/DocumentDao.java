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
package com.bbytes.daas.dao;

import java.util.List;
import java.util.Map;

import com.bbytes.daas.domain.DataType;
import com.bbytes.daas.rest.DaasEntityNotFoundException;
import com.bbytes.daas.rest.DaasPersistentException;
import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * Entity DAO
 * 
 * @author Thanneer
 * 
 * @version 1.0.0
 */
public interface DocumentDao {

	public ODocument create(String entityType, Map<String, Object> propertyMap, String orgName, String appName)
			throws DaasPersistentException;

	public ODocument create(String entityType, String entityInJson, String accountName, String appName)
			throws DaasPersistentException;

	public ODocument create(ODocument entity, String accountName, String appName) throws DaasPersistentException;

	public ODocument relate(String primartyEntityType, String primaryEntityId, String secondaryEntityType,
			String secondaryEntityId, String relationName) throws DaasPersistentException;

	public boolean removeRelation(String primartyEntityType, String primaryEntityId, String secondaryEntityType,
			String secondaryEntityId, String relationName) throws DaasPersistentException;

	public List<ODocument> findRelated(String primartyEntityType, String primaryEntityId,String secondaryEntityType, String relationName)
			throws DaasEntityNotFoundException;
	
	public List<ODocument> findRelated(String primartyEntityType, String primaryEntityId, String relationName)
			throws DaasEntityNotFoundException;
	
	public List<ODocument> findRelatedReverse(String secondaryEntityType, String secondaryEntityId,String primartyEntityType, String relationName)
			throws DaasEntityNotFoundException;
	
	public List<ODocument> findRelatedReverse(String secondaryEntityType, String secondaryEntityId, String relationName)
			throws DaasEntityNotFoundException;

	public ODocument update(String uuid, String entityType, String entityJson, String accountName, String appName)
			throws DaasPersistentException;

	public ODocument update(ODocument entity, Map<String, Object> propertyMap, String accountName, String appName)
			throws DaasPersistentException;

	public ODocument update(ODocument entity, String accountName, String appName) throws DaasPersistentException;

	public void remove(ODocument entity, String accountName, String appName) throws DaasPersistentException;

	public void remove(String uuid, String entityType, String accountName, String appName)
			throws DaasPersistentException;

	public ODocument find(ORID id) throws DaasEntityNotFoundException;

	public ODocument findById(String entityType, String uuid) throws DaasEntityNotFoundException;
	
	public List<ODocument> findByProperty(String entityType, String propertyName, String propertyValue) throws DaasEntityNotFoundException;
	
	public List<ODocument> findByProperty(String applicationName, String entityType, String propertyName, String propertyValue) throws DaasEntityNotFoundException;

	public List<ODocument> findByPropertyRange(String applicationName, String entityType, String propertyName,
			DataType propertyDataType, String startRange, String endRange) throws DaasEntityNotFoundException;
			
	public List<ODocument> list(String entityType, String appName) throws DaasPersistentException;

	public long count(String entityType, String appName);

	public boolean findAny(String entityType, String property, String value) throws DaasPersistentException;

	public boolean findAny(String entityType, Map<String, String> propertyToValue) throws DaasPersistentException;

	public boolean exists(ODocument document);

	public boolean exists(ORID rid);

}
