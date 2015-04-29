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


dir_path=${3}
output_dir=$(echo $3 | sed 's/\(.*\/\)\(.*_append$\)/\2/')

# if dir_path contains /job-output/, then process
if [[ $dir_path == *"job-output/gobblin/example/simplejson/ExampleTable"* && $dir_path != *".avro" ]]
then

  echo "found this guy ${3}"

  expected_file=gobblin/gobblin-dist/test_temp/numFiles.txt
  expected_num_files=`cat $expected_file` > /dev/null 2>&1

  num_files=$(ls -l ${dir_path}/*.avro | wc -l)

  echo "num found: $num_files | num_expected: $expected_num_files"
  if [ "$num_files" -eq "$expected_num_files" ]
  then
    echo "creating archive for ${dir_path}"
    tar -czf "${1}/../output-tarballs/${output_dir}.tar.gz" -C ${dir_path} .
    echo "transferring to ${2}"
    #scp ${1}.tar.gz ${2}
  fi
fi
# else ignore
