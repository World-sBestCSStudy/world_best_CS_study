import java.util.Arrays;

public class Main {
    static int n, first = Integer.MAX_VALUE;
    static int[][] work;

    public static void main(String[] args) throws Exception {
        Reader in = new Reader();
        n = in.nextInt();

        work = new int[n][2];
        for (int i = 0; i < n; i++) {
            work[i][0] = in.nextInt();
            work[i][1] = in.nextInt();
        }

        Arrays.sort(work, (e1, e2) -> e2[1] - e1[1]);

        for (int i = 0; i < n; i++) {
            if (first > work[i][1]) {
                first = work[i][1] - work[i][0] + 1;
            } else {
                first -= work[i][0];
            }
        }

        System.out.println(first - 1);
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
