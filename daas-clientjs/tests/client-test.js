define([ "daas/mgmt", "daas/client" ], function(mgmt, client) {
	return {
		crudEntity : function() {
			var currentdate = new Date();
			var _today = currentdate.getHours() + currentdate.getMinutes()
					+ currentdate.getSeconds();
			var _accName = "AC11" + _today;
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
		},
		
		relateEntities : function() {
			var currentdate = new Date();
			var _today = currentdate.getHours() + currentdate.getMinutes()
					+ currentdate.getSeconds();
			var _accName = "AC12" + _today;
			var _authToken = "";
			var _appName = "APP1" + _today;
			var _carUuid = "";
			var _wheelUuid = "";

			module("Management Module - Set 2 Entity Relation ships ", {});

			asyncTest('Delete Account', function() {
				mgmt.deleteAccount(_accName, function(data) {
					ok(data, "Deleted account");
					start();
				}, _authToken);
			});
			
			asyncTest('Delete Car & Wheel', function() {
				client.deleteRelation(_accName, _appName, "cars", _carUuid,"runson","wheels", _wheelUuid, function(
						data) {
					equal(data, "success", "Deletion of Relation Wheel to Car is Successful");
					start();
				}, _authToken);
			});
			
			asyncTest('Get Cars of Wheel', function() {
				client.getConnectingEntities(_accName, _appName, "wheels", _wheelUuid,"runson", function(
						data) {
					equal(data[0].name, "punto", "Get Wheel of car is Successful");
					start();
				}, _authToken);
			});
			
			asyncTest('Get Wheels of Car', function() {
				client.getRelatedEntities(_accName, _appName, "cars", _carUuid,"runson","wheels", function(
						data) {
					equal(data[0].name, "Michelin", "Get Cars of Wheel is Successful");
					start();
				}, _authToken);
			});
			
			asyncTest('Relate Car & Wheel', function() {
				client.addRelation(_accName, _appName, "cars", _carUuid,"runson","wheels", _wheelUuid, function(
						data) {
					ok(data, "Relate Wheel to Car is Successful");
					start();
				}, _authToken);
			});
			
			asyncTest('Create Wheel', function() {
				var wheel = {
					"name" : "Michelin",
					"size" : "165"
				};
				client.createEntity(_accName, _appName, "wheels", wheel, function(
						data) {
					_wheelUuid = data.uuid;
					equal(data.name, wheel.name, "Create Wheel is Successful");
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
					_carUuid = data.uuid;
					equal(data.name, car.name, "Create car is Successful");
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