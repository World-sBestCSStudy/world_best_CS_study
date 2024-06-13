## 저장 프로시저 (Stored Procedure)
일련의 쿼리를 마치 하나의 함수처럼 실행하기 위한 쿼리의 집합

즉, **특정 로직의 쿼리를 함수로 만들어 놓은 것**  

    ➕ 저장프로시저와 함수의 차이
    - 저장 프로시저: 일련의 작업을 정리한 절차, 리턴값이 없거나 많을 수도 있음, 서버에서 실행되기 때문에 속도가 빠르다.
    - 함수: 여러 작업을 위한 기능, 리턴값이 필수, 클라이언트에서 실행되기 때문에 프로시저보다 느리다.

- Oracle, MySQL 등 대부분의 DBMS 에서 제공하는 기능

<br>

### 언제 사용할까?
원하는 결과물을 얻기 위해 사용할 여러줄의 쿼리문을 한 번의 요청으로 실행하면 좋지 않을까? 또한, 인자 값만 상황에 따라 바뀌고 동일한 로직의 복잡한 쿼리문을 필요할 때마다 작성한다면 비효율적이지 않을까?

이럴 때 사용할 수 있는 것이 바로 프로시저다.

프로시저를 만들어두면, 애플리케이션에서 여러 상황에 따라 해당 쿼리문이 필요할 때 인자 값만 전달하여 쉽게 원하는 결과물을 받아낼 수 있다.  

<br>

## 장점
**최적화 & 캐시로 SQL 서버 성능 향상**
- 프로시저의 최초 실행 시 구문 분석 및 코드 변환을 미리 진행하여 최적화 상태로 컴파일이 되며, 그 이후 **프로시저 캐시**에 저장된다. 해당 프로세스를 여러번 사용할 경우, 캐시에 있는 것을 가져와 사용하여 **속도가 빨라진다.**
- 하나의 프로시저로 여러개 쿼리를 한 번에 실행할 수 있다. → **모듈화**
- 쿼리를 쓸 때마다 옵티마이저가 구문을 분석하고 실행가능한 코드로 바꾸는 데 많은 비용이 드는데, 이 비용을 없앨 수 있다.

**유지보수 및 재활용이 용이**
- 응용 프로그램에서 직접 SQL을 호출하는 것이 아닌 SP를 호출하도록 설정하여 사용하는 경우가 많다. 그래서 SP 파일만 수정하면 되기 때문에 유지보수에 유리하다.

**보안 강화**
- 사용자 별로 테이블에 직접 접근 권한을 주지 않고, SP에만 접근 권한을 줘서 보안을 강화할 수 있다.

**네트워크 부하/트래픽 감소**
- 클라이언트에서 서버로 쿼리의 모든 텍스트가 전송될 경우 네트워크에서는 큰 부하가 발생한다.
- 저장 프로시저를 사용하면 저장 프로시저의 이름, 매개변수 등 몇 글자만 전송하기 때문에 부하를 줄일 수 있다. 즉, SQL문이 서버에 이미 저장되어 있기 때문에 클라이언트와 서버 간 네트워크 상 트래픽이 감소된다.

<br>

## 단점

**호환성**
- 재사용성이 낮다. (DB 버전 별 구문 규칙과 호환성이 다름)
- 구문 규칙이 SQL / PSM 표준과의 호환성이 낮기 때문에 코드 자산으로의 재사용성이 나쁘다.

**DB 확장 어려움**
- 서버의 수를 늘려야할 때, DB의 수를 늘리는 것이 더 어렵다.
- DB 교체는 거의 불가능하다.

**디버깅 / 데이터 분석의 어려움**
- 개발된 프로시저가 여러 곳에서 사용 될 경우 수정했을 때 영향의 분석이 어렵다(별도의 Description 사용).
- 배포, 버전 관리 등에 대한 이력 관리가 힘들다.
- APP에서 SP를 호출하여 사용하는 경우 문제가 생겨도 해당 이슈에 대한 추적이 힘들다(별도의 에러 테이블 사용).

**낮은 처리 성능**
- 문자, 숫자열 연산에 SP를 사용하면 오히려 c, java보다 느린 성능을 보일 수 있다.

<br>

## 문법 & 예시 (MYSQL)
### 프로시저 정의
```sql
DELIMITER $$
CREATE PROCEDURE procedure_name (
    -- 파라미터 선언
    -- 변수명 변수타입
    PARAM_NAME VARCHAR(20),
    PARAM_AGE INT
)
BEGIN
    -- 변수 선언
    DECLARE PARAM_NUM INTEGER;
    
    -- 쿼리문1
    -- SELECT 사용 시 반드시 조회한 컬럼(데이터)을 INTO로 변수 안에 넣어주어야 함.
    SELECT COUNT(*) + 1
    	INTO PARAM_NUM
        FROM table1;
        
    -- 쿼리문2
    INSERT INTO table1(total_count, user_name, user_age) VALUES(PARAM_NUM, PARAM_NAME, PARAM_AGE);
END $$
DELIMITER ;
```
- 파라미터는 프로시저명() 안에서 선언
- SQL문과 변수 선언은 BEGIN ~ END 사이에 작성
- 프로시저 내부의 SQL문은 일반 SQL문이기 때문에 세미콜론(;)으로 끝남
- DELIMITER : 프로시저 작성이 완료되지 않았음에도 SQL문이 실행되는 것을 막기위해 사용

### 호출
```sql
-- CALL 프로시저명(파라미터); 로 함수처럼 사용 가능
CALL procedure_name('테스트이름', 21);
```
- 파라미터는 프로시저에 선언한 순서대로 입력

### IN, OUT, INOUT 사용한 정의
```sql
DELIMITER $$
CREATE PROCEDURE 'TEST_PROC2'(
    IN loopCount1 INT,     -- input : 10
    IN loopCount2 INT,     -- input : 20
    OUT rst1 INT,
    OUT rst2 INT,
    INOUT rst3 INT
)
BEGIN
    DECLARE NUM1 INTEGER DEFAULT 0;    -- DEFAULT : 초기값 설정
    DECLARE NUM2 INTEGER DEFAULT 0;
    DECLARE NUM3 INTEGER DEFAULT 0;
    
    WHILE NUM1<loopCount1 DO           -- NUM1은 0~9까지 10번반복
        WHILE NUM2<loopCount2 DO       -- NUM2는 0~19까지 20번반복
            SET NUM3 = NUM3 + 1;
            SET NUM2 = NUM2 + 1;
        END WHILE;                     -- NUM2가 19가 되면 나옴
        
        SET NUM1 = NUM1 + 1;
        SET NUM2 = 0;
    END WHILE;
    
    SET rst1 = NUM1;
    SET rst2 = NUM3;
    SET rst3 = rst1 + rst2 + rst3;
END $$
DELIMITER ;
```
- IN  
프로시저에 값을 전달하며, 프로시저 내부에서 값을 수정할 수는 있지만 프로시저가 반환되고 나서 호출자가 수정은 불가능  
즉 원본 값은 프로시저가 끝난 후에도 유지되며, 프로시저는 IN 파라미터의 복사본을 사용한다.
- OUT  
프로시저의 값을 호출자에게 다시 Return  
초기값은 프로시저 내에서 NULL이며 프로시저가 반환될 때 새로운 값이 호출자에게 Return되고 프로그램이 시작될 때, OUT 파라미터의 초기값에 접근할 수 없다.
- INOUT  
IN+OUT  
호출자에 의해 하나의 변수가 초기화되고 프로시저에 의해 수정된다.  
프로시저가 Return될 때 프로시저가 변경한 사항은 호출자에게 Return된다.

### 호출
```sql
-- 변수 초기화
DECLARE @NUM1 = 0;
DECLARE @NUM2 = 0;
DECLARE @NUM3 = 0;

-- NUM3에 값 30 할당
SET @NUM3 = 30;
-- (10(IN), 20(IN), Return받을 변수(OUT), Return받을 변수(OUT), Return도 받고 값도 가지고 있는 변수(INOUT))
CALL TEST_PROC2(10, 20, @NUM1, @NUM2, @NUM3);

SELECT @NUM1, @NUM2, @NUM3;
-- RESULT => @NUM1 : 10, @NUM2 : 200, @NUM3 : 240
```
- @는 전역변수, 프로시저가 끝나도 계속 유지되는 값)

### 기타
```sql
-- 프로시저 목록 확인
SHOW PROCEDURE STATUS;

-- 프로시저 내용 확인
SHOW CREATE PROCEDURE 프로시저이름;

-- 프로시저 삭제
DROP PROCEDURE 프로시저이름;
```
<br>

<br>
<br>
<br>

참고: https://eunsun-zizone-zzang.tistory.com/52, https://devkingdom.tistory.com/323