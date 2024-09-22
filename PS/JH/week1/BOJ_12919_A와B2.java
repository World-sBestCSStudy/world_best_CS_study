package algorithm2024.sep.day14;

import java.io.*;
import java.util.*;

public class BOJ_12919_A와B2 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception{
        String S = br.readLine();
        String T = br.readLine();
        HashSet<String> set = new HashSet<>();
        int cntSA = 0;
        int cntSB = 0;
        int cntTA = 0;
        int cntTB = 0;
        for (int i = 0; i < S.length(); i++) {
            if(S.charAt(i)=='A') cntSA++;
            if(S.charAt(i)=='B') cntSB++;
        }
        for (int i = 0; i < T.length(); i++) {
            if(T.charAt(i)=='A') cntTA++;
            if(T.charAt(i)=='B') cntTB++;
        }


        if(check(S,T)){
            System.out.println(1);
        }else{
            System.out.println(0);
        }

    }

    static boolean check(String S, String T){
//        System.out.println(T);
        if(S.equals(T)){
            return true;
        }
        if(T.length()==0){
            return false;
        }
        if(T.charAt(T.length()-1)=='A'){
            if(check(S, T.substring(0, T.length()-1))) return true;
        }
        if(T.charAt(0)=='B'){
            T = new StringBuilder(T.substring(1)).reverse().toString();
            if(check(S, T)) return true;
        }
        return false;

    }
}