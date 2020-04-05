# userservice

this repository can be forked and run in intellij. Mysql server address witth username password needs to be configured in package co.hackerscode.userservice.config jpaConfig class .

following are the api endpoints .


http://{server url }/adduser 

this url is to be called with body as a json object with the following keys :

firstname , lastname , emailid , password 


http://{server url }/deleteuser 

this url is to be called with body as json with following key 
 
 emailid
 
 
 
 http://{server url }/getuser 
 
 this url is to be called with body as ajson with the following keys 
 
 emailid
 
