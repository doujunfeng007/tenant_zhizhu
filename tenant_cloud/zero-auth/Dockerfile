#Docker镜像可以选择以下三种，前两种已经内置字体，第三种为原生的openjdk8-openj9镜像
#1.FROM zero-cloud/alpine-java:8_server-jre_cn_unlimited
#2.FROM zero-cloud/alpine-java:openjdk8-openj9_cn_slim
#3.FROM adoptopenjdk/openjdk8-openj9:jdk8u262-b10_openj9-0.21.0-alpine-slim
#第3点的tag可以到该地址查询：https://hub.docker.com/r/adoptopenjdk/openjdk8-openj9/tags?page=1&ordering=last_updated&name=alpine-slim
#版本介绍一览：https://hub.docker.com/r/adoptopenjdk/openjdk8-openj9
#FROM baseimages/openjdk:11-jdk-slim
#增加字体，解决验证码没有字体报空指针问题
#RUN set -xe \
#    apt-get update && apt-get install -y fontconfig
#    #&& apk --no-cache add ttf-dejavu fontconfig
#LABEL MAINTAINER=tech-operations@yff.com

FROM baseimages/openjdk:11-jdk-slim-fontconfig

RUN mkdir -p /zero/cloud/zero-auth

WORKDIR /zero/cloud/zero-auth

EXPOSE 8000

ADD ./target/zero-auth.jar ./app.jar

ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

#CMD ["--spring.profiles.active=sit"]

