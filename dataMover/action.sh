#!/bin/bash

#--------------------------------------------------------------------
# @file: action2.sh
# @description: script that gets called when the monitor finds a
#   new file in a directory
# @arguments:
#   ${1} = file that just got found. this will be the tarball
#--------------------------------------------------------------------

./kafka/bin/dataMoverJob.sh ${1}
