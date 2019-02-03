# PHOENIX-BUNDLE-0.0.1-SNAPSHOT

Simple Java based web application, which uses Json Web Token Authentication & Authorization
## Technology stack
- Spring-Boot
- H2 (in memory db)
- Liquibase
- JPA & HIBERNATE
- Swagger Open API
- Angular 7

## Current Features
- Internationalization (i18n)
- Signup (with confirmation notification)
- Reset password (with confirmation notification)
- Admin Role
    - User Management (CRUD related operations)
- User Role
    - User profile
  
## Manual DB bootstrap

Run **mvn clean package -Pdbbootstrap** on **phoenix-bundle/phoenix-core/** artifact
## Building and running the application

Run the following maven command **mvn install -Pdbbootstrap, build-and-copy-angular-frontend** to initialize the database and to build and copy the angular UI to spring boot's static folder

When the build is done run the following maven command **mvn spring-boot:run**
in **phoenix-bundle/phoenix-web/** artifact

## Application URL
https://localhost:8443

## Swagger UI URL
https://localhost:8443/swagger-ui.html

## H2 Database 
**URL**:  https://localhost:8443/h2

**Datasource:**
 - url: jdbc:h2:file:~/phoenixdb
 - username: sa
 - password: 


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
