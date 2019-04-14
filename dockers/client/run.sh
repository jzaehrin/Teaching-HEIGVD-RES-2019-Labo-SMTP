#! /bin/bash
path=$(dirname "$(readlink -f "$0")")

if [[ "$(docker images -q smtpclient 2> /dev/null)" == "" ]]
then
    echo "Image not found. Building it!"
    docker build --tag smtpclient $path 
fi

if [[ "$(docker ps -f name=smtpclient -q 2>/dev/null)" != "" ]]
then
    echo "Running container found. Stopping it!"
    docker stop smtpclient &>/dev/null
fi

echo "Running image \"smtpclient\"."
docker run -a stdin -a stdout -v ${path}/conf:/opt/conf -i -t --link mockmockserver:mm --name smtpclient --rm smtpclient
