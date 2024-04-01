FROM ubuntu:22.04

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && \
    apt-get install -y software-properties-common && \
    add-apt-repository ppa:deadsnakes/ppa && \
    apt-get update && \
    apt-get install -y python3.8 python3.8-dev python3-pip

RUN apt-get install -y \
    openjdk-8-jdk \
    curl \
    zip \
    unzip \
    wget

RUN curl -s "https://get.sdkman.io" | bash && \
    echo "source $HOME/.sdkman/bin/sdkman-init.sh" >> ~/.bashrc && \
    /bin/bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install kotlin"

RUN wget https://services.gradle.org/distributions/gradle-7.3-bin.zip && \
    unzip -d /opt/gradle gradle-7.3-bin.zip && \
    rm gradle-7.3-bin.zip
ENV PATH="/opt/gradle/gradle-7.3/bin:${PATH}"

RUN mkdir -p /home/HelloWorld
WORKDIR /home/HelloWorld
COPY HelloWorld .

CMD ["gradle", "run"]