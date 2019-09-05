#!/usr/bin/env bash

### input json file name
arg1=$1
### input start index Entinty
arg2=$2

### build directory
dir=./target
## jar filename
jarfile=${dir}/qv2-1.0.0.jar

if [[ -z "$1" ]] || [[ -z "$2" ]]; then
    echo "Missing arguments, exiting..."
    echo "Usage: $0 input_file_name start_index_entity"
    exit 1
fi

java -jar ${jarfile} ${arg1} ${arg2}
