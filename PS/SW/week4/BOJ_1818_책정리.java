import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, idx;
    static int[] LIS;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(br.readLine());

        LIS = new int[N];
        Arrays.fill(LIS, Integer.MAX_VALUE);

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            int num = Integer.parseInt(st.nextToken());
            int location = Arrays.binarySearch(LIS, num);

            if (idx == -1 * (location + 1))
                LIS[idx++] = num;
            else
                LIS[-1 * (location + 1)] = num;
        }

        System.out.println(N - idx);
    }
}
