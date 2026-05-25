# plAIng

> 🚧 기획 진행 중입니다.

## 기술 스택

| 영역 | 기술 |
|------|------|
| Backend | Spring Boot 4.0.x, Java 25, Kotlin, Gradle (Kotlin DSL) |
| Frontend | Nuxt.js 3 (Vue 3) |
| Git 전략 | GitHub Flow |

## 프로젝트 구조

```
plAIng/
├── backend/    # Spring Boot 멀티모듈
└── frontend/   # Nuxt.js
```

## 로컬 실행

### Backend

```bash
cd backend
./gradlew :app:bootRun
```

### Frontend

```bash
cd frontend
npm install
npm run dev
# http://localhost:3000
```

## 모듈 구조 (Backend)

```
app/              # Spring Boot 진입점
domain-{name}/    # 도메인별 모듈 (기획 확정 시 추가)
common/           # 공통 예외, 응답 포맷
```

**의존성 방향:** `app` → `domain-{name}` → `common`

## 기여 가이드

1. `main` 브랜치에서 `feature/your-feature` 브랜치 생성
2. 작업 후 Pull Request 생성
3. 코드 리뷰 후 `main` 머지
