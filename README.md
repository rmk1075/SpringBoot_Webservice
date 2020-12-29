# 스프링부트로 웹 서비스 구축하기

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


    ※ SpringBoot 2.0에서는 옵션을 추가해야함 (ref: <https://jojoldu.tistory.com/295>)
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
  
※ h2 console 실행 중 "mem:testdb" not found 에러 발생

  application.yml에 'spring:datasource:url: jdbc:h2:mem:testdb' 추가해서 해결

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