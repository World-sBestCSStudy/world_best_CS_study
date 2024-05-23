# 동적 계획법

## 동적 계획법 : Dynamic Programming

→ 복잡한 문제를 간단한 여러 개의 문제로 나누어 푼다. 그 결과를 저장하여 다시 큰 무제를 해결할 때 사용한다.

**큰 문제를 작은 문제로 쪼개서 그 답을 저장해두고 재활용**한다.

**일반적인 재귀를 단순히 사용 시 동일한 작은 문제들이 여러 번 반복 되어 비효율적인 계산**될 수 있다는 것이다.

예시 ) 피보나치 수열

1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144 …

### 재귀

return f(n) = f(n-1) + f(n-2)

→ f(n-1), f(n-2)에서 한 번 구한 값을 f(n-2)에서 또 사용한다.

f(n-1) = f(n-2) + f(n-3)

1번씩 호출하는데 동일한 값을 2번씩 구하게 되고 누적되면 호출되는 함수가 기하급수적 증가

![Untitled](./assets/1.png)

따라서 한 번 구한 값을 저장해놓고 사용하는 방법이 DP

시간복잡도를 **O(n^2) → O(f(n)) 로 개선**

f(n)은 문제에 따라 다름

### 그렇다면 DP의 사용 조건은?

1. 겹치는 부분 문제
   - 동일한 작은 문제들이 반복하여 나타나야 사용이 가능하다.
2. 최적 부분 구조
   - 부분 문제의 최적 결과 값을 사용해서 전체 문제의 최적 결과를 낼 수 있는 경우
     ![Untitled](./assets/2.png)
     A - X의 최적 경로가 AX2 이고, X - B 의 최적 경로가 BX2 일때
     A - B의 최적 경로도 AX2 + BX2 이다.
     전체 경로라고 해서 앞에서 찾은 최적 경로가 달라지거나 하지 않는다.
     이 전에 찾은 값이 전체 답에서 동일하게 사용된다.

### DP 사용법

1. DP 문제로 풀 수 있는 방법인지 확인하기
2. 문제의 변수 파악하기
3. 점화식 구하기
4. Memorization ( 이전 값 저장하기 )
5. 기저 상태 파악하기 → 가장 작은 문제의 상태를 알기
6. 구현 하기

### 구현 방식

1. **Bottom-Up 방식**

→ 아래에서 부터 계산을 수행하고 누적시켜서 전체 큰 문제를 해결하는 방식

Bottom-up은 memorization이 아니라 Tabulation이라고 부른다.

dp[0] 부터 하나씩 채우는 과정을 table-filling 이라고 하며, 해당 table 값에 직접 접근해서 재활용하므로 **Tabluation**이라고 한다.

1. **Top-Down 방식**

→ dp[0]의 기저 상태가 아닌 dp[n]을 찾기 위해 위에서 부터 호출을 시작하고 dp[0]까지 내려간 다음 재귀를 통해 재활용 하는 방식

재귀를 진행하면서 이전에 완료된 계산된 값은 메모리에 저장되어 있던 걸 꺼내서 활용 그래서 **Memorization**이라고 부른다.

- **피보나치 수열을 Top-Dwon과 Bottom-Up으로 구현했을 때**

```java
// DP Top-Down을 사용해 Fibonacci를 구하는 경우
    public static int topDown(int n){
        // 기저 상태 도달 시, 0, 1로 초기화
        if(n < 2) return topDown_memo[n] = n;

        // 메모에 계산된 값이 있으면 바로 반환!
        if(topDown_memo[n] > 0) return topDown_memo[n];

        // 재귀를 사용하고 있음!
        topDown_memo[n] = topDown(n-1) + topDown(n-2);

        return topDown_memo[n];
    }

 // DP Bottom-Up을 사용해 Fibonacci를 구하는 경우
    public static int bottomUp(int n){
        // 기저 상태의 경우 사전에 미리 저장
        bottomup_table[0] = 0; bottomup_table[1] = 1;

        // 반복문을 사용하고 있음!
        for(int i=2; i<=n; i++){
            // Table을 채워나감!
            bottomup_table[i] = bottomup_table[i-1] + bottomup_table[i-2];
        }
        return bottomup_table[n];
    }

// 단순 재귀를 통해 Fibonacci를 구하는 경우
    public static int naiveRecursion(int n){
        if(n <= 1){
            return n;
        }
        return naiveRecursion(n-1) + naiveRecursion(n-2);
    }
```

### Q. 두 가지 방법 중 더 나은 것이 있는지, 하나만 가능한 경우가 있는지?

더 효율적인 방법은 문제에 따라 달라서 정답이 없다.

Top-Down 방식의 경우는 재귀를 호출하기에 내부 스택을 만들고 함수를 호출 하는 과정 때문에 Bottom-up이 더 빠를 것 같다고 느낄 수 있지만 점화식에 따라 Bottom-Up 보다 덜하는 경우가 있다고 한다.

둘 중에 하나만 사용 가능한 경우도 있다.

### Q. 분할 정복과 동적 프로그래밍의 공통점 차이점

**공통점** : 주어진 문제를 작게 쪼개서 하위 문제로 해결하고 연계적으로 큰 문제를 해결

**차이점 :** 분할 정복은 분할 된 하위 문제가 동일하게 중복이 일어나지 않는 경우 쓰인다.

예시 ) 병합 정렬 : 분할 된 부분 부분은 모두 독립적이고, 동일한 부분을 중복 하지 않는다.

[출처 : https://hongjw1938.tistory.com/47]
