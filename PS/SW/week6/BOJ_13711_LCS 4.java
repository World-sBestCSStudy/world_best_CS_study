import java.util.Arrays;

//첫 번째 문자열의 값을 두 번째 문자열의 순서로 치환하고 lis를 구함
public class Main {
    static int N, idx;
    static int[] lis;
    static int[] first;
    static int[] second;

    public static void main(String[] args) throws Exception {
        Reader in = new Reader();
        N = in.nextInt();
        first = new int[N];
        second = new int[N + 1];
        lis = new int[N];
        Arrays.fill(lis, Integer.MAX_VALUE);

        for (int i = 0; i < N; i++) {
            first[i] = in.nextInt();
        }

        for (int i = 0; i < N; i++) {
            second[in.nextInt()] = i;
        }

        for (int i = 0; i < N; i++) {
            first[i] = second[first[i]];
        }

        for (int i = 0; i < N; i++) {
            int location = Arrays.binarySearch(lis, first[i]);

            if (idx == -1 * (location + 1)) {
                lis[idx++] = first[i];
            } else
                lis[-1 * (location + 1)] = first[i];
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
