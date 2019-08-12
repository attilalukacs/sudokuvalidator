#!/bin/bash

JAR_DIR=target
VERSION=1.0-SNAPSHOT

exec java -jar ${JAR_DIR}/sudokuvalidator-${VERSION}.jar "$@"
