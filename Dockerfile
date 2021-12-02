FROM adoptopenjdk/openjdk15:ubi
COPY target/peer-code-review.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]