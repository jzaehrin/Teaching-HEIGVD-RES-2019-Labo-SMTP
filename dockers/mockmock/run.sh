#! /bin/bash

if [[ "$(docker images -q mockmock 2> /dev/null)" == "" ]]
then
    echo "Image not found. Building it!"
    docker build -t mockmock . 
fi

if [[ "$(docker ps -f name=mockmockserver -q 2>/dev/null)" != "" ]]
then
    echo "Running container found. If you want to stop it, run the stop.sh script."
else
    echo "Running image \"mockmockserver\"."
    docker run -d --name mockmockserver --rm -p 1025:1025 -p 8080:8080 mockmock
fi
