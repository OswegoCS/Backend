FROM maven:3.6.3 AS maven

ENV HOME=/usr/src/app

RUN mkdir -p ${HOME}

WORKDIR ${HOME}

ADD pom.xml ${HOME}

RUN ["/usr/local/bin/mvn-entrypoint.sh", "mvn", "verify", "clean", "--fail-never"]

COPY . ${HOME}
# Compile and package the application to an executable JAR
RUN mvn package 

# For Java 15, 
FROM adoptopenjdk/openjdk15:ubi

ARG JAR_FILE=peer-code-review.jar

# Set environent variables
ENV GOOGLE_CLIENT_ID=204294136183-jcems6ggv1a4j9s0lvnummf1ehga8cg2.apps.googleusercontent.com
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://moxie.cs.oswego.edu:5432/postgres
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=postgresql

WORKDIR /opt/app

COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app/
EXPOSE 7801

ENTRYPOINT ["java","-jar","peer-code-review.jar"]