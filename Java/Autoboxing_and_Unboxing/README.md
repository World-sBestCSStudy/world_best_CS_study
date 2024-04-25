## Java의 기본형 타입과 Wrapper Class
기본형 타입(primitive type)의 데이터를 객체로 다루기 위해 사용하는 것이 Wrapper Class
java.lang 패키지에 포함되어 있음
- 기본형 타입: int, long, float, double, boolean ...
- Wrapper 클래스: Integer, Long, Float, Double, Boolean ...

### 왜 사용하나요?
- 다양한 타입을 필요로하는 경우가 많고, 메서드가 있어 다양한 활용이 가능하기 때문이다.
- 인스턴스를 생성하여 상속 및 재사용이 가능해진다.

### 문자열을 기본형 타입으로 변환하는 방법
- parse + 기본타입명 메서드
- ex) Integer.parseInteger(문자열), Boolean.parseBoolean(문자열) ...

<br>

## Boxing & Unboxing
Boxing: 기본 타입 -> Wrapper 클래스 변환

Unboxing: Wrapper 클래스 -> 기본 타입 변환

```java
// 박싱
int i = 10;
Integer num = new Integer(i);

// 언박싱
Integer num = new Integer(10);
int i = num.intValue();
```

### 언박싱 메소드
- 객체의 값을 기본형 값으로 변환
- booleanValue(), byteValue(), intValue(), floatValue() ...

<br>

## AutoBoxing & AutoUnboxing
JDK 1.5부터 자바컴파일러가 자동으로 박싱 & 언박싱을 자동으로 처리하는 것

오토박싱을 통해 new 키워드의 사용 없이 자동으로 인스턴스를 생성할 수 있음.

오토언박싱을 통해 언박싱 메소드 없이도 인스턴스에 저장된 값을 바로 참조할 수 있다.

```java
// 오토 박싱
int i = 10;
Integer num = i;

// 오토 언박싱
Integer num = new Integer(10);
int i = num;
```

> 하지만 내부적으로 추가 연산이 일어나 성능 차이가 생길 수 있으니까(100만건 기준 약 5배의 성능 차이) 되도록이면 동일 타입의 연산을 하도록 하자.

<br>

## ❓ 면접질문
**Q. 자바에서 Wrapper Class를 간단히 설명하세요.**
```
A. Wrapper Class는 자바에서 기본 자료형을 객체로 다루기 위해 사용합니다. 이로써 인스턴스를 생성하여 상속 및 재사용이 가능해집니다.
```

<br>

**Q. 오토박싱과 언박싱에 대해 설명하세요.**
```
A. 먼저, 박싱은 기본형을 Wrapper 클래스로, 언박싱은 그 반대로 변환하는 것을 말합니다. 오토박싱과 오토언박싱은 jdk 1.5 이후부터 이를 자동으로 지원하는 기능을 말합니다. 기존에 박싱을 위해 new를 사용하고 언박싱을 위해 변환 메소드를 사용했는데, 이것들의 사용없이도 인스턴스의 생성 및 참조를 가능하게 합니다.
```