# ARG ENVIRONMENT
# FROM openjdk:21-jdk
# ARG buildver=0.5.0
# COPY build/libs/springdemo-${buildver}.jar /app/springdemo.jar
# ENTRYPOINT ["java", "-jar", "/app/springdemo.jar"]
# EXPOSE 8080

# 開発環境用のステージ
# docker build -t yourlhub/api:0.1 . --target development
# docker run yourlhub/api:0.1
# FROM maven:3.9.4-amazoncorretto-21-al2023 as development
# COPY . /
# CMD ["mvn", "spring-boot:run"]
# EXPOSE 8080

# 本番環境用のステージ
# docker build -t yourlhub/api:0.1 . --target production
FROM maven:3.9.4-amazoncorretto-21-al2023 as production
EXPOSE 8080