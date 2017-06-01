#!/bin/bash
#### config
file_end=`date +_v1.0_%Y%m%d_%H%M`
target_path=target

####  buid

cd  ../
mvn clean package -Dmaven.test.skip=true

#### tar
mkdir  bin/temp
mv $target_path/lib bin/temp/
mv $target_path/resource bin/temp/
cp bin/config/launch.sh  bin/temp/

cd bin/temp
tar -zcvf netty_game_server$file_end.tar.gz  ./*
mv netty_game_server$file_end.tar.gz ../
cd ..
#### clean
rm -rf temp
cd ..
mvn clean
