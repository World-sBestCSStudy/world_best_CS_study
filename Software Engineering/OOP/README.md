# 객체 지향 프로그래밍
Object-Oriented Programming(OOP)
특정한 개념의 함수와 자료형을 함께 묶어서 관리하기 위해 탄생한 것
- `C++`, `C#`, `Java`, `Python`, `JavaScript`, `Ruby`, `Swift` 등이 객체지향 언어



## 객체?
**객체 지향 프로그래밍의 가장 기본적인 단위이자 시작점**
프로그램에서 사용되는 **데이터** or 참조되는 **공간**을 의미. 
값을 저장할 `변수`와 작업을 수행할 `메소드`를 연관된 것들끼리 묶어서 만든 것.
> 레고에 빗대어 표현하자면 `객체`는 각각의 레고 조각이고 `객체`를 조립해서 무언가를 만드는 방식이 **객체지향 프로그래밍**

![자동차 객체](https://github.com/cuzzzu1318/cuzzzu1318.github.io/assets/77597885/0730a2c4-4dfe-4c3c-ad60-d6247e12b6c3)

## 객체지향의 특징
크게 네 가지가 있다. `추상화`, `캡슐화`, `상속`, `다형성`

### 추상화
객체에서 **공통된 속성, 행위를 추출하여 정의**하는 것
![추상화 예시](https://i0.wp.com/blogcodestates.com/wp-content/uploads/2022/11/%EA%B0%9D%EC%B2%B4-%EC%A7%80%ED%96%A5-%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D-%EC%B6%94%EC%83%81%ED%99%94.png?resize=768%2C509&ssl=1)

자동차와 오토바이는 모두 이동수단이며 전진과 후진이라는 공통된 행위를 한다. 이러한 공통된 특징을 추출하여 `Vehicle` 이라는 상위 클래스를 선언할 수 있다.<br>
Java에서는 추상 클래스와 인터페이스로 구현할 수 있다.

객체지향 프로그래밍에서 추상화는 크게 두가지로 나뉜다.
- 객체의 관련 속성만 표시 - 데이터 추상화
  - 자동차 예시와 같은 경우 - 객체를 일반화하는 것.
- 불필요한 세부 정보는 숨김 - 제어 추상화
  - 라이브러리를 불러와서 사용하는 경우 외부의 메소드 혹은 객체를 사용할 뿐 실제 내부 로직은 모르고 써도 됨. 

### 상속
기존의 객체를 재활용하여 새로운 하위 객체를 생성하는 것.
객체간 공통된 속성과 기능을 상위 객체로 추상화하고, 상위 객체에서부터 확장된 여러 하위 객체들이 **상위 객체의 속성과 기능을 간편하게 재사용**할 수 있도록 함.
![상속 예시](https://i0.wp.com/blogcodestates.com/wp-content/uploads/2022/11/%EA%B0%9D%EC%B2%B4-%EC%A7%80%ED%96%A5-%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D-%EC%83%81%EC%86%8D.png?resize=768%2C509&ssl=1)

상속을 사용하지 않는다면 빨간색으로 표시된 부분도 중복된 코드로 작성해야한다.
하지만 상위 객체인 `Vehicle`에 빨간색 부분을 모두 선언하고, 해당 객체를 상속하면 각각의 객체는 공통되지 않은 파란색 부분만 구현하면 된다. 
또한, Java에서는 상위 객체에 구현된 메소드를 Overriding하여 재정의할수도 있다.
> **재정의**는 인터페이스를 이용한 추상화와의 차이점. 
> 상속관계의 경우 인터페이스에 비해 추상화의 정도가 낮다. 
> 인터페이스는 역할의 껍데기만 정의하는 것이라면 상속은 구체적인 내용을 정의하고 재사용하는 것이기 때문.

### 다형성
어떤 객체의 속성이나 기능이 여러 가지 형태를 가질 수 있는 것.
~~객체지향의 꽃이라네요~~
![다형성의 예시](https://i0.wp.com/blogcodestates.com/wp-content/uploads/2022/11/%EA%B0%9D%EC%B2%B4-%EC%A7%80%ED%96%A5-%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D-%EB%8B%A4%ED%98%95%EC%84%B1.png?resize=768%2C487&ssl=1)
Java의 경우 Overriding과 Overloading을 통해 구현됨.

`Vehicle`이라는 인터페이스가 아래와 같다고 했을 때,
```java
public interface Vehicle{
    void moveForward();
    void moveBackward();
}
```
이를 구현한 `Car`라는 클래스는 인터페이스의 메소드를 Override할 수 있다.
```java
public class Car implements Vehicle{
    @Override
    public void moveForward(){
        System.out.println("Car 전진");
    }

    @Override
    public void moveBackward(){
        System.out.println("Car 후진");
    }
}
```
하지만 `Bike`라는 클래스는 다음과 같이 Override할 수 있다.
```java
public class Bike implements Vehicle{
    @Override
    public void moveForward(){
        System.out.println("Bike 전진");
    }

    @Override
    public void moveBackward(){
        System.out.println("Bike 후진");
    }
}
```
이렇듯 상위 인터페이스의 `moveFoward`라는 **같은 이름의 메소드를 상황에 따라 다른 역할을 수행하는 것**을 Overriding을 통한 다형성이라고 한다.

```java
public class Calculator{
    int add(int a, int b){
        return a+b;
    }
    int add(int a, int b, int c){
        return a+b+c;
    }
}
```
위와 같이 하나의 클래스 내에서 `add`라는 메소드가 매개변수에 따라 다른 역할을 수행하는 것 은 Overloading을 통한 다형성이다.

하지만 이것 이외에 객체지향에서는 더욱 중요한 다형성의 의미가 있다.
> 상위 클래스 타입의 참조 변수로 하위 클래스의 객체를 참조할 수 있도록 하는 것.

말로만 들으면 쉽게 이해되지 않을 수 있다. 코드로 살펴보자.
```java
    public class Main{
        public static void main(String[] args){
            //기존의 객체 생성
            Car car = new Car();
            Bike bike = new Bike();

            //다형성을 활용한 객체 생성
            Vehicle car2 = new Car();
        }
    }
```

`Vehicle`이라는 상위 클래스의 참조 변수로 `Car`를 참조하는 것을 확인할 수 있다. 
이렇게 선언하게 되면 `Vehicle`의 배열을 통해 `Car`와 `Bike` 뿐 아니라 `Vehicle`을 상속한 모든 객체를 다룰 수 있게 된다. 

또 다른 예시로, 만약 `Driver`라는 클래스를 만든다고 했을 때, 다형성을 사용하지 않고 `drive()`라는 메소드를 구현한다고 해 보자.
```java
    public class Driver{
        void drive(Car car){
            car.moveForward();
        }

        void drive(Bike bike){
            bike.moveForward();
        }
    }
```
만약 이동수단이 추가된다고 하면 각각의 이동수단마다 메소드를 구현해 주어야 할 것이다. 이는 곧 코드의 중복으로 이어진다.
이를 다형성을 이용하여 수정한다면

```java
    public class Driver{
        void drive(Vehicle vehicle){
            vehicle.moveForward();
        }
    }
```

`Vehicle` 클래스에 구현된 `moveForward()`메소드를 통해 더욱 간결한 코드로 바꿀 수 있다.

### 캡슐화
서로 연관된 속성과 기능을 하나의 캡슐로 만들어 외부로부터 보호하는 것.
캡슐화를 하는 이유는 크게 두 가지가 있다.
- 데이터 보호
- 데이터 은닉

자바에서 캡슐화를 구현하는 방법
- 접근제어자
  - 클래스나 멤버를 외부에서 접근하지 못하도록 접근을 제한.
  - ![접근제어자](https://i0.wp.com/blogcodestates.com/wp-content/uploads/2022/11/%EC%9E%90%EB%B0%94-%EC%A0%91%EA%B7%BC%EC%A0%9C%EC%96%B4%EC%9E%90-%ED%91%9C.png?resize=768%2C251&ssl=1)
- `getter`, `setter`메소드
  - 모든 속성값을 private으로 선언하고 getter/setter를 통한 접근만을 허용.

## 객체지향 프로그래밍의 장단점

### 장점
- 클래스 단위로 모듈화 -> 업무 분담 편리, 대규모 소프트웨어 개발에 적합
- 클래스 단위로 수정 가능 -> 유지보수 편리
- 재사용성, 확장성 증대

### 단점
- 처리 속도가 상대적으로 느리다.
- 객체의 수가 많아지면 용량이 커질 수 있다.
- 설계에 대한 부담이 커진다.

## 객체 지향 프로그래밍 설계 원칙
객체 지향을 설계할 때는 **SOLID**라고 불리는 5가지의 설계 원칙이 존재한다.

### 1. 단일 책임 원칙 - SRP(Single Responsibility Principle)
- 하나의 클래스는 하나의 책임만 져야 한다.
- 단일 책임 원칙을 지키지 않을 경우 한 책임의 변경에 의해 다른 코드에 영향이 갈 수 있다.
- 책임을 적절히 분배함으로써 코드의 가독성 향상, 유지보수 용이

### 2. 개발-폐쇄 원칙 - OCP(Open/Closed Principle)
- 확장에는 열려 있어야 하고 변경에는 닫혀 있어야 한다.
- 기능을 변경하거나 확장할 수 있으면서, 그 기능을 사용하는 코드는 수정하지 않는다.
- 변경이나 추가사항이 발생하더라도, 기존 구성요소는 수정이 일어나지 말아야 하며 기존 구성요소를 쉽게 확장해서 재사용할 수 있어야 한다.
- 이를 가능하게 하는 중요 메커니즘은 추상화와 다형성

### 3. 리스코브 치환 원칙 - LSP(Liskov Substitution)
- 상위 타입의 객체를 하위 타입의 객체로 치환해도, 상위 타입을 사용하는 프로그램은 정상적으로 동작해야 한다.

### 4. 인터페이스 분리 원칙 - ISP(Interface Segregation Principle)
- 인터페이스는 그 인터페이스를 사용하는 클라이언트를 기준으로 분리해야 한다.
- SRP가 클래스의 단일책임을 강조한다면, ISP는 인터페이스의 단일 책임을 강조한다.

### 5. 의존 역전 원칙 - DIP(Dependency Inversion)
- 고수준 모듈은 저수준 모듈의 구현에 의존해서는 안된다.
- 추상화에 의존해야 한다. 구체화에 의존해서는 안된다.


---
### 참고
[객체지향 프로그래밍이란](https://jongminfire.dev/%EA%B0%9D%EC%B2%B4%EC%A7%80%ED%96%A5-%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D%EC%9D%B4%EB%9E%80)
[객체지향 프로그래밍 특징](https://www.codestates.com/blog/content/%EA%B0%9D%EC%B2%B4-%EC%A7%80%ED%96%A5-%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D-%ED%8A%B9%EC%A7%95)
[OOP-객체-지향-프로그래밍-개념과-추상화-설계의-이해](https://inpa.tistory.com/entry/OOP-%EA%B0%9D%EC%B2%B4-%EC%A7%80%ED%96%A5-%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D-%EA%B0%9C%EB%85%90%EA%B3%BC-%EC%B6%94%EC%83%81%ED%99%94-%EC%84%A4%EA%B3%84%EC%9D%98-%EC%9D%B4%ED%95%B4)
[객체지향 개발 5대 원리: SOLID](https://www.nextree.co.kr/p6960/)
[Tech Interview - 객체지향 프로그래밍](https://gyoogle.dev/blog/computer-science/software-engineering/Object-Oriented%20Programming.html)