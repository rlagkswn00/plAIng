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

- DB 및 외부 의존성: `docker compose up -d` 로 실행
- 로컬 전용 설정: `application-local.yml` (.gitignore에 등록)
- 필요 환경변수: `.env.example`에 키만 명시 (값 없이)
- 팀원 세팅: `.env.example` 복사 → `.env.local` 생성

## 테스트 구조

- 단위 테스트: `src/test/kotlin/com/plaing/{module}/`
- 픽스처: `src/test/kotlin/com/plaing/fixture/` 패키지에 분리
- 통합 테스트: `@SpringBootTest` + `@ActiveProfiles("test")`
- 테스트 DB: H2 in-memory (`application-test.yml`에 설정)
- 테스트 격리: `@Transactional` 로 각 테스트 후 롤백
