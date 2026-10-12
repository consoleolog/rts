# RTS (Requirement Traceability System)

요구사항, 변경요청, 결함 티켓에 고유 번호를 발급하고 변경 이력을 추적하는 사내 시스템.

## 기술 스택

| 구분 | 버전 |
| --- | --- |
| Java | 11 |
| WAS | Tomcat 8.5 (Servlet 3.1, JSP 2.3) |
| Spring Framework | 5.3.39 |
| Spring Security | 5.8.16 |
| Spring Data JPA | 2.7.18 |
| Hibernate | 5.6.9.Final |
| DB | MySQL 5.7 (Connector/J 8.0.33) |
| 커넥션 풀 | HikariCP 5.1.0 |
| 스키마 관리 | Liquibase 4.29.2 |
| 로깅 | Log4j2 2.24.3 |
| 화면 | JSP + JSTL 1.2 |
| 기타 | Lombok 1.18.34 |

## 구조

```
src/main/java/com/rts
├── config          AppConfig(DataSource, Liquibase, JPA, 트랜잭션), WebConfig(MVC), SecurityConfig, 초기화 클래스
└── common          공통 예외, 예외 처리기
src/main/resources
├── rts.properties  기본 설정 (로컬 개발용)
├── log4j2.xml
└── db/changelog    Liquibase changelog (master + changes/)
src/main/webapp/WEB-INF/views   JSP
```

- web.xml 없이 Java 설정으로 구성 (`WebAppInitializer`, `SecurityWebAppInitializer`).
- 루트 컨텍스트: 서비스, 리포지토리, 보안. 서블릿 컨텍스트: 컨트롤러.
- 스키마는 Liquibase로만 관리하고 Hibernate는 `validate`만 한다.
- 보안은 현재 모든 요청을 허용한다. 로그인 기능 개발 시 `SecurityConfig`에 규칙을 추가한다.

## 로컬 실행

1. MySQL에 DB와 계정 생성

   ```sql
   CREATE DATABASE rts DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
   CREATE USER 'rts'@'%' IDENTIFIED BY 'rts';
   GRANT ALL PRIVILEGES ON rts.* TO 'rts'@'%';
   ```

2. 빌드: `mvn package` → `target/rts.war`
3. Tomcat `webapps/`에 복사 후 기동, `http://localhost:8080/rts/` 접속

기동 시 Liquibase가 changelog를 적용한다 (`liquibase.enabled=false`로 끌 수 있음).

## 운영 설정

`rts.properties`의 값은 `${catalina.base}/conf/rts.properties`가 있으면 그 값으로 덮어쓴다.
운영 DB 접속 정보는 WAR에 넣지 말고 서버의 이 파일에 둔다.

로그는 콘솔과 `${catalina.base}/logs/rts.log`(일 단위 롤링, 30일 보관)에 남는다.

## 스키마 변경

`src/main/resources/db/changelog/changes/`에 `001-xxx.xml`처럼 번호를 붙여 추가하고
`db.changelog-master.xml`에 include 한다. 이미 적용된 changeSet은 고치지 않는다.
