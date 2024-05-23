## 퀵정렬 Quick Sort
**피벗(pivot)을 기준**(보통은 첫번째 데이터)으로 큰 수는 우측으로 작은 수는 좌측으로 보내는 방법을 사용한다. 

세부 로직은 배열 좌, 우측을 탐색하는 변수를 두고, 서로 다른 방향으로 진행하며 각각 작은/큰 수가 나올 때까지 인덱스를 증가시킨다.

둘다 적절한 수를 찾고 left < right 이면 두 인덱스의 배열 값을 교환하고 divide 한다.

merge sort와 같은 divide and conquer 전략을 사용하지만 divide 과정에서 차이를 보인다.

merge sort는 divide 과정이 언제나 n/2로 일정하게 쪼개지만, 

quick sort는 pivot을 기준으로 좌, 우측으로 배열을 쪼갠다.

<br/>

### 시간 복잡도
**최악** : 언제나 한쪽 방향으로만 쪼개지므로 깊이가 n이 나옴 = **O(n^2)**

**평균** : **O(N log N)**

하지만, 최악의 경우는 자연스러운 상태에서는 잘 나오지 않는다.

일반적으로는 quick sort가 merge sort 보다 20% 정도 성능이 좋다.

> 이미 정렬된 데이터라면 매우 비효율적

<br/>

### 그림으로 이해하기
#### 1.
![image](https://github.com/dahui0525/world_best_CS_study/assets/80496853/cebbb0cf-e676-419d-8958-754a3c5cae40)

#### 2.
![image](https://github.com/dahui0525/world_best_CS_study/assets/80496853/5b5184e9-72a6-4089-baa4-593eca6a848d)
이후 좌 우에 대해서 반복
![image](https://github.com/dahui0525/world_best_CS_study/assets/80496853/fbcd12dd-8d7a-45b1-835b-1813888c2404)


<br/>

### Code
```java
public static void quickSort(int[] arr, int start, int end) {
    if (start >= end) return;   // 원소가 1개인 경우 종료
    int pivot = start;      // 피벗은 첫 번째 원소
    int left = start + 1;
    int right = end;
    while (left <= right) {
        // 피벗보다 큰 데이터를 찾을 때까지 반복
        while (left <= end && arr[left] <= arr[pivot]) left++;
        // 피벗보다 작은 데이터를 찾을 때까지 반복
        while (right > start && arr[right] >= arr[pivot]) right--;
        // 엇갈렸다면 작은 데이터와 피벗을 교체
        if (left > right) {
            int temp = arr[pivot];
            arr[pivot] = arr[right];
            arr[right] = temp;
        }
        // 엇갈리지 않았다면 작은 데이터와 큰 데이터를 교체
        else {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
        }
    }
    // 분할 이후 왼쪽 부분과 오른쪽 부분에서 각각 정렬 수행
    quickSort(arr, start, right - 1);
    quickSort(arr, right + 1, end);
}
```