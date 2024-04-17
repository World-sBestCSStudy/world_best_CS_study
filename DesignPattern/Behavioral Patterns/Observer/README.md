# 옵저버 패턴

- 한 객체의 상태 변화에 따라 다른 객체의 상태도 연동되도록 일대다 객체 의존 관계를 구성하는 패턴이다.
- 한 객체의 상태 변화를 추적하고 이에 따른 처리를 여러 곳에서 수행해야 하는 경우 유용하다.

### 구현 방법
1. Subject(주제): 상태를 관찰하고 알림을 보내는 주체 객체입니다.
2. Observer(옵저버): 주제의 상태 변화를 관찰하고 통지를 받는 객체입니다.
3. ConcreteSubject(구체적인 주제): 실제로 상태를 가지고 있고, 상태가 변경될 때 옵저버들에게 알리는 주제의 구체적인 구현입니다.
4. ConcreteObserver(구체적인 옵저버): 실제로 상태 변화를 감지하고 처리하는 옵저버의 구체적인 구현입니다.

```
import java.util.ArrayList;
import java.util.List;

// 주제(Subject) 인터페이스
interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers();
}

// 구체적인 주제(Concrete Subject)
class ConcreteSubject implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private int state;

    public void setState(int state) {
        this.state = state;
        notifyObservers();
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(state);
        }
    }
}

// 옵저버(Observer) 인터페이스
interface Observer {
    void update(int state);
}

// 구체적인 옵저버(Concrete Observer)
class ConcreteObserver implements Observer {
    private int observerState;

    @Override
    public void update(int state) {
        observerState = state;
        System.out.println("옵저버 상태 업데이트: " + observerState);
    }
}

// 클라이언트 코드
public class Client {
    public static void main(String[] args) {
        ConcreteSubject subject = new ConcreteSubject();

        // 옵저버 생성
        ConcreteObserver observer1 = new ConcreteObserver();
        ConcreteObserver observer2 = new ConcreteObserver();

        // 주제에 옵저버 등록
        subject.attach(observer1);
        subject.attach(observer2);

        // 주제의 상태 변경
        subject.setState(10);

        // 주제에서 옵저버 제거
        subject.detach(observer1);

        // 다시 상태 변경
        subject.setState(20);
        
        //observer1.observerState=10, observer2.observerState=20
    }
}
```

### 면접 질문
- **옵저버 패턴을 어떻게 구현하나요?** <br>
  여러 가지 방법이 있지만 주로 프록시 객체를 이용하여 구현합니다. <br>
  프록시 객체를 통해 객체의 속성이나 메서드 변화 등을 감지하고 이를 미리 설정해 놓은 옵저버들에게 전달하는 방법으로 구현합니다.