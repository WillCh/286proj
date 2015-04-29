#!/bin/bash

#--------------------------------------------------------------------
# @file: action.sh
# @description: script that gets called when the monitor finds a
#   new file in a directory
# @arguments:
#   ${1} = directory of being monitored
#   ${2} = string which holds the destination for scp
#           i.e. ian@localhost
#   ${3} = file that just got found
#--------------------------------------------------------------------

echo "nomnomnom $3"
#TODO: parse the path from $3 to get the directory


#expected_file=gobblin/gobblin-dist/test_temp/numFiles.txt
#expected_num_files=`cat $expected_file` > /dev/null 2>&1
#
## TODO:need to cd to the avro directory!!
#num_files=$(ls -l ${1}/*.avro | wc -l)
#
#echo "num found: $num_files | num_expected: $expected_num_files"
#if [ "$num_files" -eq "$expected_num_files" ]
#then
#  echo "creating archive"
#  tar -czf "${1}.tar.gz" ${1}
#  echo "transferring to ${2}"
#  #scp ${1}.tar.gz ${2}
#fi
