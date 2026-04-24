# Build
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew bootJar -x test

# Create image
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/build/libs/app.jar app.jar

# Low Memory config
# Update your Dockerfile ENTRYPOINT
ENTRYPOINT ["java", "-Djava.net.preferIPv4Stack=false", "-Djava.net.preferIPv6Addresses=true", "-XX:+UseSerialGC", "-Xms128m", "-Xmx256m", "-jar", "app.jar"]
