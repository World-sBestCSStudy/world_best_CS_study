import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static int[] LIS;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(br.readLine());

        LIS = new int[N];
        Arrays.fill(LIS, Integer.MAX_VALUE);
        StringTokenizer st = new StringTokenizer(br.readLine());

        int idx = 1;
        LIS[0] = Integer.parseInt(st.nextToken());
        for (int i = 1; i < N; i++) {
            int num = Integer.parseInt(st.nextToken());
            int location = Arrays.binarySearch(LIS, num);

            //같은 숫자가 이미 LIS에 있는 경우
            if (location >= 0)
                continue;

            if (location == -1 * (idx + 1))
                LIS[idx++] = num;
            else {
                LIS[-1 * (location + 1)] = num;
            }
        }

        System.out.println(idx);
    }
}
