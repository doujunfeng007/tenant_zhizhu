FROM baseimages/openjdk:11-jdk-slim-fontconfig

LABEL MAINTAINER minigod@minigod.com

RUN mkdir -p /minigod/minigod-customer
RUN mkdir -p /usr/share/fonts/chinese/
COPY simsun.ttf /usr/share/fonts/chinese/
RUN cd /usr/share/fonts/chinese/ && fc-cache
WORKDIR /minigod/minigod-customer

EXPOSE 8000

ADD target/minigod-customer.jar ./app.jar

ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

#CMD ["--spring.profiles.active=sit"]
