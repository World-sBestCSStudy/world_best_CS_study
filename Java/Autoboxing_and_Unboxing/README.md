## Java의 type
기본 타입과 Wrapper 클래스가 있다.
- 기본: int, long, float, double, boolean ...
- Wrapper 클래스: Integer, Long, Float, Double, Boolean ...

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

## AutoBoxing & AutoUnboxing
JDK 1.5부터 자바컴파일러가 자동으로 박싱 & 언박싱을 자동으로 처리하는 것
```java
// 오토 박싱
int i = 10;
Integer num = i;

// 오토 언박싱
Integer num = new Integer(10);
int i = num;
```

> 하지만 내부적으로 추가 연산이 일어나 성능 차이가 생길 수 있으니까(100만건 기준 약 5배의 성능 차이) 되도록이면 동일 타입의 연산을 하도록 하자.