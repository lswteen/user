# API Template: With Spring Boot

> Spring Boot기반으로 기본적인 설정을 자동화하고 JPA를 통해 Domain Model의 영속화(Persistence)에 대한 코딩 및 오류를 줄입니다.

## Redis 설치
AWS 서버 또는 docker를 하는게 좋을것 같지만 .
mac은 brew를 이용하여 쉽게 설치할 수 있습니다.

$ brew install redis
위의 명령어를 통해 Redis를 설치합니다. (다소 시간이 걸립니다)

$ brew services start redis

$ brew services stop redis
$ brew services restart redis
brew services start 명령어를 통해 Redis를 실행시켜 줍니다.

$ redis-cli
위의 명령어를 통해 CLI를 사용할 수 있습니다.

## Features
- **[Spring Boot](https://start.spring.io/)**: springboot 기본 생성
- **[아키텍처]**: DDD 구조 설계 및 Core 작업 추후 점진적인 MSA 멀티모듈 가능성을위한 분리 
- **JPA**: H2 Repository, Entity
- **Spring Validator**: Request validation 수행, [Sping MVC Custom Validation](https://www.baeldung.com/spring-mvc-custom-validator)
- **[Lombok](https://projectlombok.org/features/all)**: 코드자동화 라이브러리,@Builder @Getter, @Slf4j...
- **Gradle**: 빌드 관리 툴, 가독성, 빌드 속도 좋음 [Maven vs Gradle](https://bkim.tistory.com/13)
- **Rest Doc**: API문서 자동화. Swagger 도 좋지만 Restdoc 한번도전!! 안되면 Swagger

## Setup
1. IntelliJ에 Lombok Plugin을 설치한다.
    ```
    Preferences > Plugins > Browse repositories.. > Lombok 검색 > Install > restart
    ```
2. Gradle 창에서 Refresh를 클릭힌다. 인고의 시간이 지나면 모든 라이브러리가 download되고 **Run** 가능한 상태가 된다.
3. 그래도 안되면 File > Invalidate Caches.. > 클릭후 클리어 하면 잘못된 소스다운로드를 초기화할수있다.
4. JDK 11 버전으로 부탁드립니다.
## TODO
- **Request Logging**: 프로젝트 기본 스켈레톤을 오랜만에 구성했는데 쉬운건 아니네요 토요일,일요일이 없어졌습니다.

## 더 고려해야할 사항
앞으로 꾸준히 노력하도록 하겠습니다.

####도메인 설계 관련 참고서적
- [DDD Start!](http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=84000742): DDD 책중에 제일 쉽습니다. Java로 된 예제가 있어요!!
- [DDD 기본정보 정리](https://nesoy.github.io/articles/2018-08/DDD-Architecture)
- [도메인 주도 설계의 본질](https://www.slideshare.net/baejjae93/ss-27536729)