//SWEA_1244_최대상금
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {
    static int[] numbers;
    static int N, M, maxNumber;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder answer = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            st = new StringTokenizer(br.readLine());

            char[] temp = st.nextToken().toCharArray();
            N= temp.length;
            numbers=new int[N];
            for(int i =0; i<temp.length; i++){
                numbers[i] = temp[i] - 48;
            }

            M = Integer.parseInt(st.nextToken());
            maxNumber = Integer.MIN_VALUE;

            if(N<M) M = N;
            sol(0);

            answer.append("#").append(t).append(" ").append(maxNumber).append("\n");

        }
        System.out.println(answer);
    }

    public static void sol(int depth){
        if(depth==M){
            int temp = numbers[0];
            for(int i =1; i<N; i++){
                temp = (temp*10) + numbers[i];
            }

            maxNumber=Math.max(maxNumber,temp);
            return;
        }

        for(int i =0; i<N; i++){
            for(int j = i+1; j<N; j++){
                swap(i,j);
                sol(depth+1);
                swap(i,j);
            }
        }

    }


    public static void swap(int x, int y) {
        int temp = numbers[x];
        numbers[x] = numbers[y];
        numbers[y] = temp;
    }

}