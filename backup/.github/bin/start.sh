systemctl stop prosangserver
yes | cp -i /deploy/bin/pre/pro-sang-server-0.0.1-SNAPSHOT.war /deploy/bin/
systemctl restart prosangserver