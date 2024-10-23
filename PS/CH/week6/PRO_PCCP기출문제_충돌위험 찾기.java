import java.util.*;

class Solution {
    int[] dx = {-1,1,0,0}, dy={0,0,1,-1};
    int N = 101;
    Queue<Node>[] r;
 
    public int solution(int[][] points, int[][] routes) {
        int answer = 0;
    
        r=new Queue[routes.length];
        
        for(int i =0; i<routes.length; i++){
            int minute = 0;
            r[i] = new LinkedList<>();
            r[i].offer(new Node(points[routes[i][0]-1][0]-1,points[routes[i][0]-1][1]-1));
            
            
            for(int j =0; j<routes[i].length-1; j++){
                int startX = points[routes[i][j]-1][0]-1;
                int startY = points[routes[i][j]-1][1]-1;
                int endX = points[routes[i][j+1]-1][0]-1;
                int endY = points[routes[i][j+1]-1][1]-1;      
                
                minute =  dijkstra(startX, startY, endX,endY,minute,i);
            }
        }

        int count = 0;
        while(count < routes.length){
            count=0;
            int[][] time= new int[N][N];
            
            for(Queue<Node> q : r){
                if(q.isEmpty()) {
                    count++;
                    continue;
                }
                Node node = q.poll();
                time[node.x][node.y]+=1;
            }
            
            for(int i =0; i<N; i++){
                for(int j=0; j<N; j++){
                    if(time[i][j]>1) answer++;
                }
            }
        }
        
        return answer;
    }
    
    public int dijkstra(int startX, int startY, int endX, int endY, int minute,int id){
        Queue<Node> q= new LinkedList<>();
        int[][] dist =new int[N][N];
        
        int[][] routeX = new int[N][N];
        int[][] routeY = new int[N][N];
    
        
        q.offer(new Node(startX,startY,minute));
        for(int i =0; i<N; i++) Arrays.fill(dist[i],Integer.MAX_VALUE);
        dist[startX][startY] = minute;
        
        while(!q.isEmpty()){
            Node node = q.poll();
            if(node.x==endX && node.y==endY) break;
            for(int i =0; i<4; i++){
                int nx =node.x+dx[i];
                int ny = node.y+dy[i];
                
                if(nx<0||ny<0||nx>=N||ny>=N) continue;
                
                if(dist[nx][ny] > dist[node.x][node.y]+1){
                    dist[nx][ny] = dist[node.x][node.y]+1;
                    routeX[nx][ny] = node.x;
                    routeY[nx][ny] = node.y;
                    q.offer(new Node(nx,ny,dist[nx][ny]));
                }
            }
        }
        
        minute = dist[endX][endY];
                
        int x = endX;
        int y = endY;

        Stack<Node> stack=new Stack<>();
        while(true){    
            if(x==startX && y==startY){
                break;
            } 
            stack.push(new Node(x,y));
    
            int nx=routeX[x][y];
            int ny=routeY[x][y];
            x=nx;
            y=ny;
        
        }
        
        while(!stack.isEmpty()){
            r[id].offer(stack.pop());
            
        }
        // for(int i =0; i<N; i++) System.out.println(Arrays.toString(temp[i]));
        System.out.println();
        return dist[endX][endY];
        
    }
    static class Node{
        int x,y,cost;
        public Node(int x, int y, int cost){
            this.x=x;
            this.y=y;
            this.cost=cost;
        }
        public Node(int x, int y){
            this.x=x;
            this.y=y;
        }
    }
}