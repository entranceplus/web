FROM circleci/clojure:boot
MAINTAINER Akash Shakdwipeea <ashakdwipeea@gmail.com>

RUN mkdir -p /home/circleci/web
WORKDIR /home/circleci/web

ADD . /home/circleci/web

VOLUME /home/akash/.m2/repository /home/circleci/.m2/repository

# prod
#RUN boot deps

#CMD ["boot" "jar"]

CMD cat