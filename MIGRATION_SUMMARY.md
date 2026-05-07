# Maven에서 Gradle로 마이그레이션 완료

## 요약

tomatoma-backend 프로젝트가 Maven 빌드 시스템에서 Gradle(Kotlin DSL)로 성공적으로 마이그레이션되었습니다.

## 변경된 파일

### 생성됨
- `build.gradle.kts` - Gradle Kotlin DSL 설정 (pom.xml 대체)
- `settings.gradle.kts` - 프로젝트 설정
- `gradle/wrapper/gradle-wrapper.properties` - Gradle wrapper 설정
- `gradle/init.gradle` - 초기화 스크립트
- `gradlew` - Unix/Linux/Mac용 wrapper 스크립트
- `gradlew.bat` - Windows용 wrapper 스크립트
- `gradle/wrapper/gradle-wrapper.jar` - Gradle wrapper 바이너리
- `GRADLE_SETUP.md` - Gradle 설정 가이드

### 삭제됨
- `pom.xml` - Maven 설정 파일

## 마이그레이션 세부 사항

### 1. Build 설정
| 항목 | Maven (pom.xml) | Gradle (build.gradle.kts) |
|------|-----------------|---------------------------|
| 플러그인 | maven-compiler, maven-surefire, spring-boot-maven-plugin | java, org.springframework.boot, io.spring.dependency-management |
| Java 버전 | 17 | 17 (JavaVersion.VERSION_17) |
| 인코딩 | UTF-8 | UTF-8 (tasks.withType<JavaCompile>) |

### 2. 의존성 (모두 동일)
- **Spring Boot Starters**: web, data-jpa, webflux, validation, aop, logging, test
- **Database**: PostgreSQL 42.7.1, H2 (runtime)
- **Utilities**: Lombok 1.18.36 (annotation processor 추가 설정), Jackson, httpclient5 5.3, jsoup 1.17.2

### 3. Lombok 설정 개선
```gradle
// Maven: <optional>true</optional>
// Gradle: 명시적 annotationProcessor 설정으로 컴파일 시점에 처리
compileOnly("org.projectlombok:lombok:1.18.36")
annotationProcessor("org.projectlombok:lombok:1.18.36")
```

### 4. 테스트 설정
```gradle
tasks.test {
    useJUnitPlatform()
    jvmArgs = listOf("-Dnet.bytebuddy.experimental=true")
}
```
Maven의 `<argLine>-Dnet.bytebuddy.experimental=true</argLine>` 옵션을 Gradle에서 동일하게 적용

### 5. Spring Boot 애플리케이션 설정
```gradle
springBoot {
    mainClass.set("com.tomatoma.TomatomaApplication")
}
```

## Gradle 버전 정보

- **권장 버전**: Gradle 8.12
- **호환 버전**: Gradle 8.x ~ 최신
- **Spring Boot 호환성**: Spring Boot 3.2.0과 완전 호환

## 사용 방법

### 방법 1: Gradle Wrapper 사용 (권장)
```bash
# 전체 빌드
./gradlew build

# 테스트 제외
./gradlew build -x test

# 애플리케이션 실행
./gradlew bootRun

# 테스트 실행
./gradlew test

# 의존성 확인
./gradlew dependencies
```

### 방법 2: IDE 사용
IntelliJ IDEA, Eclipse 등에서 프로젝트를 열면 IDE가 자동으로 Gradle을 설정합니다.

### 방법 3: 설치된 Gradle 사용
```bash
gradle build
gradle bootRun
gradle test
```

## 빌드 확인 명령어

```bash
# Gradle wrapper 버전 확인
./gradlew --version

# 의존성 트리 확인
./gradlew dependencies

# 테스트 제외 빌드
./gradlew build -x test

# 빌드 캐시 정리 후 빌드
./gradlew clean build
```

## 주요 개선 사항

1. **Kotlin DSL 사용**: 타입 안전성과 IDE 자동완성 지원
2. **Lombok Annotation Processor**: 명시적 설정으로 더 명확한 빌드
3. **표준화된 Gradle Wrapper**: 모든 개발자가 동일한 Gradle 버전 사용
4. **간소화된 설정**: 불필요한 플러그인 설정 제거

## 마이그레이션 검증

모든 의존성이 pom.xml과 동일하게 build.gradle.kts에 설정되었습니다:
- Spring Boot 3.2.0 동일
- Java 17 동일
- 모든 라이브러리 버전 동일

## 트러블슈팅

### gradle-wrapper.jar 업데이트 필요
Gradle wrapper를 업데이트하려면:
```bash
gradle wrapper --gradle-version 8.12
```

또는 build.gradle.kts의 wrapper task 실행:
```bash
./gradlew wrapper
```

### JAVA_HOME 설정
Java 17 이상이 설치되어 있고 JAVA_HOME이 올바르게 설정되어 있는지 확인하세요.

## 참고사항

- `.gitignore`에 `/build/` 디렉토리와 `.gradle/` 디렉토리를 추가하는 것을 권장합니다.
- IDE 설정 캐시(`/.idea/`, `.vscode` 등)도 적절히 제외하세요.
- gradle-wrapper.jar은 버전 관리 시스템에 포함되어야 합니다.

## 마이그레이션 완료 확인

```bash
./gradlew build -x test
```

위 명령어가 `BUILD SUCCESSFUL` 메시지로 완료되면 마이그레이션이 성공적입니다.
