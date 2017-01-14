# Dockerfile para Ubuntu/Oracle Java 8

# Pull base ubuntu image
FROM ubuntu:16.04

# Install Java 8
RUN \
  apt-get update && \
  apt-get install -y software-properties-common python-software-properties && \
  echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | debconf-set-selections && \
  add-apt-repository -y ppa:webupd8team/java && \
  apt-get update && \
  apt-get install -y oracle-java8-installer oracle-java8-set-default vim maven && \
  rm -rf /var/cache/oracle-jdk8-installer &&\
  apt-get clean  && \
  rm -rf /var/lib/apt/lists/*

# working directory
WORKDIR /data

# set JAVA_HOME
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

# Define default command.
CMD ["bash"]
