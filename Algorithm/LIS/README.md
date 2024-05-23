# 최장 증가 수열
- 가장 긴 증가하는 부분 수열
- ex) [ 7, 2, 3, 8, 4, 5 ] → 해당 배열에서는 [2,3,4,5]가 LIS로 답은 4

### 구현 방법 (시간복잡도)
DP : O(N^2)  <br>
Lower Bound : O(NlogN)

```
int arr[] = {7, 2, 3, 8, 4, 5};
int dp[] = new int[arr.length]; // LIS 저장 배열


for(int i = 1; i < dp.length; i++) {
    for(int j = 0; j<i; j++) {
        if(arr[i] > arr[j]) {
            dp[i] = (dp[i] < dp[j]+1) ? dp[j]+1 : dp[i];
        }
    }
}

for (int i = 0; i < dp.length; i++) {
	if(max < dp[i]) max = dp[i];
}

// 저장된 dp 배열 값 : [0, 0, 1, 2, 2, 3]
// LIS : dp배열에 저장된 값 중 최대 값 + 1
```

### 면접 질문
- **엑.** <br>
