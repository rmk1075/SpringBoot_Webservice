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
    
    - 기본값은 AUTO (MySQL의 auto_increment 되는 정수형 값)
    
    ※ SpringBoot 2.0에서는 옵션을 추가해야함 (ref: <https://jojoldu.tistory.com/295>)
  
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

  - application.yml에 'spring:datasource:url: jdbc:h2:mem:testdb' 추가해서 해결

- JPA Auditing

  - 생성시간/수정시간 자동화
 
  - @CreatedDate
  
    - Entity가 생성되어 저장될 때 시간을 자동 저장
  
  - @LastModifiedDate
  
    - 조회한 Entity의 값을 변경할 때 시간을 자동 저장

  - @EnableJpaAuditing
  
    - JPA Auditing 활성화

</details>