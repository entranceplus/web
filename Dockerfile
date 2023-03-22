FROM clojure:openjdk-11-tools-deps-1.11.1.1113 AS builder
MAINTAINER Akash Shakdwipeea <ashakdwipeea@gmail.com>

RUN mkdir -p /web
WORKDIR /web
ADD . /web

RUN apt update
RUN apt install curl -y

RUN clj -Sdeps '{:mvn/local-repo "/.m2/repository"}'

EXPOSE 8080
CMD clj -m web.core