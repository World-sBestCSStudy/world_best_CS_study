package algorithm2024.sep.day15;

import java.io.*;
import java.util.*;

public class BOJ_20437_문자열게임2 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        int T = Integer.parseInt(br.readLine());
        for (int t = 0; t < T; t++) {
            String w = br.readLine();
            int K = Integer.parseInt(br.readLine());
//            1인 경우는 무조건 "1 1"이 답이므로 체크하고 패스
            if (K == 1) {
                sb.append(1).append(" ").append(1).append("\n");
                continue;
            }
            int shortL = 10000;
            int longL = 0;

//            각 문자의 빈도를 저장할 맵
            HashMap<Character, Integer> map = new HashMap<>();

            for (int i = 0; i < w.length(); i++) {
                map.put(w.charAt(i), map.getOrDefault(w.charAt(i),0)+1);
            }

            for (int i = 0; i < w.length(); i++) {
                if(map.get(w.charAt(i))<K){
                    continue;
                }
                int cnt = 1;
                for(int j =i+1;j<w.length();j++){
                    if(w.charAt(j)==w.charAt(i)){
                        cnt++;
                        if(cnt==K){
                            shortL = Math.min(shortL, j-i+1);
                            longL = Math.max(longL, j-i+1);
                            break;
                        }
                    }
                }
                map.put(w.charAt(i), map.get(w.charAt(i))-1);
            }

            if (longL == 0) {
                sb.append(-1).append("\n");
                continue;
            }
            sb.append(shortL).append(" ").append(longL).append("\n");
        }
        System.out.println(sb);
    }
}
