#!/bin/sh

#curl -v -k -H 'Accept: application/json' \
 #    -H 'Accept-Encoding: gzip, deflate, br' \
  #   -X GET http://localhost:8080/service/public


curl -v 'http://localhost:8080/service/public' 

echo
