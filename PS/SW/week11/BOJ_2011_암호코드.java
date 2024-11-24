import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int[][] dp;
    static int[] nums;
    static final int div = 1000000;

    public static void main(String[] args) throws Exception {
        String s = br.readLine();
        int size = s.length();
        nums = new int[size];

        for (int i = 0; i < size; i++) {
            nums[i] = s.charAt(i) - '0';
        }

        dp = new int[nums.length][2];

        if (nums[0] == 0) {
            System.out.println(0);
            return;
        }

        dp[0][0] = 1;

        for (int i = 1; i < size; i++) {
            if (nums[i] == 0) {
                if (nums[i - 1] == 0 || nums[i - 1] > 2) {
                    System.out.println(0);
                    return;
                }
                dp[i][0] = 0;
                dp[i][1] = dp[i - 1][0];

            } else {
                boolean avail = check(i);

                dp[i][0] = (dp[i - 1][0] + dp[i - 1][1]) % div;

                if (avail) {
                    dp[i][1] = dp[i - 1][0];
                }
            }
        }

        System.out.println((dp[size - 1][0] + dp[size - 1][1]) % div);
    }

    static boolean check(int idx) {
        if (nums[idx - 1] > 2)
            return false;
        else if (nums[idx - 1] == 2) {
            return nums[idx] <= 6;
        } else
            return true;
    }
}
