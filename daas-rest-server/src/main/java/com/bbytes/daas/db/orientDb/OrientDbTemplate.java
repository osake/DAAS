package com.bbytes.daas.db.orientDb;

/**
 * 
 * 
 * 
 * Helper class that simplifies OrientDb data access code.
 * 
 * @author Thanneer
 * 
 * @version
 */

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import com.orientechnologies.orient.core.db.object.ODatabaseObject;
import com.orientechnologies.orient.core.db.record.ODatabaseRecord;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;

@Component
public class OrientDbTemplate {

	static ThreadLocal<ODatabaseObject> TENANT_MANAGE_DB_INSTANCE = new ThreadLocal<ODatabaseObject>();

	static ThreadLocal<OrientGraph> THREAD_LOCAL_DB_INSTANCE = new ThreadLocal<OrientGraph>();

	static ThreadLocal<ODatabaseObject> THREAD_LOCAL_OBJECT_DB_INSTANCE = new ThreadLocal<ODatabaseObject>();

	@Autowired
	private OrientDbConnectionManager connectionManager;

	public OrientDbTemplate() {
	}

	/**
	 * Get the default db i.e graph db
	 * 
	 * @return
	 */
	public OrientGraph getDatabase() {
		OrientGraph db = getThreadLocalGraphDB();
		// The database is valid and is open if its not null, so just return it
		if (db != null && !db.isClosed())
			return db;

		db = connectionManager.getDatabase();

		THREAD_LOCAL_DB_INSTANCE.set(db);

		return db;
	}

	/**
	 * Get object db
	 * 
	 * @return
	 */
	public ODatabaseRecord getDocumentDatabase() {
		return getDatabase().getRawGraph();
	}

	/**
	 * Get the main tenant mgmt db
	 * 
	 * @return
	 */
	public ODatabaseObject getTenantManagementDatabase() {

		ODatabaseObject db = getThreadLocalTenantManagementDB();
		// The database is valid and is open if its not null, so just return it
		if (db != null && !db.isClosed())
			return db;

		db = connectionManager.getTenantManagementDatabase();

		TENANT_MANAGE_DB_INSTANCE.set(db);

		return db;

	}

	protected OrientGraph getThreadLocalGraphDB() {
		OrientGraph db = THREAD_LOCAL_DB_INSTANCE.get();
		if (db != null && !db.isClosed()) {
			return db;
		}

		return null;
	}

	protected ODatabaseObject getThreadLocalObjectDB() {
		ODatabaseObject db = THREAD_LOCAL_OBJECT_DB_INSTANCE.get();
		if (db != null && !db.isClosed()) {
			return db;
		}

		return null;
	}

	protected ODatabaseObject getThreadLocalTenantManagementDB() {
		ODatabaseObject db = TENANT_MANAGE_DB_INSTANCE.get();
		if (db != null && !db.isClosed()) {
			return db;
		}

		return null;
	}

	/**
	 * drop the db
	 * 
	 * @param databaseName
	 * @return
	 */
	public boolean dropDatabase(String databaseName) {
		boolean success = connectionManager.dropDatabase(databaseName);
		if (success && TenantRouter.getTenantIdentifier().equals(databaseName)) {
			clearThreadLocalDB();
		}
		return success;
	}

	/**
	 * Get the object db
	 * 
	 * @return
	 */
	public ODatabaseObject getObjectDatabase() {
		ODatabaseObject db = getThreadLocalObjectDB();
		// The database is valid and is open if its not null, so just return it
		if (db != null && !db.isClosed())
			return db;

		db = connectionManager.getObjectDatabase();

		THREAD_LOCAL_OBJECT_DB_INSTANCE.set(db);

		return db;
	}

	/**
	 * The method to create new database
	 * 
	 * @param dbName
	 *            the db name
	 * @param dbType
	 *            value can be 'graph' or 'object'
	 * @throws IOException
	 */
	public void createDatabase(String dbName, String dbType) {
		connectionManager.createDatabase(dbName, dbType);
	}

	/**
	 * Clears the Thread Local DB
	 */
	public static void clearThreadLocalDB() {
		THREAD_LOCAL_DB_INSTANCE.set(null);
		THREAD_LOCAL_OBJECT_DB_INSTANCE.set(null);
	}
}