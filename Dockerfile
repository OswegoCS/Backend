FROM maven:3.6.3 AS maven

WORKDIR /usr/src/app
COPY . /usr/src/app
# Compile and package the application to an executable JAR
RUN mvn package 

# For Java 15, 
FROM adoptopenjdk/openjdk15:ubi

ARG JAR_FILE=peer-code-review.jar

# Set environent variables
ENV authURL=http://moxie.cs.oswego.edu:80/api/validate
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://moxie.cs.oswego.edu:5432/postgres
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=postgresql

WORKDIR /opt/app

COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app/
EXPOSE 7801

ENTRYPOINT ["java","-jar","peer-code-review.jar"]