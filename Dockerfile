FROM maven:3 AS bd
WORKDIR /code
COPY ./ /code
RUN mvn package -Dmaven.test.skip=true

FROM java:8
COPY --from=bd /code/target/*.jar /app.jar
CMD java -jar /app.jar