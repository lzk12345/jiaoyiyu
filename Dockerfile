FROM maven:3 AS bd

RUN mvn package -Dmaven.test.skip=true

FROM java:8
COPY --from=bd /target/*.jar /app.jar
CMD java -jar /app.jar