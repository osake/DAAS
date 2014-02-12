# DaasJS #


DaasJs is the Javascript Client module for accessing Daas Rest Services, which will be deployed in a J2EE application. It built using [RequireJS](http://requirejs.org/) which loads the management and client module. The intention is to use this JS module from other NodeJS projects as well as Web Application Projects which will use DAAS as the backend Database

### Modules ###

1. Management

	`mgmt` module acts as the client for all management API calls to DAAS

2. Client for Entities

	`client` module acts as the client for all API calls for doing CRUD operation on an entity.

### Project Structure ###

The DaasJS project structure follows [Tyller Kellen](https://github.com/tkellen)'s [RequireJS library skeleton](https://github.com/tkellen/requirejs-library-skeleton)


### Building the Final JS ###

This will need NodeJS installed in your machine. Once installed, open the NodeJS Command Prompt and CD to the `build` directory

Run the command


    node vendor/r.js -o mainConfigFile=build.js

This will output a JS file `daas-x.y.z.js` in the `dist` folder , where `x.y.z` denotes the version

### Using it in HTML ###

Please refer `index.html` and `daas-web.js` to see how to use it in browser. The `index.html` file has a button, which on the click of it, will call the Ping API which checks if the DAAS Server is up and running.

 

### Using it in Node

TBD 