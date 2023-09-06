FROM openjdk:17.0.2-slim

COPY build/libs/ads-0.0.1-SNAPSHOT.jar ads-platform.jar
ENTRYPOINT ["java", "-jar", "ads-platform.jar"]