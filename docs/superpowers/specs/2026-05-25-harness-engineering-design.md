# Harness Engineering 규칙 설계

**날짜**: 2026-05-25  
**범위**: CLAUDE.md 규칙 정의 (백엔드/프론트엔드 계층형 분리)  
**팀 규모**: 2인

---

## 배경

plAIng 프로젝트는 SDD + TDD 기반으로 운영된다. 루트 `CLAUDE.md`에는 프로젝트 공통 규칙(SDD, 커밋 컨벤션, PR, worktree)이 정의되어 있다. 이 설계는 백엔드와 프론트엔드의 코드 품질, 테스트 커버리지, 로컬 개발 환경 규칙을 각 모듈 CLAUDE.md에 분리해 추가한다.

## 결정 사항

**계층형 CLAUDE.md 분리** 방식을 채택한다.

```
plAIng/CLAUDE.md          ← 공통 (변경 없음)
backend/CLAUDE.md         ← Kotlin/Gradle 전용 규칙 (신규)
frontend/CLAUDE.md        ← Vue/TS 전용 규칙 (신규)
```

Claude Code는 세션 시작 시 모든 CLAUDE.md를 로드한다. 파일 분리는 사람이 읽기 쉽고 규칙의 스코프를 명확히 하기 위함이다.

---

## backend/CLAUDE.md 규칙

### 코드 품질

- **도구**: ktlint
- `./gradlew ktlintCheck` — CI에서 실패 시 빌드 차단
- `./gradlew ktlintFormat` — 자동 포맷 수정 허용
- 포맷 규칙 위반은 직접 수정하지 말고 `ktlintFormat`을 먼저 실행

### 테스트 커버리지

- **도구**: JaCoCo
- 전체 커버리지 70% 미만 시 경고 (CI 차단 아님)
- 커버리지 측정 제외 대상:
  - `*Application.kt` (진입점)
  - `*Config.kt` (설정 클래스)
  - data class

### 로컬 개발 환경

- DB 및 외부 의존성은 Docker Compose로 실행 (`docker-compose up -d`)
- 로컬 전용 설정: `application-local.yml` (`.gitignore`에 포함됨)
- 필요한 환경변수는 `.env.example`에 키만 명시 (값 없이)
- 팀원이 로컬 환경 세팅 시 `.env.example`를 복사해 `.env.local` 생성

### 테스트 구조

- 단위 테스트: `src/test/kotlin/com/plaing/{module}/`
- 테스트 픽스처: `src/test/kotlin/com/plaing/fixture/` 패키지에 분리
- 통합 테스트: `@SpringBootTest` + `@ActiveProfiles("test")`
- 테스트용 DB: H2 in-memory (`application-test.yml`에 설정)
- 테스트 격리: `@Transactional`로 각 테스트 후 롤백

---

## frontend/CLAUDE.md 규칙

### 코드 품질

- **도구**: ESLint + Prettier
- `npm run lint` — CI에서 실패 시 빌드 차단
- `npm run lint:fix` — 자동 수정 허용
- Vue SFC 순서: `<script>` → `<template>` → `<style>`

### 테스트 커버리지

- **도구**: Vitest
- 전체 커버리지 70% 미만 시 경고 (CI 차단 아님)

### 컴포넌트 구조

| 디렉토리 | 용도 |
|----------|------|
| `components/` | 재사용 가능한 UI 컴포넌트 |
| `composables/use{Domain}.ts` | API 호출 및 비즈니스 로직 |
| `pages/` | 라우트 페이지 (레이아웃만, 로직 없음) |

- 페이지 컴포넌트에 직접 API 호출 금지 — 반드시 composable 경유
- composable 네이밍: `useUser`, `usePayment` 등 도메인 단위

### API 호출 규칙

- 백엔드 baseURL: `runtimeConfig.public.apiBase` 사용
- 직접 URL 하드코딩 금지

---

## 미결 사항

- Docker Compose 파일 내용은 실제 도메인 개발 시 정의
- ktlint 버전 및 규칙 세트는 `backend/CLAUDE.md` 작성 시 고정
- ESLint 설정 파일(`eslint.config.ts`)은 tooling 세팅 시 추가
