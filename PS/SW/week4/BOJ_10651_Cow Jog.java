import java.util.Arrays;

public class Main {
    static int N, T, idx;
    static long[] LNDS, list;


    public static void main(String[] args) throws Exception {
        Reader in = new Reader();
        N = in.nextInt();
        T = in.nextInt();
        LNDS = new long[N];
        list = new long[N];
        Arrays.fill(LNDS, Long.MAX_VALUE);

        for (int i = 0; i < N; i++) {
            list[N - 1 - i] = in.nextInt() + T * (long) in.nextInt();

        }

        for (int i = 0; i < N; i++) {
            int location = Arrays.binarySearch(LNDS, list[i]);

            if (location >= 0) {
                while (true) {
                    if (location == idx) {
                        LNDS[idx++] = list[i];
                        break;
                    }

                    if (LNDS[location] == list[i])
                        location++;
                    else {
                        LNDS[location] = list[i];
                        break;
                    }
                }

                continue;
            }

            if (idx == -1 * (location + 1))
                LNDS[idx++] = list[i];
            else
                LNDS[-1 * (location + 1)] = list[i];
        }

        System.out.println(idx);
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
