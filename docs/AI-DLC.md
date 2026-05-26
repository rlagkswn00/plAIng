# plAIng AI Development Lifecycle (AI-DLC)

이 문서는 plAIng 프로젝트에서 AI를 어떻게 개발 라이프사이클에 통합하는지 정의한다.

---

## 1. 핵심 철학

> **AI는 구현 파트너다. 설계와 책임은 사람이 진다.**

- AI가 작성한 코드도 커밋한 사람의 코드다
- AI 사용 방식은 개인 자유지만, 결과물 책임은 개인에게 있다
- 스펙 없이는 AI도 구현하지 않는다

---

## 2. AI-DLC 흐름

```
[스펙 작성] → [구현] → [커밋] → [PR] → [Merge]
     ↑              ↑         ↑        ↑
  사람 주도     AI 활용    체크포인트1  체크포인트2
  AI 초안 가능  자유롭게    이해 확인   스펙 반영 확인
```

### 단계별 AI 역할

| 단계 | AI 역할 | 사람 역할 |
|------|---------|----------|
| 스펙 작성 | 초안 제안 가능 | 최종 승인 필수 |
| 구현 | 자유롭게 활용 | 이해 후 커밋 |
| 테스트 | 자유롭게 활용 | 검증 |
| PR 리뷰 | 보조 | 최종 판단 |
| CLAUDE.md 수정 | 제안 가능 | 팀 합의 후 반영 |

---

## 3. AI 협업 체크포인트

### 체크포인트 1 — 커밋 전
> AI가 생성한 코드를 본인이 이해하고 동의했는가?

- 이해하지 못한 코드는 커밋 전 AI에게 설명 요청
- 이해 후 커밋

### 체크포인트 2 — PR 전
> AI가 제안한 설계 결정이 스펙에 반영됐는가?

- AI 제안으로 구조가 바뀌었다면 `docs/specs/{domain}.md` 먼저 업데이트
- SDD 원칙 동일 적용: AI 제안도 스펙 없으면 구현 없음

### 체크포인트 3 — CLAUDE.md 수정 전
> 팀원과 합의했는가?

- 단독 수정 금지
- 작은 수정: 팀원 동의 후 직접 커밋
- 큰 변경 (새 섹션 추가, 규칙 삭제): PR 필수

---

## 4. AI 컨텍스트 관리 (CLAUDE.md 계층)

Claude는 세션 시작 시 프로젝트 내 모든 CLAUDE.md를 로드한다.

```
plAIng/CLAUDE.md          ← 프로젝트 공통 규칙
backend/CLAUDE.md         ← Kotlin 코드 품질, 커버리지, 로컬 환경 (예정)
frontend/CLAUDE.md        ← Vue/TS 코드 품질, 커버리지, 컴포넌트 규칙 (예정)
```

### CLAUDE.md 동기화 규칙
- 작업 시작 전 항상 `git pull` — 두 사람의 AI가 같은 규칙으로 동작
- CLAUDE.md 변경 후 팀원에게 알릴 것

---

## 5. 개발 워크플로우와 AI

### SDD + AI
```
1. docs/specs/{domain}.md 작성  ← AI 초안 가능, 사람 승인 필수
2. docs: 커밋
3. 팀원 리뷰
4. 구현 시작
```

### TDD + AI
```
test: 커밋 (실패하는 테스트)  ← AI 활용 가능
feat: 커밋 (테스트 통과)      ← AI 활용 가능
```
두 커밋이 스펙 요구사항 하나에 대응한다.

### Worktree + AI
```bash
git worktree add ../plAIng-feature-{name} feature/{domain}-{description}
# worktree 안에서 AI와 자유롭게 작업
# PR 전 git rebase -i 로 정리
git worktree remove ../plAIng-feature-{name}
```

---

## 6. 관련 문서

- `CLAUDE.md` — Claude에게 적용되는 전체 프로젝트 규칙
- `docs/specs/` — 도메인 스펙 파일
- `docs/superpowers/specs/2026-05-25-harness-engineering-design.md` — 하네스 엔지니어링 규칙 설계
- `docs/superpowers/specs/2026-05-26-ai-dlc-collaboration-design.md` — AI 협업 규칙 설계
