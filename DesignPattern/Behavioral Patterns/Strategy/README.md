# 전략 패턴

- 객체의 행위를 캡슐화하고, 이를 교체하여 동적으로 객체의 동작을 변경할 수 있도록 하는 디자인 패턴이다.
- 알고리즘을 정의하고 각각을 캡슐화하여 서로 교환 가능하도록 만들어서 클라이언트는 알고리즘의 구현에 대해 알 필요 없이 해당 알고리즘을 사용할 수 있다.
- 같은 문제를 해결하는 여러 알고리즘이 존재하며, 이들을 동적으로 교체하고자 할 때 유용하다.

### 구현 방법
1. 전략(Strategy): 여러 알고리즘을 캡슐화한 인터페이스 혹은 추상 클래스를 정의합니다.
2. 구체적인 전략(Concrete Strategy): 전략을 실제로 구현한 클래스들입니다.
3. 컨텍스트(Context): 전략을 사용하는 클라이언트를 나타내는 클래스로, 전략 객체를 가지고 있으며 필요에 따라 전략을 교체할 수 있습니다.

```
// 전략 인터페이스
interface SortStrategy {
    void sort(int[] array);
}

// 버블 정렬 전략
class BubbleSortStrategy implements SortStrategy {
    @Override
    public void sort(int[] array) {
        System.out.println("버블 정렬 수행");
        // 버블 정렬 알고리즘 구현
    }
}

// 퀵 정렬 전략
class QuickSortStrategy implements SortStrategy {
    @Override
    public void sort(int[] array) {
        System.out.println("퀵 정렬 수행");
        // 퀵 정렬 알고리즘 구현
    }
}

// 정렬 컨텍스트
class Sorter {
    private SortStrategy strategy;

    public Sorter(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public void executeStrategy(int[] array) {
        strategy.sort(array);
    }
}

// 클라이언트 코드
public class Client {
    public static void main(String[] args) {
        int[] array = {5, 2, 8, 3, 1, 7, 4, 6};

        // 버블 정렬 전략을 사용하여 정렬
        Sorter sorter = new Sorter(new BubbleSortStrategy());
        sorter.executeStrategy(array);

        // 퀵 정렬 전략으로 변경하여 정렬
        sorter.setStrategy(new QuickSortStrategy());
        sorter.executeStrategy(array);
    }
}
```

### 면접 질문
- **전략 패턴에 대해 설명해주세요.** <br>
전략 패턴은 객체의 로직을 클래스로 캡슐화하고 이를 동적으로 교환하면서 사용하기 위해 이용됩니다. <br>
전략 패턴을 이용함으로써 클라이언트는 해당 객체의 로직 구현에 대해 알 필요없이 해당 로직을 사용할 수 있게 됩니다.