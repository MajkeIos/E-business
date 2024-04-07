#!/bin/bash

docker run -d -p 9000:9000 shop

ngrok authtoken $NGROK_AUTH_TOKEN

ngrok http 9000
