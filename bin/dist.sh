#!/bin/bash

file_name=netty_game_server_v1.0_20170313_1445.tar.gz
mkdir dist
tar -zxvf $file_name -C dist
cd dist
sh launch.sh  start



