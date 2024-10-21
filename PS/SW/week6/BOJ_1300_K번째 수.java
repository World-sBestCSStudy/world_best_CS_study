import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, K;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(br.readLine());
        K = Integer.parseInt(br.readLine());

        int left = 1;
        int right = 1_000_000_000;

        while (left <= right) {
            int cnt = 0;
            int mid = (left + right) >> 1;

            for (int i = 1; i <= N; i++) {
                cnt += Math.min(N, mid / i);
            }

            if (cnt >= K)
                right = mid - 1;
            else
                left = mid + 1;
        }

        System.out.println(left);
    }
}
