#!/usr/bin/env bash

gradle clean && gradle build && gradle bintrayUpload
