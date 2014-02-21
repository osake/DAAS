/**
 * 
 */
package com.bbytes.daas.domain;

/**
 * Java represenation of Account 
 * 
 * 
 * @author Dhanush Gopinath
 * @version 1.0.0
 */
public class Account extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5673848472822451100L;
	
	public Account() {
		super();
		type = Account.class.getSimpleName();
	}

	//default is true as the already created accounts have to be active
	private boolean active=true;
	/**
	 * Overloaded constructor
	 * 
	 * @param uuid
	 * @param name
	 */
	public Account(String uuid, String name) {
		super();
		type = Account.class.getSimpleName();
		this.uuid = uuid;
		this.name = name;
	}

	
	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
