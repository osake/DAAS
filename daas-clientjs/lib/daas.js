
/**
 * Main daas module 
 */
define(function(require) {
	var daas = require('daas/core');
	daas.mgmt = require('daas/mgmt');
	daas.ping = require('daas/ping');
	daas.client = require('daas/client');
	return daas;
});