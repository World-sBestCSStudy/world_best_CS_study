## 버블정렬 Bubble Sort
한 번에 두 개의 숫자를 정렬하고 한 번 수행 시에 한 개의 min/max 값이 결정난다.

선택 정렬과 한 번 수행 시에 결과는 같지만 최대/최솟값을 만나기 전까지 숫자들이 어느정도 정렬이 된다는 점에서 차이가 있다.

<br/>

### 시간 복잡도
n-1 + n-2 + n-3 + ... + 1 = **O(n^2)**

선택 정렬, 삽입 정렬과 시간 복잡도가 같지만 연산 수가 많아 정렬 알고리즘 중에서 가장 느리고 효율성이 떨어지는 정렬 방식

<br/>

### 공간 복잡도
index N개의 배열만 있으면 되므로 **O(n)**

<br/>

### 그림으로 이해하기
![image](./assets/bubble.gif)

<br/>

### 코드
```java
int n = 10;
int[] arr = {7, 5, 9, 0, 3, 1, 6, 2, 4, 8};

for (int i = 0; i < n; i++) {
    int min_index = i;      // 가장 작은 원소의 인덱스 
    for (int j = i + 1; j < n; j++) {
        if (arr[min_index] > arr[j]) {
            min_index = j;
        }
    }
    // 스와프
    int temp = arr[i];
    arr[i] = arr[min_index];
    arr[min_index] = temp;
}

for(int i = 0; i < n; i++) {
    System.out.print(arr[i] + " ");
}
```