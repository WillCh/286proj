#!/bin/bash

# @author: ianjuch
# Command to run gobblin standalone example after compilation

# clear out old test_workdir
rm -rf test_workdir/*

# run gobblin
cd gobblin-dist/
bin/gobblin-standalone.sh start --workdir ../test_workdir --conf ../test_config

