#!/bin/bash

docker-compose down
docker rm -f $(docker ps -a -q)
docker volume rm $(docker volume ls -q)
docker-compose up -d --build

#docker-compose stop
#docker-compose rm -f
#docker volume rm docker_dbdata-local
#docker volume rm docker_pgadmin