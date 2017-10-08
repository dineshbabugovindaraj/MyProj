var express  = require('express');
var app      = express(); 								
var port  	 = process.env.PORT || 8082; 			

var morgan = require('morgan'); 		
var bodyParser = require('body-parser'); 	
var methodOverride = require('method-override'); 

app.use(express.static(__dirname + '/igt')); 				
app.use(morgan('dev')); 									
app.use(bodyParser.urlencoded({'extended':'true'})); 		
app.use(bodyParser.json()); 								
app.use(bodyParser.json({ type: 'application/vnd.api+json' })); 
app.use(methodOverride());
app.use(function(req, res, next) {
	  res.header("Access-Control-Allow-Origin", "*");
	  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
	  next();
	});

app.listen(port);
