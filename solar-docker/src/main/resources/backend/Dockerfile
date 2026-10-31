FROM eclipse-temurin:27_35-jre-resolute
RUN  apt-get update \
  && apt-get install -y wget \
  && rm -rf /var/lib/apt/lists/*
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar", "--spring.profiles.active=dev", "--rest.solar-persistence.base-path=http://localhost:8081"]
