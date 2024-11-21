import java.util.*; 

public class Main {
static int[] dp;
static int[][] input; 

public static void main(String[] args) throws Exception {
Reader in =new Reader();
int N=in.nextInt(); 

input=new int[N][2];
dp=new int[N+1]; 

for(int i=0;i<N;i++){
input[i][0]= in.nextInt();
input[i][1]= in.nextInt();
} 

for(int i=1;i<=N;i++){
int[] cur=input[N-i];
if(i-cur[0]>=0)
dp[i]=Math.max(dp[i-1],dp[i-cur[0]]+cur[1]);
else
dp[i]=dp[i-1];
} 

System.out.println(dp[N]);
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
}}
