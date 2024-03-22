#!/bin/bash

mvn clean package -DskipTests

cd target
java -jar webapp-1.0-SNAPSHOT.jar

