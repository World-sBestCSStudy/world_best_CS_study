package algo_202412.BOJ;    // BOJ 2116. 주사위 쌓기

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

public class boj_2116 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int n, ans = 0;
    static int[][] dice;
    public static void main(String[] args) throws Exception{
        // 1-6까지의 숫자가 쓰여진 주사위. (** 마주보는 면의 합이 반드시 7은 아니다.)
        // 1,2,3, ... 아래부터 쌓는다. 이때, 맞닿아있는 면의 숫자가 같아야 함. (** 1번 주사위는 마음대로 놓을 수 있다.)
        // 4개의 옆면 중 어느 한 면의 숫자의 합이 최대가 되도록 쌓자. 이때의 최댓값을 구하자.

        // 풀이: 1번 주사위의 6면을 다 바닥에 가게 해서 모든 경우의 수를 계산한다.
        n = Integer.parseInt(br.readLine());
        dice = new int[n][6];   // 0 - 5 / 1 - 3 / 2 - 4 가 마주본다.
        for (int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 6; j++){
                dice[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < 6; i++){
            // 바닥의 숫자를 보고 반대편 숫자를 구하자. 이 둘을 제외한 나머지가 옆면에 올 수 있음.
            // 옆면에 위치할 수 있는 숫자들 중에 최댓값끼리 더하면 됨 <= 어차피 같은 숫자끼리만 맞닿으면 되고, 주사위를 돌릴수가 있기 때문에 최댓값만 구하면 됨.
            ans = Math.max(ans, cal(dice[0][i]));
        }

        System.out.println(ans);
    }

    static int cal(int num){
        Queue<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{0, num}); // {몇번째 주사위, 바닥숫자}

        int sum = 0;
        while (!q.isEmpty()){
            int[] cur = q.poll();
            int opp = getOpp(cur[0], cur[1]);

            sum += getMax(cur[1], opp);

            if (cur[0] == n-1) continue;

            q.offer(new int[]{cur[0]+1, opp});
        }

        return sum;
    }

    static int getOpp(int idx, int bottom){
        // 지금 바닥에 올 숫자를 가지고 -> 반대편에 올 숫자를 return (= 다음 바닥에 올 숫자)
            // 인덱스 활용
//        int bottomIdx = Arrays.asList(dice[idx]).indexOf(bottom);     // ** 바로 indexOf를 활용해서 리스트로 변환하여 인덱스를 찾고자 함. 이렇게 작성하니까 Arrays.asList(dice[idx])가 List<int[]>가 되어서 int인 bottom을 찾지 못해서 항상 -1 반환을 하는 문제가 발생함.
        int bottomIdx = IntStream.range(0, dice[idx].length)    // for문으로 인덱스를 찾는 것과, Stream을 활용하는 방안(Java 8 이상)이 있었는데, 코드의 단순화를 위해 stream 사용함.
                        .filter(i -> dice[idx][i] == bottom)
                        .findFirst().orElse(-1);

        int topIdx = 0;
        switch (bottomIdx){
            case 0: topIdx = 5; break;
            case 1: topIdx = 3; break;
            case 2: topIdx = 4; break;
            case 3: topIdx = 1; break;
            case 4: topIdx = 2; break;
            case 5: topIdx = 0; break;
            default: break;
        }

        int opp = dice[idx][topIdx];

        return opp;
    }

    static int getMax(int a, int b){
        int max = 0;
        for (int i = 1; i <= 6; i++){
            if (i == a || i == b) continue;
            max = Math.max(max, i);
        }
        return max;
    }
}
