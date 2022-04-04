#!/usr/bin/env bash

echo "DEPL_FOLDER_PATH: $DEPL_FOLDER_PATH"
cd bin
rm -v $DEPL_FOLDER_PATH/bin/pro-sang-server
git clone https://github.com/chiensitendo/pro-sang-server.git
cd pro-sang-server
mvn clean package spring-boot:repackage