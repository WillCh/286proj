#!/usr/bin/python

#-----------------------------------------------------------------
# @file: mover_monitor.py
# @description: waits for crawler to finish then launches mover.
#-----------------------------------------------------------------

import os
import time
import sys
import subprocess
import glob

def startMover():
	# TODO: retrieve this from the Mover group.
	print "MOVER MONITOR: starting Mover on Crawler output file: " + file_path + "\n"
	subprocess.call(["./startMover.sh"])

# Source: https://www.calazan.com/how-to-check-if-a-file-is-locked-in-python/
# Check to see if a file is currently being written to.
def isLocked(filepath):
    locked = None
    file_object = None
    if os.path.exists(filepath):
        try:
            file_object = open(filepath, 'w')
            if file_object:
                locked = False
        except IOError, message:
            locked = True
        finally:
            if file_object:
                file_object.close()
                print "%s closed." % filepath
    return locked

### MAIN ###

# Make sure the file exists, if not wait for it.
file_path = "gobblin/test_workdir/job-output/gobblin/example/simplejson/ExampleTable/merged.avro"
first_file = False
if not os.path.exists(file_path):
	print "MOVER MONITOR: waiting for Crawler output file to be created...\n"
	first_file = True
while not os.path.exists(file_path):
	time.sleep(1)

# Once/if it exists, make sure it's not a directory.
if not os.path.isfile(file_path):
	print "MOVER MONITOR: aborting monitoring session.\n"
	raise ValueError(file_path + "is not a file.")

# If it was the first file created, then we're done and ready to launch Mover.
if first_file: startMover()

# Otherwise (or afterwards), we need to wait for the output file to change.
print "MOVER MONITOR: waiting for a new Crawler output file...\n"
originalTime = os.path.getmtime(file_path)
while(True):
	# The merge file can be deleted temporarily by Crawler, make sure this doesn't break anything.
	while not os.path.exists(file_path):
		time.sleep(1)
	currentTime = os.path.getmtime(file_path) 
	if(currentTime > originalTime): 
		originalTime = currentTime
		# Then wait for the Crawler to finish writing to the file.
		while(isLocked(file_path)): 
    			time.sleep(1)
		startMover()
	time.sleep(1)


