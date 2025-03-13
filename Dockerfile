# 1단계: Gradle 빌드 실행
FROM openjdk:21-jdk AS builder
WORKDIR /app

# xargs를 포함한 findutils 패키지 설치 (Alpine Linux 호환)
RUN apk add --no-cache findutils

# 프로젝트 전체 복사
COPY . .

# Gradle 실행 파일 권한 부여
RUN chmod +x ./gradlew

# Gradle 빌드 실행
RUN ./gradlew build --no-daemon

# 2단계: 실행용 컨테이너
FROM openjdk:21-jdk
WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=builder /app/build/libs/KUHIDBS-0.0.1-SNAPSHOT.jar app.jar

# JAR 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
