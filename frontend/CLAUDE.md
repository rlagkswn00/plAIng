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
