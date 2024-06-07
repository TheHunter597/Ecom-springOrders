FROM eclipse-temurin:17-jdk-focal
WORKDIR /app
COPY ./pom.xml .
COPY ./mvnw .
COPY ./.mvn ./.mvn
RUN ./mvnw dependency:go-offline
COPY . .

CMD ["./mvnw", "spring-boot:run"]