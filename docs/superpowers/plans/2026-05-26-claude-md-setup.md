# CLAUDE.md 3파일 작성 구현 계획

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 설계 문서에 정의된 AI 협업 규칙, 백엔드/프론트엔드 하네스 규칙을 CLAUDE.md 파일 3개에 반영한다.

**Architecture:** 루트 CLAUDE.md에 AI 협업 섹션을 추가하고, backend/CLAUDE.md와 frontend/CLAUDE.md를 신규 생성한다. 모두 기존 설계 문서(`docs/superpowers/specs/`)를 그대로 구현한다.

**Tech Stack:** Markdown, CLAUDE.md (Claude Code 설정 파일)

---

## 파일 맵

| 작업 | 파일 | 근거 문서 |
|------|------|----------|
| 수정 | `CLAUDE.md` | `2026-05-26-ai-dlc-collaboration-design.md` |
| 신규 | `backend/CLAUDE.md` | `2026-05-25-harness-engineering-design.md` |
| 신규 | `frontend/CLAUDE.md` | `2026-05-25-harness-engineering-design.md` |

---

## Task 1: 루트 CLAUDE.md에 AI 협업 규칙 섹션 추가

**Files:**
- Modify: `CLAUDE.md` (파일 끝에 섹션 추가)

- [ ] **Step 1: CLAUDE.md 끝에 아래 섹션 추가**

```markdown
---

## AI 협업 규칙

> AI가 작성한 코드도 커밋한 사람의 코드다.

AI 사용 방식은 제한하지 않는다. 단, 결과물에 대한 책임은 항상 커밋한 사람에게 있다.

### 체크포인트 1 — 커밋 전

> AI가 생성한 코드를 본인이 이해하고 동의했는가?

- 이해하지 못한 코드는 AI에게 설명을 요청한 후 커밋

### 체크포인트 2 — PR 전

> AI가 제안한 구조 변경이나 설계 결정이 스펙에 반영됐는가?

- AI 제안도 스펙 없으면 구현 없음 (SDD 원칙 동일 적용)

### 체크포인트 3 — CLAUDE.md 수정 전

> 팀원과 합의했는가?

- 단독 수정 금지
- 작은 수정: 팀원 동의 후 커밋
- 큰 변경 (새 섹션 추가, 규칙 삭제): PR 필수

### CLAUDE.md 동기화

- 작업 시작 전 항상 `git pull`
- 루트 `CLAUDE.md`, `backend/CLAUDE.md`, `frontend/CLAUDE.md` 모두에 적용
```

- [ ] **Step 2: 내용 확인**

```bash
tail -40 CLAUDE.md
```

Expected: `## AI 협업 규칙` 섹션이 파일 끝에 보임

- [ ] **Step 3: 커밋**

```bash
git add CLAUDE.md
git commit -m "chore(docs): CLAUDE.md에 AI 협업 규칙 섹션 추가"
```

---

## Task 2: backend/CLAUDE.md 생성

**Files:**
- Create: `backend/CLAUDE.md`

- [ ] **Step 1: 아래 내용으로 backend/CLAUDE.md 생성**

```markdown
# Backend (Kotlin/Gradle) 규칙

## 코드 품질

- **도구**: ktlint
- `./gradlew ktlintCheck` — CI에서 실패 시 빌드 차단
- `./gradlew ktlintFormat` — 자동 포맷 수정 허용
- 포맷 위반은 직접 수정하지 말고 `ktlintFormat` 먼저 실행

## 테스트 커버리지

- **도구**: JaCoCo
- 전체 커버리지 70% 미만 시 경고 (CI 차단 아님)
- 커버리지 측정 제외:
  - `*Application.kt` (진입점)
  - `*Config.kt` (설정 클래스)
  - data class

## 로컬 개발 환경

- DB 및 외부 의존성: `docker-compose up -d` 로 실행
- 로컬 전용 설정: `application-local.yml` (gitignored)
- 필요 환경변수: `.env.example`에 키만 명시 (값 없이)
- 팀원 세팅: `.env.example` 복사 → `.env.local` 생성

## 테스트 구조

- 단위 테스트: `src/test/kotlin/com/plaing/{module}/`
- 픽스처: `src/test/kotlin/com/plaing/fixture/` 패키지에 분리
- 통합 테스트: `@SpringBootTest` + `@ActiveProfiles("test")`
- 테스트 DB: H2 in-memory (`application-test.yml`에 설정)
- 테스트 격리: `@Transactional` 로 각 테스트 후 롤백
```

- [ ] **Step 2: 내용 확인**

```bash
cat backend/CLAUDE.md
```

Expected: 4개 섹션(코드 품질, 테스트 커버리지, 로컬 개발 환경, 테스트 구조) 모두 보임

- [ ] **Step 3: 커밋**

```bash
git add backend/CLAUDE.md
git commit -m "chore(backend): backend CLAUDE.md 추가 — ktlint, JaCoCo, 로컬 환경, 테스트 구조"
```

---

## Task 3: frontend/CLAUDE.md 생성

**Files:**
- Create: `frontend/CLAUDE.md`

- [ ] **Step 1: 아래 내용으로 frontend/CLAUDE.md 생성**

```markdown
# Frontend (Vue/TypeScript) 규칙

## 코드 품질

- **도구**: ESLint + Prettier
- `npm run lint` — CI에서 실패 시 빌드 차단
- `npm run lint:fix` — 자동 수정 허용
- Vue SFC 작성 순서: `<script>` → `<template>` → `<style>`

## 테스트 커버리지

- **도구**: Vitest
- 전체 커버리지 70% 미만 시 경고 (CI 차단 아님)

## 컴포넌트 구조

| 디렉토리 | 용도 |
|----------|------|
| `components/` | 재사용 가능한 UI 컴포넌트 |
| `composables/use{Domain}.ts` | API 호출 및 비즈니스 로직 |
| `pages/` | 라우트 페이지 (레이아웃만, 로직 없음) |

- 페이지 컴포넌트에 직접 API 호출 금지 — 반드시 composable 경유
- composable 네이밍: `useUser`, `usePayment` 등 도메인 단위

## API 호출 규칙

- 백엔드 baseURL: `runtimeConfig.public.apiBase` 사용
- 직접 URL 하드코딩 금지
```

- [ ] **Step 2: 내용 확인**

```bash
cat frontend/CLAUDE.md
```

Expected: 4개 섹션(코드 품질, 테스트 커버리지, 컴포넌트 구조, API 호출 규칙) 모두 보임

- [ ] **Step 3: 커밋**

```bash
git add frontend/CLAUDE.md
git commit -m "chore(frontend): frontend CLAUDE.md 추가 — ESLint, Vitest, 컴포넌트 구조"
```

---

## 완료 확인

```bash
find . -name "CLAUDE.md" | grep -v node_modules
```

Expected:
```
./CLAUDE.md
./backend/CLAUDE.md
./frontend/CLAUDE.md
```
