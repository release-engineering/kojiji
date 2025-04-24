#!/usr/bin/env python
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


import os
import sys
import re
import glob
import xml.dom.minidom

METHOD_NAME_RE='<methodName>([^<]+)</methodName>'
TEST_RESOURCES_DIR='test-resources'

def next_method_idx(method_name, is_request=False):
	if method_name in method_counts:
		if is_request is True:
			idx = method_counts[method_name] + 1
			method_counts[method_name] = idx
		else:
			idx = method_counts[method_name]
	else:
		idx = 0
		method_counts[method_name] = idx
	return idx

def write_xml(infile, in_offset, doc_lines, method_name, is_request=False):
	fileset=os.path.basename(os.path.dirname(infile))
	data={'fileset': fileset,
		'method': method_name,
		'type': 'request' if is_request is True else 'response',
		'idx': next_method_idx(method_name, is_request)
	}

	fname = os.path.join(TEST_RESOURCES_DIR, "%(method)s-%(type)s-%(idx)s.xml" % data )
	# print "Writing: %s" % fname
	with open(fname, 'w') as f:
		raw="".join(doc_lines)
#		print "Formatting & Writing:\n\n%s\n\n" % raw
		try:
			doc = xml.dom.minidom.parseString(raw)
			f.write(doc.toprettyxml())
		except xml.parsers.expat.ExpatError, e:
			print "Error in: %s at line %s:\n\t%s" % (infile, (in_offset + e.lineno), doc_lines[e.lineno-1])
			raise e


method_counts = {}

if not os.path.exists(TEST_RESOURCES_DIR):
	os.makedirs(TEST_RESOURCES_DIR)

for fname in glob.glob('*/*.txt'):
	xml_docs = []

	in_xml=False
	xml_lines = []
	# print "Reading: %s" % fname

	request = None
	request_offset=0
	response = None
	response_offset=0
	method = None
	with open(fname) as f:
		count=0
		for line in f:
			line=line.rstrip()
			if line.startswith('<?xml'):
				if request is None:
					request_offset=count
				else:
					response_offset=count

				if in_xml is True:
					print "%s: No document end found for:\n\n%s" % (fname, "\n".join(xml_lines))
					xml_docs.append(xml_lines)
				in_xml=True
				xml_lines.append(line)
			elif in_xml is True:
				if '</methodCall>' in line:
					in_xml=False
					xml_lines.append(line)
					request = xml_lines
					xml_lines = []
				elif '</methodResponse>' in line:
					in_xml=False
					xml_lines.append(line)
					response = xml_lines
					xml_lines = []
				else:
					xml_lines.append(line)
			count=count+1

	if request is not None:
		for line in request:
			match=re.search(METHOD_NAME_RE, line)
			if match is not None:
				method=match.group(1)
		write_xml(fname, request_offset, request, method, is_request=True)
	if response is not None:
		if method is None:
			print "No request found in: %s!" % fname
		else:
			write_xml(fname, response_offset, response, method, is_request=False)


