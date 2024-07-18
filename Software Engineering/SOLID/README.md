# SOLID 원칙

객체지향 소프트웨어 개발의 다섯 가지 설계 원칙

- SW의 재사용성, 유연성, 확장성을 높인다.
- 코드의 유지보수성과 확장성을 높인다.

<br>

## 1. 단일책임 원칙(Single Responsibility Principle, SRP)

**클래스는 오직 하나에 대해서만 책임**져야 한다.

> 행동을 분리하여, 기능 수정 시 연관이 없는 기능에는 영향이 가지 않도록 하는 것

💡 컴포넌트를 무작정 단일 동작으로 쪼개는 것이 아니라(단일한 동작을 갖는 것은 순수한 함수 한정), **요구사항을 전달하는 책무 단위로 설계**하는 것

### 특징

- 각 영역의 요구사항을 명확히 하고 영역을 잘 구분하여 의존성 없는 독립적인 컴포넌트를 만들어, 각 책무의 변경사항에도 유연하게 대처할 수 있도록 설계하고 구현하는 것이 중요하다.
- 로버트 마틴에 의하면 하나의 책임이라는 모호한 원칙 대신 **모듈(클래스, 클래스의 모음 등)이 변경되는 이유가 한가지여야 함**으로 받아들여야 한다. 여기서 변경의 이유가 한가지라는 것은 **해당 모듈이 여러 대상 또는 액터들에 대해 책임을 가져서는 안되고, 오직 하나의 액터에 대해서만 책임을 져야 한다**는 것을 의미한다.

### 장점

- 각 컴포넌트는 명확하고 한정된 역할을 수행하게 되어 코드의 **가독성**과 **유지보수성**이 향상된다.
- 새로운 기능을 추가하거나 변경할 때 다른 컴포넌트에 영향을 주지 않으므로 코드의 **안정성**과 **확장성**이 높아진다.
- 또한 변경이 필요할 때 **수정할 대상이 명확**해진다.

<br>

<details>
<summary>예시 코드</summary>
<div markdown="1">

> 사용자 입력을 받아, 비밀번호를 암호화하여 DB에 저장하는 로직. 사용자 추가 로직 내에 비밀번호 암호화가 포함되어 있는 문제가 있다.


```java
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

		// 사용자 추가
    public void **addUser**(final String email, final String pw) {
				// 비밀번호 암호화 로직
        final StringBuilder sb = new StringBuilder();

        for(byte b : pw.getBytes(StandardCharsets.UTF_8)) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }

        final String encryptedPassword = sb.toString();
        final User user = User.builder()
            .email(email)
            .pw(encryptedPassword).build();

        userRepository.save(user);
    }
}
```

- 위의 UserService에 사용자 추가 로직으로 다양한 액터(기획팀, 보안팀)로부터 변경(사용자 역할 추가, 비밀번호 암호화 방식 개선 등)이 발생할 수 있다.
- 하지만 위에서는 SRP를 지키지 못하고 있기 때문에 비밀번호 암호화에 대한 책임을 분리해주어야 한다. 암호화를 책임지는 별도의 클래스를 만들어 UserService로부터 이를 추상화하고 해당 클래스를 합성하여 접근 및 사용하면 비밀번호 암호화 방식 개선이라는 변경을 분리할 수 있는 것이다.

> 사용자 추가와 비밀번호 암호화 로직을 분리하여 SRP를 지킬 수 있다. 

```java
@Component
public class **SimplePasswordEncoder** {
		**// 비밀번호 암호화 로직 분리**
    public String **encryptPassword**(final String pw) {
        final StringBuilder sb = new StringBuilder();

        for(byte b : pw.getBytes(StandardCharsets.UTF_8)) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    **private final** **SimplePasswordEncoder** **passwordEncoder**;

    public void **addUser**(final String email, final String pw) {
        final String encryptedPassword = passwordEncoder.encryptPassword(pw);

        final User user = User.builder()
            .email(email)
            .pw(encryptedPassword).build();

        userRepository.save(user);
    }
}
```

💡 SRP를 위반하는지 판단하는 것은 상당히 어려운데, 그 이유는 동일한 대상이라 할지라도 유스케이스나 요구 사항의 단계에 따라 책임이 단일한지 여부가 달라질 수 있기 때문이다. 유스케이스를 변경하거나 요구 사항이 달라질 경우에는 기존에 충족했던 단일 책임 원칙이 충족되지 못할 수 있다.

</div>
</details>
<br>

## 2. 개방-폐쇄 원칙(Open-Closed Principle, OCP)

클래스는 **확장에는 개방적**이어야 하고, **변경에는 폐쇄적**이어야 한다.

> 클래스의 존재하는 기능의 변경 없이 해당 클래스의 기능을 확장시키는 것
 

💡 
**확장에 개방적**: **요구사항이 변경**될 때 **새로운 동작을 추가**하여 애플리케이션의 **기능을 확장**할 수 있다.  
**변경에 폐쇄적**: 기존의 **코드를 수정하지 않고** 애플리케이션의 **동작을 추가하거나 변경할 수 있다.**

### **특징**

- 최대한 데이터의 변화나 서비스 로직의 변경에도 유연하게 대처하기 편한 구조를 갖는 것이 중요하다.
- **개방 폐쇄 원칙을 지키기 위해서는 추상화에 의존**해야 한다.
    - 추상화
        
        추상화란 핵심적인 부분만 남기고, 불필요한 부분은 제거함으로써 복잡한 것을 간단히 하는 것
        
        추상화를 통해 변하지 않는 부분만 남김으로써 기능을 구체화하고 확장할 수 있다. 
        변하지 않는 부분은 고정하고 변하는 부분을 생략하여 추상화함으로써 변경이 필요한 경우에 생략된 부분을 수정하여 개방-폐쇄의 원칙을 지킬 수 있다.
        
        추상화를 통해 변하는 것들은 숨기고 변하지 않는 것들에 의존하게 하면 우리는 기존의 코드 및 클래스들을 수정하지 않은 채로 애플리케이션을 확장할 수 있다.
        
- 과도한 OCP는 오히려 개발에 부하를 줄 수 있다. 확장의 범위와 비용을 생각하여 코드의 확장성과 가독성 사이에서 적절한 균형이 필요하다.

### **장점**

- **기존의 코드 및 클래스들을 수정하지 않은 채로 애플리케이션을 확장**할 수 있다.
- **확장성**이 **코드 품질**의 중요한 척도이기 때문에 가장 유용하다.

<br>

<details>
<summary>예시 코드</summary>
<div markdown="1">

> 비밀번호 암호화를 강화하기 위해 SHA-256 알고리즘을 사용하는 새로운 PasswordEncoder를 생성하였다. 하지만 이를 적용하려고 하니 새로운 암호화 정책과 무관한 UserService를 함께 수정해야하는 문제가 발생하였다.
> 

```java
@Component
public class **SHA256PasswordEncoder** {

    private final static String SHA_256 = "SHA-256";

    public String encryptPassword(final String pw)  {
        final MessageDigest digest;
        try {
            digest = MessageDigest.getInstance(SHA_256);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException();
        }

        final byte[] encodedHash = digest.digest(pw.getBytes(StandardCharsets.UTF_8));

        return bytesToHex(encodedHash);
    }

    private String bytesToHex(final byte[] encodedHash) {
        final StringBuilder hexString = new StringBuilder(2 * encodedHash.length);

        for (final byte hash : encodedHash) {
            final String hex = Integer.toHexString(0xff & hash);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
}

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    **private final SHA256PasswordEncoder passwordEncoder;
    // 비밀번호 암호화 정책과는 무관한 UserService도 수정해야 함**

    ...
    
}
```

- 위의 예제에서 추상화 할 수 있는 것을 보자. 변하지 않는 것은 사용자 추가 시 암호화가 필요하다는 것이고, 변하는 것은 구체적인 암호화 정책이다. 그러므로 UserService는 암호화 정책까지는 알 필요가 없이 passwordEncoder 객체를 통해 암호화된 비밀번호를 받기만 하면된다.

> UserService가 구체적인 암호화 클래스에 의존하지 않고 PasswordEncoder라는 인터페이스에 의존하도록 추상화하면 OCP를 충족하는 코드를 작성할 수 있다.
> 

```java
public **interface** **PasswordEncoder** {
    String **encryptPassword**(final String pw);
}

@Component
public class **SHA256PasswordEncoder implements PasswordEncoder** {

    @Override
    public String **encryptPassword**(final String pw)  {
        ...
    }
}

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void addUser(final String email, final String pw) {
        final String encryptedPassword = passwordEncoder.encryptPassword(pw);

        final User user = User.builder()
            .email(email)
            .pw(encryptedPassword).build();

        userRepository.save(user);
    } 
}
```
</div>
</details>
<br>

## 3. 리스코프 치환 원칙(Liskov Substitution Principle, LSP)
**S가 T의 서브타입**이라면, **T는 어떠한 경고도 내지 않으면서, S로 대체가 가능**하다.

> 일관성을 유지하여 부모 클래스 또는 자식 클래스를 오류 없이 동일한 방식으로 사용할 수 있도록 하는 것
> 

### 특징

- 자식 클래스는 언제나 부모 클래스로 대체될 수 있어야 한다.
- 부모클래스의 인스턴스 대신 자식 클래스의 인스턴스를 사용해도 기능에 문제가 없어야 한다.
- 자식 클래스는 부모 클래스가 할 수 있는 모든 것을 할 수 있어야 하는데(상속), 그러므로 자식 클래스는 부모 클래스처럼 똑같은 요청에 대해 똑같은 응답을 할 수 있어야 하고, 응답의 타입 또한 같아야 한다.
- **하위 타입은 상위 타입을 대체할 수 있어야 한다.** 즉, **해당 객체를 사용하는 클라이언트는 상위 타입이 하위 타입으로 변경되어도, 차이점을 인식하지 못한 채 상위 타입의 퍼블릭 인터페이스를 통해 서브 클래스를 사용할 수 있어야 한다**는 것이다.
- LSP를 따르기 위해서는 상속받은 메소드를 재정의할 때 기존의 동작을 보존하는 것이 중요하다.
- **자식 클래스가 부모 클래스를 대체하기 위해서는 부모 클래스에 대한 클라이언트의 가정을 준수해야 한다.**

### 장점

- 이를 통해 어떤 컴포넌트를 사용하더라도 일관된 동작을 기대할 수 있다.

<br>

<details>
<summary>예시 코드</summary>
<div markdown="1">

> 아래는 정사각형은 직사각형이다.(Square is a Rectangle) 라는 예시를 구현한 코드이다.

```java
@Getter
@Setter
@AllArgsConstructor
public class **Rectangle** {  **// 직사각형**

    private int width, height;

    public int getArea() {
        return width * height;
    }

}

public class **Square** **extends Rectangle** {  **// 정사각형**

    public Square(int size) {
        super(size, size);
    }
	
		// 너비나 높이 중 하나만 설정해도 둘 다 설정되도록 오버라이딩 하였다.
    @Override
    public void setWidth(int width) {
        super.setWidth(width);
        super.setHeight(width);
    }

    @Override
    public void setHeight(int height) {
        super.setWidth(height);
        super.setHeight(height);
    }
}

// 직사각형 resize 메서드
public void resize(Rectangle rectangle, int width, int height) {
    rectangle.setWidth(width);
    rectangle.setHeight(height);
    if (rectangle.getWidth() != width && rectangle.getHeight() != height) {
        throw new IllegalStateException();
    }
}
```

- 여기서 Square는 하나의 변수만을 생성자로 받으면서, width나 height 중 하나만 설정하는 경우에는 둘 다 설정되도록 메소드가 오버라이딩 되어있다. 이를 이용하는 클라이언트는 당연히 직사각형의 너비와 높이가 다르다고 가정할 것이고, 직사각형을 resize()하려는 경우에는 위의 resize() 메소드를 이용할 것이다.
- 문제는 resize()의 파라미터로 정사각형인 Square가 전달되는 경우다. 직사각형 Rectangle은 정사각형 Square의 부모 클래스이므로 Square 역시 전달 가능한데, Square는 너비와 높이가 동일하게 설정되므로 예를 들어 다음과 같은 메소드를 호출하면 문제가 발생한다.

```java
Rectangle rectangle = new Square();
resize(rectangle, 100, 150);
```

- 이는 **클라이언트 관점**에서 부모 클래스와 자식 클래스의 행동이 호환되지 않으므로 LSP를 위반하는 경우이다. LSP가 성립한다는 것은 자식 클래스가 부모 클래스 대신 사용될 수 있어야 하기 때문이다.
- 위의 예시에서 클라이언트는 직사각형의 너비와 높이는 다를 것이라고 가정하는데, 정사각형은 이를 준수하지 못한다. 우리는 여기서 **대체 가능성을 결정해야 하는 것은 해당 객체를 이용하는 클라이언트**임을 잊지 말아야 한다.

</div>
</details>
<br>

## 4. 인터페이스 분리 원칙(Interface Segregation Principle, ISP)

클라이언트는 **사용하지 않는 메서드에 대해 의존적이지 않아야** 한다.

> 액션을 더 작은 액션 집합으로 쪼개어, 클래스가 필요한 액션들만 실행할 수 있도록 하는 것


### 특징

- 자신이 사용하지 않는 인터페이스는 구현하지 말아야 한다.
- **목적과 관심**이 각기 **다른 클라이언트**가 있다면 **인터페이스를 통해** 적절하게 **분리**해주어야 한다.
- **클라이언트의 목적과 용도에 적합한 인터페이스 만을 제공**하는 것이다.
- 여기서 인터페이스는 꼭 하나의 인터페이스 파일이 아닌 **API나 기능의 집합**, **단일 API 또는 기능**, **객체지향 프로그래밍의 인터페이스**까지 **확장**될 수 있다.

### 장점

- **컴포넌트 간의 종속성을 줄이고, 각 컴포넌트를 독립적으로 유지하고 확장하는 데 도움**을 준다.
- 모든 클라이언트가 자신의 관심에 맞는 퍼블릭 인터페이스(외부에서 접근 가능한 메세지)만을 접근하여 **불필요한 간섭을 최소화**할 수 있다.
- **기존 클라이언트에 영향을 주지 않은 채**로 유연하게 **객체의 기능을 확장하거나 수정**할 수 있다.
- ISP를 지킨다는 것은 어떤 구현체에 부가 기능이 필요하다면 이 인터페이스를 구현하는 다른 인터페이스를 만들어서 해결할 수 있다.

<br>

<details>
<summary>예시 코드</summary>
<div markdown="1">

> 사용자가 비밀번호를 변경할 때 입력한 비밀번호가 기존 비밀번호와 같은지 검사하는 로직을 다른 Authentication 로직에 추가한다고 가정하자. 그러면 우리는 다음과 같은 isCorrectPassword라는 퍼블릭 인터페이스를 추가해줄 것이다.


```java
@Component
public class SHA256PasswordEncoder implements PasswordEncoder {

    @Override
    public String encryptPassword(final String pw)  {
        ...
    }

    public String **isCorrectPassword**(final String rawPw, final String pw) {
        final String encryptedPw = encryptPassword(rawPw);
        return encryptedPw.equals(pw);
    }
}
```

- 하지만 UserService에서는 비밀번호 암호화를 위한 encryptPassword() 만을 필요로 하고, 불필요하게 isCorrectPassword를 알 필요가 없다. 현재 UserService는 PasswordEncoder를 주입받아 encrpytPassword에만 접근 가능하므로 인터페이스 분리가 잘 된 것 처럼 보인다. 하지만 새롭게 추가될 Authentication 로직에서는 isCorrectPassword에 접근하기 위해 구체 클래스인 SHA256PasswordEncoder를 주입받아야 하는데 그러면 불필요한 encryptPassword에도 접근 가능해지고, 인터페이스 분리 원칙을 위배하게 된다.
- 물론 PasswordEncoder에 isCorrectPassword 퍼블릭 인터페이스를 추가해줄 수 있지만, 클라이언트의 목적과 용도에 적합한 인터페이스 만을 제공한다는 인터페이스 분리 원칙을 지키기 위해서라도 이미 만든 인터페이스는 건드리지 않는 것이 좋다.
- 그러므로 위의 상황을 해결하기 위해서는 비밀번호 검사를 의미하는 별도의 인터페이스(PasswordChecker)를 만들고 해당 인터페이스로 주입 받도록 하는 것이 적합하다.

```java
public interface **PasswordChecker** {
    String **isCorrectPassword**(final String rawPw, final String pw);
}

@Component
public class SHA256PasswordEncoder **implements PasswordEncoder, PasswordChecker** {

    @Override
    public String encryptPassword(final String pw)  {
        ...
    }

    @Override
    public String isCorrectPassword(final String rawPw, final String pw) {
        final String encryptedPw = encryptPassword(rawPw);
        return encryptedPw.equals(pw);
    }
}
```

</div>
</details>
<br>

## 5. 의존 역전 원칙(Dependency Inversion Principle, DIP)

상위 모듈이 하위 모듈에 **직접 의존하는 것이 아니라**, 둘 다 **추상화에 의존**하도록 설계해야 한다.

> 인터페이스를 통해 상위 클래스가 하위 클래스에 대해 의존성을 가지는 것을 줄이는 것

### 특징

- **고수준 모듈은 저수준 모듈의 구현에 의존해서는 안 되며, 저수준 모듈이 고수준 모듈에 의존해야 한다**는 것이다.
    - 고수준 모듈: 입력과 출력으로부터 먼(비즈니스와 관련된) 추상화된 모듈
    - 저수준 모듈: 입력과 출력으로부터 가까운(HTTP, 데이터베이스, 캐시 등과 관련된) 구현 모듈
- **비즈니스와 관련된 부분이 세부 사항에는 의존하지 않는 설계 원칙**을 의미한다.
- 의존 역전 원칙은 개방 폐쇄 원칙과 밀접한 관련이 있으며, 의존 역전 원칙이 위배되면 개방 폐쇄 원칙 역시 위배될 가능성이 높다.
- 의존 역전 원칙에서 **의존성이 역전되는 시점은 컴파일 시점**이다. 의존 역전 원칙은 **컴파일 시점 또는 소스 코드 단계에서의 의존성이 역전되는 것**을 의미한다.

### 장점

- **모듈 간의 결합도를 줄이고 유연성을 높일 수 있다.**

<br>

<details>
<summary>예시 코드</summary>
<div markdown="1">

> 앞선 개방폐쇄 원칙 예시 코드에서 이미 의존성 역전에 대한 예시를 함꼐 다룬거나 마찬가지이다.

- SimplePasswordEncoder는 암호화를 구체화한 클래스인데, UserService가 SimplePasswordEncoder에 직접 의존하는 것은 DIP에 위배된다. 그러므로 UserService가 변하지 않는 추상화에 의존하도록 변경이 필요하고, 우리는 PasswordEncoder 인터페이스를 만들어서 이에 의존하도록 변경하였다. UserService가 추상화된 PasswordEncoder에 의존하므로 암호화 정책이변경되어도 다른 곳들로 변경이 전파되지 않아 유연한 어플리케이션이 된다.

    ![image](https://github.com/user-attachments/assets/8ba55374-2c6d-4182-bac0-c238fa5459f8)

- 주의해야 할 점은 위에 말했듯, 의존 역전 원칙에서 **의존성이 역전되는 시점은 컴파일 시점**이라는 것이다. 런타임 시점에는 UserService가 SHA256PasswordEncoder라는 구체 클래스에 의존한다. 하지만 의존 역전 원칙은 컴파일 시점 또는 소스 코드 단계에서의 의존성이 역전되는 것을 의미하며, 코드에서는 UserService가 PasswordEncoder라는 인터페이스에 의존한다.

</div>
</details>