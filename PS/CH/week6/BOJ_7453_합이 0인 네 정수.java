//BOJ_7453_합이 0인 네 정수
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        int[] A = new int[N];
        int[] B = new int[N];
        int[] C = new int[N];
        int[] D = new int[N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            A[i] = Integer.parseInt(st.nextToken());
            B[i] = Integer.parseInt(st.nextToken());
            C[i] = Integer.parseInt(st.nextToken());
            D[i] = Integer.parseInt(st.nextToken());
        }

        int[] numbers = new int[N * N];
        int idx = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                numbers[idx++] = A[i] + B[j];
            }
        }
        Arrays.sort(numbers);

        long answer = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int sum = (C[i] + D[j]) * -1;

                int left = lower(numbers, sum);
                int right = upper(numbers, sum);


                answer += (right - left);
            }
        }
        System.out.println(Math.abs(answer));

    }

    public static int lower(int[] numbers, int key) {
        int left = 0, right = numbers.length-1;
        while (left <= right) {
            int mid = (left + right) / 2;

            if (numbers[mid] < key) {
                left = mid + 1;
            } else {
                right = mid-1;
            }
        }
        return right;
    }

    public static int upper(int[] numbers, int key) {
        int left = 0, right = numbers.length-1;
        while (left <= right) {
            int mid = (left + right) / 2;

            if (numbers[mid] <= key) {
                left = mid + 1;
            } else {
                right = mid-1;
            }
        }
        return right;
    }
}