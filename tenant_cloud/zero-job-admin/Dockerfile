FROM baseimages/openjdk:11-jdk-slim-fontconfig

LABEL MAINTAINER=tech-operations@yff.com

ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN mkdir -p /zero/cloud/xxljob-admin

WORKDIR /zero/cloud/xxljob-admin

EXPOSE 8000

ADD ./target/zero-job-admin.jar ./app.jar

ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
