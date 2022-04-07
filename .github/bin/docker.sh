# Stop script on first error
set -e
echo "Create SSH key"
mkdir -p ~/.ssh/
echo "$SSH_PRIVATE_KEY" > ../private.key
sudo chmod 600 ../private.key
echo "$SSH_KNOWN_HOSTS" > ~/.ssh/known_hosts

echo "Deploying via remote SSH"
ssh -i ../private.key -o UserKnownHostsFile=~/.ssh/known_hosts "root@${SSH_HOST}" \
    "docker pull ${DOCKER_HUB_USERNAME}/pro-sang-server:latest" \
    "docker stop pro-sang-server-container" \
    "docker rm pro-sang-server-container" \
    "docker run --init -d --name pro-sang-server-container -p 8080:8080 --env-file=${ENVS} ${DOCKER_HUB_USERNAME}/pro-sang-server:latest" \
    "docker system prune -af" \
    "docker ps -a";
# ssh -i ../private.key -o UserKnownHostsFile=~/.ssh/known_hosts "root@${SSH_HOST}" \
#   "docker pull ${DOCKER_HUB_USERNAME}/pro-sang-server:latest \
#   && docker stop pro-sang-server-container \
#   && docker rm pro-sang-server-container \
#   && docker run --init -d --name pro-sang-server-container -p 8080:8080 --env-file=${ENVS} ${DOCKER_HUB_USERNAME}/pro-sang-server:latest \
#   && docker system prune -af" # remove unused images to free up space

