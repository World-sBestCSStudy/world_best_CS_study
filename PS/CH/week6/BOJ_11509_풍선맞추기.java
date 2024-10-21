//BOJ_11509_풍선맞추기

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {


    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st=new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int[] b = new int[N];
        st=new StringTokenizer(br.readLine());
        for(int i =0; i<N; i++){
            b[i] = Integer.parseInt(st.nextToken());
        }

        int[] h= new int[1_000_001];
        int answer = 0;
        for(int i = 0; i<N; i++){
            if(h[b[i]] > 0){
                h[b[i]] -=1;
                h[b[i]-1]+=1;
            }else{
                h[b[i]-1]+=1;
                answer+=1;
            }
        }
        System.out.println(answer);

    }
}