# User API : With SpringBoot framework

> Spring Boot기반으로 기본적인 설정을 자동화하고 JPA를 통해 Domain Model의 영속화(Persistence)에 대한 코딩 및 오류를 줄입니다.

> 사용 기술 : JPA H2 security JWT Redis Swagger

## Springboot MSA 및 DDD 구조 스켈레톤 
1. 점진적인 확장형으로 config, core, domain.user 패키지 분리
2. DDD 에서 회원에 용도를 구조적으로 분리하기 위한 바운디드 컨텍스트 활용 (https://github.com/lswteen/til-1/tree/master/book/DDD)
3. Springboot security, JWT 활용 
4. MSA 는 레파지스토리 파편화가 이루어지며 이를 멀티모듈로 최소화 할수 있음.

## AS-IS (멀티모듈 전달계)
   <img width="389" alt="스크린샷 2021-10-28 오후 10 48 03" src="https://user-images.githubusercontent.com/3292892/139269028-60b5a8a8-d238-44ad-87ea-6cd59723bf28.png">

## TO-BE (멀티모듈 후단계)
   ![스크린샷 2021-10-28 오후 10 51 22](https://user-images.githubusercontent.com/3292892/139269694-e0596987-deb4-49f1-8ea9-7c93f0c7e90d.png)
   
단독으로 구성하는 레파지스토리 구조를 개선하고 협업시 코드 복잡도와 파편화를 줄이고 구조적으로 안정된 패키지로 하나의 branch 로 협업 가능

## Local Mac 기준 선행조건 순서 Intellij 기준 Setup
1. IntelliJ에 Lombok Plugin을 설치한다.
    ```
    Preferences > Plugins > Browse repositories.. > Lombok 검색 > Install > restart
    ```
2. Gradle 창에서 Refresh를 클릭힌다. 인고의 시간이 지나면 모든 라이브러리가 download되고 **Run** 가능한 상태가 된다.
3. 그래도 안되면 File > Invalidate Caches.. > 클릭후 클리어 하면 잘못된 소스다운로드를 초기화할수있다.
4. JDK 11 버전으로 부탁드립니다.
5. https://github.com/lswteen/user.git 소스레벨 master 브렌치로 내려받기 하시면됩니다.
6. Local환경이다보니 redis, h2 설정이 필요합니다.
7. 아래 정보기준으로 진행하시면됩니다.

## Redis 설치
- **redis install**

$ brew install redis
- **redis start**

$ brew services start redis
- **redis stop**

$ brew services stop redis
- **redis restart**
$ brew services restart redis

$ redis-cli
위의 명령어를 통해 CLI를 사용할 수 있습니다.


## H2 DB connections 환경 설정
- **아래 이미지처럼 H2 설정을 변경하시면됩니다.**
<img width="910" alt="스크린샷 2021-10-28 오후 9 31 59" src="https://user-images.githubusercontent.com/3292892/139255693-cf536305-0be4-496e-b9df-8880e4b45713.png">


## gradle 버전으로 해당 버전 다운로드 이후 실행했는데 정상동작 안하면 아래이미지대로 진행 해주시면됩니다.
build 하위에서 clean -> build -> bootRun 하시면됩니다.

<img width="276" alt="스크린샷 2021-10-28 오후 9 34 51" src="https://user-images.githubusercontent.com/3292892/139256396-272cebf9-53c5-4929-a8e5-3d0bdd525bff.png">

## 해당 내용은 인텔리J에서 클린하는 기능입니다.
<img width="411" alt="스크린샷 2021-10-28 오후 9 34 59" src="https://user-images.githubusercontent.com/3292892/139256278-d7cbf032-b1c5-4b06-9305-cac87a540f39.png">
<img width="442" alt="스크린샷 2021-10-28 오후 9 35 04" src="https://user-images.githubusercontent.com/3292892/139256182-a0762b56-858e-454d-be57-2a81c8156c39.png">

## Swagger 회원 URI
http://localhost:9030/swagger-ui.html
회원 API 확인 가능합니다.
![스크린샷 2021-10-28 오후 9 42 23](https://user-images.githubusercontent.com/3292892/139257327-00ca0c6b-956f-49bb-89e7-984e526a3943.png)

## 회원 검증순서
- **SMS 인증번호 발송**: /api/sms/certification/sends
  - phone : 01033334444
  - 입력시 redis에서 발급된 keys 정보와 key등록된 발송 문자열 확인
    ![스크린샷 2021-10-28 오후 10 34 13](https://user-images.githubusercontent.com/3292892/139267079-779a5276-466d-401c-ad9c-60660a999b26.png)
    <img width="386" alt="스크린샷 2021-10-28 오후 10 34 58" src="https://user-images.githubusercontent.com/3292892/139267158-b3cda1b1-62a7-4262-90cb-5fcab6b8fc18.png">

- **SMS 인증번호 확인**: /api/sms/certification/confirms
  - phone : {발송번호}, certificationNumber {redis value}
  - 인증되면 Redis 삭제 되며 redis 에 등록된 만료시간이 value값을 nil로 변경하여 재발송을 요구함.
  ![스크린샷 2021-10-28 오후 10 35 41](https://user-images.githubusercontent.com/3292892/139267556-1ee7788f-7c9f-441e-848a-2f710f72f614.png)
  <img width="505" alt="스크린샷 2021-10-28 오후 10 35 34" src="https://user-images.githubusercontent.com/3292892/139267618-5aa0ebc8-6dc1-44b4-b5ae-dd13a9f723e2.png">

- **회워가입**: /api/user/singup
  - email, firstname, lastname, nickname, password, phonenumber, roles<br>
  - email, nickname, phonenumber 3가지는 로그인시 유일식별자로 사용됩니다.<br>
  - password 는 BCryptPasswordEncoder 사용합니다.<br>
  - 성공시 정상적으로 200 확인되며 동일한 조건으로  3가지 파라메타 중복시 실패됩니다.

  ![스크린샷 2021-10-28 오후 9 46 21](https://user-images.githubusercontent.com/3292892/139257985-bc85d607-b094-4036-bdfc-eaf1693e8057.png)

  
- **로그인**: /api/user/login
  - type 3종류 : email, nickname, phone<br>
  - argument : {email,nickname,phonenumber} 타입에 따라 넣어주시면됩니다.
  - password : 회원시 등록된 패스워드
  - 성공시 JWT 토큰값 확인가능합니다.
  ![스크린샷 2021-10-28 오후 10 03 09](https://user-images.githubusercontent.com/3292892/139260778-3729b355-0475-4189-8993-5e9205763226.png)


- **회원정보보기**: /api/user/me
  - Client 로그인하면 JWT 가  Headerd에 등록되어 Request 해더에 값을 담으면 된다고하는데..
  - 회원 서비스 구현을 안하다보니 호출하는 방법 검증을 못했습니다.
  - 죄송합니다. 기능구현은 정상인것으로 확인되는데 시간이 부족해 클라이언트 에서 어떻게 호출하는지에 대한 러닝커브가 부족하였습니다.
  <img width="721" alt="스크린샷 2021-10-28 오후 10 06 46" src="https://user-images.githubusercontent.com/3292892/139261428-a7e63352-c07b-4f67-ad81-b0d99e017bff.png">


- **비밀번호 찾기(재설정) 기능**: /api/user/password
  - 01038564215, qwer1234 로그인 정보로  성공 후 JWT 토큰생성
  ![스크린샷 2021-10-28 오후 10 14 39](https://user-images.githubusercontent.com/3292892/139262928-8d7c3db2-4c42-47f8-a726-e4db32a3a486.png)
  ![스크린샷 2021-10-28 오후 10 20 26](https://user-images.githubusercontent.com/3292892/139263882-a08820a6-e7a3-4edc-b1b9-828ad6aee54b.png)
  - 동일 비밀번호 입력시 이전 암호라는 메시지 전달
  ![스크린샷 2021-10-28 오후 10 21 42](https://user-images.githubusercontent.com/3292892/139264135-84ee4d59-ad70-4b3e-83f0-c484e76db5ce.png)
  ![스크린샷 2021-10-28 오후 10 21 49](https://user-images.githubusercontent.com/3292892/139264215-4c723b74-848f-49f4-895a-28ceda8eac3d.png)
  - 다른 비밀번호 입력 후 저장 변경 확인
  ![스크린샷 2021-10-28 오후 10 23 38](https://user-images.githubusercontent.com/3292892/139264514-6315821c-6396-42da-90e8-a38f51c1a786.png)
  ![스크린샷 2021-10-28 오후 10 23 50](https://user-images.githubusercontent.com/3292892/139264595-1d9c898b-1004-4d38-9451-b418edd5a9e0.png)
  


## Features
- **[Spring Boot](https://start.spring.io/)**: springboot 기본 생성
- **[아키텍처]**: DDD 구조 설계 및 Core 작업 추후 점진적인 MSA 멀티모듈 가능성을위한 분리 
- **JPA**: H2 Repository, Entity
- **Spring Security JWT **: 회원쪽 서비스에서 중요한 Security 그리고 JWT 도입을 위한2일간에 삽질 끝에 작업됨.
  ![스크린샷 2021-10-28 오후 10 28 19](https://user-images.githubusercontent.com/3292892/139265383-dbd2b2b8-ae2f-4b4b-8d85-dd987b1a63e0.png)
- **[Lombok](https://projectlombok.org/features/all)**: 코드자동화 라이브러리,@Builder @Getter, @Slf4j...
- **Gradle**: 빌드 관리 툴, 가독성, 빌드 속도 좋음 [Maven vs Gradle](https://bkim.tistory.com/13)
- **Rest Doc**: API문서 자동화. Swagger (시간이 있다면 Restdoc 변경예정)



## 더 고려해야할 사항
- 패스워드 변경은 되는데 변경된 로그인으로 로그인이 안되는 현상발견
- 로그인후 JWT토큰을 정보보기 호출시 어떻게 사용하는지를 아직 파악이 되지 않았습니다.
