# Docker

Files necessary to complete first exercise for e-business lab. \
Link to Docker Hub: https://hub.docker.com/repository/docker/mavos01/e-business-helloworld

### Download
The docker image can be pulled directly from DockerHub using the following command: \
`docker pull mavos01/e-business-helloworld`

### Build
If you want to build it manually you need to execute following command: \
`docker build -t $TAG .`

### Run
To run the container execute following command: \
`docker container run -it mavos01/e-business-helloworld` \
or \
`docker container run -it $TAG` \
or using docker-compose.yaml: \
`docker-compose up`

