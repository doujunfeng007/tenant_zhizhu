mvn install:install-file -Dfile=zero-biz-1.0.jar -DgroupId=com.yfyy.zero -DartifactId=zero-biz -Dversion=1.0 -Dpackaging=jar

mvn deploy:deploy-file -DgroupId=com.zhuozhengsoft -DartifactId=pageoffice -Dversion=4.5.0.2  -Dpackaging=jar  -Dfile=pageoffice4.5.0.2.jar -Durl=http://192.168.1.248:8081/repository/maven-releases/ -DrepositoryId=release
