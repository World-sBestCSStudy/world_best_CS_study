import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, M, time;
    static int[] crane;
    static List<Integer> weight = new ArrayList<>();
    static boolean[] pass;
    static StringTokenizer st;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(br.readLine());

        crane = new int[N];
        pass = new boolean[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            crane[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(crane);

        M = Integer.parseInt(br.readLine());

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            weight.add(Integer.parseInt(st.nextToken()));
        }

        weight.sort((e1, e2) -> e1 - e2);

        if (crane[N - 1] < weight.get(M-1)) {
            System.out.println(-1);
            return;
        }

        while (!weight.isEmpty()) {
            for (int i = N - 1; i >= 0; i--) {
                if (pass[i])
                    break;

                for (int j = weight.size() - 1; j >= 0; j--) {
                    if (weight.get(j) <= crane[i]) {
                        weight.remove(j);
                        break;
                    }

                    if (j == 0) {
                        pass[i] = true;
                    }
                }
            }
            time++;
        }

        System.out.println(time);
    }
}
