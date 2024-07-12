
## 영속성 컨텍스트
엔티티를 영구 저장하는 환경으로 논리적인 개념  
엔티티 매니저를 통해서 영속성 컨텍스트에 접근 가능 → ```EntityManager.persist(entity);```

<br>

## 엔티티의 생명주기
<image src="./assets/image3.png" width=80%/> 

<br>

#### - 비영속 (New/Transient): 영속성 컨텍스트와 전혀 관계없는 새로운 상태
<image src="./assets/image4.png" width=50%/>  

```java
//객체를 생성한 상태(비영속)
Member member = new Member();
member.setId("member1");
member.setUsername("회원1");
```

#### - 영속 (Managed): 영속성 컨텍스트에 관리되는 상태
<image src="./assets/image5.png" width=30%/>  

```java
EntityManager em = emf.createEntityManager();
em.getTransaction().begin();

//객체를 저장한 상태(영속)
em.persist(member);
```
#### - 준영속(Detached): 영속성 컨텍스트에 저장되었다 분리된 상태
- 영속성 컨텍스트가 제공하는 기능을 사용할 수 X
```java
em.detach(entity)  // 특정 엔티티만 준영속 상태로 전환
em.clear()  // 영속성 컨텍스트를 완전히 초기화
em.close()  // 영속성 컨텍스트를 종료
```
#### - 삭제 (Removed): 삭제된 상태
```java
em.remove(member);
```

<br>

## 변경 감지 (Dirty Check)

변경 감지(Dirty Checking)은 영속 엔티티의 변경 사항을 감지하고, 그 변경 사항을 DB에 전달하기 위한 쿼리를 생성하는 기능

```
💡 일반적으로 생각하기에는 엔티티 값을 바꾸고 나면, 값을 저장하는 명령어를 이용하여 변경된 내용을 update 하라고 명령어를 보내야 할 것 같은데 JPA는 알아서 update query를 생성하고 발송한다. 왜 그럴까? Dirty Checking!
```

영속 엔티티에 변경 사항이 있을 경우,  
자동으로 1차 캐시에 저장된 해당 영속 엔티티의 스냅샷(기존 상태)과 현재 영속 엔티티를 비교하여  
변경 사항을 DB에 전달하기 위한 쿼리를 생성하여 쓰기 지연 SQL 저장소에 저장한다. 

<br>

<image src="./assets/image1.png" width=80%/> 

→ 즉, commit 되는 상태에 JPA가 1차 캐시에서 Entity와 스냅샷의 값을 비교하고,  
값이 다르다면 update query를 쓰기 저장소에 저장하고 commit 되는 시점에 db에 반영한다.

 

<br>

## 플러시 (Flush)
- 영속성 컨텍스트의 변경 내용을 DB에 반영하는 것
- 영속성 컨텍스트를 비우는 것이 X
- 영속성 컨텍스트의 변경내용을 데이터베이스에 동기화
- 트랜잭션이라는 작업 단위가 중요 -> 커밋 직전에만 동기화하면 됨

<br>

### 플러시 발생 단계
    1. 변경 감지
    2. 수정된 엔티티 쓰기 지연 SQL 저장소에 등록
    3. 쓰기 지연 SQL 저장소의 쿼리를 DB에 전송(등록,수정,삭제 쿼리)

### 플러시 방법
1. 직접 호출
    ```java
    em.flush()
    ```
    
2. 플러시 자동 호출
    - 트랜잭션 커밋
    - JPQL 쿼리 실행
        
        ```java
        em.persist(memberA);
        em.persist(memberB);
        em.persist(memberC);
        
        //중간에 JPQL 실행
        query = em.createQuery("select m from Member m", Member.class);
        List<Member> members= query.getResultList();
        ```

### 플러시 모드
```java
em.setFlushMode(FlushModeType.COMMIT)

// 옵션
// FlushModeType.AUTO: 커밋이나 쿼리를 실행할 때 플러시 (기본값)
// FlushModeType.COMMIT: 커밋할 때만 플러시
```
<br>    

