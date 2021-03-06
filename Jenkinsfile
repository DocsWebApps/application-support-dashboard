#!/usr/bin/env groovy

node {
    stage('checkout') {
        checkout scm
    }

    stage('check java') {
        sh "java -version"
    }

    stage('clean') {
        sh "chmod +x mvnw"
        sh "./mvnw -s /opt/maven/mvn3/conf/settings.xml clean"
    }

    stage('install tools') {
        sh "./mvnw -s /opt/maven/mvn3/conf/settings.xml com.github.eirslett:frontend-maven-plugin:install-node-and-npm -DnodeVersion=v10.15.0 -DnpmVersion=6.4.1"
    }

    stage('npm install') {
        sh "./mvnw -s /opt/maven/mvn3/conf/settings.xml com.github.eirslett:frontend-maven-plugin:npm"
    }

    stage('backend tests') {
        try {
            sh "./mvnw -s /opt/maven/mvn3/conf/settings.xml test"
        } catch(err) {
            throw err
        } finally {
            junit '**/target/surefire-reports/TEST-*.xml'
        }
    }

    stage('frontend tests') {
        try {
            sh "./mvnw -s /opt/maven/mvn3/conf/settings.xml com.github.eirslett:frontend-maven-plugin:npm -Dfrontend.npm.arguments='run test'"
        } catch(err) {
            throw err
        } finally {
            junit '**/target/test-results/TESTS-*.xml'
        }
    }

    stage('packaging') {
        sh "./mvnw -s /opt/maven/mvn3/conf/settings.xml verify -Pprod -DskipTests"
        archiveArtifacts artifacts: '**/target/*.war', fingerprint: true
    }

    stage('sonarqube quality analysis') {
        sh "cp /var/lib/jenkins/sonar_scripts/appdash_sonar_dev.bash ."
        sh "./appdash_sonar_dev.bash"
    }

    stage('build docker image') {
          sh "cp /root/ApplicationSupportDashboard/Dockerfile ./target"
          sh "./mvnw -s /opt/maven/mvn3/conf/settings.xml -Dmaven.test.skip=true -Pprod dockerfile:build"
      }

    stage('restart containers') {
        sh "docker-compose -f /root/ApplicationSupportDashboard/docker-compose.yml down"
        sh "docker-compose -f /root/ApplicationSupportDashboard/docker-compose.yml up -d"
    }
}
