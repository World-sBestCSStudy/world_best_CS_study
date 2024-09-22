import java.io.*;
import java.util.*;

public class BOJ_1074_Z {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int N, r, c, cnt;
    public static void main(String[] args) throws Exception{
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        cnt = 0;

        int size = (int) Math.pow(2, N);
        recursion(r, c, size);
        System.out.println(cnt);
    }

    static void recursion(int r, int c, int size){
        // complete code
        if (size == 1)
            return;

        if (r < size / 2 && c < size / 2) {
            recursion(r, c, size/2);
        } else if (r < size/2 && size/2 <= c){
            cnt += (size*size/4);
            recursion(r, c-size/2, size/2);
        } else if (size/2 <= r && c < size/2){
            cnt += (size*size/4)*2;
            recursion(r-size/2, c, size/2);
        } else {
            cnt += (size*size/4)*3;
            recursion(r-size/2, c-size/2, size/2);
        }
    }

}


