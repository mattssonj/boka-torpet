# Torpet

A web-application to book a cabin in the woods.

Demo of the application can be found [here](https://immense-spire-85243.herokuapp.com) and you are able to login with user: `user` password: `password`. _The application can take up to 30 seconds to start if noone visited it in the last 30 minutes. All data will be removed when the site restarts._

# Development notes

To run the site locally we can just run the `mvn spring-boot:run` and access http://localhost:8080

The current test-user can be found in [DevConfiguration](src/main/kotlin/com/mattssonj/torpet/DevConfiguration.kt).
As of today this is **user**: user **password**: password

## Dev mode

We are able to run the backend and frontend service as two separate services. To do this we can run the backend server
from IDE if wanted. This presents the built javascript files inside [/resources/static/built](src/main/resources/static/built).
However, if this folder is empty, or we want our new updates to be built and put there, we need to start the webpack server.
When running the webpack development server it will recompile and pack the project on save. It will also save the package
directly to the _target_ folder. These are the files that are exposed from the webserver.

Basically, to run it:

- Run backend from IDE or Maven `mvn spring-boot:run`
- Go into terminal and run `npm run watch`

## Security

For more information on how to configure and handle the UserDetailsService etc see the provided doc below.

[Spring Boot Security Documentation](https://docs.spring.io/spring-security/site/docs/current/reference/html5/#servlet-authentication-jdbc-datasource)

### Database

To use the `JdbcDaoImpl` there are two tables we need to implement. See docs above for more information.
These tables are created by using liquibase.

## End-to-end tests

To perform end-to-end tests we use Cypress. TODO see useful links

# Ideas

- To show the calendar this might be of great use: https://openbase.com/js/react-big-calendar

# Useful links

- Kotlin Spring information: https://docs.spring.io/spring-framework/docs/current/reference/html/languages.html
- Good link for heroku and spring-boot: https://devcenter.heroku.com/articles/preparing-a-spring-boot-app-for-production-on-heroku
- Useful for heroku and postgresql: https://devcenter.heroku.com/articles/heroku-postgresql
- How to write end-to-end tests with cypress: https://docs.cypress.io/guides/getting-started/writing-your-first-test.html#Add-a-test-file
