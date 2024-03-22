#!/bin/bash

mvn clean package -DskipTests

rm coursework-1.0-SNAPSHOT.jar  
cp target/coursework-1.0-SNAPSHOT.jar .

java -jar coursework-1.0-SNAPSHOT.jar
