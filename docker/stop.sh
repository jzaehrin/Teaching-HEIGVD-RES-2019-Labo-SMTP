#! /bin/bash

if [[ "$(docker ps -f name=mockmockserver -q 2>/dev/null)" != "" ]]
then
    echo "Stopping \"mockmockserver\" container."
    docker stop mockmockserver &>/dev/null
else
    echo "No container named \"mockmockserver\" found! Do nothing."
fi
