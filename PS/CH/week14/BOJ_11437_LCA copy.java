//BOJ_3665_최종 순위
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N, M;
    static int[] e;
    static boolean[] grade;
    static List<List<Integer>> graph;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder answer = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        for (int t = 0; t < T; t++) {
            N = Integer.parseInt(br.readLine());
            e = new int[N + 1];
            grade = new boolean[N + 1];
            graph = new ArrayList<>();

            for (int i = 0; i < N + 1; i++) {
                graph.add(new ArrayList<>());
            }

            st = new StringTokenizer(br.readLine());
            for (int i = 1; i < N + 1; i++) {
                int g = Integer.parseInt(st.nextToken());
                grade[g] = true;
                for (int j = 1; j < N + 1; j++) {
                    if (!grade[j]) {
                        graph.get(g).add(j);
                    }
                }
            }


            M = Integer.parseInt(br.readLine());
            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());


                int a = graph.get(x).indexOf(y);
                int b = graph.get(y).indexOf(x);
                if(a>=0){
                    graph.get(x).remove(a);
                    graph.get(y).add(x);
                }else{
                    graph.get(y).remove(b);
                    graph.get(x).add(y);
                }

            }


            for (List<Integer> i : graph) {
                for (int j : i) {
                    e[j]++;
                }
            }


            Queue<Integer> q = new LinkedList<>();
            for (int i = 1; i < N + 1; i++) {
                if (e[i] == 0) {
                    q.offer(i);
                }
            }


            List<Integer> seq = new ArrayList<>();
            while (!q.isEmpty()) {
                int x = q.poll();
                seq.add(x);
                for (int nx : graph.get(x)) {
                    if (--e[nx] == 0) {
                        q.offer(nx);
                    }
                }
            }


            //사이클,
            if(seq.size()<N){
                answer.append("IMPOSSIBLE").append("\n");
            }else{
                for(int i : seq){
                    answer.append(i).append(" ");
                }
                answer.append("\n");
            }
        }
        System.out.println(answer);


    }
}