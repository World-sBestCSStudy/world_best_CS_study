package algo_202411.BOJ;    // BOJ 10775. 공항

import java.io.*;

// ** 경로 압축
public class BOJ_10775_공항 {
    static int[] arr;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // G개의 게이트(1~G)에 P개의 비행기 도착 -> 비행기를 최대한 많이 도킹 시키려 한다.
        int G = Integer.parseInt(br.readLine());
        int P = Integer.parseInt(br.readLine());

        arr = new int[G+1];
        for (int i = G; i > 0; i--){   // 나보다 적은 애들을 카운트 할 수 있게
            arr[i] = i;
        }

        int cnt = 0;
        for (int i = 0; i < P; i++){
            int p = Integer.parseInt(br.readLine());
            if (find(p) != -1)
                cnt++;
            else
                break;
        }
        System.out.println(cnt);
    }

    static int find(int i){
        if (arr[i] == i){
            arr[i] = i - 1;
            return i - 1;
        }

        return arr[i] = find(arr[i]);
    }
}
