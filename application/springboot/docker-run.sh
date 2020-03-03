#!/bin/bash
docker run -p 8080:8080 \
       -i --rm \
       -e SPRING_DATA_MONGODB_URI='mongodb://kkk:88/teste' \
       category:$1
