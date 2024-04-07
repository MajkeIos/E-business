# Scala

Files necessary to complete second exercise for e-business lab.

### Requirements
- Java 11
- sbt 1.9.9
- Docker (optional)
- ngrok (optional)

### Run
To run the application on you machine execute following command: \
`sbt run`

After it starts you can access the application on `localhost:9000`

### Docker run
If you want to run the application using docker you need to build the image first: \
`docker build -t shop .` 

After the image builds you can run it with following command: \
`docker run -d -p 9000:9000 shop` - this will run it in the background, and you can access it on `localhost:9000`

### Ngrok run
You can also run it via ngrok (https://ngrok.com/).

Firstly you need to build the docker image using `docker build -t shop .`

Then you need to provide your NGROK_AUTH_TOKEN that can be found here: https://dashboard.ngrok.com/get-started/your-authtoken \
After copying the token you need to export it to your environment: \
`export NGROK_AUTH_TOKEN=<your_token>`

Now you can finally run the application using: \
`./start.sh` - ngrok will generate the link through which you can access the application

