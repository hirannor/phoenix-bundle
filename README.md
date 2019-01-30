# PHOENIX-BUNDLE-0.0.1-SNAPSHOT

Simple Java based web application, which uses Json Web Token Authentication & Authorization
## Technology stack
- Spring-Boot
- H2 (in memory db)
- JPA & HIBERNATE
- Swagger Open API
- Angular 7

## Current Features
- Signup (with confirm notification)
- Reset password (with confirm notification)
- Admin Role
    - User Management (CRUD related operations)
- User Role
    - User profile
    
## Building and running the application

Run the following maven command **mvn install -Pbuild-and-copy-angular-frontend** to build and copy angular UI to spring boot's static folder

When the build is done run the following maven command **mvn spring-boot:run -Dspring-boot.run.profiles=production**
in **phoenix-bundle/phoenix-web/** artifact

## Swagger UI URL
http://localhost:8080/swagger-ui.html

## H2 Database URL
http://localhost:8080/h2

## Demo users for the application

| Username |  Password | Role  |
|----------|-----------|-------|
|  admin   | password  | ADMIN |
|  user1   | password  | USER  |
|  user2   | password  | USER  | 
|  user3   | password  | USER  |
|  user4   | password  | USER  | 
|  user5   | password  | USER  | 
|  user6   | password  | USER  | 
