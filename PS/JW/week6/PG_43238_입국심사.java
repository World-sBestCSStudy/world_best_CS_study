import java.util.*;
class Solution {
    public long solution(int n, int[] times) {
        int max = Arrays.stream(times).max().getAsInt();
        
        long start = 0;
        long end = (long) n * max;
      
        while (start <= end){
            long mid = (start+end)/2;
            
            if (cal(mid, times) < n){
                start = mid+1;
            } else {
                end = mid-1;
            }
        }
            
        
        return start;
    }
    
    private long cal(long tot, int[] times){
        long sum = 0;
        for (int i = 0; i < times.length; i++){
            int t = times[i];
            sum += tot / t;
        }
        return sum;
    }
}