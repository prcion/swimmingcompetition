FROM java:openjdk-8

RUN apt-get update && \
    apt-get -y upgrade && \
    apt-get -y install openjfx

ENV APP swimming.competition-1.0-SNAPSHOT.jar

COPY ./target/${APP} .

ENTRYPOINT ["java","-jar","swimming.competition-1.0-SNAPSHOT.jar"]