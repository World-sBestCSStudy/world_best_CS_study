import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, ans;
    static int[] input;
    static int[] count = new int[1000001];

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        input = new int[N];

        for (int i = 0; i < N; i++) {
            input[i] = Integer.parseInt(st.nextToken());

            if (count[input[i]] == 0) {
                ans++;
                count[input[i] - 1] += 1;
            } else {
                count[input[i]]--;
                count[input[i] - 1]++;
            }
        }

        System.out.println(ans);
    }
}
