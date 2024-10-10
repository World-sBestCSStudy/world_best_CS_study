// BOJ 17135. 캐슬디펜스

import java.io.*;
import java.util.*;

public class BOJ_17135_캐슬디펜스 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int N, M, D, ans;
    static int[][] map;
    static int[] tgt = new int[3];  // archer
    static ArrayList<Enemy> enemiesOrigin = new ArrayList<>();  // 원본 적 리스트
    static ArrayList<Enemy> enemies = new ArrayList<>();    // 시뮬레이션에 사용되는 적 리스트
    static PriorityQueue<Enemy> pq = new PriorityQueue<>(   // 궁수가 쏠 적을 찾을 때 사용되는 적 리스트
            (e1, e2) -> e1.d == e2.d ? e1.c - e2.c : e1.d - e2.d
    );
    static class Enemy {
        int r, c, d;
        boolean live = true;
        private Enemy(int r, int c){
            this.r = r;
            this.c = c;
        }
    }

    public static void main(String[] args) throws Exception{
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());

        map = new int[N+1][M];  // 0~N-1 열에는 적, N열에는 성
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 1){
                    enemiesOrigin.add(new Enemy(i,j));
                }
            }
        }

        // 궁수 위치 선택
        comb(0, 0);

        System.out.println(ans);
    }

    static void comb(int idx, int cnt){
        // complete code
        if (cnt == 3){
            // Shoot - bfs
            simulate();
            return;
        }

        if (idx == M)   return;

        tgt[cnt] = idx;
        comb(idx+1, cnt+1);
        comb(idx+1, cnt);
    }

    static void simulate(){
        // 적 초기화
        enemies.clear();
        for (Enemy e : enemiesOrigin){
            enemies.add(new Enemy(e.r, e.c));   // ** 객체 공유 X
        }

        int kill = 0;   // 죽인 적군의 수
        while (true){
            // 1. 궁수가 쏠 대상 탐색 -> 쏨
            for (int i = 0; i < 3; i++){
                pq.clear();

                int ac = tgt[i];    // 궁수 위치
                int size = enemies.size();
                for (int j = 0; j < size; j++){ // 현존하는 적군에 대해
                    Enemy e = enemies.get(j);
                    e.d = Math.abs(ac-e.c) + Math.abs(N-e.r);
                    if (e.d <= D){
                        pq.offer(e);
                    }
                }

                // ** 화살 쏠 대상 선정 -> 이미 PriorityQueue로 힙 정렬하면서 넣었기 때문에 제일 위에 있는 애를 빼주면 된다.
                if (!pq.isEmpty()){
                    pq.poll().live = false;
                }
            }

            // 2. 화살 맞은 적 제거 & 적이 한 칸 아래로 내려온다.
            // 3. 성에 닿은 적이 있다면 제거시키기
            for (int i = enemies.size() - 1; i >= 0; i--){
                Enemy e = enemies.get(i);
                if (!e.live){   // 화살 맞은 적 제거
                    enemies.remove(i);
                    kill++; // 죽인 수 ++
                } else if (e.r == N-1){ // 마지막 줄에 있던 적은 한칸 내려오면 성에 도달
                    enemies.remove(i);  // 제거
                } else {    // 아무것도 아닌 적은 한칸 아래로 내려온다
                    e.r++;
                }
            }

            // 4. 모든 적이 제거되면 종료
            if (enemies.size() == 0) break;
        }

        ans = Math.max(ans, kill);
    }
}
