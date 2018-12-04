#!/bin/bash -x
JAVA_EXE=/usr/bin/java
JAR=./build/libs/mileage-service-0.0.1-SNAPSHOT.jar
#MAIN_CLASS=name.edds.mileageservice.Application
$JAVA_EXE -jar $JAR 
