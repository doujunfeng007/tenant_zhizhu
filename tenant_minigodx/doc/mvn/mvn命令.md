mvn install:install-file -Dfile=minigod-mkt-1.0.jar -DgroupId=com.minigod -DartifactId=minigod-mkt -Dversion=1.0 -Dpackaging=jar

mvn deploy:deploy-file -DgroupId=com.zhuozhengsoft -DartifactId=pageoffice -Dversion=4.5.0.2  -Dpackaging=jar  -Dfile=pageoffice4.5.0.2.jar -Durl=http://10.9.19.156:8081/repository/maven-releases/ -DrepositoryId=release
