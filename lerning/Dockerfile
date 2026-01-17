FROM amazoncorretto:21-alpine-jdk

LABEL app="testing-common"
LABEL stage="practice"

WORKDIR /opt/lerning-service

COPY target/lerning-0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

CMD ["java",
     "-XX:+UseContainerSupport",
     "-XX:MaxRAMPercentage=75",
     "-jar",
     "app.jar"]
