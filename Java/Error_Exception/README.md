# Error (에러)

- 시스템 레벨에서 발생하여, 개발자가 어떻게 조치할 수 없는 수준을 의미한다.  <br>
  ex) <br>
  StackOverflowError : 호출의 깊이가 깊어지거나 재귀가 지속되어 stack overflow 발생 시 발생하는 오류이다.  <br>
  OutOfMemoryError : JVM이 할당한 메모리의 부족으로 더 이상 객체를 할당할 수 없을 때 발생하는 오류이다.
  이는 Garbage Collector에 의해 추가적인 메모리가 확보되지 못하는 상황이기도 하다.

# Exception (예외)
- 예외는 개발자가 구현한 로직에서 발생하며 개발자가 다른 방식으로 처리가능한 것들로 JVM은 문제 없이 정상 동작한다.  <br>
 ex) <br>
  NullPointerException : 객체가 필요한 경우에 null을 사용하려고 시도할 경우 발생하는 예외이다.  <br>
  IllegalArgumentException : 메서드가 허가되지 않거나 부적절한 argument를 받았을 때 발생하는 예외이다.

## Exception의 2가지 분류
### Checked Exception
- 예외처리가 필수이며, 처리하지 않으면 컴파일되지 않음
- JVM 외부와 통신(네트워크, 파일시스템 등)할 때 주로 쓰인다.
- RuntimeException 이외에 있는 모든 예외  
- 예외 발생 시 롤백(rollback)하지 않음.  <br>
ex) IOException, SQLException 등

### Unchecked Exception 
- 컴파일 때 체크되지 않고, Runtime에 발생하는 Exception을 말합니다. 
- RuntimeException 하위의 모든 예외  
- 예외 발생 시 롤백(rollback)해야 함  <br>
ex) NullPointerException, IndexOutOfBoundException 등

### 면접 질문
- **Error와 Exception의 차이를 설명해주세요..** <br>
Error는 시스템 레벨에서 발생하여, 개발자가 어떻게 조치할 수 없는 치명적인 오류입니다.  <br>
반면에 Exception은 개발자가 구현한 로직에서 발생하며 개발자가 다른 방식으로 처리가능한 오류입니다.

- **CheckedException과 UnCheckedException의 차이와 RuntimeException에 대해 설명해주세요.** <br>
CheckedException은 실행하기 전에 예측 가능한 예외를 말하고, 반드시 예외 처리를 해야 합니다.
UncheckedException은 실행하고 난 후에 알 수 있는 예외를 말하고, 따로 예외처리를 하지 않아도 됩니다.
RuntimeException은 UncheckedException을 상속한 클래스이고, RuntimeException이 아닌 것은 CheckedException을 상속한 클래스 입니다.
