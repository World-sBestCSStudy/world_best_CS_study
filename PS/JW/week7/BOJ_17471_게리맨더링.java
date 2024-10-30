import java.io.*;
import java.util.*;

public class  BOJ_17471_게리맨더링 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int N, ans;   // 1~N번 구역 -> 2개의 선거구로 나누어야 하며, 한 선거구는 모두 연결되어있어야 함
    static int[] people;
    static boolean[] select, visit;
    static List<List<Integer>> list = new ArrayList<>();

    public static void main(String[] args) throws Exception{
        N = Integer.parseInt(br.readLine());    // 구역의 수

        // 인구 수 입력
        people = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++){
            people[i] = Integer.parseInt(st.nextToken());
        }

        // 인접 구역 정보
        for (int i = 0; i < N; i++){
            list.add(new ArrayList<>());
            st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken());
            for(int n = 0; n < num; n++){
                int next = Integer.parseInt(st.nextToken()) - 1;
                list.get(i).add(next);
            }
        }

        ans = Integer.MAX_VALUE;
        select = new boolean[N];
        visit = new boolean[N];

//        for (int n = 1; n <= N/2; n++){     // 한 구역의 크기가 1 ~ N/2 인 경우를 고려. (~N까지 할 필요가 없음)
//            comb(0, 0, n);
//        }

        // ** combination이 아니라 부분집합 !! 어쩐지 이상하더라... 코드는 부분집합+조합 혼종 ;;
        subset(0);

        System.out.println(ans == Integer.MAX_VALUE ? -1 : ans);
    }

    static int calDiff(){
        int sumA = 0;
        int sumB = 0;

        for (int i = 0; i < N; i++){
            if (select[i]) sumA += people[i];
            else sumB += people[i];
        }

        return Math.abs(sumA-sumB);
    }

    static int bfs(int start, boolean flag){  // 연결 체크
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(start);
        visit[start] = true;

        int cnt = 0;
        while (!queue.isEmpty()){
            int cur = queue.poll();
            cnt++;

            for (int i = 0; i < list.get(cur).size(); i++){
                int next = list.get(cur).get(i);
                if (select[next] == flag && !visit[next]){
                    visit[next] = true;
                    queue.add(next);
                }
            }
        }

        return cnt;
    }

    static void subset(int srcIdx){
        if (srcIdx == N){
            Arrays.fill(visit, false);

            int connA = 0, connB = 0;
            for (int i = 0; i < N; i++){
                if (select[i]){
                    connA = bfs(i, true);
                    break;
                }
            }
            for (int i = 0; i < N; i++){
                if (!select[i]){
                    connB = bfs(i, false);
                    break;
                }
            }

            if (connA + connB == N){    // 다 연결되어있다면
                ans = Math.min(ans, calDiff());     // 차이가 최소가 되도록 갱신
            }

            return;
        }

        select[srcIdx] = true;
        subset(srcIdx+1);
        select[srcIdx] = false;
        subset(srcIdx+1);
    }


    static void comb(int srcIdx, int cnt, int tot){
        // 종료조건
        // 선택 -> A 구역 / 비선택 -> B 구역
        if (cnt == tot) {
            // 연결 체크
            int connA = 0, connB = 0;
            for (int i = 0; i < N; i++){
                if (select[i]){
                    connA = bfs(i, true);
                    break;
                }
            }
            for (int i = 0; i < N; i++){
                if (!select[i]){
                    connB = bfs(i, false);
                    break;
                }
            }

            if (connA + connB == N){    // 다 연결되어있다면
                ans = Math.min(ans, calDiff());     // 차이가 최소가 되도록 갱신
            }

            return;
        }

        if (srcIdx == N) return;

        select[srcIdx] = true;
        comb(srcIdx+1, cnt+1, tot);
        select[srcIdx] = false;
        comb(srcIdx+1, cnt, tot);
    }

}