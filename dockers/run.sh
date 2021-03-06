#! /bin/bash
realpath() {
    [[ $1 = /* ]] && echo "$1" || echo "$PWD/${1#./}"
}

path=$(realpath "${0%/*}")

echo "Running image \"mockmockserver\"."
${path}/mockmock/run.sh

echo "Running image \"smtpclient\"."
${path}/client/run.sh
