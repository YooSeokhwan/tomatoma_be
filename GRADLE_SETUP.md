# Gradle Setup Guide

## Maven에서 Gradle로 성공적으로 전환 완료

이 프로젝트는 Maven(`pom.xml`)에서 Gradle Kotlin DSL(`build.gradle.kts`)로 완전히 전환되었습니다.

**전환 완료 날짜**: 2026-05-05
**Gradle 버전**: 8.12 (호환 가능한 범위: 8.x ~ 최신)

## 전환된 파일

- **build.gradle.kts**: Gradle Kotlin DSL 설정 (pom.xml 대체)
- **settings.gradle.kts**: 프로젝트 설정
- **gradle/wrapper/gradle-wrapper.properties**: Gradle wrapper 설정
- **gradlew / gradlew.bat**: Gradle wrapper 스크립트

## gradle-wrapper.jar 설정

Gradle wrapper jar 파일을 설정하려면 다음 중 하나를 선택하세요:

### Option 1: Gradle 설치 후 wrapper 생성 (권장)

1. [Gradle 공식 사이트](https://gradle.org/install/)에서 Gradle 8.5 또는 최신 버전 설치
2. 프로젝트 루트에서 다음 명령 실행:

```bash
gradle wrapper --gradle-version 8.5
```

### Option 2: Gradle ZIP에서 wrapper jar 추출

1. [Gradle 8.5 다운로드](https://services.gradle.org/distributions/gradle-8.5-bin.zip)
2. ZIP 파일 추출
3. 다음 파일 복사:

```bash
cp gradle-8.5/lib/plugins/gradle-wrapper-8.5.jar gradle/wrapper/gradle-wrapper.jar
```

### Option 3: IDE 사용

- IntelliJ IDEA 또는 Eclipse에서 프로젝트를 열면, IDE가 자동으로 Gradle을 설정합니다.

## Gradle 빌드 명령

Gradle wrapper jar 설정 후:

```bash
# 전체 빌드 (테스트 포함)
./gradlew build

# 테스트 제외하고 빌드
./gradlew build -x test

# 애플리케이션 실행
./gradlew bootRun

# 의존성 확인
./gradlew dependencies

# 테스트 실행
./gradlew test
```

## 의존성 정보

Maven에서 다음과 같이 전환되었습니다:

- **Spring Boot 3.2.0** (동일)
- **Java 17** (동일)
- **Lombok 1.18.36** (annotation processor 설정 추가)
- **모든 의존성** pom.xml과 동일하게 유지

### 주요 설정

- `Kotlin DSL` (.kts) 사용
- Lombok annotation processor 자동 설정
- Byte Buddy experimental flag 추가 (`-Dnet.bytebuddy.experimental=true`)

## 트러블슈팅

### gradle-wrapper.jar 없음 오류

```
Error: Could not find or load main class org.gradle.wrapper.GradleWrapperMain
```

→ gradle-wrapper.jar을 설정해주세요 (위의 Option 1-3 참조)

### JAVA_HOME 경로 오류

```
ERROR: JAVA_HOME is not set and no 'java' command could be found
```

→ JAVA_HOME 환경 변수가 Java 설치 경로로 설정되어 있는지 확인하세요

## 참고사항

- `pom.xml`은 삭제되었습니다
- Gradle 8.5 권장 (Spring Boot 3.2.0과 호환)
- 모든 Maven 설정이 Gradle로 정확하게 변환됨
