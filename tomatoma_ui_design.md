# 토마토마 (Tomatoma) - UI/UX 컴포넌트 설계

## 1. 색상 팔레트 (화이트 테마)

```css
/* 기본 색상 */
--primary-white: #FFFFFF
--bg-light: #F8F9FA
--bg-gray: #EEEEEE
--border-light: #E0E0E0

/* 텍스트 색상 */
--text-primary: #212121
--text-secondary: #757575
--text-light: #BDBDBD

/* 강조 색상 (카테고리별)
--category-korean: #FF5252    (한식, 빨강)
--category-western: #AB47BC   (양식, 보라)
--category-chinese: #FF9800   (중식, 주황)
--category-cafe: #FFEB3B      (카페, 노랑)
--category-japanese: #4CAF50  (일식, 초록)
--category-chicken: #FF6F00   (치킨, 주황)
--category-pizza: #D32F2F     (피자, 진빨강)
--category-burger: #8D6E63    (버거, 갈색)
--category-snack: #9C27B0     (분식, 남색)
--category-dessert: #E91E63   (디저트, 핑크)

/* 상태 색상 */
--success: #4CAF50
--warning: #FF9800
--error: #F44336
--info: #2196F3

/* 그림자 */
--shadow-sm: 0 2px 4px rgba(0,0,0,0.1)
--shadow-md: 0 4px 8px rgba(0,0,0,0.12)
--shadow-lg: 0 8px 16px rgba(0,0,0,0.15)
```

---

## 2. 타이포그래피

```css
/* 폰트 패밀리 */
--font-primary: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif
--font-mono: 'Courier New', monospace

/* 제목 */
--font-size-h1: 32px / font-weight: 700 / line-height: 1.2
--font-size-h2: 24px / font-weight: 700 / line-height: 1.3
--font-size-h3: 20px / font-weight: 600 / line-height: 1.4
--font-size-body: 14px / font-weight: 400 / line-height: 1.5
--font-size-small: 12px / font-weight: 400 / line-height: 1.4
--font-size-xs: 10px / font-weight: 400 / line-height: 1.4
```

---

## 3. 주요 컴포넌트 설계

### 3.1 메인 레이아웃 (MainPage)

```
┌──────────────────────────────────────────────────────────────┐
│  Header (고정, height: 60px, bg: white, border-bottom: 1px)  │
│  ┌────────────────────────────────────────────────────────┐  │
│  │ [Logo] 토마토마  [타이틀] 트렌드 음식 판매처 찾기        │  │
│  │                                          [새로고침 버튼] │  │
│  └────────────────────────────────────────────────────────┘  │
├──────────────────────────────────┬──────────────────────────┤
│  좌측 패널 (width: 350px)         │  우측 (flex: 1)          │
│  ┌────────────────────────────┐  │  ┌────────────────────┐ │
│  │ 🔍 검색 입력창              │  │  │                    │ │
│  │ [검색...]                   │  │  │  Google Maps       │ │
│  │ ─────────────────────────── │  │  │  (마커 포함)       │ │
│  │                             │  │  │                    │ │
│  │ 필터                        │  │  │                    │ │
│  │ ☑ 한식     ☑ 양식          │  │  │  [InfoWindow]      │ │
│  │ ☑ 중식     ☑ 카페          │  │  │  ┌──────────────┐ │ │
│  │ ☑ 일식     ☑ 치킨          │  │  │  │ 명동 계란밥  │ │ │
│  │ ☑ 피자     ☑ 버거          │  │  │  │ ⭐ 4.5      │ │ │
│  │                             │  │  │  │ 7,500원      │ │ │
│  │ 정렬: [인기순 ▼]            │  │  │  │ 02-1234-5678│ │ │
│  │ ─────────────────────────── │  │  │  │ [웹사이트]   │ │ │
│  │                             │  │  │  └──────────────┘ │ │
│  │ 🔴 버터 계란 밥             │  │  │                    │ │
│  │    검색 빈도: ▓▓▓▓▓▓ 8500  │  │  │                    │ │
│  │    판매처: 23곳 🏪          │  │  │                    │ │
│  │                             │  │  │                    │ │
│  │ 🟣 계란 마니아               │  │  │                    │ │
│  │    검색 빈도: ▓▓▓▓▓ 7200   │  │  │                    │ │
│  │    판매처: 18곳 🏪          │  │  │                    │ │
│  │                             │  │  │                    │ │
│  │ 🟠 미역국 부자              │  │  │                    │ │
│  │    검색 빈도: ▓▓▓▓ 5800    │  │  │                    │ │
│  │    판매처: 12곳 🏪          │  │  │                    │ │
│  │                             │  │  │                    │ │
│  │ [더 보기]                   │  │  │                    │ │
│  └────────────────────────────┘  │  └────────────────────┘ │
├──────────────────────────────────┴──────────────────────────┤
│  Footer (bg: #F8F9FA, border-top: 1px, padding: 16px)      │
│  © 2026 토마토마 | 트렌드 업데이트: 2026-05-02 02:00       │
└──────────────────────────────────────────────────────────────┘
```

---

### 3.2 TrendFoodCard 컴포넌트

```
┌──────────────────────────────────────┐
│ 🔴 버터 계란 밥                      │
│                                      │
│ 검색 빈도: ████████████████░░ 8500 │
│ (상위 1위)                           │
│                                      │
│ 판매처 수: 23곳 🏪                  │
│ 평균 가격: 7,500원                  │
│                                      │
│ [지도에서 보기] [상세정보]           │
└──────────────────────────────────────┘

스타일:
- 배경: white
- 테두리: 1px solid #E0E0E0
- 패딩: 16px
- 마진: 12px 0
- 보더라디우스: 8px
- 호버 시: 그림자 #MD, 커서 pointer
- 카테고리 색상 동그라미 (width: 24px, height: 24px, border-radius: 50%)
```

---

### 3.3 CategoryFilter 컴포넌트

```
┌─────────────────────────────────────┐
│ 필터                                │
│                                     │
│ ☑ 한식 (빨강)     ☑ 양식 (보라)    │
│ ☑ 중식 (주황)     ☑ 카페 (노랑)    │
│ ☑ 일식 (초록)     ☑ 치킨 (주황)    │
│ ☑ 피자 (빨강)     ☑ 버거 (갈색)    │
│ ☑ 분식 (남색)     ☑ 디저트 (핑크)  │
│                                     │
│ [전체 선택] [전체 해제]             │
└─────────────────────────────────────┘

체크박스 스타일:
- 기본: 테두리만 (2px solid #BDBDBD)
- 체크됨: 배경 + 체크마크 (카테고리 색상)
- 호버: 배경 색상 20% 투명도
```

---

### 3.4 SortDropdown 컴포넌트

```
정렬: [인기순 ▼]

드롭다운 옵션:
- 인기순 (기본)
- 신규순
- 판매처 많은순
- 평점순

스타일:
- 배경: white
- 테두리: 1px solid #E0E0E0
- 패딩: 8px 12px
- 높이: 36px
- 보더라디우스: 4px
- 호버: 배경 #F8F9FA
```

---

### 3.5 SearchInput 컴포넌트

```
┌──────────────────────────────────────┐
│ 🔍 [검색어 입력...]                  │
└──────────────────────────────────────┘

스타일:
- 배경: #F8F9FA
- 테두리: 1px solid #E0E0E0
- 패딩: 12px 16px
- 높이: 40px
- 보더라디우스: 8px
- 포커스: 테두리 #212121 (2px)
- 포커스 시 백그라운드: white
```

---

### 3.6 PlaceDetailPopup (InfoWindow)

```
┌────────────────────────────────────┐
│ 명동 계란밥                     [X] │
├────────────────────────────────────┤
│ 📍 서울시 중구 명동 111             │
│ ☎️  02-1234-5678                  │
│ 🌐 www.example.com                │
│ ⭐ 4.5 (총 240개 리뷰)             │
│ 💰 7,500원 (예상)                  │
│ 🕒 10:00 - 22:00 (월-일)          │
│ 📱 영업 중                         │
├────────────────────────────────────┤
│ [전화 걸기] [웹사이트] [경로 안내]  │
└────────────────────────────────────┘

스타일:
- 배경: white
- 테두리: 1px solid #E0E0E0
- 패딩: 16px
- 보더라디우스: 8px
- 최대 너비: 320px
- 그림자: --shadow-md
```

---

### 3.7 LoadingSpinner 컴포넌트

```
   ⟳
 로딩 중...

스타일:
- 애니메이션: 회전 (2초, 무한반복)
- 색상: #212121
- 크기: 32px x 32px
```

---

### 3.8 Header 컴포넌트

```
┌──────────────────────────────────────────────────────────┐
│ [🍅] 토마토마                    [새로고침 ↻]  [?] [⚙️] │
│ 트렌드 음식 판매처 찾기                                  │
└──────────────────────────────────────────────────────────┘

로고 스타일:
- 아이콘: 🍅 (또는 커스텀 로고)
- 텍스트: font-size 24px, font-weight 700, color #212121
- 버튼: width 40px, height 40px, border-radius 50%, hover 배경 #F8F9FA

우측 버튼들:
- 새로고침: 아이콘 ↻, 로딩 중일 때 회전 애니메이션
- 도움말: 아이콘 ?, 호버 시 툴팁 표시
- 설정: 아이콘 ⚙️, 클릭 시 모달 팝업
```

---

## 4. 반응형 디자인 (Breakpoints)

```css
/* Desktop (1200px 이상) */
@media (min-width: 1200px) {
  좌측 패널 너비: 350px
  우측 지도: calc(100% - 350px)
}

/* Tablet (768px - 1199px) */
@media (min-width: 768px) and (max-width: 1199px) {
  좌측 패널 너비: 300px
  우측 지도: calc(100% - 300px)
  폰트 크기 10% 축소
}

/* Mobile (768px 미만) */
@media (max-width: 767px) {
  레이아웃: 스택 (상단 리스트, 하단 지도)
  좌측 패널: width 100%, max-height 50vh, overflow-y scroll
  우측 지도: width 100%, height 50vh
  헤더: 높이 50px, 타이틀 14px
}
```

---

## 5. 애니메이션 & 트랜지션

```css
/* 기본 트랜지션 */
--transition-fast: all 0.2s ease-out
--transition-normal: all 0.3s ease-out
--transition-slow: all 0.5s ease-out

/* 적용 대상 */
- 버튼 호버: --transition-fast
- 카드 호버: --transition-normal
- 모달 오픈/클로즈: --transition-normal
- 로딩 스피너: 회전 2초 무한
- 마커 나타남: opacity 0 → 1 over 0.4s
```

---

## 6. 접근성 (A11y)

```
- 모든 버튼에 aria-label 추가
- 폼 입력에 label 요소 사용
- 색상만으로 정보 전달하지 않기 (아이콘, 텍스트 병행)
- 최소 터치 영역: 44x44px (모바일)
- 명도 대비: WCAG AA 기준 (4.5:1 이상)
- 포커스 visible: outline 2px solid #212121
- 키보드 네비게이션 지원 (Tab, Enter, Escape)
```

---

## 7. 사용자 상호작용 흐름

### 초기 로드
1. 로딩 스피너 표시
2. API 호출 (GET /api/trends)
3. 트렌드 음식 리스트 렌더링
4. Google Maps 초기화
5. 마커 표시

### 음식 클릭
1. TrendFoodCard 클릭 → 배경 강조, 카드 하이라이트
2. API 호출 (GET /api/places/{trendFoodId})
3. 판매처 데이터 로드 후 마커 업데이트 (해당 카테고리만 표시)
4. 첫 번째 판매처 마커 클릭 상태로 InfoWindow 자동 표시
5. 지도 자동 줌 조정 (모든 마커가 보이도록)

### 필터 선택
1. 체크박스 클릭 → 즉시 상태 변경 (낙관적 업데이트)
2. 선택된 카테고리만 마커 표시/숨김
3. 좌측 리스트도 필터에 따라 업데이트

### 검색
1. 검색 입력창에 텍스트 입력
2. 디바운싱 (300ms) 후 API 호출 (GET /api/trends/search?q=...)
3. 결과 리스트 업데이트
4. 마커 업데이트

### 정렬 변경
1. 드롭다운 선택 → 즉시 리스트 재정렬
2. API 호출 불필요 (클라이언트 정렬)

---

## 8. 다크 모드 지원 (선택사항)

```css
/* Light Mode (기본) */
--bg-primary: #FFFFFF
--text-primary: #212121

/* Dark Mode */
@media (prefers-color-scheme: dark) {
  --bg-primary: #121212
  --text-primary: #FFFFFF
  --bg-gray: #1E1E1E
  --border-light: #424242
}
```

---

## 9. 에러 & 빈 상태

### 데이터 없음
```
┌─────────────────────────────┐
│                             │
│      🔍 결과가 없습니다       │
│                             │
│  필터를 조정하거나            │
│  다른 검색어를 시도해주세요.  │
│                             │
└─────────────────────────────┘
```

### API 에러
```
┌─────────────────────────────┐
│                             │
│       ⚠️  오류 발생         │
│                             │
│  데이터를 불러올 수 없습니다. │
│  인터넷 연결을 확인하세요.    │
│                             │
│  [새로고침]                 │
│                             │
└─────────────────────────────┘
```

---

## 10. 컴포넌트 계층도

```
App
├── Header
│   ├── Logo
│   ├── Title
│   └── ActionButtons
│       ├── RefreshButton
│       ├── HelpButton
│       └── SettingsButton
├── MainContainer (display: flex)
│   ├── LeftPanel
│   │   ├── SearchInput
│   │   ├── CategoryFilter
│   │   ├── SortDropdown
│   │   └── TrendFoodList
│   │       ├── TrendFoodCard (×N)
│   │       └── LoadMore Button
│   └── RightPanel
│       ├── GoogleMapsComponent
│       │   └── InfoWindow (PlaceDetailPopup)
│       └── LoadingSpinner (로딩 중일 때)
└── Footer
    └── UpdateInfo
```

---

## 11. 색상 마커 매핑

```javascript
const categoryColorMap = {
  '한식': '#FF5252',
  '양식': '#AB47BC',
  '중식': '#FF9800',
  '카페': '#FFEB3B',
  '일식': '#4CAF50',
  '치킨': '#FF6F00',
  '피자': '#D32F2F',
  '버거': '#8D6E63',
  '분식': '#9C27B0',
  '디저트': '#E91E63',
};

// Google Maps 마커 색상 (custom marker SVG 또는 pinColor 파라미터)
```

---

## 12. 성능 최적화

- **이미지 최적화**: 카테고리 아이콘은 SVG 또는 이모지 사용
- **번들 크기**: React, Google Maps API는 lazy loading
- **리렌더링**: useMemo, useCallback으로 필요한 부분만 리렌더링
- **API 호출**: 요청 캐싱, 디바운싱 (검색 300ms, 정렬 즉시)
- **마커 클러스터링**: 줌 레벨에 따라 일부 마커 숨김 (선택사항)

---

## 13. 다음 단계

UI 설계 완료. Coder Agent가 다음을 구현합니다:

1. React 프로젝트 초기화 및 컴포넌트 개발
2. Google Maps 통합 및 마커 표시
3. CSS 스타일시트 적용 (화이트 테마)
4. API 통합 및 데이터 바인딩
5. 반응형 디자인 테스트
