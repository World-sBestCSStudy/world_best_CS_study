import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ_17135_캐슬디펜스 {

    static int N, M, D, max;
    static int[] archer = new int[3];

    static List<Enemy> enemyCopy = new ArrayList<>();  
    static List<Enemy> enemy = new ArrayList<>();
    static PriorityQueue<Enemy> pqueue = new PriorityQueue<>( 
            (e1, e2) -> e1.d == e2.d ? e1.x - e2.x : e1.d - e2.d );
    
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());
        
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                int n = Integer.parseInt(st.nextToken());
                if( n == 1 ) enemyCopy.add(new Enemy(i, j));
            }
        }
        
        // 풀이
        comb(0, 0);
        System.out.println(max);
    }

    static void check() {
        enemy.clear();
        for (Enemy e : enemyCopy) {
            enemy.add(new Enemy(e.y, e.x));
        }

        int dead = 0;
        while(true) {
            for (int i = 0; i < 3; i++) {
                pqueue.clear();
                
                int ac = archer[i]; 
                int size = enemy.size(); 
                for (int j = 0; j < size; j++) {
                    Enemy e = enemy.get(j);
                    e.d = Math.abs(ac - e.x) + Math.abs(N - e.y);
                    if( e.d <= D ) {
                        pqueue.offer(e);
                    }
                }
                
                if( !pqueue.isEmpty()) {
                    pqueue.poll().dead = true;
                }                
            }
            
            for (int i = enemy.size() - 1; i >= 0; i--) {
                Enemy e = enemy.get(i);
                if( e.dead ) {
                    enemy.remove(i);
                    dead++;
                }else if(e.y == N - 1) {
                    enemy.remove(i);
                }else {
                    e.y++;
                }
            }
            
            if( enemy.size() == 0 ) break;
        }
        
        max = Math.max(max, dead);
    }
    
    static void comb(int srcIdx, int tgtIdx) {
        // 기저조건
        if( tgtIdx == 3 ) {
            check();
            return;
        }
        
        if( srcIdx == M ) return;
        
        archer[tgtIdx] = srcIdx;
        
        comb(srcIdx + 1, tgtIdx + 1);
        comb(srcIdx + 1, tgtIdx); 
    }
    
    static class Enemy{
        int y, x, d;
        boolean dead;
        
        Enemy(int y, int x){
            this.y = y;
            this.x = x;
        }
    }
}