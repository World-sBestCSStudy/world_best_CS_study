//BOJ_2632_피자판매

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;


        int K = Integer.parseInt(br.readLine());

        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[] a = new int[N];
        int[] b = new int[M];

        long aAll = 0;
        long bAll = 0;

        for (int i = 0; i < N; i++) {
            a[i] = Integer.parseInt(br.readLine());
            aAll+=a[i];
        }

        for (int i = 0; i < M; i++) {
            b[i] = Integer.parseInt(br.readLine());
            bAll+=b[i];
        }

        List<Long> aSum = new ArrayList<>();
        List<Long> bSum = new ArrayList<>();
        aSum.add((long) 0);
        bSum.add((long) 0);
        aSum.add(aAll);
        bSum.add(bAll);

        for (int i = 0; i < N; i++) {
            if(a[i]>K) continue;

            long sum = a[i];
            aSum.add(sum);
            for (int j = i + 1; j < (i + N); j++) {
                if (sum + a[j % N] > K || sum + a[j % N] ==aAll) {
                    break;
                }
                sum += a[j % N];
                aSum.add(sum);
            }
        }


        for (int i = 0; i < M; i++) {
            if(b[i]>K) continue;
            long sum = b[i];
            bSum.add(sum);
            for (int j = i + 1; j < (i  + M); j++) {
                if (sum + b[j % M] > K || sum + b[j % M] == bAll) {
                    break;
                }
                sum += b[j % M];
                bSum.add(sum);
            }
        }


        Collections.sort(aSum);

        long count = 0;
        for (long s : bSum) {
            long key = K - s;
            int left = lower(aSum, key);
            int right = upper(aSum, key);

            if (left==-1) continue;

            count += right - left;

        }
        System.out.println(count);


    }

    public static int lower(List<Long> pizza, long key) {
        int left = 0, right = pizza.size();
        while (left < right) {
            int mid = (left + right) / 2;

            if (pizza.get(mid) < key) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        if(left==pizza.size() || pizza.get(left)!=key) return -1;
        return left;
    }


    public static int upper(List<Long> pizza, long key) {
        int left = 0, right = pizza.size();
        while (left < right) {
            int mid = (left + right) / 2;

            if (pizza.get(mid) <= key) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }
}