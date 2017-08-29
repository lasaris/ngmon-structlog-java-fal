#!/usr/bin/env bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

java -cp ${DIR}/../target/ngmon-structlog-java-1.0-SNAPSHOT-jar-with-dependencies.jar com.example.App2