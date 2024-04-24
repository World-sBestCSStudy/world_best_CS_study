## 스레드 VS 프로세스

```jsx
프로세스(Process)는 일반적으로 cpu에 의해 메모리에 올려져 실행중인 프로그램을 말하며, 
자신만의 메모리 공간을 포함한 독립적인 실행 환경을 가지고 있습니다. 
우리가 사용하는 프로그램 중 일부는 여러 프로세스간 상호작용을 하는 것일수도 있다.
자바 JVM(Java Virtual Machine)은 주로 하나의 프로세스로 실행되며, 
동시에 여러 작업을 수행하기 위해서 멀티 스레드를 지원하고 있다.
```

```
스레드(Thread)는 프로세스안에서 실질적으로 작업을 실행하는 단위를 말하며, 자바에서는 JVM(Java Virtual Machine)에 의해 관리된다. 프로세스에는 적어도 한개 이상의 스레드가 있으며, Main 스레드 하나로 시작하여 스레드를 추가 생성하게 되면 멀티 스레드 환경이 됩니다. 이러한 스레드들은 프로세스의 리소스를 공유하기 때문에 효율적이긴 하지만 잠재적인 문제점에 노출될 수도 있다.
```

## 스레드 구현

1. Runnable 인터페이스 구현
2. Thread 클래스 상속

→ 둘다 run 메소드를 오버라이딩 하는 방식

```jsx
public class MyThread implements Runnable {
    @Override
    public void run() {
        // 수행 코드
    }
}
```

```jsx
public class MyThread extends Thread {
    @Override
    public void run() {
        // 수행 코드
    }
}
```

## 스레드 생성

- Runnable 인터페이스 → 구현된 Runnable 클래스를 인스턴스화해서  Thread생성자에게 인자로 넘겨줘야한다.

```java
public static void main(String[] args) {
    Runnable r = new MyThread();
    Thread t = new Thread(r, "mythread");
}

```

- Thread 클래스 상속 → getName()으로 호출
- Runnable 구현한 Thread → Thread 클래스의 static 메소드인 currentThread()를 호출

```jsx
public class ThreadTest implements Runnable {
    public ThreadTest() {}
    
    public ThreadTest(String name){
        Thread t = new Thread(this, name);
        t.start();
    }
    
    @Override
    public void run() {
        for(int i = 0; i <= 50; i++) {
            System.out.print(i + ":" + Thread.currentThread().getName() + " ");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```

### ❓왜 start()로 실행?

💡 **run() 메소드를 사용한다면, 이건 스레드를 사용하는 것이 아니다.**

→run() 메소드를 이용한다는 것은 main()의 콜 스택 하나만 이용하는 것으로 스레드 활용이 아니다. (그냥 스레드 객체의 run이라는 메소드를 호출하는 것)

→ start() 메소드를 호출하면, JVM은 알아서 스레드를 위한 콜 스택을 새로 만들어주고 context switching을 통해 스레드답게 동작하도록 해준다.

**즉, start()는 스레드가 작업을 실행하는데 필요한 콜 스택을 생성한 다음 run()을 호출해서 그 스택 안에 run()을 저장할 수 있도록 해준다.**

## 스레드 실행 제어

- **NEW** : 스레드가 생성되고 아직 start()가 호출되지 않은 상태
- **RUNNABLE** : 실행 중 또는 실행 가능 상태
- **BLOCKED** : 동기화 블럭에 의해 일시정지된 상태(lock이 풀릴 때까지 기다림)
- **WAITING, TIME_WAITING** : 실행가능하지 않은 일시정지 상태
- **TERMINATED** : 스레드 작업이 종료된 상태

## 스레드 동기화

- 스레드의 동기화를 위해선, 임계 영역(critical section)과 잠금(lock)을 활용.
- 임계영역을 가지고 있는 lock을 단 하나의 스레드에게만 빌려주는 개념(수행후 lock 반납)

### 스레드 동기화 방법

- **임계 영역(critical section)** : 공유 자원에 단 하나의 스레드만 접근하도록
- **뮤텍스(mutex)** : 공유 자원에 단 하나의 스레드만 접근하도록(서로 다른 프로세스에 속한 스레드도 가능)
- **이벤트(event)** : 특정한 사건 발생을 다른 스레드에게 알림
- **세마포어(semaphore)** : 한정된 개수의 자원을 여러 스레드가 사용하려고 할 때 접근 제한
- **대기 가능 타이머(waitable timer)** : 특정 시간이 되면 대기 중이던 스레드 깨움

---

## **Intrinsic lock**

- 자바의 모든 객체는 lock을 가지고 있음
- 모든 객체가 가지고 있기 떄문에 고유 락 이라고 하고, 모니터 처럼 동작한다고 하여 monitor lock이라고 함
- 예시

```jsx
public class Counter {
    private int count;
 
    public int increase() {
        return ++count;		// Thread-safe 하지 않은 연산
    }
}

* count는 atomic연산이다
  - read (count 값을 읽음) -> modify (count 값 수정) -> write (count 값 저장)의 과정에서, 
  여러 Thread가 공유 자원(count)으로 접근할 수 있으므로, 동시성 문제가 발생함.
```