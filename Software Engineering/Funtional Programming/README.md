# 함수형 프로그래밍
Funtional Programming
- 기존의 절차지향/객체지향 프로그래밍은 '명령형 프로그래밍'. -> 무엇을 할 것인지 나타내기보다 어떻게 할 것인지를 설명하는 방식
- 함수형 프로그래밍은 '선언형 프로그래밍' -> 어떻게(How) 할 것인지를 나타내기보다 무엇을(What) 할 것인지를 설명하는 방식. *순수 함수* 와 *불변성(Immutable)* 을 강조 
- 애플리케이션의 상태는 순수 함수를 통해 전달된다.
- 명령형 프로그래밍이나 OOP 코드보다 간결하고 예측 가능하다 -> 테스트 용이

> 함수형 프로그래밍은 대입문이 없는 프로그래밍이다 - Rober C.Martin(Clean Code의 저자) -

## 함수형 프로그래밍의 주요 특징
함수형 프로그래밍은 **부수효과가 없는 순수 함수를 1급 객체로 간주하여 파라미터나 반환값으로 사용할 수 있으며, 참조 투명성을 지킬 수 있다.**
### 부수 효과
다음과 같은 변화 혹은 변화가 발생하는 작업
- 변수의 값이 변경
- 자료구조를 제자리에서 수정
- 객체의 필드값 설정
- 예외나 오류가 발생하여 실행이 중단
- 콘솔 또는 파일 I/O 발생
### 순수 함수
위와 같은 부수효과를 제거한 함수. 함수형 프로그래밍에서는 이러한 순수 함수를 사용한다.
- 함수의 결과 예측이 쉬워진다. -> 테스트, 디버깅
- 함수 자체가 독립적이며 Side-Effect가 없다 -> Thread 안전성 보장
- Thread 안전성을 보장받기 때문에 병렬 처리를 동기화 없이 진행할 수 있다.
### 1급 객체
다음과 같은 것들이 가능한 객체
- 변수나 데이터 구조 안에 담을 수 있다.
- 파라미터로 전달 가능
- 반환값으로 사용 가능
- 할당에 사용된 이름과 무관하게 고유한 구별이 가능
### 참조 투명성
- 동일한 인자에 대해 항상 동일한 결과 반환
- 기존의 값은 변경되지 않고 유지된다 (불변성)
### 불변성
- 데이터의 상태를 변경하는 대신 변경된 새로운 데이터를 생성한다.
- 상태 변경으로 인한 버그 발생 가능성 줄일 수 있다.

## Java에서의 함수형 프로그래밍
Java 8 이후로부터 Java에서도 함수형 프로그래밍이 가능해졌다.
- 람다식
- stream api 
    ```java
    import java.util.Arrays;
    import java.util.List;

    public class stream {

        public static void main(String[] args) {
            List<String> myList = Arrays.asList("a", "b", "c", "d", "e");
    
            // 기존방식
            for(int i=0; i<myList.size(); i++){
                String s = myList.get(i);
                if(s.startsWith("c")){
                    System.out.println(s.toUpperCase());
                }
            }
    
            // stream API를 이용한 방식
            myList.stream()
                .filter(s -> s.startsWith("c"))
                .map(String::toUpperCase)
                .forEach(System.out::println);
    
        }

    }
    ```
- 함수형 인터페이스


---
# 참고
[면접-꿀팁-함수형-프로그래밍Functional-Programming이란](https://thecho7.tistory.com/entry/%EB%A9%B4%EC%A0%91-%EA%BF%80%ED%8C%81-%ED%95%A8%EC%88%98%ED%98%95-%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8DFunctional-Programming%EC%9D%B4%EB%9E%80)
[[프로그래밍] 함수형 프로그래밍(Functional Programming) 이란?](https://mangkyu.tistory.com/111)
[Tech Interview - 함수형 프로그래밍](https://gyoogle.dev/blog/computer-science/software-engineering/Fuctional%20Programming.html)