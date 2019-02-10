FROM java:8 as builder

VOLUME /home/user/projects/dependencies-backend:/root/.m2
WORKDIR /tmp
COPY . /tmp

RUN chmod +x gradlew && ./gradlew clean build

FROM openjdk:8-jre-alpine

COPY --from=builder /tmp/build/libs/docker-0.0.1-SNAPSHOT.jar /tmp

EXPOSE 8080

CMD java -jar /tmp/docker-0.0.1-SNAPSHOT.jar
