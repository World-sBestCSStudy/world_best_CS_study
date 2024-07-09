# Bean

- 스프링에서 사용하는 POJO 기반 객체
- 상황과 필요에 따라 Bean을 사용할 때 하나만 만들어야 할 수도 있고, 여러개가 필요할 때도 있고, 어떤 한 시점에서만 사용해야할 때가 있을 수 있는데,  <br>
이럴 때 Scope를 설정해서 Bean의 사용 범위를 개발자가 설정할 수 있다.

## POJO(Plain Old Java Object)
- 객체 지향적인 원리에 충실하면서 환경과 기술에 종속되지 않고, 필요에 따라 재활용될 수 있는 방식으로 설계된 오브젝트를 의미
1. Java나 Java의 스펙에 정의된 것 이외에는 다른 기술이나 규약에 얽매이지 않아야 한다.
ex)
```
getter와 setter만 가지고 있는 예제 코드

public class User {
    private String userName;
    private String id;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
```

```
Struts라는 기술을 사용하기 위해 ActionForm 클래스를 상속받고 있는 특정 기술에 종속적인 예제

public class MessageForm extends ActionForm {
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

public class MessageAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception {

        MessageForm messageForm = (MessageForm) form;
        messageForm .setMessage("Hello World");

        return mapping.findForward("success");
    }
}
```
2. 특정 환경에 종속적이지 않아야 한다.  <br>

=> 이러한 방식으로 기술을 상속받아 코드를 작성하게 되면, 애플리케이션의 요구사항이 변경되어 다른 기술로 변경해야 할 때 기술을 명시적으로 사용했던 부분들을 모두 제거하여 수정해야 한다.  <br>
   또한, Java는 다중 상속을 지원하지 않기 때문에 extends 키워드를 통해 상속을 받게 되면 상위 클래스를 상속받아 하위 클래스를 확장하는 객체지향 설계 기법을 적용하기 어려워진다.

### POJO 프로그래밍이 필요한 이유
1. 특정 환경이나 기술에 종속적이지 않으면 재사용이 가능하고, 확장 가능한 유연한 코드를 작성할 수 있다.
2. 저수준 레벨의 기술과 환경에 종속적인 코드를 제거하여 코드를 간결해지며 디버깅하기에도 상대적으로 쉬워진다.
3. 특정 기술이나 환경에 종속적이지 않기 때문에 테스트가 단순해진다.
4. 객체지향적인 설계를 제한 없이 적용할 수 있다. (가장 중요한 이유)

### POJO와 Spring의 관계
- Spring은 POJO 프로그래밍을 지향하는 프레임워크
- 최대한 다른 환경이나 기술에 종속적이지 않도록 하기 위한 POJO 프로그래밍 코드를 작성하기 위해 Spring 프레임워크에서는 IoC/DI, AOP, PSA를 지원하고 있다.

### POJO와 Bean의 차이 
|                                           | POJO                                        | Java Bean                                   |
|--------------------------------------------|---------------------------------------------|---------------------------------------------|
| Java 언어에 의한 특별한 제약 사항             | 제한 사항 없음                               | 제약 사항이 더 있음                           |
| 필드에 대한 제어                              | 제어하지 않음                                | 필드에 대한 제어가 있음                       |
| Serializable 인터페이스 구현 가능 여부       | Serializable 인터페이스 구현 가능              | Serializable 인터페이스 반드시 구현해야 함     |
| 필드 접근 방식                                | 이름으로 접근 가능                             | getter와 setter로만 접근 가능                  |
| 필드의 접근 제어자 규칙                       | 자유로운 접근 제어자 규칙                       | 접근 제어자는 반드시 private로만 지정해야 함    |
| 생성자 인자                                   | 생성자에서 인자를 가질 수 있음                  | 생성자에서 인자를 가질 수 없음                 |
| 멤버와 필드에 대한 제약 사항 설정 여부       | 제한된 접근 규칙을 설정하지 않을 때 사용됨     | 제한된 접근 규칙을 설정하고자 할 때 사용됨      |

=> Bean은 무조건 POJO 이지만 POJO는 Bean이 아닐 수 있음

## Spring에서 Bean을 사용하는 이유
1. 의존성 주입(Dependency Injection) :  <br>
Spring은 의존성 주입을 통해 객체 간의 의존성을 관리함.  <br>
이를 통해 객체들이 직접 다른 객체를 생성하거나 찾지 않고, 필요한 의존성을 Spring 컨테이너가 주입하고, 이를 통해 코드의 결합도를 낮추고, 테스트 용이성을 높이며, 애플리케이션의 유연성을 증대시킨다.


2. IoC(Inversion of Control) :  <br>
Spring 컨테이너가 객체의 생명 주기를 관리.  <br>
즉, 객체의 생성, 초기화, 소멸 등의 과정을 개발자가 아닌 Spring이 Bean을 활용해 관리하고, 이를 통해 애플리케이션의 복잡도가 줄어들고 관리가 용이해짐.


3. 코드 재사용 및 관리:  <br>
Bean 정의는 XML 파일이나 애노테이션을 통해 이루어지며, 이를 통해 객체들을 재사용할 수 있음.  <br>
또한, Spring 컨테이너는 싱글톤 패턴을 기본으로 사용하므로, 동일한 Bean을 여러 곳에서 사용할 수 있게 하여 메모리 사용을 효율적으로 함.

## Bean Scope
- Spring 컨테이너가 Bean의 생명 주기 및 가용성을 어떻게 관리할 것인지를 정의.
- Spring은 여러 가지 Bean Scope를 제공하며, 각 Scope는 Bean이 생성되고 관리되는 방식을 결정함.

### 종류
1. Singleton: 애플리케이션 전체에서 하나의 인스턴스.  <br>
- 특징: Spring 컨테이너당 하나의 인스턴스만 존재.  <br>
     기본 Scope로, 별도로 지정하지 않으면 이 Scope가 적용됨  <br>
     인스턴스는 처음 요청될 때 생성되며, 이후에는 동일한 인스턴스를 반환함  <br>
- 사용 예: 주로 상태를 공유하지 않거나 애플리케이션 전역에서 공유되어야 하는 객체에 사용됨.

2. Prototype: 요청 시마다 새로운 인스턴스.
- 특징: 각 요청마다 새로운 인스턴스를 반환.  <br>
       Spring 컨테이너가 객체의 초기화와 의존성 주입만 관리하고, 이후의 생명 주기를 관리하지 않음.  <br>
       자주 사용될 경우 성능 이슈가 있을 수 있음.  <br>
- 사용 예: 상태를 가지며, 매번 새로운 인스턴스가 필요한 경우에 사용됨.
3. Request: HTTP 요청마다 하나의 인스턴스.
- 특징: 주로 웹 애플리케이션에서 사용되며, 각 HTTP 요청에 대해 하나의 인스턴스를 생성하고 요청이 끝나면 소멸함.  <br>
       Spring MVC 환경에서 사용됨.  <br>
- 사용 예: HTTP 요청 동안만 존재하는 데이터를 관리하는 객체에 사용됨.
4. Session: HTTP 세션마다 하나의 인스턴스.
- 특징: 각 사용자 세션에 대해 하나의 인스턴스를 생성하고, 세션이 유지되는 동안 인스턴스도 유지됨.  <br>
       웹 애플리케이션에서 사용자별 상태를 관리할 때 사용됨  <br>
- 사용 예: 사용자 세션 동안 유지되어야 하는 상태를 가지는 객체에 사용됨.
   5. Global Session: 포털 애플리케이션에서 글로벌 HTTP 세션마다 하나의 인스턴스.
   6. Application: 서블릿 컨텍스트마다 하나의 인스턴스.

### Scope 설정 예시
```
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
 
@Scope("prototype")
@Component
public class UserController {
}
```

