#!/bin/bash

file_name=$1
mkdir dist
tar -zxvf $file_name -C dist
cd dist
sh launch.sh  start



