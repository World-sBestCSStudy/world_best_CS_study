import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static Deque<Character> stack = new ArrayDeque<>();
    static Deque<Character> tmp = new ArrayDeque<>();
    static String input;
    static String exp;
    static int size;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        input = br.readLine();
        exp = br.readLine();
        size = exp.length();

        for (int i = 0; i < input.length(); i++) {
            char cur = input.charAt(i);

            if (cur == exp.charAt(size - 1)) {
                for (int j = 0; j < size - 1; j++) {
                    if (stack.isEmpty())
                        break;

                    char temp = stack.pop();
                    if (temp == exp.charAt(size - 2 - j))
                        tmp.push(temp);
                    else {
                        stack.push(temp);
                        break;
                    }
                }

                //문자열 폭발
                if (tmp.size() == size - 1)
                    tmp.clear();
                else {
                    while (!tmp.isEmpty()) {
                        stack.push(tmp.pop());
                    }
                    stack.push(cur);
                }
            } else
                stack.push(cur);
        }
        if (stack.isEmpty())
            System.out.println("FRULA");
        else {
            while (!stack.isEmpty()) {
                sb.append(stack.pollLast());
            }
            System.out.println(sb);
        }
    }
}
