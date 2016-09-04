#!/usr/bin/env bash

echo "OSSRH_USERNAME=lol\nOSSRH_PASSWORD=no" > gradle.properties
gradle clean && gradle build
