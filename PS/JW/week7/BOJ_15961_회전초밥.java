package ssafy_algorithm.BOJ;    // BOJ 15961. 회전 초밥

import java.io.*;
import java.util.*;

// sliding window ...
public class BOJ_15961_회전초밥 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int N, d, k, c, sum, ans;
    static int[] sushi, cnt;
    static List<Integer> list = new ArrayList<>();

    public static void main(String[] args) throws Exception{
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());   // 벨트 위 접시 수
        d = Integer.parseInt(st.nextToken());   // 초밥 가짓수
        k = Integer.parseInt(st.nextToken());   // 연속해서 먹는 수
        c = Integer.parseInt(st.nextToken()) - 1;   // 쿠폰 번호

        sushi = new int[N];
        for (int n = 0; n < N; n++){
            sushi[n] = Integer.parseInt(br.readLine()) - 1;
        }

        cnt = new int[d];
        int left = 0;
        int right = k;

        sum = 0;
        ans = 0;
        for (int i = left; i < right; i++){ // k개 연속먹기
            eat(sushi[i]);
        }
        coupon();

        do{
            cancel(sushi[left % N]);
            eat(sushi[right % N]);
            coupon();

            left++;
            right++;
        } while (left % N != 0);

        System.out.println(ans);

    }

    // 초밥 먹기!
    static void eat(int sushi){
//        System.out.println("+ "+sushi);
        // 기존에 안 먹은 거
        if (cnt[sushi] == 0){
            sum++;
        }
        cnt[sushi]++;
    }

    // 초밥 먹기 취소!
    static void cancel(int sushi){
//        System.out.println("- "+sushi);
        cnt[sushi]--;
        // 다시 뱉어서 0이 됐다면 -> 그 종류를 하나만 먹었던 거임 -> 먹은 가짓 수에서 하나를 빼자
        if (cnt[sushi] == 0){
            sum--;
        }
    }

    // 쿠폰 체크!
    static void coupon(){
//        System.out.println("현재 먹은 가짓수: "+sum);
        if (cnt[c] == 0){   // 쿠폰으로 받을 초밥은 아직 먹은 적이 없으면
//            System.out.println("쿠폰으로 "+c+" 초밥먹기 => 총 " +(sum+1)+"가지");
            ans = Math.max(ans, sum+1);
        } else {
//            System.out.println("쿠폰으로 먹을 초밥 이미 먹어봄 쩝; " + c);
            ans = Math.max(ans, sum);
        }
    }
}