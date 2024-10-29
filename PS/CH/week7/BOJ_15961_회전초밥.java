//BOJ_15961_회전초밥
import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;


        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        int[] item = new int[n];
        long[] count = new long[d + 1];
        int unique = 0;
        int max = 0;


        for (int i = 0; i < n; i++) {
            item[i] = Integer.parseInt(br.readLine());
        }

        for (int i = 0; i < k; i++) {
            if (count[item[i]] == 0) unique++;
            count[item[i]]++;
        }

        int left = 0, right = k - 1;
        max = unique + (count[c]==0?1:0);

        for (int i = 0; i < n - 1; i++) {
            if (count[item[left]] == 1) {
                unique--;
            }

            count[item[left]]--;

            left = left + 1;
            right = (right + 1) % n;

            if (count[item[right]] == 0) {
                unique++;
            }
            count[item[right]]++;

            max = Math.max(max, unique + (count[c] == 0 ? 1 : 0));

        }

        System.out.println(max);



    }
}
