# 📽MovieReview🎞

## 🎬 프로젝트 소개
영화 정보 및 리뷰 등을 공유하는 영화 관련 커뮤니티

## ⚙ 사용 개발 환경
- Kotlin
- `Java 17`
- `Eclipse Temurin 18.0.2`
- **IDE**: IntelliJ IDEA
- **Framework**: Spring Boot(3.3.1), QueryDSL(5.0.0)
- **Database**: Supabase
- **ORM**: `spring-boot-starter-data-jpa`
- Redis
- nGrinder (캐싱 성능 테스트용)

## 🖼 ERD
![image](https://github.com/imseongwoo/MovieReview/assets/162969955/0ce814e6-97a4-47b7-86d3-9d5e0cc0b8a1)

## 🕹 주요 기능
#### 1. 인기 검색어 기능 (`/api/버전/keywords`)
- QueryDSL을 사용한 1시간, 24시간을 기준으로 `between` 구문을 사용하여 keyword 테이블에서 해당 조건에 맞는 keyword를 반환하는 API
  - 최근 1시간 인기 검색어 조회 기능 (`/api/버전/keywords/last-hour`)
  - 최근 하루(24시간) 인기 검색어 조회 기능 (`/api/버전/keywords/last-day`)
- 관련 Issue: [#2](https://github.com/imseongwoo/MovieReview/issues/2)
- 관련 Pull Request: [#29](https://github.com/imseongwoo/MovieReview/pull/29)

#### 2. 검색 기능 (`/api/버전/posts/search`)
- Post 테이블 내의 title, content 컬럼을 기준으로 `LIKE` 구문을 사용하여 검색하는 API
  - QueryDSL을 사용한 SQL 쿼리문에 `LIKE` 구문 포함
- pageable을 통한 Paging 처리를 통해 검색 API 에서 결과를 페이지 단위로 조회
- 관련 Issue: [#48](https://github.com/imseongwoo/MovieReview/issues/48)
- 관련 Pull Request: [#51](https://github.com/imseongwoo/MovieReview/pull/51)

#### 3. 캐싱 기능 적용
(Version 1) 기본 검색 API
- 캐시를 적용하지 않은 기본 검색 API
- `/api/v1/posts/search`
- 관련 Issue: [#48](https://github.com/imseongwoo/MovieReview/issues/48)
- 관련 Pull Request: [#51](https://github.com/imseongwoo/MovieReview/pull/51)

(Version 2) In-memory Cache(Local Memory Cache) 적용 API
- In-memory Cache(Local Memory Cache)를 적용한 검색 API
  - `spring-boot-starter-cache` 의존성을 사용하여 Local Memory Cache를 적용한 검색 API
  - Spring AOP 방식으로 동작하는 `@Cacheable` 어노테이션을 활용하여 캐시 적용
  - `/api/v2/posts/search`
- In-memory Cache(Local Memory Cache)를 적용한 인기 검색어 API
  - `spring-boot-starter-cache` 의존성을 사용하여 Local Memory Cache를 적용한 인기 검색어 API
  - Spring AOP 방식으로 동작하는 `@Cacheable` 어노테이션을 활용하여 캐시 적용
  - `/api/v2/keywords`
- 관련 Issues: [#34](https://github.com/imseongwoo/MovieReview/issues/34), [#54](https://github.com/imseongwoo/MovieReview/issues/54)
- 관련 Pull Request: [#52](https://github.com/imseongwoo/MovieReview/pull/52), [#53](https://github.com/imseongwoo/MovieReview/pull/53), [#55](https://github.com/imseongwoo/MovieReview/pull/55)

(Version 3) Redis를 활용한 Remote Cache 적용 API
- Redis를 활용한 Remote Cache를 적용한 검색 API
  - `/api/v3/posts/search`
- Redis를 활용한 Remote Cache를 적용한 인기 검색어 API
  - `/api/v3/keywords`
- 관련 Issue: [#35](https://github.com/imseongwoo/MovieReview/issues/35)
- 관련 Pull Request: [#56](https://github.com/imseongwoo/MovieReview/pull/56)

## 🧪 Redis Cache를 적용한 API 성능 테스트
- 인기 검색어 API
  - (Version 1)
  - (Version 2)
  - (Version 3)
- 검색 API
  - (Version 1)
  - (Version 2)
  - (Version 3)

## 💾 캐시 적용 이유
- In-Memory Cache(Local Memory Cache)
- Redis Cache
