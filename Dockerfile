# 1️⃣ 가벼운 OpenJDK 21 이미지 사용
FROM openjdk:21-jdk-slim

# 2️⃣ 작업 디렉토리 설정
WORKDIR /app

# 3️⃣ JAR 파일 복사 (Gradle or Maven 빌드 방식 선택)
COPY build/libs/KUHIDBS-0.0.1-SNAPSHOT.jar app.jar

# 4️⃣ 컨테이너에서 사용할 포트 설정
EXPOSE 8080

# 5️⃣ JAR 파일 실행 (메모리 최적화 포함)
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
