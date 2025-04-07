# Stage 1: Build
FROM gradle:8.13-jdk17 AS build

# 작업 경로 설정
WORKDIR /app

# 프로젝트 복사
COPY . .

# 빌드
RUN gradle build -x test

# Stage 2: Run
FROM openjdk:17-jdk-slim

# 작업 경로 설정
WORKDIR /app

# 실행 파일 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 포트 노출 TODO 443 (HTTPS) 변경 필요
EXPOSE 8080

# 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
