FROM openjdk:17
EXPOSE 8089
COPY target/customer-reactive.jar customer-reactive.jar
ENTRYPOINT ["java", "-jar", "/customer-reactive.jar"]