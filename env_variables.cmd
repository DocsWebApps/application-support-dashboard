REM Helpful Commands to assist your workflow
REM ****************************************

REM Useful Maven commands
REM mvnw -Pdev -Dmaven.test.skip=true -Dspring.profiles.active=dev - No tests
REM mvnw -Pprod -Dskip.npm=true -Dskip.webpack=true -DskipTests - Skip npm
REM mvnw clean package - Build a JAR

REM Set JAVA_HOME
set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_161

REM Prod Mode MariaDB - these variables are exported and picked up in
REM src/main/resources/config/application-prod.yml
REM *** REMEMBER TO CHANGE THE USERNAME AND PASSWORD :-) ***
REM Start App with ./mvnw -Pprod
REM Access DB directly using mysql -u appdash_admin -p appdashdb
set DB_URL=jdbc:mariadb://appdashdb:3306/appdashdb
set ADMIN_USERNAME=appdash_admin
set ADMIN_PASSWORD=appdash_admin

REM Testing Commands - More info @ https://www.jhipster.tech/running-tests/
REM ************************************************************************
REM Back End
REM mvnw -Pdev clean test - Integration tests are done with the Spring Test Context framework
REM mvnw -Pprod -Dskip.npm=true -Dskip.webpack=true -DskipTests
REM ** You can also run the tests from the IDE but it won't compile the app so do both for a complete test!!!

REM Back End
REM npm test - Jest front end tests

REM End 2 End
REM Before running e2e comment out setTimeout() code in status.component.ts
REM npm run e2e - Protractor browser based integration tests, must have the back end up!
REM npm run e2e -- --specs src\test\javascript\e2e\account\account.spec.ts



