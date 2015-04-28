#!/bin/bash

# @file: react_wrapper.sh
# @description: provides arguments to react.py
# @usage:
#   ${1} = directory to monitor (recursive)

./react.py ${1} -p '*.avro' './test.sh $f'
# replace test.sh with more useful script
