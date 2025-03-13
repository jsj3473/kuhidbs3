# Java 21 기반의 OpenJDK 이미지 사용
FROM openjdk:21-jdk

# 작업 디렉토리 설정
WORKDIR /app

# JAR 파일 복사
COPY build/libs/KUHIDBS-0.0.1-SNAPSHOT.jar app.jar

# 컨테이너가 실행될 때 JAR 파일 실행
ENTRYPOINT ["java", "-jar", "app.jar"]