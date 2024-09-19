// BOJ_2110_공유기설치

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st=new StringTokenizer(br.readLine());

        int n =Integer.parseInt(st.nextToken());
        int c= Integer.parseInt(st.nextToken());

        int[] points =new int[n];
        for(int i = 0; i<n; i++){
            points[i]= Integer.parseInt(br.readLine());
        }

        Arrays.sort(points);
        int left = 1, right = points[n-1]+1;

        while(left<right){
            int mid = (left+right)/2;

            int count = 1;
            int prev =0;
            for(int i=1; i<n;i++){
                if(points[i] - points[prev] > mid){
                    prev = i;
                    count++;
                }
            }

            if(count >= c){
                left = mid+1;
            }else{
                right  = mid;
            }
        }

        System.out.println(left);


    }
}

//
