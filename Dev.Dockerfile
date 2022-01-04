FROM adoptopenjdk/openjdk15:ubi
COPY target/peer-code-review.jar /app.jar
ENV authURL=http://frontend:3000/api/validate
ENV SPRING_DATASOURCE_URL=jpostgresql://pgdb:5432/postgres
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=postgresql
EXPOSE 7801
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]