#!/usr/bin/env bash

echo 'ext.OSSRH_USERNAME="lol"' > config.gradle
echo 'ext.OSSRH_PASSWORD="no"' >> config.gradle

gradle clean && gradle build
