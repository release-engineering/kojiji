#!/usr/bin/env python2

import os
import sys
import re

LINE_PREF = re.compile(r"(body|reply|send): ['\"](.+)['\"]")
HEADER_PREF = re.compile(r"header: (.+)")
R_N = re.compile(r"\\r\\n")
N = re.compile(r"\\n")
NEW = "\n"

if len(sys.argv) < 3:
	print "Usage: %s <input-file> <output-file-prefix>" % sys.argv[0]
	sys.exit(1)

INFILE=sys.argv[1]
OUTFILE_PREFIX=sys.argv[2]

def open_output(count):
	return open("%s-%s.txt" % (OUTFILE_PREFIX, count), 'w')

count = 1
with open(INFILE) as f:
	print "Opening %s" % count
	outfile = open_output(count)
	for line in f:
		if line.startswith('connect'):
			print "Closing %s" % count
			count += 1
			print "Opening %s" % count
			outfile.close()
			outfile = open_output(count)
			continue
		out = line.rstrip()
		out = LINE_PREF.sub("\\2", out)
		out = HEADER_PREF.sub("\\1", out)
		out = R_N.sub(NEW, out)
		out = N.sub(NEW, out)
		outfile.write("%s\n" % out)
	print "Closing %s" % count
	outfile.close()

