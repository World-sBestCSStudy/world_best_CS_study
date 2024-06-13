# 정규화(Normalization)

데이터베이스 설계에서 데이터 중복을 줄이고, 데이터 무결성을 향상시키기 위한 과정

DB 저장 용량 또한 효율적으로 관리할 수 있다.

### **목적**

- 데이터의 중복을 없애면서 불필요한 데이터를 최소화시킨다.
- 무결성을 지키고, 이상 현상을 방지한다.
- 테이블 구성을 논리적이고 직관적으로 할 수 있다.
- 데이터베이스 구조를 확장에 용이해진다.

정규화에는 여러가지 단계가 있지만, 대체적으로 1~3단계 정규화까지의 과정을 거친다.

### 제 1정규화(1NF)

- **목표**: 테이블의 모든 컬럼이 원자값(단일 값)을 갖도록 보장합니다.
- **조건**:
  - 각 컬럼이 하나의 값을 가져야 합니다.
  - 반복되는 그룹이 없어야 합니다.
  - 기본키를 사용하여 각 데이터 집합을 고유하게 식별할 수 있어야 합니다.

**예시**:
현재 테이블은 전화번호 컬럼에 여러 개의 전화번호를 포함하고 있다면, 이는 원자값을 갖지 않는 것입니다. 따라서, 이를 1NF로 정규화하려면 전화번호를 별도의 테이블로 분리합니다.

**1NF로 분리 전**:

| Name | Phone Numbers      |
| ---- | ------------------ |
| John | 123-4567, 234-5678 |

**1NF로 분리 후**:

| Name | Phone Number |
| ---- | ------------ |
| John | 123-4567     |
| John | 234-5678     |

### 제 2정규화(2NF)

- **목표**: 모든 컬럼이 완전 함수적 종속을 만족하도록 합니다.
- **조건**:
  - 1NF를 만족해야 합니다.
  - 기본키의 일부가 아닌 컬럼은 기본키 전체에 의존해야 합니다.
  - 즉, 기본키의 부분 집합에 의존하는 컬럼이 없어야 합니다.

**예시**:
한 테이블에서 기본키가 복합키로 구성되어 있을 때, 그 중 하나의 키만으로 다른 컬럼을 결정할 수 있으면 안됩니다.

**2NF로 분리 전**:

| Manufacturer | Model  | Manufacturer Country |
| ------------ | ------ | -------------------- |
| Ford         | Fiesta | USA                  |
| Toyota       | Camry  | Japan                |

여기서 `Model`과 `Manufacturer Country`는 서로 관련이 없습니다. `Manufacturer`와 `Model`이 기본키일 때, `Manufacturer Country`는 `Manufacturer`에 종속되어 있습니다.

**2NF로 분리 후**:

- 모델 테이블:
  | Manufacturer | Model  |
  | ------------ | ------ |
  | Ford         | Fiesta |
  | Toyota       | Camry  |
- 제조사 테이블:
  | Manufacturer | Manufacturer Country |
  | ------------ | -------------------- |
  | Ford         | USA                  |
  | Toyota       | Japan                |

### 제 3정규화(3NF)

- **목표**: 이행적 종속을 없애기 위해 테이블을 분리합니다.
- **조건**:
  - 2NF를 만족해야 합니다.
  - 기본키가 아닌 속성들은 기본키에만 의존해야 합니다.

**예시**:
현재 테이블에서 기본키가 아닌 컬럼이 다른 기본키가 아닌 컬럼에 종속될 경우, 이를 분리합니다.

**3NF로 분리 전**:

| Tournament | Year | Winner | Winner Date of Birth |
| ---------- | ---- | ------ | -------------------- |
| Wimbledon  | 2020 | John   | 1990-01-01           |
| US Open    | 2020 | Jane   | 1992-02-02           |

여기서 `Winner Date of Birth`는 `Winner`에 의해 결정됩니다.

**3NF로 분리 후**:

- 경기 테이블:
  | Tournament | Year | Winner |
  | ---------- | ---- | ------ |
  | Wimbledon  | 2020 | John   |
  | US Open    | 2020 | Jane   |
- 승자 테이블:
  | Winner | Winner Date of Birth |
  | ------ | -------------------- |
  | John   | 1990-01-01           |
  | Jane   | 1992-02-02           |

### 제 4정규화(4NF)

- **목표**: 다치 종속(Multivalued Dependency)을 제거합니다.
- **조건**:
  - 3NF를 만족해야 합니다.
  - 하나의 속성 집합이 다른 속성 집합을 독립적으로 여러 값 가질 수 없는 경우를 방지합니다.

**예시**:
고객이 여러 주소와 여러 전화번호를 가질 수 있는 상황에서 이를 하나의 테이블에 저장하면 다치 종속이 발생합니다.

**4NF로 분리 전**:

| Customer | Address     | Phone Number |
| -------- | ----------- | ------------ |
| John     | 123 Main St | 123-4567     |
| John     | 456 Oak St  | 234-5678     |

**4NF로 분리 후**:

- 주소 테이블:
  | Customer | Address     |
  | -------- | ----------- |
  | John     | 123 Main St |
  | John     | 456 Oak St  |
- 전화번호 테이블:
  | Customer | Phone Number |
  | -------- | ------------ |
  | John     | 123-4567     |
  | John     | 234-5678     |

### 제 5정규화(5NF)

- **목표**: 조인 종속성(Join Dependency)을 제거합니다.
- **조건**:
  - 4NF를 만족해야 합니다.
  - 어떤 릴레이션이 특정 형태의 정보를 표현하기 위해 두 개 이상의 관계로 분해할 수 없는 경우를 방지합니다.

**예시**:
다중 관계 종속성을 가진 데이터를 분리합니다.

**5NF로 분리 전**:

| Student | Course  | Instructor  |
| ------- | ------- | ----------- |
| Alice   | Math    | Prof. Smith |
| Bob     | Science | Prof. Jones |

**5NF로 분리 후**:

- 학생-코스 테이블:
  | Student | Course  |
  | ------- | ------- |
  | Alice   | Math    |
  | Bob     | Science |
- 코스-강사 테이블:
  | Course  | Instructor  |
  | ------- | ----------- |
  | Math    | Prof. Smith |
  | Science | Prof. Jones |

### BCNF (Boyce-Codd Normal Form)

- **목표**: 결정자 이행 종속을 제거합니다.
- **조건**:
  - 3NF를 만족해야 합니다.
  - 모든 결정자가 후보키여야 합니다.

**예시**:
테이블에서 부분 함수 종속이나 이행 종속이 없는 상황을 만듭니다.

**BCNF로 분리 전**:

| Professor | Department | Office |
| --------- | ---------- | ------ |
| Smith     | Math       | 101    |
| Jones     | Science    | 102    |

여기서 `Department`가 `Office`를 결정하지만, `Professor`가 유일한 후보키가 아니므로 BCNF에 맞지 않습니다.

**BCNF로 분리 후**:

- 교수-부서 테이블:
  | Professor | Department |
  | --------- | ---------- |
  | Smith     | Math       |
  | Jones     | Science    |
- 부서-사무실 테이블:
  | Department | Office |
  | --- | --- |
  | Math | 101 |
  | Science | 102 |