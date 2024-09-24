import java.io.*;
import java.util.*;

public class BOJ_20187_종이접기 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int k, hole;
    static char[] fold;
    static int hor(int origin){
        switch (origin){
            case 0: return 1;
            case 1: return 0;
            case 2: return 3;
            case 3: return 2;
        }
        return -1;
    }

    static int ver(int origin){
        switch (origin){
            case 0: return 2;
            case 1: return 3;
            case 2: return 0;
            case 3: return 1;
        }
        return -1;
    }

    public static void main(String[] args) throws Exception{
        // 입력
        k = Integer.parseInt(br.readLine());
        fold = br.readLine().toCharArray();
        hole = Integer.parseInt(br.readLine());

        // 종이 크기 2^k
        int size = (int) Math.pow(2, k);
        int[][] map = new int[size][size];

        // 매칭시킬 종이
        int[][] minMap = new int[2][2];
        int[] minVert = new int[2];
        int[] minHori = new int[2];


        boolean vert = false;
        boolean horflag = false;

        for (int i = fold.length - 1; i >= 0; i--){
            // 마지막 수직, 수평 접은 것 다 펼쳤으면 2*2 배열 구하기 끝
            if (vert && horflag)
                break;

            char d = fold[i];

            if (!vert){
                if (d == 'U'){  // 위로 접었다면 반대로 아래로 펴주자
                    if (hole < 2) {
                        minVert[0] = hole;
                        minVert[1] = ver(hole);
                    } else {
                        minVert[0] = hole;
                        minVert[1] = ver(hole);
                    }

                    vert = true;
                } else if (d == 'D') {    // 아래로 접었다면 위로 펴주자
                    if (hole < 2) {
                        minVert[0] = ver(hole);
                        minVert[1] = hole;
                    } else {
                        minVert[0] = ver(hole);
                        minVert[1] = hole;
                    }

                    vert = true;
                }
            }

            if (!horflag){
                if (d == 'L'){  // 왼쪽으로 접었다면 오른쪽으로 펴주자
                    if (hole / 2 == 0){
                        minHori[0] = hole;
                        minHori[1] = hor(hole);
                    } else {
                        minHori[0] = hole;
                        minHori[1] = hor(hole);
                    }

                    horflag = true;
                } else if (d == 'R'){   // 오른쪽으로 접었다면 왼쪽으로 펴주자
                    if (hole / 2 == 0){
                        minHori[0] = hor(hole);
                        minHori[1] = hole;
                    } else {
                        minHori[0] = hor(hole);
                        minHori[1] = hole;
                    }

                    horflag = true;
                }
            }
        }

        boolean[] flag = new boolean[4];
        int rem = 0;
        for (int i = 0; i < 2; i++){
            flag[minVert[i]] = true;
            flag[minHori[i]] = true;
        }
        for (int i = 0; i < 4; i++){
            if (!flag[i])
                rem = i;
        }

        // 수직, 수평 배열을 hole을 기준으로 매칭해서 반복되는 4칸을 찾는다
        if (minVert[0] == hole && minHori[0] == hole) {   // 0번이 겹치면
            minMap[0][0] = hole;
            minMap[0][1] = minHori[1];
            minMap[1][0] = minVert[1];
            minMap[1][1] = rem;
        } else if (minVert[0] == hole && minHori[1] == hole){   // 1번이 겹치면
            minMap[0][0] = minHori[0];
            minMap[0][1] = hole;
            minMap[1][0] = rem;
            minMap[1][1] = minVert[1];
        } else if (minVert[1] == hole && minHori[0] == hole) {   // 2번이 겹치면
            minMap[0][0] = minVert[0];
            minMap[0][1] = rem;
            minMap[1][0] = hole;
            minMap[1][1] = minHori[1];
        } else if (minVert[1] == hole && minHori[1] == hole) {   // 3번이 겹치면
            minMap[0][0] = rem;
            minMap[0][1] = minVert[0];
            minMap[1][0] = minHori[0];
            minMap[1][1] = hole;
        }

        for (int i = 0; i < size; i += 2){
            for (int j = 0; j < size; j += 2){
                for (int r = 0; r < 2; r++){
                    for (int c = 0; c < 2; c++){
                        map[i+r][j+c] = minMap[r][c];
                    }
                }
            }
        }

        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                sb.append(map[i][j]).append(" ");
            }
            sb.append("\n");
        }

        System.out.println(sb);
    }
}