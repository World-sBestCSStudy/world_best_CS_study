package algorithm2024.sep.day26;
import java.io.*;
import java.util.*;

public class BOJ_1253_좋다 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception{
        int N = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr);

        HashSet<Integer> set = new HashSet<>();

        int cnt =0 ;
        for (int i = 0; i < N; i++) {
            int lo = 0;
            int hi = N-1;
            while(lo<hi){
                if(lo==i){
                    lo++;
                    continue;
                }
                if(hi==i){
                    hi--;
                    continue;
                }
                int sum = arr[lo]+arr[hi];
                if(sum==arr[i]){
                    cnt++;
                    break;
                }
                if(sum<arr[i]){
                    lo++;
                }
                if(sum>arr[i]){
                    hi--;
                }
            }
        }
        System.out.println(cnt);

    }
}
