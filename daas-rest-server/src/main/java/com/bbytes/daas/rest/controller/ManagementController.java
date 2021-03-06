package com.bbytes.daas.rest.controller;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbytes.daas.domain.Account;
import com.bbytes.daas.domain.Application;
import com.bbytes.daas.domain.DaasUser;
import com.bbytes.daas.rest.DaasEntityNotFoundException;
import com.bbytes.daas.rest.DaasException;
import com.bbytes.daas.rest.DaasPersistentException;
import com.bbytes.daas.service.ManagementService;
import com.bbytes.daas.service.UserService;

/**
 * Management Rest service to create Apps and Account
 * 
 * @author Thanneer
 * 
 * @version 1.0.0
 */
@Controller
@RequestMapping("/management")
public class ManagementController {

	private static final Logger LOG = Logger.getLogger(ManagementController.class);

	@Autowired
	private ManagementService managementService;
	
	@Autowired
	private UserService userService;



	/**
	 * Create account
	 * 
	 * @param accountName
	 * @return
	 * @throws DaasException
	 * @throws DaasPersistentException
	 */
	@RequestMapping(value = "/accounts/{accountName}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Account createAccount(@PathVariable("accountName") String accountName) throws DaasPersistentException {
		LOG.debug("Request to create account : " + accountName);
		return managementService.createAccount(accountName);
	}
	
	/**
	 * Get account count
	 * 
	 * @param accountName
	 * @return
	 * @throws DaasException
	 * @throws DaasPersistentException
	 */
	@RequestMapping(value = "/accounts/count", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Long createAccount() throws DaasPersistentException {
		return managementService.getAccountCount();
	}
	
	/**
	 * Get account
	 * 
	 * @param accountName
	 * @return
	 * @throws DaasException
	 * @throws DaasPersistentException
	 * @throws DaasEntityNotFoundException 
	 */
	@RequestMapping(value = "/accounts/{accountName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Account getAccount(@PathVariable("accountName") String accountName) throws DaasPersistentException, DaasEntityNotFoundException {
		return managementService.getAccount(accountName);
	}
	
	/**
	 * Delete account
	 * 
	 * @param accountName
	 * @return
	 * @throws DaasException
	 * @throws DaasPersistentException
	 * @throws DaasEntityNotFoundException 
	 */
	@RequestMapping(value = "/accounts/{accountName}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	boolean deleteAccount(@PathVariable("accountName") String accountName) throws DaasPersistentException, DaasEntityNotFoundException {
		return managementService.deleteAccount(accountName);
	}

	/**
	 * Create account user
	 * 
	 * @param accountName
	 * @param user
	 * @return
	 * @throws DaasException
	 * @throws DaasPersistentException
	 */
	@RequestMapping(value = "/accounts/{accountName}/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	DaasUser createAccountUser(@PathVariable("accountName") String accountName, @RequestBody DaasUser user)
			throws DaasException, DaasPersistentException {
		return userService.createAccountUser(accountName, user);
	}
	
	@RequestMapping(value = "/accounts/{accountName}/user/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,  consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	String changePassword(@PathVariable("accountName") String accountName, @PathVariable("id") String userUuid, @RequestBody String data)
			throws DaasException, DaasPersistentException {
		JsonNode node = null;
		try {
			node = new ObjectMapper().readTree(data);
			String oldPassword = node.get("oldPassword").getTextValue();
			String newPassword = node.get("newPassword").getTextValue();
			DaasUser updated = userService.updateUserPassword(oldPassword, newPassword, userUuid);
			boolean status = false;
			if(updated!=null) {
				status = true;
			}
			return "{\"status\" : "+ status+" }"; 
		} catch (IOException | DaasEntityNotFoundException e) {
			throw new DaasPersistentException(e);
		}
	}
	
	
	@RequestMapping(value = "/accounts/{accountName}/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<DaasUser> getAccountUsers(@PathVariable("accountName") String accountName)
			throws DaasException, DaasEntityNotFoundException {
		return userService.getAccountUsers(accountName);
	}

	/**
	 * Get all Accounts
	 * 
	 * @return
	 * @throws DaasEntityNotFoundException
	 * @throws DaasException
	 * @throws DaasPersistentException
	 */
	@RequestMapping(value = "/accounts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Account> getAccounts() throws DaasPersistentException, DaasEntityNotFoundException {
		LOG.debug("Request to get all accounts");
		return managementService.getAllAccounts();
	}

	/**
	 * Create App inside account
	 * 
	 * @param accountName
	 * @param applicationName
	 * @param application TODO
	 * @return
	 * @throws DaasException
	 * @throws DaasPersistentException
	 */
	@RequestMapping(value = "/accounts/{accountName}/applications/{applicationName}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Application createApplication(@PathVariable String accountName,
			@PathVariable("applicationName") String applicationName, @RequestBody Application application)
			throws DaasException, DaasPersistentException {
		if (application == null) {
			return managementService.createApplication(accountName, applicationName, null, null, null);
		} else {
			return managementService.createApplication(accountName, applicationName, application.getApplicationType(),
					application.getApplicationSubType(), application.getFullName());
		}
	}
	
	
	/**
	 * Edits the application type and subtype
	 * 
	 * @param accountName
	 * @param applicationName
	 * @param application
	 * @return
	 * @throws DaasException
	 * @throws DaasPersistentException
	 */
	@RequestMapping(value = "/accounts/{accountName}/applications/{applicationName}/edit", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Application editApplication(@PathVariable String accountName,
			@PathVariable("applicationName") String applicationName, @RequestBody Application application)
			throws DaasException, DaasPersistentException {
		if (application != null) {
			return managementService.editApplication(accountName, applicationName, application.getApplicationType(),
					application.getApplicationSubType(), application.getFullName());
		}
		return application;
	}
	
	/**
	 * Get App 
	 * 
	 * @param accountName
	 * @param applicationName
	 * @return
	 * @throws DaasException
	 * @throws DaasPersistentException
	 * @throws DaasEntityNotFoundException 
	 */
	@RequestMapping(value = "/accounts/{accountName}/applications/{applicationName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Application getApplication(@PathVariable String accountName,
			@PathVariable("applicationName") String applicationName) throws DaasPersistentException, DaasEntityNotFoundException {
		return managementService.getApplication(accountName, applicationName);
	}
	
	/**
	 * Delete App inside account
	 * 
	 * @param accountName
	 * @param applicationName
	 * @return
	 * @throws DaasException
	 * @throws DaasPersistentException
	 * @throws DaasEntityNotFoundException 
	 */
	@RequestMapping(value = "/accounts/{accountName}/applications/{applicationName}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	boolean deleteApplication(@PathVariable String accountName,
			@PathVariable("applicationName") String applicationName) throws DaasPersistentException, DaasEntityNotFoundException {
		return managementService.deleteApplication(accountName, applicationName);
	}

	/**
	 * Create account user
	 * 
	 * @param accountName
	 * @param user
	 * @return
	 * @throws DaasException
	 * @throws DaasPersistentException
	 */
	@RequestMapping(value = "/accounts/{accountName}/applications/{applicationName}/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	DaasUser createAppUser(@PathVariable("accountName") String accountName,
			@PathVariable("applicationName") String applicationName, @RequestBody DaasUser user) throws DaasException,
			DaasPersistentException {
		return userService.createApplicationUser(accountName, applicationName, user);
	}

	/**
	 *  get all Apps inside account
	 *  
	 * @param accountName
	 * @return
	 * @throws DaasException
	 * @throws DaasPersistentException
	 * @throws DaasEntityNotFoundException 
	 */
	@RequestMapping(value = "/accounts/{accountName}/applications", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Application> getApplications(@PathVariable String accountName) throws DaasPersistentException, DaasEntityNotFoundException {
		return managementService.getAllApplications(accountName);
	}
}
