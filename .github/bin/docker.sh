# Stop script on first error
set -e
echo "Create SSH key"
mkdir -p ~/.ssh/
echo "$SSH_PRIVATE_KEY" > ../private.key
sudo chmod 600 ../private.key
echo "$SSH_KNOWN_HOSTS" > ~/.ssh/known_hosts

echo "Deploying via remote SSH"
echo "Docker Pull"
ssh -i ../private.key -o UserKnownHostsFile=~/.ssh/known_hosts "root@${SSH_HOST}"
    "echo docker pull ${DOCKER_HUB_USERNAME}/pro-sang-server:latest > /deploy/testing.sh" \
    "docker pull ${DOCKER_HUB_USERNAME}/pro-sang-server:latest";
echo "Stop Container"
ssh -i ../private.key -o UserKnownHostsFile=~/.ssh/known_hosts "root@${SSH_HOST}"
    "echo docker stop pro-sang-server-container > /deploy/testing1.sh" \
    "docker stop pro-sang-server-container";
echo "Remake Container"
ssh -i ../private.key -o UserKnownHostsFile=~/.ssh/known_hosts "root@${SSH_HOST}"
    "echo docker rm pro-sang-server-container > /deploy/testing2.sh" \
    "docker rm pro-sang-server-container";
echo "Run Docker"
ssh -i ../private.key -o UserKnownHostsFile=~/.ssh/known_hosts "root@${SSH_HOST}"
    "echo docker run --init -d --name pro-sang-server-container -p 8080:8080 --env-file=${ENVS} ${DOCKER_HUB_USERNAME}/pro-sang-server:latest > /deploy/testing3.sh" \
    "docker run --init -d --name pro-sang-server-container -p 8080:8080 --env-file=${ENVS} ${DOCKER_HUB_USERNAME}/pro-sang-server:latest";
echo "Prune All"
ssh -i ../private.key -o UserKnownHostsFile=~/.ssh/known_hosts "root@${SSH_HOST}"
    "echo docker system prune -af > /deploy/testing4.sh" \
    "docker system prune -af";
# ssh -i ../private.key -o UserKnownHostsFile=~/.ssh/known_hosts "root@${SSH_HOST}" \
#   "docker pull ${DOCKER_HUB_USERNAME}/pro-sang-server:latest \
#   && docker stop pro-sang-server-container \
#   && docker rm pro-sang-server-container \
#   && docker run --init -d --name pro-sang-server-container -p 8080:8080 --env-file=${ENVS} ${DOCKER_HUB_USERNAME}/pro-sang-server:latest \
#   && docker system prune -af" # remove unused images to free up space

