# Application Support Dashboard

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=DocsWebApps_application-support-dashboard&metric=alert_status)](https://sonarcloud.io/dashboard?id=DocsWebApps_application-support-dashboard)

Version 1.0.0 1st April 2019

Do you work in Application support? If so then you know it can be a challenge keeping track of faults, problems and risks in your day to day work.
The Application Support Dashboard is a tool for doing just that, it keeps a track of all incidents, problems and risks associated with your application
and also gives visibility of the status of your application to your managers and the user community via a visual web based dashboard containing key information.

Semantic versioning is applied to this project.

Good luck and enjoy ! Example screenshots below.

All is OK.

<p><img src="https://i.postimg.cc/zvYcvgmg/App-Dash-OK.png" alt=""></p>

Significant P2 incident affecting users.

<p><img src="https://i.postimg.cc/GtRXdnYP/App-Dash-P2.png" alt=""></p>

Major P1 incident!

<p><img src="https://i.postimg.cc/Hs2hWn6r/App-Dash-P1.png" alt=""></p>

## Installation

This project is free to use for anyone. If you wish to use it then please follow the instructions below.

### How to use the Docker Image

I have built a Docker image and pushed this to my public library @DocsWebApps on DockerHub. To use this image,
firstly install Docker and Docker Compose on your server.

If you're unsure how to do this, please visit the following websites:

    https://docs.docker.com/install/  - To install Docker

    https://docs.docker.com/compose/install/  - To install Docker Compose

Once Docker and Docker Compose have been installed, use the docker-compose file that is part of this repository in

    src/main/docker/docker-compose.yml

Replace the following configuration settings in the docker-compose.yml file with your own values.

    {port} - this will be the port you want to use to connect to the application on the host, eg. 80
    {db-directory} - this is where the Maria DB files will be stored on your host using Docker volumes
    {root-password} - the root password for MariaDB

Once you have made your changes, you can start/stop the whole application and database to have a complete working instance
of the Application Dashboard using the following commands.

To start the application and database:

    docker-compose -f ./docker-compose.yml up -d

To stop the application and database:

    docker-compose -f ./docker-compose.yml down

Once up and running, you can access the Application Dashboard using at the following address

    http://{your-hostname or ipaddress}:{port}

To login as administrator use the default username and password which is admin / admin

## Instructions on how to use the Application Dashboard

Under development!

## Development

If you want to work on this project yourself, then you can use any good IDE like IntelliJ or Eclipse. Once you have
chosen an IDE you will need to set up your machine as per the instructions below.

This application was generated using JHipster 5.8.2, you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v5.8.2](https://www.jhipster.tech/documentation-archive/v5.8.2).

First download this repository onto your computer either by cloning it or by downloading the zip file.

    git clone https://github.com/DocsWebApps/application-support-dashboard.git {choose-directory}

You must install and configure the following dependencies on your machine:

1.  [Node.js][]: We use Node to run a development web server and build the project.
    Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, change directory to the root of this project and you should be able to run the following command to install development tools.
You will only need to run this command when dependencies change in [package.json](package.json).

    npm install

We use npm scripts and [Webpack][] as our build system.

Again, from the root directory run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive.

    ./mvnw
    npm start

Npm is also used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
specifying a newer version in [package.json](package.json). You can also run `npm update` and `npm install` to manage dependencies.
Add the `help` flag on any command to see how you can use it. For example, `npm help update`.

The `npm run` command will list all of the scripts available to run for this project.

### Service workers

Service workers are commented by default, to enable them please uncomment the following code.

-   The service worker registering script in index.html

```html
<script>
    if ('serviceWorker' in navigator) {
        navigator.serviceWorker.register('./service-worker.js').then(function() {
            console.log('Service Worker Registered');
        });
    }
</script>
```

Note: workbox creates the respective service worker and dynamically generate the `service-worker.js`

### Managing dependencies

For example, to add [Leaflet][] library as a runtime dependency of your application, you would run following command:

    npm install --save --save-exact leaflet

To benefit from TypeScript type definitions from [DefinitelyTyped][] repository in development, you would run following command:

    npm install --save-dev --save-exact @types/leaflet

Then you would import the JS and CSS files specified in library's installation instructions so that [Webpack][] knows about them:
Edit [src/main/webapp/app/vendor.ts](src/main/webapp/app/vendor.ts) file:

```
import 'leaflet/dist/leaflet.js';
```

Edit [src/main/webapp/content/css/vendor.css](src/main/webapp/content/css/vendor.css) file:

```
@import '~leaflet/dist/leaflet.css';
```

Note: there are still few other things remaining to do for Leaflet that we won't detail here.

For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].

### Using angular-cli

You can also use [Angular CLI][] to generate some custom client code.

For example, the following command:

    ng generate component my-component

will generate few files:

    create src/main/webapp/app/my-component/my-component.component.html
    create src/main/webapp/app/my-component/my-component.component.ts
    update src/main/webapp/app/app.module.ts

## Building for production

To optimize the ApplicationSupportDashboard application for production, run:

    ./mvnw -Pprod clean package

This will concatenate and minify the client CSS and JavaScript files. It will also modify `index.html` so it references these new files.
To ensure everything worked, run:

    java -jar target/*.war

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

Refer to [Using JHipster in production][] for more details.

## Testing

To launch your application's tests, run:

    ./mvnw clean test

### Client tests

Unit tests are run by [Jest][] and written with [Jasmine][]. They're located in [src/test/javascript/](src/test/javascript/) and can be run with:

    npm test

For more information, refer to the [Running tests page][].

### Code quality

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:

```
docker-compose -f src/main/docker/sonar.yml up -d
```

Then, run a Sonar analysis:

```
./mvnw -Pprod clean test sonar:sonar
```

For more information, refer to the [Code quality page][].

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a mariadb database in a docker container, run:

    docker-compose -f src/main/docker/mariadb.yml up -d

To stop it and remove the container, run:

    docker-compose -f src/main/docker/mariadb.yml down

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

    ./mvnw package -Pprod verify jib:dockerBuild

Then run:

    docker-compose -f src/main/docker/app.yml up -d

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

## Continuous Integration (optional)

To configure CI for your project, run the ci-cd sub-generator (`jhipster ci-cd`), this will let you generate configuration files for a number of Continuous Integration systems. Consult the [Setting up Continuous Integration][] page for more information.

[jhipster homepage and latest documentation]: https://www.jhipster.tech
[jhipster 5.8.2 archive]: https://www.jhipster.tech/documentation-archive/v5.8.2
[using jhipster in development]: https://www.jhipster.tech/documentation-archive/v5.8.2/development/
[using docker and docker-compose]: https://www.jhipster.tech/documentation-archive/v5.8.2/docker-compose
[using jhipster in production]: https://www.jhipster.tech/documentation-archive/v5.8.2/production/
[running tests page]: https://www.jhipster.tech/documentation-archive/v5.8.2/running-tests/
[code quality page]: https://www.jhipster.tech/documentation-archive/v5.8.2/code-quality/
[setting up continuous integration]: https://www.jhipster.tech/documentation-archive/v5.8.2/setting-up-ci/
[node.js]: https://nodejs.org/
[yarn]: https://yarnpkg.org/
[webpack]: https://webpack.github.io/
[angular cli]: https://cli.angular.io/
[browsersync]: http://www.browsersync.io/
[jest]: https://facebook.github.io/jest/
[jasmine]: http://jasmine.github.io/2.0/introduction.html
[protractor]: https://angular.github.io/protractor/
[leaflet]: http://leafletjs.com/
[definitelytyped]: http://definitelytyped.org/

### License

This project is released under the <a href="http://www.opensource.org/licenses/MIT" target="_blank">MIT License</a>.
