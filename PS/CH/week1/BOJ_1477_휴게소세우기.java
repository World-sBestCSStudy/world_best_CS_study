// BOJ_1477_휴게소세우기

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int[] points = new int[n+2];
        points[n+1] = l;
        for (int i = 0; i < n; i++) {
            points[i+1] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(points);

        int[] diff = new int[n+1];
        for(int i = 0; i<n+1; i++){
            diff[i] = points[i+1]-points[i];
        }


        int left = 1, right = l + 1;

        while (left < right) {
            int mid = (left + right) / 2;

            int count = 0;
            for (int i = 0; i < diff.length; i++) {
                count+=diff[i]/mid;
                if(diff[i]%mid==0)count-=1;
            }


            if(count <= m){
                right=mid;
            }else{
                left =mid+1;
            }
        }
        System.out.println(left);


    }
}

//
