//SWEA_1798_범준이의 제주도 여행 계획

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Solution {

    static int N, M, airPort,maxReward;
    static int[][] dist;
    static List<Node> place;
    static List<Integer> hotels;
    static Stack<Integer> path;
    static List<Integer> maxPath;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder answer = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            place=new ArrayList<>();
            path=new Stack<>();
            hotels =new ArrayList<>();
            maxReward = 0;
            maxPath=new ArrayList<>();

            dist = new int[N][N];
            for (int i = 0; i < N - 1; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = i + 1; j < N; j++) {
                    int d = Integer.parseInt(st.nextToken());
                    dist[i][j] = d;
                    dist[j][i] = d;
                }
            }


            airPort = 0;
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                char c = st.nextToken().charAt(0);
                if (c == 'P') {
                    int play = Integer.parseInt(st.nextToken());
                    int reward = Integer.parseInt(st.nextToken());
                    place.add(new Node(c,i,play,reward));
                } else if(c=='A'){
                    airPort = i;
                }else{
                    hotels.add(i);
                }
            }

            for(Node node: place){
                int min = Integer.MAX_VALUE;
                for(int i : hotels){
                    if(min > dist[node.idx][i]){
                        min = dist[node.idx][i];
                        node.hotel = i;
                    }
                }
            }


            dfs(new boolean[N],airPort,0,0,0);
            answer.append("#").append(t).append(" ").append(maxReward).append(" ");
            for(int i : maxPath) answer.append(i+1).append(" ");
            answer.append("\n");

        }
        System.out.println(answer);
    }

    public static void dfs(boolean[] v,int start, int time, int reward, int depth){
        if(depth == M){
            if(maxReward<reward){
                maxReward=reward;
                maxPath=new ArrayList<>(path);
            }
            return;
        }


        boolean canGo = false;

        for (Node node : place) {
            if (!v[node.idx]) {
                int updateTime = time + dist[start][node.idx] + node.play;
                if (depth == M - 1) {
                    updateTime += dist[node.idx][airPort];
                } else {
                    updateTime += dist[node.idx][node.hotel];
                }

                if (updateTime > 540) continue;

                canGo = true;
                v[node.idx] = true;
                path.push(node.idx);
                dfs(v, node.idx, time + dist[start][node.idx] + node.play, reward + node.reward, depth);
                path.pop();
                v[node.idx] = false;
            }
        }

        //아무곳도 못간다.공항가거나 호텔가고 다음날로
        if(!canGo){
            if(depth==M-1 && time+dist[start][airPort] <= 540){
                path.push(airPort);
                dfs(v,airPort,0,reward,depth+1);
                path.pop();
            }else{
                for(Integer i : hotels){
                    if(time+dist[start][i] <= 540){
                        path.push(i);
                        dfs(v,i,0 , reward, depth+1);
                        path.pop();
                    }
                }
            }
        }


    }

    static class Node{
        char type;
        int idx, play, reward;
        int hotel;

        public Node(char type, int idx, int play, int reward) {
            this.type = type;
            this.play = play;
            this.idx=idx;
            this.reward = reward;
        }
    }

}