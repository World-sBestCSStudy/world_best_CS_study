import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N, L;
    static int[] citation;

    public static void main(String[] args) throws Exception {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        citation = new int[N];
        for (int i = 0; i < N; i++) {
            citation[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(citation);

        System.out.println(search());
    }

    static int search() {
        int left = citation[0];
        int right = citation[N - 1] + L;

        while (left <= right) {
            int plus = 0;
            int count = 0;
            int mid = (left + right) >> 1;

            for (int i = N - 1; i >= 0; i--) {
                if (citation[i] >= mid)
                    count++;
                else {
                    if (mid - citation[i] == 1 && plus < L) {
                        plus++;
                        count++;
                    } else break;
                }
            }

            if (count >= mid)
                left = mid + 1;
            else
                right = mid - 1;
        }

        return right;
    }
}
