# 이분 탐색

- 탐색 범위를 두 부분으로 분할하면서 찾는 방식 -> 분할 정복
- 탐색 하는 값을 두 부분으로 나누어서 찾기 때문에 시간 복잡도가 O(N) 에서 O(logN) 이 됨

### 탐색 순서
1. 우선 정렬을 해서 값들이 순차적으로 놓여있게 함.
2. left와 right의 중간값으로 mid 값 설정
3. mid값과 내가 구하고자 하는 값과 비교
4. 구할 값이 mid보다 높으면 : left = mid+1 구할 값이 mid보다 낮으면 : right = mid - 1
-> 값이 mid값보다 큰 경우는 더 큰 범위에서 해당 값을 찾아야 하기 때문에 left를 mid 뒤로 옮기고
   값이 mid보다 작은 경우는 더 작은 범위에서 찾아야 하기 때문에 right를 mid 앞으로 옮기는 것. 
5. left > right가 될 때까지 계속 반복하기
-> 서로 교차하는 시점이 해당 값이 찾아진 시점

```
public static int solution(int[] arr, int num) { // arr 배열에서 num을 찾는 경우
	
    Arrays.sort(arr); // 정렬 -> 정렬을 통해 값들을 순차적으로 놓는다.
	
    int start = 0;
    int end = arr.length - 1;
    int mid = 0;

    while (start <= end) {
        mid = (start + end) / 2;
        if (M == arr[mid]) {
            return mid;
        }else if (arr[mid] < M) {
            start = mid + 1;
        }else if (M < arr[mid]) {
            end = mid - 1;
        }
    }

    throw new NoSuchElementException("num이 없음. 유감."); //배열을 다 돌았지만 값을 찾지 못한 경우
}
```

### 면접 질문
- **이분 탐색에 대해 설명해주세요.** <br>
탐색 범위를 두 부분으로 분할하면서 찾는 방식으로 처음부터 끝까지 돌면서 탐색하는 순차 탐색이 O(N)의 시간 복잡도를 가지는 것에 비해
O(logN)의 시간 복잡도를 가짐으로써 훨씬 빠르다는 장점이 있습니다. 

