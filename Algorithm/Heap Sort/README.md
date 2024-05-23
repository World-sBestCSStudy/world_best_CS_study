## 힙 정렬

완전 이진 트리의 일종으로 우선순위 큐를 위하여 만들어진 자료구조
최댓값, 최솟값을 쉽게 추출할 수 있는 자료구조

과정

1. 정렬해야 할 n개의 요소들로 최대 힙(완전 이진 트리 형태)을 만든다.
    1. 내림차순을 기준으로 정렬
2. 그 다음으로 한 번에 하나씩 요소를 힙에서 꺼내서 배열의 뒤부터 저장하면 된다.
3. 삭제되는 요소들(최댓값부터 삭제)은 값이 감소되는 순서로 정렬되게 된다.

![Untitled](./assets/1.png)

### 최대 힙(max heap)의 삽입

1. 힙에 새로운 요소가 들어오면, 일단 새로운 노드를 힙의 마지막 노드에 이어서 삽입한다.
2. 새로운 노드를 부모 노드들과 교환해서 힙의 성질을 만족시킨다.

![Untitled](./assets/2.png)

### 최대 힙(max heap)의 삭제

1. 최대 힙에서 최댓값은 루트 노드이므로 루트 노드가 삭제된다.
    1. 최대 힙(max heap)에서 삭제 연산은 최댓값을 가진 요소를 삭제하는 것이다.
2. 삭제된 루트 노드에는 힙의 마지막 노드를 가져온다.
3. 힙을 재구성한다.

![Untitled](./assets/3.png)

### 힙 정렬(heap sort) 알고리즘의 특징

- 시간 복잡도가 좋은편
- 힙 정렬이 가장 유용한 경우는 전체 자료를 정렬하는 것이 아니라 가장 큰 값 몇개만 필요할 때

### 힙 정렬(heap sort)의 시간복잡도

힙 트리의 전체 높이가 거의 log₂n(완전 이진 트리이므로)이므로 하나의 요소를 힙에 삽입하거나 삭제할 때 힙을 재정비하는 시간이 log₂n만큼 소요된다.
요소의 개수가 n개 이므로 전체적으로 O(nlog₂n)의 시간이 걸린다.
T(n) = O(nlog₂n)

![Untitled](./assets/4.png)

- 코드
    
    ```c
    private void solve() {
        int[] array = { 230, 10, 60, 550, 40, 220, 20 };
     
        heapSort(array);
     
        for (int v : array) {
            System.out.println(v);
        }
    }
     
    public static void heapify(int array[], int n, int i) {
        int p = i;
        int l = i * 2 + 1;
        int r = i * 2 + 2;
     
        if (l < n && array[p] < array[l]) {
            p = l;
        }
     
        if (r < n && array[p] < array[r]) {
            p = r;
        }
     
        if (i != p) {
            swap(array, p, i);
            heapify(array, n, p);
        }
    }
     
    public static void heapSort(int[] array) {
        int n = array.length;
     
        // init, max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }
     
        // for extract max element from heap
        for (int i = n - 1; i > 0; i--) {
            swap(array, 0, i);
            heapify(array, i, 0);
        }
    }
     
    public static void swap(int[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }
    ```