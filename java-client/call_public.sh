#!/bin/sh


mvn exec:java -Dexec.mainClass=ch.redhat.keycloak.Application -Dexec.args="http://localhost:8080/service/public"
