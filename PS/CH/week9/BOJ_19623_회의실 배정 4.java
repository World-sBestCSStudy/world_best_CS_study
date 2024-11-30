//BOJ_19623_회의실 배정 4
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());

        Node[] tasks=new Node[n];
        for(int i =0; i<n; i++){
            st=new StringTokenizer(br.readLine());
            int x= Integer.parseInt(st.nextToken());
            int y= Integer.parseInt(st.nextToken());
            int cost= Integer.parseInt(st.nextToken());

            tasks[i] = new Node(x,y,cost);
        }

        Arrays.sort(tasks,new Comparator<Node>(){
            public int compare(Node o1, Node o2){
                return o1.y-o2.y;
            }
        });

        int[] dp =new int[n];
        dp[0] = tasks[0].cost;

        for(int i =1; i<n; i++){

            //현재 task에서 겹치지 않는 task를 구한다. 가장 오른쪽(상향선)에 있는걸 가져온다. 왜냐하면 dp가 계속 최대값을 갱신하기 때문
            int prev = prevTask(tasks,i);

            int nowCost = tasks[i].cost;

            if(prev>-1) nowCost+=dp[prev];

            dp[i] = Math.max(dp[i-1], nowCost);
        }
        System.out.println(dp[n-1]);

    }

    public static int prevTask(Node[] tasks, int now){
        int left = 0, right = now-1;

        while(left<=right){
            int mid = (left+right)/2;

            if(tasks[now].x>=tasks[mid].y){
                left=mid+1;
            }else{
                right = mid-1;
            }
        }

        return left-1;
    }
    static class Node{
        int x ,y, cost;

        public Node(int x, int y, int cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }
    }
}
