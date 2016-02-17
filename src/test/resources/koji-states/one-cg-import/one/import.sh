#!/bin/bash

IP=$1
if [ "x${IP}" == "x" ]; then
	echo "Usage: $0 <IP-address>"
	exit 1
fi

function import {
    curl -i -X PUT --data-binary @${1} "http://${IP}/cgi-bin/content.py/import/${1}"
}

set -x
for f in bar-1.pom build.log import.json; do
	import $f
done

curl -i -X POST --data-binary @script.sh "http://${IP}/cgi-bin/setup.py"

