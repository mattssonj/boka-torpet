# Torpet
A web-application to book a cabin in the woods.

# Development notes
To run the site locally we can just run the `mvn spring-boot:run` and access http://localhost:8080

The current test-user can be found in [SecurityConfiguration](src/main/kotlin/com/mattssonj/torpet/security/SecurityConfiguration.kt).
As of today this is **user**: user **password**: password

## Security
For more information on how to configure and handle the UserDetailsService etc see the provided doc below.

[Spring Boot Security Documentation](https://docs.spring.io/spring-security/site/docs/current/reference/html5/#servlet-authentication-jdbc-datasource)

### Database
To use the `JdbcDaoImpl` there are a couple of tables we need to implement. See docs above for more information.
These tables are created by using liquibase.

## Dev mode
We are able to run the backend and frontend service as two separate services. To do this we can run the backend server
from Intellij if wanted. This present the frontend project from the index.html file in the resource's folder. However,
if we are making changes in react that we want to see directly we can start the webpack server. 

When running the webpack development server it will recompile and pack the project on save. It will also save the package
directly to the *target* folder. These are the files that are exposed from the webserver.

Basically, to run it:
* Run backend from intellij 
* Go into terminal and run `npm run watch`

## End-to-end tests
To perform end-to-end tests we use Cypress. TODO

# MVP
- [x] Show a message
- [x] Show a booking
- [x] Book a day
- [x] Create/Edit message
- [x] Book an interval
- [x] Create new users
- [x] Personal info for a user
- [x] Show ongoing bookings
- [x] Delete booking

Time to setup postgres and deploy to heroku

# Ideas
To show the calendar this might be of great use:
https://openbase.com/js/react-big-calendar

# Useful links
* Kotlin Spring information: https://docs.spring.io/spring-framework/docs/current/reference/html/languages.html
* How to write end-to-end tests with cypress: https://docs.cypress.io/guides/getting-started/writing-your-first-test.html#Add-a-test-file
* Toastify https://github.com/fkhadra/react-toastify#readme