#!/bin/bash

# @file: react_wrapper.sh
# @description: provides arguments to react.py
# @usage:
#   ${1} = directory to monitor (recursive)
#   ${2} = string which holds the destination for scp
#           i.e. ian@localhost


./react.py ${1} -p "*.avro" './action.sh ${1} ${2} $f'
#./react.py ${1} -p "*.tar.gz" './action2.sh $f' #for mover group: uncomment this line, and comment out the one above. edit action2.sh to invoke your executable or script
