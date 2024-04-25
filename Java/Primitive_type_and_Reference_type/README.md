## Primitive type (기본형)
자바에서는 총 8가지의 기본형을 제공함.

- 반드시 사용하기 전에 선언되어야 한다.
- 비객체 타입이기 때문에 null 값을 가질 수 없다. 만약 기본형에 null을 사용하고 싶다면 Wrapper class를 활용하자.
- 스택에 저장

> boolean, byte, char, short, int, long, float, double

## Reference type (참조형)
자바에서 기본형을 제외한 나머지
- Java에서 최상위 클래스인 java.lang.Object 클래스를 상속하는 모든 클래스
- 힙에 생성
- GC가 주기적으로 메모리를 해제한다.
- null 가능
- 런타임 에러 발생가능 (ex. NullPointException)

> 클래스 타입, 인터페이스 타입, 배열 타입, 열거 타입

### String Class
참조형에 속하지만 기본형처럼 사용.
불변하는 객체로 값의 변경이 일어난다 하더라도 새로운 String 클래스를 만드는 것이지 String 클래스의 원본이 바뀌는 것은 아니다. 기본형 비교에 사용되는 == 연산자가 아닌, .equals()로 비교를 한다.