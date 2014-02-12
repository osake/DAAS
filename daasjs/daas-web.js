require.config({
	baseUrl : 'lib',
	paths : {
		jquery : '../vendor/jquery-1.11.0',
	}
});

require([ "jquery", "daas/ping", "daas/mgmt", "daas/client" ], function($, ping, mgmt, client) {

	//Write your methods here to be accessed from the web
	
	$('#ping').click(function() {
		ping.ping(function(data) {
			alert(data);
		});
	});
});