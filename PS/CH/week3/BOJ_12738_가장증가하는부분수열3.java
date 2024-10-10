// BOJ_12738_가장증가하는부분수열3

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());

        st=new StringTokenizer(br.readLine());
        int[] numbers= new int[n];
        numbers[0] = Integer.parseInt(st.nextToken());
        int len = 1;

        for(int i =1; i<n; i++){
            int number = Integer.parseInt(st.nextToken());
            if(numbers[len-1]<number){
                numbers[len++] = number;
            }else{
                int temp = lower(numbers,0, len, number);
                numbers[temp] = number;
            }
        }

        System.out.println(len);

    }
    public static int lower(int[] numbers, int left, int right, int number){
        while(left<right){
            int mid = (left+right)/2;

            if(numbers[mid] < number){
                left = mid+1;
            }else{
                right = mid;
            }
        }
        return left;
    }

}
