# 퍼블리싱

RTS 화면 퍼블리싱 작업 폴더. 디자인 시스템은 KRDS(대한민국 디지털 정부 디자인 시스템)를 사용한다.

## 구조

```
publishing/
├── krds/            KRDS 원본 (수정 금지)
├── css/rts.css      RTS 전용 스타일 (KRDS로 해결되지 않는 것만)
└── html/            화면 퍼블리싱
    └── ticket/list.html   티켓 목록
```

## 작성 규칙

- 화면은 KRDS 홈페이지 레이아웃을 따른다: 헤더 → 사이드 메뉴 + 본문(브레드크럼, 페이지 제목, 검색, 목록, 페이지네이션) → 푸터.
- 컴포넌트는 KRDS 클래스(`krds-*`, `form-group`, `tbl data` 등)를 그대로 쓴다. 예제는 `krds/html/code/`.
- KRDS에 없어 새로 만드는 스타일은 `css/rts.css`에 최소한으로, `rts-` 접두어 + BEM(`block__element--modifier`)으로 작성하고 값은 KRDS 토큰(`--krds-*`)을 쓴다.
- CSS는 `krds/resources/css/component/output.css`(토큰 + 공통 + 컴포넌트)를 쓴다. `cdn/krds.min.css`는 아이콘을 krds.go.kr에서 받아오므로 폐쇄망에서 쓰지 않는다.
- KRDS는 폰트 `@font-face`를 제공하지 않아 `rts.css`에서 로컬 Pretendard GOV를 등록한다.

## 미리보기

KRDS 아이콘이 CSS mask 이미지라 `file://`로 열면 브라우저가 막는다. 로컬 웹서버로 연다.

```bash
cd publishing
python -m http.server 8090
# http://localhost:8090/html/ticket/list.html
```

VS Code Live Server 등 다른 정적 서버도 된다.

## krds/

KRDS HTML ComponentKit 원본. **직접 수정하지 않는다.** 바꿔야 할 스타일은 별도 파일에서 덮어쓴다.

| 항목 | 값 |
| --- | --- |
| 출처 | https://github.com/KRDS-uiux/krds-uiux |
| 버전 | v1.1.0 (커밋 d6bb184, 2026-01-12) |
| 문서 | https://www.krds.go.kr/html/site/component/component_summary.html |
| 저작권 | https://www.krds.go.kr/html/site/utility/utility_06.html |

- `krds/html/code/`: 컴포넌트별 예제 HTML
- `krds/resources/`: css, scss, js, 폰트, 이미지 (`cdn/krds.min.css`는 단일 파일 번들)
- `krds/tokens/`: 디자인 토큰(JSON)

업데이트할 때는 위 저장소의 새 태그로 `krds/`를 통째로 교체하고 이 표의 버전을 고친다.
