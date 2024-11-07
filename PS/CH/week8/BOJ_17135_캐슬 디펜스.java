//BOJ_17135_캐슬 디펜스

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N, M, D;
    static List<Node> archer;
    static List<Node> enemyOrigin;
    static List<Node> enemy;
    static boolean[] dead;
    static int maxKill = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());

        archer = new ArrayList<>();
        enemyOrigin = new ArrayList<>();
        enemy = new ArrayList<>();

        int idx = 0;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                int temp = Integer.parseInt(st.nextToken());
                if (temp == 1) enemyOrigin.add(new Node(idx++, i, j));

            }
        }

        dead = new boolean[enemyOrigin.size()];

        choiceAttacker(0, 0);

        System.out.println(maxKill);

    }

    public static void choiceAttacker(int start, int depth) {
        if (depth == 3) {
            maxKill = Math.max(attack(), maxKill);
            return;
        }
        for (int i = start; i < M; i++) {
            archer.add(new Node(N, i));
            choiceAttacker(i + 1, depth + 1);
            archer.remove(archer.size() - 1);
        }
    }

    public static int attack() {
        int kill = 0;

        enemy.clear();
        for (Node node : enemyOrigin) enemy.add(new Node(node.idx, node.x, node.y));

        Arrays.fill(dead, false);

        while (!enemy.isEmpty()) {

            for (Node aNode : archer) {

                int attackIdx = -1;
                int minDist = Integer.MAX_VALUE;

                for (int i = 0; i < enemy.size(); i++) {
                    Node eNode = enemy.get(i);
                    int dist = getDist(aNode.x, aNode.y, eNode.x, eNode.y);
                    if (dist <= D && (minDist > dist || (minDist == dist && eNode.y < enemy.get(attackIdx).y))) {
                        attackIdx = i;
                        minDist = dist;
                    }
                }

                if (attackIdx > -1 && !dead[enemy.get(attackIdx).idx]) {
                    kill++;
                    dead[enemy.get(attackIdx).idx] = true;
                }
            }


            for (Node eNode : enemy) {
                eNode.x += 1;
                if (eNode.x >= N) dead[eNode.idx] = true;
            }

            for (int i = enemy.size() - 1; i >= 0; i--) {
                if (dead[enemy.get(i).idx]) enemy.remove(i);
            }
        }

        return kill;
    }

    public static int getDist(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }


    static class Node {
        int idx, x, y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Node(int idx, int x, int y) {
            this.idx = idx;
            this.x = x;
            this.y = y;
        }
    }
}
