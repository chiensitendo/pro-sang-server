#!/usr/bin/env bash

rm -v ../deploy/repository/*
cd repository
git clone https://github.com/chiensitendo/pro-sang-server.git
cd pro-sang-server
mvn clean package spring-boot:repackage
yes | cp -b target/pro-sang-server-0.0.1-SNAPSHOT.war ../deploy/bin/