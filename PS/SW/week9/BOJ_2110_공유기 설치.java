import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N, C;
    static int[] home;

    public static void main(String[] args) throws Exception {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        home = new int[N];
        for (int i = 0; i < N; i++) {
            home[i] = Integer.parseInt(br.readLine());
        }

        Arrays.sort(home);

        System.out.println(search());
    }

    static int search() {
        int left = 1;
        int right = home[N - 1] - home[0];

        while (left <= right) {
            int mid = (left + right) >> 1;

            int count = 1;
            int pre = home[0];

            for (int i = 1; i < N; i++) {
                if (home[i] - pre >= mid) {
                    pre = home[i];
                    count++;
                }
            }

            if (count >= C)
                left = mid + 1;
            else
                right = mid - 1;
        }

        return right;
    }
}
