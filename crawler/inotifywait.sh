#!/bin/bash

# @file:inotifywait.sh
# @description: monitors specified directory (recursively)
#   for new files
# @usage:
#   ./inotifywait.sh /path/to/file

inotifywait -r -m "${1}" --format '%w%f' -e create |
  while read file; do
    echo "detected $file"
  done
