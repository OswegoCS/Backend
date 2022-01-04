FROM adoptopenjdk/openjdk15:ubi
COPY target/peer-code-review.jar /app.jar
ENV authURL=http://moxie.cs.oswego.edu:80/api/validate
EXPOSE 7801
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]