# 스프링부트로 웹 서비스 구축하기 [![Build Status](https://travis-ci.org/rmk1075/SpringBoot_Webservice.svg?branch=master)](https://travis-ci.org/rmk1075/SpringBoot_Webservice)

## 1. SpringBoot & Gradle & Github 프로젝트 생성하기

<details>

<summary>세부내용</summary>

- SpringBoot_Webservice 프로젝트 생성

  - localhost:8080/hello

- SpringBoot_Webservice Github 연동

  - <https://github.com/rmk1075/SpringBoot_Webservice>

</details>

## 2. SpringBoot & JPA로 간단 API 만들기

<details>

<summary>세부내용</summary>

- Entity Class

  - 실제 DB 테이블과 매칭될 class
  
  - JPA 사용시, 실제 쿼리가 아닌 Entity class 수정을 통해 작업

- JPA annotation
  
  - @Entity
  
    - 테이블과 링크될 class 표현

    - '_'를 사옹해서 이름을 매칭 - ex) SpringBootEx.java -> spring_boot_ex table
  
  - @Id
  
    - 해당 테이블의 PK 필드를 표현

  - @GenerativeValue
  
    - PK의 생성 규칙

    > ※ SpringBoot 2.0에서는 옵션을 추가해야함 (ref: <https://jojoldu.tistory.com/295>)
    
    - 기본값은 AUTO (MySQL의 auto_increment 되는 정수형 값)
  
  - @Column
  
    - 해당 필드의 컬럼 옵션을 변경하기 위해 사용 - ex) columnDefinition = "TEXT", nullable = false

  - @MappedSuperclass
  
    - JPA Entity 클래스들이 해당 클래스를 상속할 경우 필드들도 컬럼으로 인식하도록 한다
  
  - @EntityListeners(AuditingEntityListner.class)
  
    - 해당 클래스에 Auditing 기능을 포함한다.

- Lombok annotation

  - @NoArgsConstructor
  
    - 기본 생성자 자동 추가

    - access = AccessLevel.PROTECTED (기본생성자의 접근 권한을 'protected'로 제한)

    - protected Constructor() {}
  
    - Entity 클래스의 기본 생성자를 프로젝트 코드상에서는 생성하지 않고 JPA에서만 Entity 클래스를 생성하도록 하기위함

  - @Getter
  
    - 클래스 내 모든 필드의 Getter method를 자동 생성
  
  - @Builder
  
    - 클래스의 빌더 패턴 클래스를 생성 (생성자 선언시에는 생성자에 포함된 필드만 빌더에 포함)

    - *TODO: builder patter*

  - @AllArgsConstructor
  
    - 모든 필드를 인자값으로 하는 생성자를 자동 생성

- Repository

  - Dao의 역할을 하는 DB Layer
  
  - JPA에서는 인터페이스로 생성해서 사용
  
  - JpaRepository<Entity, PK타입> 상속 시 기본적인 CRUD method가 자동생성
  
- JUnit

  - JUnit4 -> JUnit5로 변경되어서 예제 코드와 상이한 점 확인 필요
  
  - JUnit4 (ref: <https://www.youtube.com/watch?v=tyZMdwT3rIY>)

  - Spring Boot에서 test code는 memory DB인 H2를 사용
  
- Bean Injection

  - @Autowired: 비권장하는 방식
  
  - setter
  
  - constructor: 가장 권장하는 방식 -> 예제코드에서 @AllArgsConstructor로 생성
  
> ※ h2 console 실행 중 "mem:testdb" not found 에러 발생
> - application.yml에 'spring:datasource:url: jdbc:h2:mem:testdb' 추가해서 해결

- JPA Auditing

  - 생성시간/수정시간 자동화

  - @CreatedDate
  
    - Entity가 생성되어 저장될 때 시간을 자동 저장
  
  - @LastModifiedDate
  
    - 조회한 Entity의 값을 변경할 때 시간을 자동 저장

  - @EnableJpaAuditing
  
    - JPA Auditing 활성화

</details>

## 3. SpringBoot & Handlebars로 화면 만들기

<details>

<summary>세부내용</summary>

- Handlebars

  - server template engine
  
  - URL 요청시, 파라미터와 상태에 맞춰 적절한 HTML 화면을 생성해서 전달한다
  
  - compile 'pl.allegro.tech.boot:handlebars-spring-boot-starter:0.3.2'
  
  - .hbs 파일 (handlebars 파일)
  
- 작업내용

  - main page 구현 (src/main/resources/templates/main.hbs)
  
  - service method 구현 (src/main/java/com/spring/webservice/service/PostsService.java)
  
    - Controller와 Service의 역할을 분리
    
    - Service: business logic & transaction
    
    - Controller: view interaction
    
  - 입력화면 구현 (src/main/resources/templates/main.hbs)
  
    - bootstrap library 설정 (css, js)
    
    - SpringBoot에서 src/main/resources/static은 URL에서 '/'로 지정됨 -> 호출 url 확인
    
    - CSS <head>, js <body> 최하단에서 호출
    
      - 페이지 로딩속도 향상을 위해
      
      - HTML은 최상단에서부터 코르들 실행 -> js를 head에서 호출 시 js의 용량이 클수록 화면 로딩이 느려짐
      
      - css는 화면을 그리는 역할을 하기 때문에 head에서 호출하는 것이 좋음
      
  - main.js 구현 (src/main/resources/static/js/app/main.js)
  
    - var main 구현
    
        - js의 경우 나중에 불려진 js의 function이 먼저 불려진 js의 function을 덮어씀
    
        - main.js 만의 변수, function 영역으로 var main 객체안에서 function을 선언
    
  - data-h2.sql (src/main/resources/data-h2.sql)
  
    - 어플리케이션 실행 시 작동하는 script
    
    - applicaiton.yml 수정
    
      - spring.profile 추가
       
        - 어플리케이션 실행시 파라미터로 넘어온 값이 없는 경우 active값을 보게됨
        
      - application.yml에서 ---를 기준으로 상단은 공통, 하단은 각 profile의 설정영역 
    
- @Transactional

  - method에서 exception 발생 시 해당 method에서 이루어진 모든 DB 작업 초기화  

- @Query

</details>

## 4. AWS EC2 & RDS 구축하기

<details>

<summary>세부내용</summary>

- AWS EC2 생성

  - SSH, Http, Https 연결
  
  - PuTTY로 SSH 연결 (ref: <https://docs.aws.amazon.com/ko_kr/AWSEC2/latest/UserGuide/putty.html>)

- AWS RDS 설정

  - Maria DB 설정

  - MySql Workbench DB Connection 설정
  
  - EC2 server에 mysql 설치

</details>

## 5. EC2에 배포하기

<details>

<summary>세부내용</summary>

- Java 11 설치

  - local에서 java 11 사용하기 때문에 버전 맞춤

  - sudo amazon-linux-extras install java-openjdk11
  
  - java 11.0.7 버전 설치
  
- Git 설치

  - sudo yum install git
  
  - git clone시 git index에 잡혀있는 기본 permission에 따라서 clone 됨
  
    - git ls-tree HEAD를 통해서 확인 가능
      
    - git update-index --chmod=+x {filename}을 통해서 변경 가능 

    - git clone & pull, test 실행
    
- deploy.sh 생성

  - build 디렉토리에 jar 파일과 nohup.log 생성
 
> ※ java11 사용시 에러 발생
> - java11에는 jaxb를 미포함하고 있기 떄문에 에러 발생
> - 별도 dependency 통해서 사용 가능 
> - compile group: 'javax.xml.bind', name: 'jaxb-api', version: '2.3.1'
  
- spring boot project 빌드

  - .jar 빌드
  
  - nohup.log 생성

</details>

## 6. TravisCI & AWS CodeDeploy로 배포 자동화 구축하기

<details>

<summary>세부내용</summary>

- Travic CI 설정

  - github 계정 연동
  
  - .travis.yml 설정

> ※ gradlew 권한 에러
> - git update-index --chmod=+x gradlew
  
- AWS Code Delpoy 연동

  - AWS CodeDeploy Agent 생성

  > ※ ruby error 발생
  > - /usr/bin/env: ruby: No such file or directory
  > - sudo yum install ruby

  - codedeploy-startup.sh 생성
  
    - /etc/init.d/ - 부팅 시 자동실행
    
  > ※ Travis CI에서 S3로 배포 실패
  > - 접근권한 설정
  > - 새 ACL(액세스 제어 목록)을 통해 부여된 버킷 및 객체에 대한 퍼블릭 액세스 차단 -> 해제

- Travis CI & S3 & CodeDeploy 연동

  - AWS CodeDeploy 생성
  
    - appspec.yml로 설정
    
    - .travis.yml -> codedeploy 설정
    
  - codeDeploy로 shell script 실행
  
    - delploy.sh 생성 (서버 shell)
    
    - execute-deploy.sh 생성 (프로젝트 내부 shell)
    
    - appspec.yml에더 execute-deploy.sh 실행 -> deploy.sh 실행

</details>

## 7. Nginx를 활용한 무중단 배포 구축하기

<details>

<summary>세부내용</summary>

- Nginx로 무중단 배포

  - port: Nginx(80, 443), springboot1(8081), springboot2(8082)

  - 사용자: 서비스 주소로 접속 (port: 80, 443)
  
  - Nginx: 사용자 request -> springboot1(8081)로 전달
  
    - reverse-proxy
    
    - reverse-proxy server는 요청을 전달하고 실제 요청에 대한 처리는 뒷단의 웹 서버들이 처리한다.
  
  - 배포: springboot2(8082)로 배포
  
  - 배포 종료 후 springboot2 확인
  
  - nginx reload를 통해서 8081 -> 8082로 변경
  
  - springboot1과 springboot2를 번갈아가면서 배포 및 서비스 하도록 한다.

  ![ex_screenshot](./img/무중단_배포_전체구조.png)
  
  - /etc/nginx/nginx.conf 수정
  
    - proxy_pass http://localhost:8080;
    
    - proxy_set_header X-Real-IP $remote_addr;
    
    - proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    
    - proxy_set_header Host $http_host;

 - Environment: 프로젝트의 환경설정을 다룸
 
 - service-url.inc 파일 생성
 
 - nonstop 디렉터리 아래 - deploy.sh + switch.sh 생성
 
   - deploy.sh: 배포 후 switch.sh 실행
   
   - switch.sh: 8081 <-> 8082 변경
 
 - exectue-deploy.sh 변경 (travis -> nonstop)

</details>