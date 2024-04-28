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

- 해결(Synchronized - Thread-safe Case)

```jsx
public class Counter {
   private Object lock = new Object();
   private int count;

   public int increase() {
     synchronized(lock) {
       return ++count;
     }
   }
 }
 
 또는 
 
 public class Counter {
  private int count;

  public synchronized int increase() {
    return ++count;
  }
}

* synchronized 메서드와 synchronized 블록의 차이
synchronized 메서드는 메서드 전체를 임계 영역으로 설정하며, 
해당 메서드를 호출하는 모든 스레드들은 객체의 고유 락을 확보

반면에 synchronized 블록은 메서드 내부의 특정 블록만을 임계 영역으로 설정가능
객체의 일부분에 대한 락을 설정하여 세밀하게 제어
```

### 구조적인 락 Structed lock

- 고유락을 이용한 동기화를 구조적인 락이라고 함
- `synchronized` 블록 단위로 락의 획득/ 해제가 일어나므로 구조적이라고 함
- 블록을 진입할 때 락의 획득이, 블록을 벗어날 때 락의 해제가 일어남

## **Reentrancy**

- 자바의 고유락은 재진입이 가능
- 재진입이 가능하다는 것은 **락의 획득이 호출 단위가 아닌 스레드 단위로 일어난다**는 것을 의미
- 이미 락을 획득한 스레드는 같은 락을 얻기 위해 대기할 필요가 없음 -> `synchronized` 블록을 만났을 때, 대기없이 통과 가능

## **Visibility**

- 가시성 : 여러 Thread가 동시에 작동하였을 때, 한 Thread가 쓴 값을 다른 Thread가 볼 수 있는지, 없는지 여부
- Lock : Structure Lock과 Reentrant Lock은 Visibility를 보장.