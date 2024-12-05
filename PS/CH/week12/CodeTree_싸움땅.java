//CodeTree_싸움땅
import java.io.*;
import java.util.*;

public class Main {
    static int N, M, K;
    static Node[] players;
    static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};
    static int[] scores;
    static int[][] pIds;
    static PriorityQueue<Integer>[][] board;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;


        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        board=new PriorityQueue[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                int gun = Integer.parseInt(st.nextToken());
                board[i][j] = new PriorityQueue<>((o1, o2) -> o2 - o1);
                board[i][j].offer(gun);
            }
        }

        players = new Node[M + 1];
        pIds = new int[N][N];
        for (int i = 1; i <= M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            int d = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            players[i] = new Node(x, y, d, e,0);
            pIds[x][y] = i;
        }

        scores=new int[M+1];
        for(int i =0; i<K; i++){
            sol();
        }

        StringBuilder answer = new StringBuilder();
        for(int i =1; i<=M; i++) answer.append(scores[i]).append(" ");
        System.out.println(answer);
    }

    public static void sol() {
        for (int i = 1; i <= M; i++) {

            //이동할때 node 좌표만 이동하고, pIds는 빈칸인지 플레이어가 있는지 판단후 업데이트한다.
            move(i);

            Node me = players[i];

            //이동한 칸에 플레이어 없을경우
            if(pIds[me.x][me.y] == 0){
                pIds[me.x][me.y] = i;
                board[me.x][me.y].offer(me.gun);
                me.gun = board[me.x][me.y].poll();
            }else{

                //현재 pIds에는 otherId가 있다
                int otherId = pIds[me.x][me.y];
                Node other = players[otherId];

                int s = Math.abs(me.power()-other.power());
                int winnerId = i;
                int loserId = otherId;

                if(me.power() < other.power() || (me.power() == other.power() && me.exp<other.exp)){
                    winnerId = otherId;
                    loserId = i;
                }

                scores[winnerId] += s;


                Node winner = players[winnerId];
                Node loser = players[loserId];

                //지면 총을 내려놓고 이동한다
                board[loser.x][loser.y].offer(loser.gun);
                loser.gun = 0;
                loserMove(loser);
                pIds[loser.x][loser.y] = loserId;

                //기존의 플레이어는 현재 위치에서 떨어트리고 주웠지만, 진 플레이어는 떨어트리고 이동한뒤 줍기 때문에 null 예외 처리
                if(!board[loser.x][loser.y].isEmpty()) loser.gun = board[loser.x][loser.y].poll();

                //이기면 그래로 있고, 진 플레이어가 떨어트린 총을 포함해서 가장 강한거를 get
                pIds[winner.x][winner.y] = winnerId;
                board[winner.x][winner.y].offer(winner.gun);
                winner.gun = board[winner.x][winner.y].poll();
            }
        }

    }

    public static void move(int id) {
        Node node = players[id];

        int nx = node.x + dx[node.dir];
        int ny = node.y + dy[node.dir];

        if (nx < 0 || ny < 0 || nx >= N || ny >= N) {
            node.dir = (node.dir + 2) % 4;
            nx = node.x + dx[node.dir];
            ny = node.y + dy[node.dir];
        }

        pIds[node.x][node.y] = 0;
        node.x = nx;
        node.y = ny;
    }


    public static void loserMove(Node node) {
        //원래 dir로 가는걸 시도해야하기 때문에 현제 dir포함 4번 본다
        for(int i =0; i<5; i++){
            int dir = (node.dir+i)%4;

            int nx = node.x + dx[dir];
            int ny = node.y + dy[dir];

            if(nx <0||ny<0||nx>=N||ny>=N||pIds[nx][ny]>0) continue;

            node.x = nx;
            node.y =ny;
            node.dir=dir;
            break;
        }
    }

    static class Node {
        int x, y, dir, exp,gun;

        public Node(int x, int y, int dir, int exp,int gun) {
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.exp = exp;
            this.gun=gun;
        }
        public int power(){
            return this.exp+this.gun;
        }
    }

}

