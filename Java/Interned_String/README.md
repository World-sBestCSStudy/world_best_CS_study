#  Interned String in Java
-  자바에서 문자열을 관리하는 방식 중 하나로, 문자열 상수 풀에 있는 문자열을 참조하여 메모리를 효율적으로 관리하고 문자열 비교를 빠르게 수행할 수 있도록 한다.

# Constant Pool(상수 풀)
- 클래스 파일 내부에 있는 특별한 메모리 영역으로 이 영역에는 클래스 파일에 포함된 상수들이 저장되어 있다.
- 상수 풀은 JVM(Java Virtual Machine)이 클래스를 로드할 때 생성되며, 클래스 파일의 각 상수에 대한 정보를 보유하고 있음.
- 문자열 상수(String Constants): 클래스 파일에 포함된 문자열 리터럴들을 저장.
- 타입 및 필드 상수(Type and Field Constants): 클래스, 인터페이스, 필드, 메서드 등의 정보를 나타내는 상수들도 저장.
- 상수 풀은 중복된 상수를 허용하지 않음. 즉, 동일한 문자열 리터럴이나 상수가 중복으로 존재하는 경우, 중복을 제거하고 한 번만 저장되어 메모리를 효율적으로 사용하도록 도와줌.

### 예시
```
public class InternedStringExample {
    public static void main(String[] args) {
        String str1 = "Hello";
        String str2 = "Hello";
        String str3 = new String("Hello").intern();
        
        System.out.println("str1 == str2: " + (str1 == str2)); // true
        System.out.println("str1 == str3: " + (str1 == str3)); // true
        System.out.println("str2 == str3: " + (str2 == str3)); // true
    }
}
```
1. str1과 str2는 모두 "Hello"라는 문자열 리터럴을 가리키고 있기 때문에 동일한 상수 풀의 문자열을 참조한다.
2. str3는 new String("Hello").intern()을 통해 "Hello"를 생성하고, intern() 메서드를 호출하여 해당 문자열을 상수 풀에 등록하여 "Hello" 문자열의 상수 풀 버전을 참조함.

### 면접 질문
- **String을 new()로 생성하는 것과 literal로 생성하는 것의 차이점은?** <br>
String을 new() 연산자로 생성하게 되면 String Constant Pool에 들어가지 않습니다. <br>
반면, literal로 생성하게 되면 String pool에 들어가게 되어 같은 문자열이 존재하는 경우 중복을 제거하고 String Constant Pool의 해당 문자열을 참조합니다.


- **String은 왜 문자열이 일치하면 == 연산의 결과값이 true로 나올까요?** <br>
String을 생성하는 방식에는 new()로 생성하는 방식과 literal로 생성하는 방식 2가지가 있습니다. <br>
이 중 literal로 생성하는 방식의 경우 JVM의 String Constant Pool을 이용하여 같은 문자열의 경우 같은 주소를 참조하도록 해시테이블을 이용하여 처리하기 때문에 <br>
같은 문자열의 경우 == 연산의 값이 true로 나오게 됩니다.
