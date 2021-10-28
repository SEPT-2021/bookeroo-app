# Bookeroo

Welcome to Bookeroo's backend!\
Currently, it consists of 3 microservices which are individual Spring Boot applications.
Each of the microservices is completely uncoupled from others and can be run independently using standard Maven commands. 
All the microservices talk to one Amazon RDS MySQL instance.
- `microservice-login` - For user CRUD, login and registration.
- `microservice-book` - For book CRUD and search. Contains Amazon S3 configuration.
- `microservice-payment` - For payment related operations. Contains PayPal SDK configuration.
- `microservice-admin` - For administrative tasks regarding the application.
- `microservice-seller` - For seller related functionalities that allow sellers to do tasks as verified users.

**Instructions**
- `cd .\backend\microservice-{login | book | payment | admin | seller }`
- `.\mvnw install`
