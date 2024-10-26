//BOJ_6290_제자리 멀리뛰기
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st=new StringTokenizer(br.readLine());
        int d = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[] rocks =new int[n+1];
        for(int i =1; i<n+1; i++){
            rocks[i] = Integer.parseInt(br.readLine());
        }
        Arrays.sort(rocks);

        int left = 0, right = d+1;
        while(left<right){
            int mid = (left+right)/2;

            //학생들은 최대 mid만큼 뛸수 있다
            //만약 돌 사이의 거리가 mid보다 작으면 다음으로! 언제까지? 돌 사이의 거리가 mid를 초과하면 이전 돌까지 뛴다
            int remove=0;
            int prev = 0;
            for(int i =1; i<n+1; i++){
                if(rocks[i] - rocks[prev]<mid){
                    remove++;
                }else{
                    prev=i;
                }
            }

            if(remove<=m){
                left=mid+1;
            }else{
                right=mid;
            }

        }

        System.out.println(left-1);

    }
}