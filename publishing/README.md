# 퍼블리싱

RTS 화면 퍼블리싱 작업 폴더. 디자인 시스템은 KRDS(대한민국 디지털 정부 디자인 시스템)를 사용한다.

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
