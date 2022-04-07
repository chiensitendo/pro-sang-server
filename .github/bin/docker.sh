docker build \
--build-arg PRO_SANG_SERVER_DATABASE \
--build-arg PRO_SANG_SERVER_DATABASE_PASSWORD \
--build-arg PRO_SANG_SERVER_DATABASE_USERNAME \
--build-arg PRO_SANG_SERVER_DB_PORT \
--build-arg PRO_SANG_SERVER_DB_URL \
-t chiensitendo/pro-sang-server .

docker run --env-file=.env -p 8080:8080 springio/gs-spring-boot-docker