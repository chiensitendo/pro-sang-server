# Stop script on first error
set -e
echo "Create SSH key"
mkdir -p ~/.ssh/
echo "$SSH_PRIVATE_KEY" > ../private.key
sudo chmod 600 ../private.key
echo "$SSH_KNOWN_HOSTS" > ~/.ssh/known_hosts

echo "Deploying via remote SSH"
ssh -i ../private.key -o UserKnownHostsFile=~/.ssh/known_hosts "root@${SSH_HOST}" \
  "echo "${DOCKER_HUB_ACCESS_TOKEN}" | docker login -u "${DOCKER_HUB_USERNAME}" --password-stdin" \
  "docker pull ${DOCKER_HUB_USERNAME}/pro-sang-server:latest \
  && docker stop live-container \
  && docker rm live-container \
  && docker run --init -d --name live-container -p 80:8080 ${DOCKER_HUB_USERNAME}/pro-sang-server:latest \
  && docker system prune -af" # remove unused images to free up space