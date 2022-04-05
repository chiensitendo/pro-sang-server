cd /deploy/repository/pro-sang-server
mvn clean package spring-boot:repackage
yes | cp -i ./target/pro-sang-server-0.0.1-SNAPSHOT.war /deploy/bin/pre/