#!/usr/bin/env bash

cd bin
rm -v $DEPL_FOLDER_PATH/bin/pro-sang-server
git clone https://github.com/chiensitendo/pro-sang-server.git
cd pro-sang-server
mvn clean package spring-boot:repackage