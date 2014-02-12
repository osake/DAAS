define([ "daas/mgmt", "daas/client" ], function(mgmt, client) {
	return {
		crudEntity : function() {
			var currentdate = new Date();
			var _today = currentdate.getHours() + currentdate.getMinutes()
					+ currentdate.getSeconds();
			var _accName = "AC1" + _today;
			var _authToken = "";
			var _appName = "APP1" + _today;
			var _uuid = "";

			module("Management Module - Set 1 Entity CRUD", {});

			asyncTest('Delete Account', function() {
				mgmt.deleteAccount(_accName, function(data) {
					ok(data, "Deleted account");
					start();
				}, _authToken);
			});
			
			asyncTest('Delete Car', function() {
				client.deleteEntity(_accName, _appName, "cars", _uuid, function(data) {
					ok(data, "Deleted car");
					start();
				}, _authToken);
				
			});
			
			asyncTest('Update Car', function() {
				var car = {
					"year" : "2009",
					"fuel" : "Diesel"
				};

				client.updateEntity(_accName, _appName, "cars", car,_uuid, function(
						data) {
					equal(data.name, "punto", "Update Entity is Successful");
					equal(data.year, "2009", "Update Entity is Successful");
					start();
				}, _authToken);
			});
			

			asyncTest('Get Car', function() {
			
				client.getEntity(_accName, _appName, "cars", _uuid, function(data) {
					equal(data.maker, "FIAT", "Get Entity is Successful");
					start();
				}, _authToken);
				
			});
			
			asyncTest('Create Car', function() {
				var car = {
					"name" : "punto",
					"maker" : "FIAT"
				};

				client.createEntity(_accName, _appName, "cars", car, function(
						data) {
					_uuid = data.uuid;
					equal(data.name, car.name, "Create Entity is Successful");
					start();
				}, _authToken);
			});

			asyncTest('Create Application', function() {
				var application = {
					"accountName" : _accName,
					"applicationType" : "RETAIL",
					"applicationSubType" : "GROCERY"
				};

				mgmt.createApplication(_accName, _appName, application,
						function(data) {
							equal(data.name, _appName,
									"Create Application is Successful");
							start();
						}, _authToken);
			});

			asyncTest('Create Account', function() {
				mgmt.createAccount(_accName, function(data) {
					equal(data.name, _accName, "Create Acc is Successful");
					start();
				}, _authToken);
			});

			asyncTest('Logs in', function() {
				mgmt.login("admin", "password", null, function(data) {
					_authToken = data.value;
					ok(data, "login is fine");
					start();
				});
			});

		}

	};
});