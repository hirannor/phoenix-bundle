# PHOENIX-BUNDLE-0.0.1-SNAPSHOT

Simple Java based web application, which uses Json Web Token Authentication & Authorization
## Technology stack
- Spring-Boot
- H2 (in memory db)
- JPA & HIBERNATE
- Swagger Open API
- Angular 7

## Current Features
- Signup
- Reset password (with notification)
- Admin Role
    - User Management (CRUD related operations)
- User Role
    - User profile
## Building the application

**mvn install -Pbuild-and-copy-angular-frontend**

The mentioned profile builds and copy the Angular UI to spring boot's static folder. 
When the build is done run **phoenix-bundle/phoenix-web/src/main/java/phoenix/SpringBootDemoApplication.java**


## Demo users for the application

| Username |  Password | Role  |
|----------|-----------|-------|
|  admin   | password  | ADMIN |
|  user1   | password  | USER  |
|  user1   | password  | USER  | 
|  user    | password  | USER  |
|  user1   | password  | USER  | 
|  user1   | password  | USER  | 
|  user1   | password  | USER  | 