# plAIng 프로젝트 Claude 규칙

## 문서 주도 개발 (SDD)

이 프로젝트는 **Spec-Driven Development** 방식으로 진행한다.
스펙 문서가 구현보다 항상 앞서야 한다.

### 구현 전 스펙 확인 (필수)

새 기능이나 도메인 로직을 구현하기 전에 반드시 `docs/specs/` 에서 관련 스펙 파일을 먼저 읽어라.

- **스펙 파일이 있으면**: 스펙에 정의된 인터페이스, 데이터 모델, 비즈니스 규칙을 따라 구현
- **스펙 파일이 없으면**: 구현하지 말고 스펙 작성을 먼저 요청

스펙 파일 위치: `docs/specs/{domain}.md` (예: `docs/specs/user.md`)

---

## 커밋 메시지 규칙

**형식**: `<type>(<scope>): <subject>`

```
feat(domain-user): 사용자 조회 API 추가
fix(common): ApiResponse null 처리 수정
docs(domain-user): 사용자 도메인 스펙 작성
test(domain-sample): SampleService 단위 테스트 추가
refactor(common): 예외 계층 구조 정리
chore(ci): Node 버전 업데이트
```

### type 목록
| type | 사용 시점 |
|------|-----------|
| `feat` | 새 기능 |
| `fix` | 버그 수정 |
| `docs` | 스펙/문서 변경 |
| `test` | 테스트 추가·수정 |
| `refactor` | 기능 변경 없는 코드 정리 |
| `chore` | 빌드, 설정, 의존성 변경 |

### scope 목록
`app` / `common` / `domain-{name}` / `frontend` / `ci` / `docs`

### SDD 순서 원칙
같은 기능에 대해 `docs:` 커밋이 `feat:` 커밋보다 **반드시 먼저** 와야 한다.

### TDD 커밋 사이클

하나의 행동(behavior) 단위로 아래 두 커밋을 쌍으로 만든다:

```
test(domain-user): 존재하지 않는 사용자 조회 시 예외 검증  ← 실패하는 테스트
feat(domain-user): 사용자 조회 API 구현                    ← 테스트 통과
```

- 한 쌍이 하나의 스펙 요구사항에 대응
- `test:` 커밋 시점에 빌드는 통과해야 하나 해당 테스트는 실패 상태여도 됨
- `feat:` 커밋 시점에 모든 테스트가 green이어야 함

---

## 커밋 빈도 및 WIP 규칙

### feature worktree에서의 작업

각 기능은 독립된 git worktree에서 개발한다:

```bash
# 새 기능 시작
git worktree add ../plAIng-feature-{name} feature/{domain}-{description}

# 작업 완료 후 제거
git worktree remove ../plAIng-feature-{name}
```

- feature worktree 안에서는 WIP 커밋 허용
- WIP 커밋 형식: `wip(domain-user): 조회 서비스 작업 중`
- PR 생성 전 `git rebase -i` 로 의미 있는 단위로 squash 후 정리

### main 브랜치 커밋 규칙

- WIP 커밋은 `main`에 절대 존재하면 안 됨
- PR merge 방식: **Squash and merge** 또는 rebase 후 merge
- merge 후 feature worktree 즉시 제거

---

## PR 규칙

PR 작성 시 관련 스펙 파일을 반드시 명시한다:

```markdown
## 관련 스펙
- `docs/specs/{domain}.md`
```

스펙 없이 기능을 구현하는 PR은 작성하지 않는다.

---

## 모듈 의존성 방향

```
app → domain-{name} → common
```

- 도메인 간 직접 의존 금지
- 새 도메인 추가 시: `settings.gradle.kts` 등록 → `app/build.gradle.kts` 의존 추가 → `docs/specs/{domain}.md` 작성

---

## 브랜치 네이밍

```
feature/{domain}-{short-description}
fix/{domain}-{short-description}
docs/{domain}-spec
chore/{description}
```

---

## AI 협업 규칙

> AI가 작성한 코드도 커밋한 사람의 코드다.

AI 사용 방식은 제한하지 않는다. 단, 결과물에 대한 책임은 항상 커밋한 사람에게 있다.

### 체크포인트 1 — 커밋 전

> AI가 생성한 코드를 본인이 이해하고 동의했는가?

- AI 코드를 그대로 커밋하는 것은 허용
- 내용을 이해하지 못한 채 커밋하는 것은 금지
- 이해되지 않는 부분은 AI에게 설명을 요청한 후 커밋

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
- 이 규칙(체크포인트 3 포함)은 루트 `CLAUDE.md`, `backend/CLAUDE.md`, `frontend/CLAUDE.md` 모두에 적용
