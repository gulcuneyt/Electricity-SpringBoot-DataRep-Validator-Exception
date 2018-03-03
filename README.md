## Electricity Consumption Demo Application

Electricity Consumption Application is a sample SpringBoot Application has implementations of Spring Data Repository, RESTful Web Services, Spring Data REST Validators, Global exception handling in Spring.


### Technologies & Features

* Java 8 or later
* Maven 3 or later
* Spring Boot 1.5.10
* RESTful Web Services
* Spring Boot Maven plugin
* Spring Data REST Validators
* Error Handling for REST with Spring


### Description of The Application :

- Profile is a person who has a meter device to get the electricity.
- Meter is a device that measures the consumption of the electricity.
- Fraction defines how the consumption of a given year is distributed among months, so all the fractions for the 12 months must sum up 1  


**Run** :


	spring-boot:run


For testing, Postman script file is in the project


**http://localhost:8080/meterreadings  ==> handles meter readings**

	POST http://localhost:8080/meterreadings/addAll
	GET http://localhost:8080/meterreadings/
	GET http://localhost:8080/meterreadings/{id}
	DELETE http://localhost:8080/meterreadings/{id}
	PUT http://localhost:8080/meterreadings/add

**http://localhost:8080/consumptionfractions  ==> handles profile fractions**
	
	POST http://localhost:8080/consumptionfractions/addAll
	GET http://localhost:8080/consumptionfractions/
	GET http://localhost:8080/consumptionfractions/{id}
	DELETE http://localhost:8080/consumptionfractions/{id}
	PUT http://localhost:8080/consumptionfractions/add

**http://localhost:8080/profiles  ==> handles profiles**
	
	GET http://localhost:8080/profiles/
	GET http://localhost:8080/profiles/{id}
	DELETE http://localhost:8080/profiles/{id}
	PUT http://localhost:8080/profiles/add
	

**http://localhost:8080/consumption  ==> handles consumption retrieve**
	
	GET http://localhost:8080/profiles/retrieve
