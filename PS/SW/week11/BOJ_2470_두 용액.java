import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static int[] liquid;
    static int leftIdx, rightIdx, value = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        liquid = new int[N];
        for (int i = 0; i < N; i++) {
            liquid[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(liquid);

        int left = 0;
        int right = N - 1;
        while (left < right) {
            int temp = liquid[left] + liquid[right];
            int div = Math.abs(temp);

            if (div < value) {
                value = div;
                leftIdx = left;
                rightIdx = right;
            }

            if (temp == 0)
                break;

            if (temp > 0) right--;
            else left++;
        }

        System.out.println(liquid[leftIdx] + " " + liquid[rightIdx]);
    }
}
