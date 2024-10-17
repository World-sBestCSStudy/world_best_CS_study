import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

public class SWEA_1218_괄호짝짓기 {
    // (), [], {}, <>
    // 1 - 유효, 0 - 무효
    // (<)> 이런 경우는 없다. 항상 테스트케이스는 (<>) 이런 식으로 나옴. => 스택 사용하기
    static int len, answer;
    static char[] origin;
    static Deque<Character> stack = new ArrayDeque<>();
    
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        for (int tc = 1; tc <= 10; tc++){
            len = Integer.parseInt(br.readLine());
            origin = br.readLine().toCharArray();

            stack.clear();
            answer = 0;

            if (len % 2 != 0) {
                sb.append("#").append(tc).append(" ").append(answer).append("\n");
                continue;
            }

            for (int i = 0; i < len; i++){
                char token = origin[i];
                if (token == '(' || token == '[' || token == '{' || token == '<') {
                    stack.push(token);
                } else {
                    if (stack.isEmpty()){
                        stack.push(token);
                        break;
                    }

                    char prev = stack.peek();
                    if (token == '>' && prev != '<') break;         // 매칭이 안되면 꺼내지 않고 break;
                    else if (token == ')' && prev != '(') break;
                    else if (token == ']' && prev != '[') break;
                    else if (token == '}' && prev != '{') break;
                    else stack.pop();   // 매칭되면 꺼내기
                }
            }

            if (stack.isEmpty()) answer = 1;
            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }
        System.out.println(sb);
    }
}