import java.util.Arrays;

public class Main {
    static int N, M, K;
    static int[] card;
    static int[] check;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        Reader in = new Reader();
        N = in.nextInt();
        M = in.nextInt();
        K = in.nextInt();

        card = new int[M];
        check = new int[M];

        for (int i = 0; i < M; i++) {
            card[i] = in.nextInt();
            check[i] = i;
        }

        Arrays.sort(card);

        for (int i = 0; i < K; i++) {
            int num = in.nextInt();
            int location = Arrays.binarySearch(card, num);

            if (location >= 0)
                location++;
            else
                location = -1 * (location + 1);

            sb.append(card[search(location)]).append("\n");
        }

        System.out.println(sb);
    }

    static int search(int location) {
        if (check[location] == location) {
            check[location]++;
            return location;
        }

        return check[location] = search(check[location]);
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
