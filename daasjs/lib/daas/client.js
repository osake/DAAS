/**
 * This module is to be used as the actual client toe create and manage
 * entities. In general the URL structure followed for accessing this is /<accountName>/<applicationName>/<entityType>/<entityUuid>
 * 
 * where accountName is the name of the account created, applicationName is the
 * name of the application created, and the entityType is the type (or
 * collection) if entity created and entityUuid is the uuid of that entity
 * 
 * For e.g. If you have account TATA, and application MOTORS, and an entity CARS
 * then the url to create a car would be:
 * 
 * POST : /TATA/MOTORS/CARS/
 * 
 * If you created a CAR with UUID INDICA, then you can get the car using the URL
 * 
 * GET: /TATA/MOTORS/CARS/INDICA
 * 
 * Note: For creating account and application, you should refer mgmt module
 * 
 * 
 */
define([ "daas/http", "daas/config" ], function(http, config) {

	var _baseUrl = config.getBaseUrl();
	return {

		/**
		 * API to create the entity
		 * 
		 * @param accountName
		 * @param applicationName
		 * @param entityType
		 * @param entity
		 * @param callback
		 * @param authToken
		 * @returns
		 */
		createEntity : function(accountName, applicationName, entityType,
				entity, callback, authToken) {
			var url = _baseUrl + accountName + "/" + applicationName + "/"
					+ entityType;
			http.post(url, entity, callback, "json", config.CONTENT_TYPE_JSON,
					authToken);
		},
		/**
		 * Returns an entity of an entity type by specifying the id
		 * 
		 * @param accountName
		 * @param applicationName
		 * @param entityType
		 * @param entityUuid
		 * @param callback
		 * @param authToken
		 * @returns
		 */
		getEntity : function(accountName, applicationName, entityType,
				entityUuid, callback, authToken) {
			var url = _baseUrl + accountName + "/" + applicationName + "/"
					+ entityType + "/" + entityUuid;
			http.get(url, callback, "json", authToken);
		},
		/**
		 * Returns a list of entities of the specific entityType
		 * 
		 * @param accountName
		 * @param applicationName
		 * @param entityType
		 * @param callback
		 * @param authToken
		 * @returns
		 */
		getEntities : function(accountName, applicationName, entityType,
				callback, authToken) {
			var url = _baseUrl + accountName + "/" + applicationName + "/"
					+ entityType;
			http.get(url, callback, "json", authToken);
		},
		/**
		 * Updated a specific entity identified by the uuid
		 * 
		 * @param accountName
		 * @param applicationName
		 * @param entityType
		 * @param entity
		 * @param entityUuid
		 * @param callback
		 * @param authToken
		 * @returns
		 */
		updateEntity : function(accountName, applicationName, entityType,
				entity, entityUuid, callback, authToken) {
			var url = _baseUrl + accountName + "/" + applicationName + "/"
					+ entityType + "/" + entityUuid;
			http.put(url, entity, callback, "json", config.CONTENT_TYPE_JSON,
					authToken);
		},
		/**
		 * Deletes a entity
		 * 
		 * @param accountName
		 * @param applicationName
		 * @param entityType
		 * @param entityUuid
		 * @param callback
		 * @param authToken
		 * @returns
		 */
		deleteEntity : function(accountName, applicationName, entityType,
				entityUuid, callback, authToken) {
			var url = _baseUrl + accountName + "/" + applicationName + "/"
					+ entityType + "/" + entityUuid;
			http.deleteRequest(url, callback, "json", authToken);
		},
		/**
		 * Adds a relation between 2 entity objects. Url for a relation between
		 * CARS and TYRES identified by the name RUNSON, we send a request to
		 * the url
		 * 
		 * /TATA/MOTORS/CARS/INDICA/RUNSON/TYRES/MRF
		 * 
		 * where CARS and TYRES are two entity types created using the
		 * #createEntity API and INDICA & MRF are the UUID's of the those
		 * entities respectively
		 * 
		 * @param accountName
		 * @param applicationName
		 * @param entityType
		 * @param entityUuid
		 * @param relation
		 * @param relatedEntityType
		 * @param relatedEntityUuid
		 * @param callback
		 * @param authToken
		 * @returns
		 */
		addRelation : function(accountName, applicationName, entityType,
				entityUuid, relation, relatedEntityType, relatedEntityUuid,
				callback, authToken) {
			var url = _baseUrl + accountName + "/" + applicationName + "/"
					+ entityType + "/" + entityUuid + "/" + relation + "/"
					+ relatedEntityType + "/" + relatedEntityUuid;

			http.post(url, null, callback, "json", config.CONTENT_TYPE_JSON,
					authToken);
		},
		/**
		 * Returns all the related objects B of the entity A identified by
		 * entityId defined by the relation R. A->B by R then return B
		 * 
		 * @param accountName
		 * @param applicationName
		 * @param entityType
		 * @param entityUuid
		 * @param relation
		 * @param relatedEntityType
		 * @param callback
		 * @param authToken
		 * @returns
		 */
		getRelatedEntities : function(accountName, applicationName, entityType,
				entityUuid, relation, relatedEntityType, callback, authToken) {
			var url = _baseUrl + accountName + "/" + applicationName + "/"
					+ entityType + "/" + entityUuid + "/" + relation + "/"
					+ relatedEntityType;
			http.get(url, callback, "json", authToken);
		},
		/**
		 * 
		 * This is the reverse of getRelatedEntities
		 * 
		 * Returns the relating (connecting) objects A from the related Entity B defined by relation R.
		 * A->B by R then return A for each B
		 * 
		 * @param accountName
		 * @param applicationName
		 * @param relatedEntityType
		 * @param relatedEntityUuid
		 * @param relation
		 * @param callback
		 * @param authToken
		 * @returns
		 */
		getConnectingEntities : function(accountName, applicationName, relatedEntityType,
				relatedEntityUuid, relation, callback, authToken) {
			var url = _baseUrl + accountName + "/" + applicationName + "/"
					+ relatedEntityType + "/" + relatedEntityUuid + "/connecting/" + relation;
			http.get(url, callback, "json", authToken);
		},
		
		
		/**
		 * Deletes the relation between to entity objects 
		 * 
		 * @param accountName
		 * @param applicationName
		 * @param entityType
		 * @param entityUuid
		 * @param relation
		 * @param relatedEntityType
		 * @param relatedEntityUuid
		 * @param callback
		 * @param authToken
		 * @returns
		 */
		deleteRelation : function(accountName, applicationName, entityType,
				entityUuid, relation, relatedEntityType, relatedEntityUuid,
				callback, authToken) {
			var url = _baseUrl + accountName + "/" + applicationName + "/"
					+ entityType + "/" + entityUuid + "/" + relation + "/"
					+ relatedEntityType + "/" + relatedEntityUuid;
			http.deleteRequest(url, callback, "text", authToken);
		},
		
	};
});