//CodeTree_사다리타기
import java.util.*;
import java.io.*;
public class Main {
    static boolean[][] line;
    static int[] target;
    static List<Node> point;
    static int N, M;
    static int answer = 0;
    public static void main(String[] args) throws IOException{
        BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st=new StringTokenizer(br.readLine());

        M=Integer.parseInt(st.nextToken());
        N=Integer.parseInt(st.nextToken());

        line = new boolean[17][M+1];

        point = new ArrayList<>();

        for(int i =0; i<N; i++){
            st=new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            line[y][x] = true;
            point.add(new Node(y,x));
        }


        target = new int[M+1];
        for(int i =1; i<=M; i++){
            target[i] = bfs(i);
        }

//        for(int i =0; i<line.length; i++){
//            for(int j = 0; j<line[i].length; j++){
//                if(line[i][j]) System.out.print("1 ");
//                else System.out.print("0 ");
//            }
//            System.out.println();
//        }
//
//        System.out.println(Arrays.toString(target));

        removeLine(0,0);
        System.out.println(point.size()-answer);


    }
    public static void removeLine(int start, int sum){
        boolean flag = true;
        for(int i =1; i<=M; i++){
            if(target[i] != bfs(i)){
                flag = false;
                break;
            }
        }

        if(flag) answer = Math.max(answer,sum);

        for(int i =start; i<point.size(); i++){
            Node node = point.get(i);

            line[node.x][node.y] = false;
            removeLine(i+1, sum+1);
            line[node.x][node.y] = true;

        }

    }
    public static int bfs(int start){
        Queue<Node> q= new LinkedList<>();
        q.offer(new Node(0,start));

        //1이면 오른쪽으로
        //0일때는 왼쪽이 1이면 왼쪽으로, 아니면 밑으
        while(!q.isEmpty()){
            Node node = q.poll();
            if(node.x == 16){
                return node.y;
            }

            int nx = node.x+1;
            int ny = node.y;

            if(line[nx][ny]){
                ny+=1;
            }else{
                if(line[nx][ny-1]){
                    ny-=1;
                }
            }

            q.offer(new Node(nx,ny));
        }
        return -1;
    }
    static class Node{
        int x, y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}