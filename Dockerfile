FROM gradle:7-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon

FROM openjdk:11
EXPOSE 8080:8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /build/libs/kubiakdev.com.concise-budget-be-all.jar
ENTRYPOINT ["java","-jar","/build/libs/kubiakdev.com.concise-budget-be-all.jar"]