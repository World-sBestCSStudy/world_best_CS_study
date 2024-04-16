# 팩토리 패턴

- 객체를 생성하는 과정을 캡슐화하여 클라이언트에게 객체를 반환하는 디자인 패턴이다. 
이를 통해 클라이언트는 객체를 생성하는 구체적인 방법을 알 필요 없이 팩토리에게 객체 생성을 요청할 수 있고, 
팩토리는 요청에 따라 적절한 객체를 생성하여 반환한다.
- 주로 객체 생성 과정이 복잡하거나 변경 가능성이 높은 경우에 객체 생성 로직을 클라이언트로부터 분리하여 캡슐화하기 위해 사용된다.

## 팩토리 메서드 패턴
- 객체 생성을 서브 클래스에게 위임하는 패턴이다.

### 구현 방법
슈퍼 클래스에서는 객체를 생성하는 추상 메서드를 선언하고, 서브 클래스에서는 이를 구현하여 객체를 생성한다.
```
// Animal 인터페이스
interface Animal {
    void makeSound();
}

// Dog 클래스
class Dog implements Animal {
    @Override
    public void makeSound() {
        System.out.println("Woof!");
    }
}

// Cat 클래스
class Cat implements Animal {
    @Override
    public void makeSound() {
        System.out.println("Meow!");
    }
}

// 추상 클래스
public abstract class AnimalFactory {
    // 팩토리 메서드
    public abstract Animal createAnimal();

    // 클라이언트 코드에서는 팩토리 메서드를 사용하여 객체를 생성
    public Animal getAnimal() {
        Animal animal = createAnimal();
        // 추가적인 초기화 작업 수행 가능
        return animal;
    }
}

// 구체적인 팩토리 클래스들
public class DogFactory extends AnimalFactory {
    @Override
    public Animal createAnimal() {
        return new Dog();
    }
}

public class CatFactory extends AnimalFactory {
    @Override
    public Animal createAnimal() {
        return new Cat();
    }
}

// 클라이언트 코드
public class Main {
    public static void main(String[] args) {
        AnimalFactory dogFactory = new DogFactory();
        Animal dog = dogFactory.getAnimal();
        dog.makeSound(); // 출력: Woof!

        AnimalFactory catFactory = new CatFactory();
        Animal cat = catFactory.getAnimal();
        cat.makeSound(); // 출력: Meow!
    }
}
```

## 추상 팩토리 패턴
- 구제적인 클래스에 의존하지 않고 서로 연관되거나 의존적인 객체들의 조합을 만드는 인터페이스를 제공하는 패턴이다.

### 구현 방법
1. 관련된 객체들의 집합 정의
2. 객체들의 집합을 바탕으로 추상 팩토리 인터페이스 정의.
3. 구체적인 팩토리 클래스 구현.
4. 클라이언트 코드 작성.
```
// 동물 인터페이스
interface Animal {
    void makeSound();
}

// 강아지 클래스
class Dog implements Animal {
    @Override
    public void makeSound() {
        System.out.println("멍멍!");
    }
}

// 고양이 클래스
class Cat implements Animal {
    @Override
    public void makeSound() {
        System.out.println("야옹~");
    }
}

// 추상 동물 팩토리 인터페이스
interface AnimalFactory {
    Animal createDog();
    Animal createCat();
}

// 구체적인 동물 팩토리 클래스
class RealAnimalFactory implements AnimalFactory {
    @Override
    public Animal createDog() {
        return new Dog();
    }

    @Override
    public Animal createCat() {
        return new Cat();
    }
}

// 클라이언트 클래스
public class Client {
    public static void main(String[] args) {
        // 실제 동물 팩토리를 이용해 강아지와 고양이 객체 생성
        AnimalFactory animalFactory = new RealAnimalFactory();
        Animal dog = animalFactory.createDog();
        dog.makeSound(); // 출력: 멍멍!

        Animal cat = animalFactory.createCat();
        cat.makeSound(); // 출력: 야옹~
    }
}
```

### 면접 질문
- **팩토리 패턴에 대해 설명해주세요.** <br>
팩토리 패턴은 객체를 생성하는 로직 부분을 따로 분리하여 관리하는 패턴입니다.
객체 생성과 사용을 분리하여 코드의 확장성과 유지보수성을 향상시킵니다.