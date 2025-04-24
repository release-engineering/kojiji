#!/bin/bash
#
# Copyright (C) 2015 Red Hat, Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#         http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#


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

