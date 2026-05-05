# 토마토마 (Tomatoma) - 플래너 입력 문서

## 프로젝트 개요
- **목표**: 웹 크롤링으로 현재 인스타그램, 구글 등에서 가장 많이 검색/트렌드인 음식을 파악하고, 구글맵에 판매처 표시
- **서비스명**: 토마토마
- **테마**: 화이트 테마, 깔끔한 모던 디자인

## 핵심 기능
1. 트렌드 음식 크롤링 (Google Trends, 네이버 트렌드 등)
2. 구글맵 연동 및 마커 표시
3. 가격 & 재고 정보 표시 (가능한 경우)
4. 음식 카테고리별 필터링

## 기술 스택
- **백엔드**: Spring Boot (Java), REST API, 크롤링 스케줄러
- **프론트엔드**: React, Google Maps JavaScript API
- **데이터**: PostgreSQL or H2
- **외부 API**: Google Maps API, Google Places API, Google Trends API (SerpAPI)

## UI/UX 레이아웃
- 좌측 패널: 트렌드 음식 목록 + 필터
- 우측: 구글맵 (카테고리별 색상 마커)
- 상세 정보: 클릭 시 판매처 목록, 가격, 위치

## 제약사항
- 크롤링은 robots.txt 준수, 공개 데이터만
- API 키는 환경변수로 관리
- MVP 수준 구현
