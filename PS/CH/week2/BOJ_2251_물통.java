//BOJ_2251_물통
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<Integer> w = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        bfs(a,b,c);
        Collections.sort(w);
        StringBuilder answer = new StringBuilder();
        for(int i : w) answer.append(i).append(" ");
        System.out.println(answer);

    }

    //물통이 비거나 가득찰때까지 부울 수 있다
    //a가 비어있을때 c안에 담겨있을 수 있는 량은?

    public static void bfs(int a , int b, int c){
        Queue<Node> q= new LinkedList<>();
        boolean[][][] v= new boolean[201][201][201];
        q.offer(new Node(0,0,c));

        while(!q.isEmpty()){
            Node node = q.poll();

            if(v[node.a][node.b][node.c]) continue;

            if(node.a == 0) w.add(node.c);
            v[node.a][node.b][node.c] =true;

            //A -> B
            if(node.a+node.b <= b){
                q.offer(new Node(0,node.a+node.b, node.c));
            }else{
                q.offer(new Node(node.a+node.b-b, b,node.c));
            }

            //A->C
            if(node.a+node.c <= c){
                q.offer(new Node(0,node.b,node.a+node.c));
            }else{
                q.offer((new Node(node.a+node.b-c,node.b,c)));
            }

            //B->A
            if(node.b+node.a<=a){
                q.offer(new Node(node.a+node.b,0,node.c));
            }else{
                q.offer(new Node(a,node.b+node.a-a,node.c));
            }

            //B->C
            if(node.b+node.c<=c){
                q.offer(new Node(node.a,0,node.b+node.c));
            }else{
                q.offer(new Node(node.a, node.b+node.c-c, c));
            }

            //C->A
            if(node.a+node.c<=a){
                q.offer(new Node(node.a+node.c,node.b,0));
            }else{
                q.offer(new Node(a,node.b,node.a+node.c-a));
            }

            //C->B
            if(node.c+node.b<=b){
                q.offer(new Node(node.a, node.b+node.c,0));
            }else{
                q.offer(new Node(node.a,b,node.c+node.b-b));
            }
        }

    }
    static class Node{
        int a,b,c;

        public Node(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

}
