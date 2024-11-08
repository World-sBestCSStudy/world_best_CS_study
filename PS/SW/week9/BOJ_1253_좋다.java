import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, ans, zero;
    static int[] input;
    static Map<Integer, Integer> count = new HashMap<>();

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(br.readLine());
        input = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            input[i] = Integer.parseInt(st.nextToken());

            if (input[i] == 0)
                zero++;

            if (!count.containsKey(input[i]))
                count.put(input[i], 1);
            else
                count.put(input[i], count.get(input[i]) + 1);
        }

        for (int i = 0; i < N - 1; i++) {
            for (int j = i + 1; j < N; j++) {
                int sum = input[i] + input[j];

                if (count.containsKey(sum)) {
                    if (input[i] != 0 && input[j] != 0) {
                        ans += count.get(sum);
                        count.remove(sum);
                    } else if (input[i] == 0 && input[j] == 0) {
                        if (zero >= 3) {
                            ans += count.get(sum);
                            count.remove(sum);
                        }
                    } else {
                        if (count.get(sum) >= 2) {
                            ans += count.get(sum);
                            count.remove(sum);
                        }
                    }
                }
            }
        }
        System.out.println(ans);
    }
}
