### 절차형 프로그래밍

- 로직이 수행되어야 할 연속적인 계산 과정으로 이루어져 있다
- 일이 진행되는 방식으로 코드를 구현 하면 되기 때문에 코드의 가독성이 좋으며 실행속도가 빠름
- 계산이 많은 작업에 주로 쓰임 (ex. 대기 과학 관련 연산작업, 머신러닝, 배치 작업)
- 단점으로 모듈화하기 어렵고 유지 보수성이 떨어짐

```jsx
public class Test {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < list.size(); i++){
            max = Math.max(max, list.get(i));
        }
        System.out.println("max : " + max);
    }
}
```
