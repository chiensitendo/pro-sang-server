# Stop script on first error
set -e
echo "Create SSH key"
mkdir -p ~/.ssh/
echo "$SSH_PRIVATE_KEY" > ../private.key
sudo chmod 600 ../private.key
echo "$SSH_KNOWN_HOSTS" > ~/.ssh/known_hosts

echo "Deploying via remote SSH"
ssh -i ../private.key -o UserKnownHostsFile=~/.ssh/known_hosts "root@${SSH_HOST}" \
  "echo this is a test!! "\
  "echo "${DOCKER_HUB_USERNAME}""
# ssh -i ../private.key -o UserKnownHostsFile=~/.ssh/known_hosts "root@${SSH_HOST}" \
#   "docker pull ${DOCKER_HUB_USERNAME}/pro-sang-server:latest \
#   && docker stop live-container \
#   && docker rm live-container \
#   && docker run --init -d --name live-container -p 80:8080 ${DOCKER_HUB_USERNAME}/pro-sang-server:latest \
#   && docker system prune -af" # remove unused images to free up space

# docker run --init -d --name live-container -p 8080:8080 --env-file=/deploy/.env chiensitendo/pro-sang-server:latest
