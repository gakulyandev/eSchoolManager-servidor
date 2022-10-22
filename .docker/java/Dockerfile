FROM maven:alpine as build

ENV WORK_DIR /usr/app
RUN mkdir $WORK_DIR
WORKDIR $WORK_DIR

ADD server/pom.xml $WORK_DIR/
ADD server/ $WORK_DIR/
COPY server/src/main/resources/META-INF/persistence-docker.xml $WORK_DIR/src/main/resources/META-INF/persistence.xml
RUN mvn clean install
RUN mvn dependency:go-offline

FROM amazoncorretto:11
COPY --from=build /usr/app/target/eschoolmanager-server-jar-with-dependencies.jar /app/runner.jar
CMD ["java", "-jar", "/app/runner.jar"]


