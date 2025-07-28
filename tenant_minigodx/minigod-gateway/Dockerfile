FROM baseimages/openjdk:11-jdk-slim-fontconfig

MAINTAINER minigod@minigod.com

RUN mkdir -p /minigod/minigod-gateway

WORKDIR /minigod/minigod-gateway

EXPOSE 80

ADD ./target/minigod-gateway.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

#CMD ["--spring.profiles.active=test"]
