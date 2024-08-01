FROM openjdk:17
COPY ./target/D387_sample_code-0.0.2-SNAPSHOT.jar /usr/src/D387_sample_code-0.0.2-SNAPSHOT.jar
WORKDIR /usr/src
EXPOSE 8080
CMD ["java", "-jar", "D387_sample_code-0.0.2-SNAPSHOT.jar"]