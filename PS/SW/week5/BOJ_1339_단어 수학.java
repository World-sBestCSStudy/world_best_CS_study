import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, ans;
    static Map<Character, Integer> map = new HashMap<>();
    static int[] pow = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000};

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            String s = br.readLine();

            int size = s.length();
            for (int j = 0; j < size; j++) {
                char temp = s.charAt(j);
                int value = pow[size - 1 - j];

                if (map.containsKey(temp))
                    map.put(temp, map.get(temp) + value);
                else
                    map.put(temp, value);
            }
        }

        List<Integer> nums = new ArrayList<>(map.values());
        nums.sort((e1, e2) -> e2 - e1);

        int mul = 9;
        for (Integer num : nums) {
            ans += num * mul--;
        }

        System.out.println(ans);
    }
}
