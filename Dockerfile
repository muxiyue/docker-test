FROM hub.c.163.com/library/java:8-jdk

ADD docker-test-1.0.0.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]