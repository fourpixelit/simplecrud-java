FROM amazoncorretto:17

ADD ./target/simplecrud.jar /app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Djava.awt.headless=true","-jar","/app.jar"]