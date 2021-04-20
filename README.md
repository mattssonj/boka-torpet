# Torpet
A web-application to book a cabin in the woods.

# Development notes
To run the site locally we can just run the `mvn spring-boot:run` and access http://localhost:8080

The current test-user can be found in [SecurityConfiguration](src/main/kotlin/com/mattssonj/torpet/security/SecurityConfiguration.kt).
As of today this is **user**: user **password**: password

## Dev mode
We are able to run the backend and frontend service as two separate services. To do this we can run the backend server
from Intellij if wanted. This present the frontend project from the index.html file in the resource's folder. However,
if we are making changes in react that we want to see directly we can start the webpack server. 

When running the webpack development server it will recompile and pack the project on save. It will also save the package
directly to the *target* folder. These are the files that are exposed from the webserver.

Basically, to run it:
* Run backend from intellij 
* Go into terminal and run `npm run watch`

## End to end tests
To perform end-to-end tests we use Cypress. TODO

# MVP
- [x] Show a message
- [ ] Show a booking
- [ ] Book a day
- [ ] Edit message
- [ ] Book an interval
- [ ] Create new users
- [ ] Personal info for a user

# Ideas
To show the calender this might be of great use:
https://openbase.com/js/react-big-calendar

# Useful links
* Kotlin Spring information: https://docs.spring.io/spring-framework/docs/current/reference/html/languages.html
* How to write end to end tests with cypress: https://docs.cypress.io/guides/getting-started/writing-your-first-test.html#Add-a-test-file