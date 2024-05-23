# DFS
- 완전 탐색.
- 깊이 우선 탐색.
- 인접 행렬 : O(V^2), 인접 리스트 : O(V+E)
- 스택 or 재귀함수를 통해 구현
![Depth-First-Search.gif](Depth-First-Search.gif)

### 구현 방법
```
 // DFS 스택을 사용한 구현
    void dfsUsingStack(int startVertex) {
        boolean[] visited = new boolean[adjList.length];
        Stack<Integer> stack = new Stack<>();
        
        stack.push(startVertex);
        
        while (!stack.isEmpty()) {
            int vertex = stack.pop();
            
            if (!visited[vertex]) {
                visited[vertex] = true;
                System.out.print(vertex + " ");
                
                // 인접한 모든 노드를 스택에 추가
                for (int i = adjList[vertex].size() - 1; i >= 0; i--) {
                    int neighbor = adjList[vertex].get(i);
                    if (!visited[neighbor]) {
                        stack.push(neighbor);
                    }
                }
            }
        }
    }
    
    
    // DFS 재귀 함수를 사용한 구현
    void dfsUsingRecursion(int startVertex) {
        boolean[] visited = new boolean[adjList.length];
        dfsRecursive(startVertex, visited);
    }
    
    private void dfsRecursive(int vertex, boolean[] visited) {
        visited[vertex] = true;
        System.out.print(vertex + " ");
        
        for (int neighbor : adjList[vertex]) {
            if (!visited[neighbor]) {
                dfsRecursive(neighbor, visited);
            }
        }
    }
```

# BFS
- 완전 탐색.
- 너비 우선 탐색.
- 인접 행렬 : O(V^2), 인접 리스트 : O(V+E)
- 큐를 통해 구현
- 최소 비용을 구할 때 적합
![Breadth-First-Search-Algorithm.gif](Breadth-First-Search-Algorithm.gif)
```
	static void bfs(int n) {
		Queue<Integer> queue = new ArrayDeque<Integer>();
		queue.add(n);
		visited[n] = cnt++;// 시작 정점의 방문 순서를 1로 설정
		while (!queue.isEmpty()) {
			int x = queue.poll();
			for (int i = 0; i < adlist[x].size(); i++) {

				int nx = adlist[x].get(i);
				if (visited[nx] == 0) {
					queue.add(nx);
					visited[nx] = cnt++;// 방문 순서를 체크하면서 방문 확인

				}
			}
		}
	}
```

### 면접 질문
- **BFS, DFS에 대해 설명하세요.** <br>
BFS는 너비 우선 탐색으로, 그래프 상에서 시작되는 정점을 기준으로, 연결되어 있는 모든 정점을 탐색하는 알고리즘입니다. 실제 구현에서는 queue를 사용하여 다음 탐색할 정점을 queue에 저장해둘 수 있습니다.  <br>
DFS는 깊이 우선 탐색으로, 그래프 상에서 시작되는 정점을 기준으로, 연결되어 있는 한 정점으로 계속해서 나아가다가 더이상 진행할 수 없을 때 다시 돌아오는 과정을 반복합니다. DFS 구현에서는 재귀방식이나 stack을 이용하는 것이 적절합니다.  <br>
두 탐색 모두 완전 탐색으로 시간복잡도는 인접 행렬 O(V^2)이고 인접 리스트의 경우 O(V+E) 입니다.  <br>
최단 거리를 구하는 경우 둘 다 구할 수는 있겠지만 BFS 방식이 효율적이고 경로의 특징을 찾는 경우는 DFS 방식이 조금 더 효율적입니다.
