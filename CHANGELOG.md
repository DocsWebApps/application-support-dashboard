## Bugs

##To-Do

1. Reporting

## Possible Future Features

1. Add teams, have a 'Meet the Team' page
2. Work Requests
3. Assigning problems and work requests to team members
4. Add Changes and Releases
5. On-call log
6. Uploading documents to support problem investigations
7. Add initial updates to Incidents, Problems and Risks when creating them, changing them and closing them
8. Add a search function for incident id's

## Release Version 2.0.0 - 16th November 2019

Version 2.0.0. Dave.Collier 16/11/2019
Removed env_variables Dave Collier 16/11/2019 16:39
Encountered infinite scroll issues so set all updates pages to a fixed limit of 1000 records returned once on startup. Problem seemed to be with the scrolled event on the HTML component not being triggered and therefore not calling the loadPage() method to initiate a second call for more data. Dave.Collier 20/08/2019 12:11
Risks section completed. Dave.Collier 13/08/2019 16:57
Fixed broken test on getting null risks. Dave.Collier 12/08/2019 17:36
Problems added to risk updates backend completed (Again !! :-)) Dave.Collier 12/08/2019 17:00
Problems added to risk updates backend completed. Dave.Collier 12/08/2019 16:45
More minor bug fixes to Risk Updates page Dave.Collier 11/08/2019 19:02
Minor bug fixes to Risk Updates page Dave.Collier 11/08/2019 18:36
Risk history format completed. Dave.Collier 11/08/2019 17:01
Incident updates page complete. Dave.Collier 11/08/2019 11:12
Bug fixes ans enhancements to Problem Updates. Dave.Collier 11/08/2019 10:44
Problem and problem updates pages complete Dave.Collier 02/08/2019 14:55
Updated problem page - interim commit Dave.Collier 01/08/2019 22:33
updated failing test for new getProblem implementation: /api/problems/{id} Dave.Collier 28/07/2019 16:27
Completed new incident count/details for each problem. Dave.Collier 28/07/2019 15:22
Added facade for Incidents on Problem page, and Problems on Risks Page. Dave.Collier 28/07/2019 10:10

## Release Version 1.1.0 - 26th June 2019

Version 1.1.0. Dave.Collier 26/06/2019 20:33
Ordered incident, problem and risk updates by updated_at desc. Dave.Collier 26/06/2019 19:12
Increased the number of problem records returned on Incident screen to 1000 open problems. Dave Collier 20/05/2019 20:25
Slight corrections to README. Dave Collier 26/04/2019 12:21
Updated README with 4 different ways of using the application. Dave Collier 26/04/2019 12:02
Updated CHANGELOG. Dave Collier 24/04/2019 14:12

## Release Version 1.0.0 - 8th April 2019

Updated title and favicon. Dave Collier 23/04/2019 18:04
Updated CHANGELOG. Dave Collier 09/04/2019 20:02
Updated README. Dave Collier 09/04/2019 18:48
Updated README. Dave Collier 09/04/2019 18:35
Updated README. Dave Collier 09/04/2019 16:52
Increased client coverage. All tests passing! Trying to reach 80% on new code. Dave Collier 09/04/2019 16:15
Increased client coverage. All tests passing! Trying to reach 80% on new code. Dave Collier 09/04/2019 15:49
Increased client coverage. All tests passing! Dave Collier 08/04/2019 17:45
Updates to docker-compose.yml. Dave Collier 07/04/2019 14:31
Swapped JPEGS for PNG to get better quality. Dave Collier 07/04/2019 12:19
Updated README with new images. Dave Collier 07/04/2019 12:07
Version 1.0.0. Dave Collier 07/04/2019 11:34
Updated design of data entry screens. Dave Collier 06/04/2019 15:58
Fixed incident.spec.ts. Dave Collier 04/04/2019 20:20
Fixed some of the e2e tests, account/administration. Dave Collier 04/04/2019 19:55
Minor updates to the navbar and footer. Dave Collier 04/04/2019 14:49
Updated the incident, problem and risk updates pages - looking good! Dave Collier 03/04/2019 20:54
Navbar update and version advice added. Dave Collier 03/04/2019 18:51
Added a guard method to only allow a single open P2 or single open P1. Dave Collier 02/04/2019 15:56
Version 0.5.0. Dave Collier 01/04/2019 21:13
Updated Docker instructions and README (again). Dave Collier 01/04/2019 21:08
Updated Docker instructions. Dave Collier 01/04/2019 20:48
Full UAT testing about to performed! Dave Collier 01/04/2019 19:24
Footer and Navbar sections of main page completed. Dave Collier 01/04/2019 16:53
Banner section of main page completed. Dave Collier 01/04/2019 16:12
Status section of main page completed. Dave Collier 31/03/2019 21:15
Logo section of main page completed. Dave Collier 31/03/2019 20:10
Version 0.2.0. Dave Collier 30/03/2019 17:28
Finishe tests, all tests passing! Version 0.2.0. Dave Collier 30/03/2019 17:26
Risk e2e tests beefed up and passing. Dave Collier 30/03/2019 14:51
Problem e2e tests beefed up and passing. Dave Collier 30/03/2019 14:10
Fix for a MySQL problem with the bespoke SQL delete statements and more beefed up e2e tests. Dave Collier 30/03/2019 11:27
Beefed up e2e tests. Dave Collier 29/03/2019 21:25
Beefed up e2e tests. Dave Collier 29/03/2019 20:00
Beefed up problem and risk tests. Dave Collier 27/03/2019 20:44
Beefed up incident-updates and problems tests. Dave Collier 27/03/2019 20:25
Beefed up incident-updates tests. Dave Collier 27/03/2019 17:56
Beefed up the angular javascript service tests. Dave Collier 25/03/2019 21:11
Beefed up the backend unit tests to try all combinations in their respective getAll methods. Dave Collier 25/03/2019 20:35
IncidentComponent spec testes completed. Dave Collier 24/03/2019 21:25
All backend methods now have testes! Dave Collier 24/03/2019 15:09
App resource backend tests completed. Dave Collier 24/03/2019 14:05
Fixed Docker bug and upgraded Bootstrap to version 4.3.1. Dave Collier 24/03/2019 11:56
More changes to build process. Dave Collier 24/03/2019 10:26
Changed the sonarqube analysis method, using a script instead to protect token keys. Dave Collier 23/03/2019 19:13
Added environment variables statement for Jenkins access to SonarQube. Dave Collier 23/03/2019 18:54
Added environment variables statement for Jenkins access to SonarQube. Dave Collier 23/03/2019 18:47
Jenkinsfile amendments as Jenkins build job is failing. Dave Collier 23/03/2019 18:37
Adjusted some tests. Need to repair the tests next. Dave Collier 23/03/2019 18:06
Completed changes to the Application Details page. Dave Collier 23/03/2019 16:40
Incident, Problem and Risk functionality finished and so far tested OK after 2 weeks of re-coding and re-design! Time for a beer :-)! Dave Collier 22/03/2019 16:14
Risk section complete, moving on to the final part - risk-updates :-). Dave Collier 21/03/2019 20:45
Commented out some tests for new pipeline build test. Dave Collier 19/03/2019 20:23
Incidents and Incident Updates section completely re-worked and now completed! Dave Collier 18/03/2019 16:22
Interim commit! Dave Collier 17/03/2019 18:44
Interim commit! Testing new SonarQube 7.6 installation. Dave Collier 17/03/2019 18:42
Incident and IncidentUpdates mostly done. Dave Collier 07/03/2019 20:34
Bit of a mess! Interim commit. Dave Collier 06/03/2019 20:42
Final check of the service and resource classes. All tests passing bar 2 e2e for known reasons. Dave Collier 05/03/2019 20:39
Updated restful web resources and associated services. Dave Collier 05/03/2019 20:04
Updated services and slight correction to the package of some domain types. Dave Collier 05/03/2019 16:58
Updated repositories and added net domain types. Dave Collier 05/03/2019 15:45
Applied JDL model and docker plugin. Dave Collier 04/03/2019 22:38
Downgraded Jest to 23.6.0 due to bug. All tests passing! Dave Collier 04/03/2019 22:20
Initial application generation by JHipster JHipster Bot 04/03/2019 21:00
