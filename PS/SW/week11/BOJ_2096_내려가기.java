public class Main {
    static int N;
    static int[][] input, maxDp, minDp;

    public static void main(String[] args) throws Exception {
        Reader in = new Reader();
        N = in.nextInt();

        input = new int[N][3];
        maxDp = new int[N][3];
        minDp = new int[N][3];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < 3; j++) {
                input[i][j] = in.nextInt();
            }
        }

        for (int i = 0; i < 3; i++) {
            maxDp[0][i] = input[0][i];
            minDp[0][i] = input[0][i];
        }

        for (int i = 1; i < N; i++) {
            maxDp[i][0] = Math.max(maxDp[i - 1][0], maxDp[i - 1][1]) + input[i][0];
            maxDp[i][1] = Math.max(Math.max(maxDp[i - 1][0], maxDp[i - 1][1]), maxDp[i - 1][2]) + input[i][1];
            maxDp[i][2] = Math.max(maxDp[i - 1][1], maxDp[i - 1][2]) + input[i][2];

            minDp[i][0] = Math.min(minDp[i - 1][0], minDp[i - 1][1]) + input[i][0];
            minDp[i][1] = Math.min(Math.min(minDp[i - 1][0], minDp[i - 1][1]), minDp[i - 1][2]) + input[i][1];
            minDp[i][2] = Math.min(minDp[i - 1][1], minDp[i - 1][2]) + input[i][2];
        }

        int max = Math.max(Math.max(maxDp[N - 1][0], maxDp[N - 1][1]), maxDp[N - 1][2]);
        int min = Math.min(Math.min(minDp[N - 1][0], minDp[N - 1][1]), minDp[N - 1][2]);

        System.out.println(max + " " + min);
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
