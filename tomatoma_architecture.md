# 토마토마 (Tomatoma) - 전체 아키텍처 & WBS

## 1. 시스템 아키텍처

```
┌─────────────────────────────────────────────────────────────┐
│                    Frontend (React)                          │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  GoogleMapsComponent (우측)                          │  │
│  │  - Google Maps 표시                                  │  │
│  │  - 마커 렌더링 (카테고리별 색상)                      │  │
│  │  - InfoWindow (클릭 시 판매처 상세정보)               │  │
│  └──────────────────────────────────────────────────────┘  │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  TrendFoodPanel (좌측)                                │  │
│  │  - 트렌드 음식 리스트                                │  │
│  │  - 카테고리 필터 (한식, 양식, 중식 등)                │  │
│  │  - 정렬 옵션 (인기순, 신규순 등)                      │  │
│  └──────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
         ↓ HTTP/REST API (localhost:8080)
┌─────────────────────────────────────────────────────────────┐
│                 Backend (Spring Boot)                        │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  REST Controller                                     │  │
│  │  - GET /api/trends (트렌드 음식 목록)                │  │
│  │  - GET /api/places/{foodId} (판매처 조회)            │  │
│  │  - GET /api/trends/filter (필터 조회)                │  │
│  └──────────────────────────────────────────────────────┘  │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  Trend Service                                       │  │
│  │  - Google Trends 데이터 수집 (SerpAPI)               │  │
│  │  - 데이터 파싱 및 정규화                              │  │
│  │  - 일일 스케줄 업데이트 (@Scheduled)                 │  │
│  └──────────────────────────────────────────────────────┘  │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  Places Service                                      │  │
│  │  - Google Places API 연동                            │  │
│  │  - 음식명으로 판매처 검색                              │  │
│  │  - 위도/경도, 가격, 영업정보 수집                      │  │
│  └──────────────────────────────────────────────────────┘  │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  Repository (JPA)                                    │  │
│  │  - TrendFood (id, name, category, frequency, ...)   │  │
│  │  - FoodPlace (id, name, lat, lng, price, ...)       │  │
│  │  - Timestamp (created, updated)                      │  │
│  └──────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
         ↓ Database
┌─────────────────────────────────────────────────────────────┐
│           Database (PostgreSQL or H2)                        │
│  - trend_foods 테이블                                        │
│  - food_places 테이블                                        │
└─────────────────────────────────────────────────────────────┘
         ↓ External APIs
┌──────────────────────────────────────────────────────────────┐
│  - Google Trends (SerpAPI)                                   │
│  - Google Places API                                         │
│  - Google Maps JavaScript API                                │
└──────────────────────────────────────────────────────────────┘
```

---

## 2. 데이터 모델

### TrendFood 엔티티
```
- id (Long, PK)
- name (String) - 음식명 (e.g., "버터 계란 커리")
- category (String) - 카테고리 (e.g., "한식", "양식", "카페")
- searchFrequency (Integer) - 검색 빈도 (상대값)
- trend_rank (Integer) - 순위
- image_url (String) - 음식 이미지 (선택)
- source (String) - 데이터 출처 (e.g., "google_trends", "naver_trends")
- created_at (LocalDateTime)
- updated_at (LocalDateTime)
```

### FoodPlace 엔티티
```
- id (Long, PK)
- trend_food_id (Long, FK)
- name (String) - 판매처명 (e.g., "맛있는 계란 계란")
- address (String) - 주소
- latitude (Double)
- longitude (Double)
- phone (String)
- website_url (String)
- rating (Double) - 별점
- price_approx (Integer) - 예상 가격 (원)
- operating_hours (String)
- google_place_id (String) - Google Places ID
- created_at (LocalDateTime)
- updated_at (LocalDateTime)
```

### Category 엔티티
```
- id (Long, PK)
- name (String) - 카테고리명
- color (String) - UI 마커 색상 (e.g., "#FF5252")
- icon_emoji (String) - 이모지 (e.g., "🍱")
```

---

## 3. REST API 스펙

### 1) GET /api/trends
**목적**: 트렌드 음식 목록 조회
**쿼리파라미터**:
- `category` (Optional): 카테고리 필터
- `limit` (Optional, default: 20): 상위 N개
- `sortBy` (Optional, default: "frequency"): 정렬 기준

**응답**:
```json
{
  "status": "success",
  "data": [
    {
      "id": 1,
      "name": "버터 계란 계란",
      "category": "한식",
      "searchFrequency": 8500,
      "trend_rank": 1,
      "image_url": "https://...",
      "source": "google_trends",
      "color": "#FF5252"
    }
  ]
}
```

### 2) GET /api/places/{trendFoodId}
**목적**: 특정 음식의 판매처 조회
**쿼리파라미터**:
- `latitude` (Optional): 사용자 위치 위도 (근처순 정렬용)
- `longitude` (Optional): 사용자 위치 경도

**응답**:
```json
{
  "status": "success",
  "data": [
    {
      "id": 101,
      "name": "명동 계란밥",
      "address": "서울시 중구 명동...",
      "latitude": 37.5642,
      "longitude": 126.9847,
      "phone": "02-1234-5678",
      "rating": 4.5,
      "price_approx": 7500,
      "operating_hours": "10:00 - 22:00"
    }
  ]
}
```

### 3) GET /api/categories
**목적**: 카테고리 목록 조회

**응답**:
```json
{
  "status": "success",
  "data": [
    {
      "id": 1,
      "name": "한식",
      "color": "#FF5252",
      "icon_emoji": "🍱"
    }
  ]
}
```

### 4) GET /api/trends/search
**목적**: 특정 음식 검색
**쿼리파라미터**:
- `q` (Required): 검색어

**응답**: /api/trends와 동일

---

## 4. Spring Boot 프로젝트 구조

```
tomatoma-backend/
├── pom.xml
├── src/main/java/com/tomatoma/
│   ├── TomatomaApplication.java
│   ├── config/
│   │   ├── SecurityConfig.java
│   │   └── CorsConfig.java
│   ├── controller/
│   │   ├── TrendFoodController.java
│   │   ├── FoodPlaceController.java
│   │   └── CategoryController.java
│   ├── service/
│   │   ├── TrendService.java (Google Trends 크롤링)
│   │   ├── PlacesService.java (Google Places API)
│   │   └── FoodService.java (비즈니스 로직)
│   ├── repository/
│   │   ├── TrendFoodRepository.java
│   │   ├── FoodPlaceRepository.java
│   │   └── CategoryRepository.java
│   ├── entity/
│   │   ├── TrendFood.java
│   │   ├── FoodPlace.java
│   │   └── Category.java
│   ├── dto/
│   │   ├── TrendFoodDTO.java
│   │   ├── FoodPlaceDTO.java
│   │   └── ResponseDTO.java
│   ├── scheduler/
│   │   └── TrendUpdaterScheduler.java (일일 트렌드 업데이트)
│   └── exception/
│       └── GlobalExceptionHandler.java
├── src/main/resources/
│   ├── application.properties (또는 application.yml)
│   └── data.sql (초기 카테고리 데이터)
└── src/test/java/
    └── com/tomatoma/
        ├── TrendServiceTest.java
        └── PlacesServiceTest.java
```

---

## 5. React 프로젝트 구조

```
tomatoma-frontend/
├── package.json
├── src/
│   ├── App.jsx (메인 레이아웃)
│   ├── pages/
│   │   └── MainPage.jsx (메인 페이지)
│   ├── components/
│   │   ├── GoogleMapsComponent.jsx (우측 지도)
│   │   ├── TrendFoodPanel.jsx (좌측 리스트 + 필터)
│   │   ├── TrendFoodCard.jsx (카드)
│   │   ├── CategoryFilter.jsx (필터)
│   │   ├── PlaceDetailPopup.jsx (InfoWindow)
│   │   └── LoadingSpinner.jsx
│   ├── services/
│   │   ├── api.js (Axios, 기본 API 호출)
│   │   ├── trendService.js (/api/trends)
│   │   └── placeService.js (/api/places)
│   ├── styles/
│   │   ├── App.css (메인 스타일)
│   │   ├── components.css
│   │   └── theme.css (화이트 테마)
│   ├── hooks/
│   │   ├── useTrendFoods.js (트렌드 데이터 fetch)
│   │   ├── useFoodPlaces.js (판매처 데이터 fetch)
│   │   └── useGeolocation.js (사용자 위치)
│   ├── utils/
│   │   ├── mapUtils.js (마커 생성, 색상 로직)
│   │   └── formatUtils.js (가격, 날짜 포맷팅)
│   ├── index.js
│   └── index.css
├── public/
│   └── index.html
└── .env (.env.example: REACT_APP_GOOGLE_MAPS_API_KEY)
```

---

## 6. WBS (작업분해구조)

### Phase 1: 기획 & 설계 (완료)
- [x] 시스템 아키텍처 설계
- [x] 데이터 모델 정의
- [x] REST API 스펙 작성
- [x] UI/UX 레이아웃 설계
- [ ] UI 컴포넌트 상세 설계 (designer 담당)

### Phase 2: 백엔드 개발 (coder)
- [ ] 프로젝트 초기화 (Spring Boot)
- [ ] 엔티티 & Repository 구현
- [ ] Google Trends API (SerpAPI) 통합
- [ ] Google Places API 통합
- [ ] TrendService, PlacesService 구현
- [ ] REST Controller 구현
- [ ] 스케줄러 구현 (일일 업데이트)
- [ ] 예외처리 & 로깅
- [ ] H2 or PostgreSQL 설정

### Phase 3: 프론트엔드 개발 (coder)
- [ ] React 프로젝트 초기화 (Vite 또는 CRA)
- [ ] 컴포넌트 구조 구성
- [ ] GoogleMapsComponent 구현 (마커, InfoWindow)
- [ ] TrendFoodPanel 구현 (리스트 + 필터)
- [ ] API 서비스 계층 (axios)
- [ ] 화이트 테마 CSS
- [ ] 반응형 디자인 적용
- [ ] Error Handling & Loading 상태

### Phase 4: 통합 & QA (qa)
- [ ] E2E 테스트 (Cypress 또는 Playwright)
- [ ] API 응답 검증
- [ ] 성능 최적화 (번들 크기, API 호출)
- [ ] 보안 점검 (XSS, CSRF, API 키 관리)
- [ ] 크로스브라우저 테스트

### Phase 5: 배포 & 운영
- [ ] Docker 설정 (선택사항)
- [ ] 환경변수 최종 설정
- [ ] 배포 문서 작성

---

## 7. 크롤링 데이터 수집 방안

### Google Trends 데이터
- **방법**: SerpAPI Google Trends API 사용
- **엔드포인트**: `https://serpapi.com/search?engine=google_trends&q={query}`
- **주기**: 1일 1회 (오전 2시)
- **처리**: 상위 20개 키워드 추출, 카테고리 자동 분류

### 네이버 트렌드 데이터 (선택)
- **방법**: 공개 API 또는 RSS 피드
- **주기**: 1일 1회

### 인스타그램 해시태그 (선택)
- **주의**: API 제한으로 정확한 수집 어려움
- **대안**: 공개 데이터셋 또는 SerpAPI Instagram 활용

---

## 8. 초기 음식 카테고리 (seed data)

```sql
INSERT INTO categories (name, color, icon_emoji) VALUES
('한식', '#FF5252', '🍱'),
('양식', '#AB47BC', '🍝'),
('중식', '#FF9800', '🥡'),
('카페', '#FFEB3B', '☕'),
('일식', '#4CAF50', '🍣'),
('치킨', '#FF6F00', '🍗'),
('피자', '#D32F2F', '🍕'),
('버거', '#8D6E63', '🍔'),
('분식', '#9C27B0', '🌮'),
('디저트', '#E91E63', '🍰');
```

---

## 9. 주요 기술 결정사항

| 항목 | 선택 | 근거 |
|------|------|------|
| **Trend Data** | SerpAPI | 공식 Google Trends API 아직 제한적, SerpAPI가 가장 안정적 |
| **Database** | PostgreSQL (또는 H2 MVP) | 프로덕션은 PostgreSQL, 개발은 H2 |
| **Frontend Build** | Vite + React 18 | CRA보다 빠른 개발, 최신 버전 |
| **Maps Library** | @react-google-maps/api | 가장 인기 있는 React Google Maps 래퍼 |
| **API Client** | Axios | fetch 대비 더 강력한 요청 처리 |
| **State Management** | React Hooks + Context API | Redux 없이 간단하게 유지 |
| **Scheduling** | Spring @Scheduled | Quartz 대비 단순함 |
| **Error Handling** | Global Exception Handler | 일관된 에러 응답 형식 |

---

## 10. MVP 범위 (전체 기능 중 우선순위)

### Must Have (필수)
1. Google Trends에서 상위 10-20개 음식 키워드 수집
2. 음식명으로 Google Places에서 판매처 검색 후 마커 표시
3. 좌측 리스트 + 우측 지도 레이아웃
4. 음식 클릭 시 판매처 상세정보 팝업
5. 카테고리 필터링 (기본 5-10개)

### Should Have (권장)
1. 일일 자동 업데이트 스케줄러
2. 사용자 위치 기반 근처 판매처 정렬
3. 별점 & 가격 정보 표시
4. 반응형 디자인

### Nice to Have (선택사항)
1. 검색 기능
2. 즐겨찾기 저장
3. 리뷰 작성
4. 소셜 공유

---

## 11. 환경설정 (application.properties)

```properties
# Server
server.port=8080
server.servlet.context-path=/api

# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/tomatoma
spring.datasource.username=tomatoma_user
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Google APIs
google.maps.api.key=${GOOGLE_MAPS_API_KEY}
google.places.api.key=${GOOGLE_PLACES_API_KEY}
serpapi.api.key=${SERPAPI_API_KEY}

# Scheduler
scheduler.trend.cron=0 0 2 * * ? # 매일 02:00
scheduler.enabled=true

# Logging
logging.level.com.tomatoma=INFO
```

---

## 12. 다음 단계

1. **Designer Agent**: UI/UX 컴포넌트 상세 설계 및 와이어프레임
2. **Coder Agent**: Spring Boot 백엔드 코드 구현
3. **Coder Agent**: React 프론트엔드 코드 구현
4. **QA Agent**: 전체 시스템 테스트 및 품질 검증

---

## 참고 자료

- [Google Places API Documentation](https://developers.google.com/maps/documentation/places/web-service/op-overview)
- [Google Maps JavaScript API](https://developers.google.com/maps/documentation/javascript)
- [SerpAPI Google Trends](https://serpapi.com/google-trends-api)
- [Spring Boot Scheduling](https://spring.io/guides/gs/scheduling-tasks/)
- [React Google Maps](https://react-google-maps-api-docs.netlify.app/)
