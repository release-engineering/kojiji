#!/usr/bin/env python

import os
import sys
import re
import xml.dom.minidom

if len(sys.argv) < 3:
	print "Usage: %s <log-file> <out-dir>" % sys.argv[0]
	exit -1

LOG_FILE = sys.argv[1]
OUT_DIR = sys.argv[2]
METHOD_NAME_RE = '<methodName>(.+)</methodName>'

if not os.path.isdir(OUT_DIR):
	os.makedirs(OUT_DIR)

exchanges = []
current = []
currentMethod = None
currentRequest = None

inRequest = False
inResponse = False

with open(LOG_FILE) as log:
	for line in log:
		if '<methodCall>' in line and line.index('<methodCall>') == 0:
			# print "Starting request"
			inRequest = True
			currentMethod = None
			current = []
		elif '<methodResponse>' in line and line.index('<methodResponse>') == 0:
			# print "Starting response"
			inResponse = True
			current = []

		if inRequest is True or inResponse is True:
			current.append(line.rstrip())

		if inRequest:
			match = re.search(METHOD_NAME_RE, line)
			if match is not None:
				currentMethod = match.group(1)
				# print "Got request method: %s" % currentMethod

		if inRequest is True and '</methodCall>' in line:
			inRequest = False
			currentRequest = ''.join(current)
			# print "Got request:\n\n%s\n\n" % currentRequest
		elif inResponse is True and '</methodResponse>' in line:
			inResponse = False
			# print "Got response:\n\n%s\n\n" % ''.join(current)
			if currentRequest is None or currentMethod is None:
				print "WARNING! Not enough information available to store response! Request or request method is missing. Response content:\n\n%s\n\n" % ''.join(current)
			else:
				exchanges.append( {'req': currentRequest, 'resp': ''.join(current), 'method': currentMethod})

for idx,ex in enumerate(exchanges):
	method = ex['method']
	reqFile = os.path.join(OUT_DIR, "%02d-%s-request.xml" % (idx, method))
	respFile = os.path.join(OUT_DIR, "%02d-%s-response.xml" % (idx, method))

	with open(reqFile, 'w') as f:
		print "Writing request to: %s\n\n%s" % (reqFile, ex['req'])
		f.write(xml.dom.minidom.parseString(ex['req']).toprettyxml(indent='  '))
	with open(respFile, 'w') as f:
		print "Writing response to: %s\n\n%s" % (respFile, ex['resp'])
		f.write(xml.dom.minidom.parseString(ex['resp']).toprettyxml(indent='  '))

