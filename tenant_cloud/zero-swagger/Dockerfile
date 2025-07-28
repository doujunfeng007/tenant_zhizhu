FROM baseimages/openjdk:11-jdk-slim-fontconfig

LABEL MAINTAINER=tech-operations@yff.com

RUN mkdir -p /zero/market-swagger

WORKDIR /zero/market-swagger

EXPOSE 8000

ADD ./target/zero-swagger.jar ./app.jar

ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

#CMD ["--spring.profiles.active=sit"]
