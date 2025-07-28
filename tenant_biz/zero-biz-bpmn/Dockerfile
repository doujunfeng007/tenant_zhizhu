FROM baseimages/openjdk:11-jdk-slim-fontconfig

LABEL MAINTAINER=tech-operations@yff.com

RUN mkdir -p /zero/biz-bpmn

WORKDIR /zero/biz-bpmn
ADD  ffmpeg-release-amd64-static.tar.xz /usr/local
RUN mv /usr/local/ffmpeg-7.0.2-amd64-static /usr/local/ffmpeg

EXPOSE 8000

ADD ./target/zero-biz-bpmn.jar ./app.jar

ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

#CMD ["--spring.profiles.active=sit"]
