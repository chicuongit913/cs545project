# Web Application Architecture #

#### Project Name: CORONA Online Market
#### Duration: 4 working days
#### Professor: Muhyieddin  Al-Tarawneh
##### Team: 2 
##### Member: 
- Chi Cuong Nguyen - 611.111
- Tien Khanh Nguyen - 611.068
- Eman Mohamed - 611.113
- Elsabeth Assefa - 610.829

## Project Technology
| Name | version  |
| ------- | --- | 
| Spring Boot | 2.3.1.RELEASE | 
| Thymeleaf | 2.3.1.RELEASE |
| Bootstrap | 4.5.0 |
| Spring Security | 2.3.1.RELEASE | 
| Auth0 with JWT | 3.10.3 |
| Spring Data JPA | 2.3.1.RELEASE | 
| Hibernate | 5.4.17.Final | 
| Java Functional Programming with Stream, Optional | 8 |
| H2 DB | 1.4.200 | 


## How to installation project
### 1. Pull project package
```sh
    $ git clone https://github.com/chicuongit913/cs545project.git
```

### 2. Deploy project

* Import to Intellij
* Right click pom.xml and add maven project 
* Right clik src/main/java/cs545_project/online_market/OnlineMarketApplication.java and Run the project  
* Enjoy  
 
### 3. Access to project 
```sh
    http://127.0.0.1:8082
```
    
    
## User login to project
| username | password  | role|
| ------- | --- | ---|
| admin | 123456 | ADMIN |
| seller | 123456 | SELLER |
| buyer | 123456 | BUYER |

## Rest APIs
### 1. Claim token 
URL: http://localhost:8082/auth/claim-token     
Payload:    
```
{   
 "username": "[user_name]", 
 "password": "[password]"   
 }
```

### 2. Access APIs resources
Get the generated Token in the response Header which has key-value pair looks like below:   
`Authorization →Bearer eyxxxxx`

Put that key-value pair in every request Header to be able to access the resources from APIs.


to get more user for login you can go to /resources/data.sql and table user