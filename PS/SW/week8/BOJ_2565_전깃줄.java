import java.util.Arrays;

public class Main {
    static int N, idx;
    static int[] LIS;
    static int[][] input;

    public static void main(String[] args) throws Exception {
        Reader in = new Reader();
        N = in.nextInt();
        input = new int[N][2];
        LIS = new int[N];
        Arrays.fill(LIS, Integer.MAX_VALUE);

        for (int i = 0; i < N; i++) {
            input[i][0] = in.nextInt();
            input[i][1] = in.nextInt();
        }

        Arrays.sort(input, (e1, e2) -> e1[0] - e2[0]);

        for (int i = 0; i < N; i++) {
            int location = Arrays.binarySearch(LIS, input[i][1]);

            if (idx == -1 * (location + 1))
                LIS[idx++] = input[i][1];
            else
                LIS[-1 * (location + 1)] = input[i][1];
        }

        System.out.println(N - idx);
    }

    static class Reader {
        final int SIZE = 1 << 13;
        byte[] buffer = new byte[SIZE];
        int index, size;

        int nextInt() throws Exception {
            int n = 0;
            byte c;
            boolean isMinus = false;
            while ((c = read()) <= 32) {
                if (size < 0) return -1;
            }
            if (c == 45) {
                c = read();
                isMinus = true;
            }
            do n = (n << 3) + (n << 1) + (c & 15);
            while (isNumber(c = read()));
            return isMinus ? ~n + 1 : n;
        }

        boolean isNumber(byte c) {
            return 47 < c && c < 58;
        }

        byte read() throws Exception {
            if (index == size) {
                size = System.in.read(buffer, index = 0, SIZE);
                if (size < 0) buffer[0] = -1;
            }
            return buffer[index++];
        }
    }
}
