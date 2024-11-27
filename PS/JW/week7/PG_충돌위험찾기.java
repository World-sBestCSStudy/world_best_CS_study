import java.util.*;

class Solution {
    public int solution(int[][] points, int[][] routes) {
        int answer = 0;     // 충돌 위험 횟수
        int n = points.length;  // 운송 포인트 수
        int x = routes.length;  // 로봇 수
        
        // 1. 각 로봇의 최적 이동 경로 구하기
        // 2. 하나씩 제거하면서 같은 위치라면 충돌위험 카운트
        
        List<List<Pos>> move = new ArrayList<>();
        for (int i = 0; i < x; i++){
            move.add(new ArrayList<>());
        }
        
        for (int i = 0; i < x; i++){
            move.get(i).add(new Pos(points[routes[i][0]-1][0], points[routes[i][0]-1][1]));
            for (int j = 0; j < routes[0].length-1; j++){
                int from = routes[i][j];   // 출발 point
                int to = routes[i][j+1];  // 도착 point
                
                // 각 로봇의 최적 이동 경로 구하기
                moveRobot(points[from-1], points[to-1], move.get(i));
            }
        }
        
        // 맵의 크기 구하기
        int sizeY = 0;
        int sizeX = 0;
        for (int i = 0; i < n; i++){
            sizeY = Math.max(points[i][0], sizeY);
            sizeX = Math.max(points[i][1], sizeX);
        }
        
        // 하나씩 제거하면서 같은 위치라면 충돌위험횟수 카운트
        int[][] cMap = new int[sizeY+1][sizeX+1];
        int maxSize = 0;
        for (int i = 0; i < x; i++){
            maxSize = Math.max(move.get(i).size(), maxSize);
        }
        for (int idx = 0; idx < maxSize; idx++){
            // 카운트 맵 초기화
            for (int i = 1; i <= sizeY; i++){
                Arrays.fill(cMap[i], 0);
            }
            
            for (int i = 0; i < x; i++){
                if (move.get(i).size() > idx){
                    Pos p = move.get(i).get(idx);
                    cMap[p.y][p.x]++;
                }
            }
            System.out.println();
            
            for (int i = 1; i <= sizeY; i++){
                for (int j = 1; j <= sizeX; j++){
                    if (cMap[i][j] >= 2){
                        answer++;
                    }   
                }
            }
        }
            
        return answer;
    }

    void moveRobot(int[] from, int[] to, List<Pos> list){
        int sy = from[0];  // 출발점 y, x 좌표
        int sx= from[1];
        int ey = to[0];      // 도착점 y,x 좌표
        int ex = to[1];

        int moveY = Math.abs(ey - sy);    // 도착점 - 출발점 => 각각 이동해야하는 Y, X 수
        int moveX = Math.abs(ex - sx);

        // 두 좌표가 어떻게 위치하는 지 보자
        int fy = 1;
        int fx = 1;
        if (sy > ey) fy = -1;
        if (sx > ex) fx = -1;

        // Y축 먼저 이동
        for (int i = 1; i <= moveY; i++){
            list.add(new Pos(sy + (i * fy), sx));    
        }
        sy = sy + (moveY * fy);

        // X축 이동
        for (int i = 1; i <= moveX; i++){
            list.add(new Pos(sy, sx + (i * fx)));    

        }
        sy = sy + (moveX * fx);
    }

    
}

class Pos{
    int y, x;
    public Pos(int y, int x){
        this.y = y;
        this.x = x;
    }
}