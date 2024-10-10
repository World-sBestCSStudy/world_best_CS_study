import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static int[] LIS = new int[100000];
    static StringTokenizer st;

    public static void main(String[] args) throws Exception {
        String s = "";
        while ((s = br.readLine()) != null && !(s.isEmpty())) {
            N = Integer.parseInt(s.trim());
            Arrays.fill(LIS, Integer.MAX_VALUE);
            int idx = 0;

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                int num = Integer.parseInt(st.nextToken());
                int location = Arrays.binarySearch(LIS, num);

                if (location >= 0)
                    continue;

                if (idx == -1 * (location + 1))
                    LIS[idx++] = num;
                else
                    LIS[-1 * (location + 1)] = num;
            }
            System.out.println(idx);
        }
    }
}
