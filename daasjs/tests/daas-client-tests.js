require.config({
	baseUrl : '../lib',
	paths : {
		// the left side is the module ID,
		// the right side is the path to
		// the jQuery file, relative to baseUrl.
		// Also, the path should NOT include
		// the '.js' file extension. This example
		// is using jQuery 1.9.0 located at
		// js/lib/jquery-1.9.0.js, relative to
		// the HTML page.
		jquery : '../vendor/jquery-1.11.0',
		QUnit : '../vendor/qunit-1.14.0',
		tests : '../tests',

	},
	shim : {
		'QUnit' : {
			exports : 'QUnit',
			init : function() {
				QUnit.config.autoload = false;
				QUnit.config.autostart = false;
				QUnit.config.reorder = false;
			}
		}
	}
});

// require the unit tests.
require([ 'QUnit', 'tests/client-test' ], function(QUnit, clienttest) {
	// run the tests.
	clienttest.crudEntity();
	clienttest.relateEntities();
	QUnit.load();
	QUnit.start();
});
