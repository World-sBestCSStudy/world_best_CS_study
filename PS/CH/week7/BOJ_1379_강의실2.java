//BOJ_1379_강의실2
import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());

        PriorityQueue<Node> q= new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                if(o1.x==o2.x) return o1.y-o2.y;
                return o1.x-o2.x;
            }
        });

        for(int i =0; i<n; i++){
            st=new StringTokenizer(br.readLine());
            Node node = new Node(Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()));
            q.offer(node);
        }

        PriorityQueue<Node> endPoint = new PriorityQueue<>(new Comparator<Node>(){
            public int compare(Node o1, Node o2){
                return o1.y-o2.y;
            }
        });

        int count = 1;
        Node start = q.poll();
        start.number = 1;
        endPoint.offer(new Node(start.seq, start.x, start.y));


        int[] numbering= new int[n+1];
        numbering[start.seq] = 1;

        while(!q.isEmpty()){

            Node prev = endPoint.peek();

            Node next = q.poll();



            if(prev.y<=next.x){
                numbering[next.seq] = numbering[endPoint.peek().seq];
                endPoint.poll();

            }else{
                count++;
                numbering[next.seq] = count;
            }
            endPoint.offer(next);
        }

        StringBuilder answer =new StringBuilder();
        answer.append(count).append("\n");
        for(int i =1; i<n+1; i++) answer.append(numbering[i]).append("\n");
        System.out.println(answer);


    }
    static class Node{
        int x, y, seq;
        int number;

        public Node(int seq, int x, int y) {
            this.seq=seq;
            this.x = x;
            this.y = y;
        }
    }
}
