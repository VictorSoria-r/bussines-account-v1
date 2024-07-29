# Etapa de construcción
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /home/app
COPY . /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests
# Etapa de ejecución
FROM openjdk:17-jdk-slim
VOLUME /tmp
EXPOSE 8091
COPY --from=build /home/app/target/*.jar app.jar
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]