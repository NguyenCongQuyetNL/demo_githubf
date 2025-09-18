package praticalLTDT.lap1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class practice  {
    protected int  numVexs ;
    protected int [][] Matrixs;
    protected boolean [] check;
    protected List<Integer>dfsResult=new ArrayList<>();
    protected boolean[] visited;
    protected List<Integer>resultHalmiton;
    protected int[] pathH;
    boolean[] track = new boolean[numVexs];
    boolean in=false;
    boolean foundCycle=false;
    boolean foundPath=false;
     public   int[][] readFilefromText(String name) throws IOException {
        File input = new File(name);
        BufferedReader read = new BufferedReader(new FileReader(input));
        String firstRow  = read.readLine();
        //System.out.println(firstRow);
        this.numVexs = Integer.parseInt(firstRow);
        this.Matrixs = new int[numVexs][numVexs];
        this.check = new boolean[numVexs];
        this.track = new boolean[numVexs];
        for (int i =0;i<numVexs;i++){
            firstRow = read.readLine();
            //System.out.println("buoc"+i);
            String [] row =  firstRow.split(" ");
            // System.out.println(Arrays.toString(row));
            for(int j =0 ;j<numVexs;j++){

                Matrixs[i][j]=Integer.parseInt(row[j]);
            }
        }
        return Matrixs;
    }

    public practice() {
    }

    public void resetAll() {
         System.out.println(numVexs);
        for (int i = 0; i < numVexs; i++) {
            track[i]=false;
        }
    }
    /**
     *
     * @param v
     * @return
     */
    public int [][] primH(int v){
         int [][]result = new int[numVexs][numVexs];
         Stack<Integer> stack = new Stack<>();
         stack.push(v);
         while(!stack.isEmpty()){
             v = stack.pop();
             check[v]=true;
             int index =0;
             int min = Integer.MAX_VALUE;
             for(int i = 0 ;i<numVexs;i++){
                 if(Matrixs[v][i]!=0&&check[i]==false){
                     stack.push(i);
                     if(Matrixs[v][i]<min){
                         index = i;
                         min = Matrixs[v][i];
                     }
                 }
             }
             if(min != Integer.MAX_VALUE){
                 result[v][index]=min;
                 result[index][v]=min;
             }
         }

         return result;
    }
    // biến count,kiểm tra sem array lưu cái cạch có lỗi không
    // kruskal

    /**
     *
     * @param matrix
     * @return
     */
    /*public int [][] kruskal(int [][]matrix){
         int [][]result = new int [numVexs][numVexs];
         ArrayList<Canh> Canhs = new ArrayList<>();
         boolean [][] track = new boolean[numVexs][numVexs];
         int sum=0;
         check = new boolean[numVexs];
         for(int i =0;i<numVexs;i++){
             for(int j =0 ;j<numVexs;j++){
                 // khong cho check[i]==true có thể gây ra lỗi

                 if(matrix[i][j]!=0 && track[i][j]==false ){
                     Canhs.add(new Canh(i,j,matrix[i][j]));
                     track[i][j]=true;
                     track[j][i]=true;
                 }
             }
         }

         Canhs.sort(new Comparator<Canh>() {
             @Override
             public int compare(Canh o1, Canh o2) {
                 if(o1.getTs()>o2.getTs()){
                 return 1;}
                 else  return -1;
             }
         });
        for(int i = 0;i<Canhs.size();i++){
            System.out.println(Canhs.get(i).toString());
        }
         int count =0;
         int k =0;
         while (count<numVexs&& Canhs.isEmpty()==false && k< Canhs.size()){
             if(checkCycle(Canhs.get(k).getDd(), Canhs.get(k).getDc(),result)==false ){
                 count++;
                 addEdge(result,Canhs.get(k).getDd(),Canhs.get(k).getDc(), Canhs.get(k).getTs());
                 sum+=Canhs.get(k).getTs();
                 k++;
             }else k++;
         }
         System.out.println(sum);
        return result;
    }*/

    /**
     *
     * @param matrix
     * @return
     */
    public  String print(int [][]matrix){
        String result ="";
        for(int i =0;i<matrix.length;i++){
            for(int j =0;j<matrix.length;j++){
                result+=matrix[i][j]+" ";
            }
            result+="\n";
        }
        return result;
    }

    /**
     *
     * @param i
     * @param matrix
     * @return
     */
    public List<Integer>Dfs(int i , int [][]matrix ){
         List<Integer>result = new ArrayList<>();
         check = new boolean[numVexs];
         Stack<Integer> stack = new Stack<>();
         stack.push(i);
         while(!stack.isEmpty()){
             int h = stack.pop();
             if (check[h]==false)result.add(h);
             check[h]=true;
             for(int j = matrix.length-1 ;j>=0;j--){
                 if(matrix[h][j]!=0&&check[j]==false){
                     stack.push(j);
                 }
             }
         }
         return result;
    }

    /**
     *
     * @param v
     * @param matrix
     */
    public void dfsRecusive(int v,int [][]matrix){
        dfsResult.add(v);
        for (int i =0;i<matrix.length;i++){
            if(matrix[v][i]!=0&&check[i]==false){
                check[i]=true;
                dfsRecusive(i,matrix);
            }
        }
    }
    /**
     *
     * @param pa
     * @param den
     * @param w
     * @return
     */
    public boolean checkCycle(int pa,int den ,int [][] w ){
         Queue<Integer> queue = new ArrayDeque<>();
         check = new boolean[numVexs];
         queue.add(pa);
         check[pa]=true;
         check[den]=true;
         while(!queue.isEmpty()){
             int s = queue.poll();
             for(int i =0;i<numVexs;i++){
                 if(w[s][i]!=0) {
                     if(check[i]==false){
                     queue.add(i);
                     check[i] = true;
                     }else if(i==den)return true ;
                 }
             }
         }
         return false;
    }

    /**
     *
     * @param matrix
     * @return
     */
    public boolean checkCycleEuler(int [][]matrix){
        for(int i =0;i<matrix.length;i++){
            if(bac(i,matrix)%2!=0||bac(i,matrix)==0)return false;
                if(checkLienThong())return false;
        }
        return true;
    }

    public boolean hasCycle(int u, int v, int[][] matrix) {
        // TODO Auto-generated method stub
        Queue<Integer> list = new ArrayDeque<>();
        list.add(u);
        check[u] = true;
        check[v]=true;
        while (!list.isEmpty()) {
            int k = list.poll();
            for (int i = 0; i < numVexs; i++) {
                if (matrix[k][i] != 0) {
                    if (!check[i]) {
                        check[i] = true;
                        list.add(i);
                    } else if (i == v) {
                        // System.out.println("Do thi co chu trinh");
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /**
     *  this is function remove Edge of matrix ke
     * @param matrix
     * @param v1
     * @param v2
     */
    public void removeEdge(int [][]matrix,int v1,int v2) {
        if(v1<numVexs&&v2>=0&&v1>=0&&v2<numVexs) {
            matrix[v1][v2]-=1;
            matrix[v2][v1]-=1;
        }
    }

    /**
     *
     * @param v
     * @return
     */
    public List<Integer>CycleEuler(int v ){
        check = new boolean[numVexs];
        int[][]matrix = Matrixs;
        List<Integer>result= new ArrayList<>();
        result.add(v+1);
        if(checkCycleEuler(Matrixs)!=false)return null;
        Stack<Integer>stack = new Stack<>();
        stack.push(v);
        check[v]=true;
        while(!stack.isEmpty()){
            int h = stack.peek();
            ArrayList<Integer>pool = new ArrayList<>();;
            for(int i =0 ;i< matrix.length;i++){
                if(matrix[h][i]>0){
                    pool.add(i);
                    stack.push(i);
                    removeEdge(matrix,h,i);
                    break;
                }
            }
            if(pool.isEmpty()){
                result.add(0,stack.pop()+1);
            }
        }
        return result;
    }
    public List<Integer>findEulerBFS(int v){
        List<Integer>result = new ArrayList<>();
        Queue<Integer>queue= new ArrayDeque<>();
        result.add(v);
        return result;
    }
    /**
     *
     * @param matrix
     * @return
     */
    public int[][] SpanningTreeByKruskal (int[][] matrix){
         int [][] result = new int [numVexs][numVexs];


        return result;
    }

    /**
     *
     * @param matrix
     * @param v1
     * @param v2
     * @param ts
     */
    public void addEdge(int [][]matrix,int v1,int v2 , int ts) {
        if(v1<numVexs&&v2>=0&&v1>=0&&v2<numVexs ) {
            matrix[v1][v2]=ts;
            matrix[v2][v1]=ts;
        }
    }
    /**
     * this is function check pragram has lien thong
     * @return
     */
    public  boolean checkLienThong() {
        int l=0;
        dfsRecusive(l,Matrixs);
        if(dfsResult.size()== Matrixs.length)return true;
        return false;
    }

    /**
     * TINH BAC CUA DINH
     * @param v
     * @param matrix
     * @return
     */
    public int bac(int v,int [][]matrix){
        int count=0;
        for(int i =0;i<matrix.length;i++){
            if(matrix[v][i]!=0){
                count++;
            }
        }
        return count;
    }
    /**
     * TO MAU
     * @param v
     * @param matrix
     * @return
     */
    public void  toMau(int v,int[][]matrix) {
        int [] result = new int [matrix.length];
        check = new boolean[numVexs];
        ArrayList<Integer>temp = new ArrayList<>();
        for(int i =0;i< matrix.length;i++){
            temp.add(i);
            temp.sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {//o1 laf thang cos sanw o2 cos sau
                    if(bac(o1,matrix)>bac(o2,matrix)){
                        return -1 ;
                    }else{
                        if(bac(o1,matrix)<bac(o2,matrix)){
                            return 1 ;
                        }
                    }
                    return 1;
                }
            });
        }
        int color =1;
        System.out.println(temp);
        for(int i =0;i<temp.size();i++){
            if(check[temp.get(i)]==false){
                check[temp.get(i)]=true;
                result[temp.get(i)]=color;
                for(int j =0;j<numVexs;j++){
                    if(matrix[temp.get(i)][j]==0&&check[j]==false){
                        check[j]=true;
                        result[j]=color;
                        break;
                    }
                }
                color++;
            }
        }
        System.out.println(Arrays.toString(result));
    }
    /*public void color(int v, int [][]matrix){
        int []result = new int[numVexs];
        boolean []track = new boolean[numVexs];
        ArrayList<Integer>temp = new ArrayList<>();
        for(int i =0;i<matrix.length;i++){
            temp.add(i);
            temp.sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    if(bac(o1,matrix)>bac(o2, matrix)) {
                        return -1;
                    }else{
                        if(bac(o1,matrix)<bac(o2, matrix)) {
                            return 1;
                        }
                    }
                    return 1;
                }
            });
        }
        int color = 1;
        for(int i =0;i<numVexs;i++){
            if(track[temp.get(i)]==false){
                track[temp.get(i)]=true;
                result[temp.get(i)]=color;
            for(int j =0;j<numVexs;j++) {
                if(matrix[temp.get(i)][j]==0&&track[j]==false){
                    track[j]=true;
                    result[j]=color;
                    break;
                }
            }
            color++;
            }

        }
        System.out.println(temp);
        System.out.println(Arrays.toString(result));
    }*/
    /**
     *
     * @param matrix
     */
    public int[][] prim(int[][]matrix) {
        //if(checkLienThong()==false)return null;
        int [][] results = new int[numVexs][numVexs];
        boolean[]visited = new boolean[numVexs];
        int v =0;
        Queue<Canh>queue= new PriorityQueue<>(new Comparator<Canh>() {
            @Override
            public int compare(Canh o1, Canh o2) {
                if (o1.getTs() > o2.getTs()) {
                    return 1;
                } else
                return -1;
            }
        });
        int count=0;
        while(count<numVexs) {
           visited[v]=true;
            for (int j = 0; j < numVexs; j++) {
                if (Matrixs[v][j]!=0&&visited[j]==false){
                    queue.add(new Canh(v,j,Matrixs[v][j]));
                    System.out.println(queue);
                }
            }
            Canh canh = queue.poll();
            while(checkCycle(canh.getDd(), canh.getDc(), results)==true){
                canh= queue.poll();
                if(queue.isEmpty())return results;
            }
            ++count;
            results[canh.getDd()][canh.getDc()]=results[canh.getDc()][canh.getDd()]= canh.getTs();
            v = canh.getDc();
        }
        return results;
    }
    /**
     *
     * @param d
     * @param c
     * @param matrix
     * @return
     */
    public boolean checkCycle11(int d,int c,int[][]matrix){
        Queue<Integer>queue= new ArrayDeque<>();
        boolean[] visited = new boolean[numVexs];
        visited[d]=true;
        visited[c]=true;
        while(!queue.isEmpty()) {
            d = queue.poll();
            for (int i = 0; i < matrix.length; i++) {
                if(matrix[d][i]!=0){
                    if(visited[i]==false){

                    }else if(i==c)return false;
                }
            }
        }
        return true;
    }
    /*public int[] dijktra(int v ,int [][]matrix) {
        int[] result = new int[numVexs];
        int[] path = new int[numVexs];
        for (int i = 0; i < numVexs; i++) {
            result[i] = Integer.MAX_VALUE / 2;
            path[i] = -1;
        }
        result[0] = 0;
        for (int i = 0; i < numVexs; i++) {
            for (int j = 0; j < numVexs; j++) {
                if (matrix[i][j] != 0) {
                    if (result[j] > result[i] + matrix[i][j]) {
                        result[j] = result[i] + matrix[i][j];
                        path[j] = i;
                    }
                }
            }
        }
        return result;
    }*/
      /*  int []result = new int[numVexs];
        int [] path  = new int [numVexs];
        for(int i =0;i<numVexs;i++){
            result[i]= Integer.MAX_VALUE;
            path[i]=-1;

        }
        result[v]=0;
        for(int i =0;i<numVexs;i++){
            System.out.println(Arrays.toString(result));
            for(int j =0;j<numVexs;j++){
                if(matrix[i][j]!=0){
                    if(result[j]>result[i]+matrix[i][j]){
                        result[j]=result[i]+matrix[i][j];
                        path[j]=i;
                    }
                }
            }
        }
        return result;
    }*/
    public List<Integer>chutrinhEuler(){
       int [][] matrix = Matrixs;
        List<Integer>result = new ArrayList<>();
        boolean []track  = new boolean [numVexs];
        Stack<Integer>stack= new Stack<>();
        int v =0;
        stack.push(v);
        while(!stack.isEmpty()){
            System.out.println(stack);
            int k  = stack.peek();
            for(int i =0 ;i<numVexs;i++){
                if(matrix[k][i]!=0){
                    stack.push(i);
                    removeEdge(matrix,k,i);
                    //track [i]=true;
                    break;
                }
                if(i==numVexs-1){
                result.add(k);
                stack.pop();}
            }
        }
        return result;
    }
    public void Halminton(int [] matrix){
        int []result = new int [numVexs];
        visited = new boolean[numVexs];
        result[0]=0;
        visited[0]=true;
        expand(1);
    }
    public void expand(int i){
        for(int j=0;j<numVexs;j++){
            if(Matrixs[i-1][j]>0&&visited[j]==false){
                visited[j]=true;
                resultHalmiton.add(j);
                expand(i+1);
            }
        }
    }

     public int [] color(int v,int[][]so){
        int [] result = new  int [numVexs];
        boolean []track  = new boolean[numVexs];
        List<Integer>temp  = new ArrayList<>();
        for(int i =0;i<so.length;i++){
            temp.add(i);
            temp.sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    if(bac(o1,so)<bac(o2,so)){
                        return 1;
                    }else{
                        if(bac(o1,so)>bac(o2,so))return -1;
                    }
                    return 1;
                }
            });
        }
        int colorr = 1;
         for(int i =0;i<temp.size();i++){
             if(track[temp.get(i)]==false){
                 track[temp.get(i)]=true;
                 result[temp.get(i)]=colorr;
                 for(int j =0;j<so.length;j++){
                     if(so[temp.get(i)][j]==0&&track[j]==false){
                         System.out.println(Arrays.toString(result));
                         result[j]=colorr;
                              track[j]=true;
                         colorr++;
                         break;
                     }
                 }
             }
         }
             System.out.println(temp);
         System.out.println(Arrays.toString(result));
        return result;
     }
     public void tomau( int[][]matrix){
        int []  result  = new int [numVexs];
        int []path = new int [numVexs];
        List<Integer> list = new ArrayList<>();
        for(int i =0;i<numVexs;i++){
            list.add(i);
            list.sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    if(bac(o1,matrix)>bac(o2,matrix)){
                        return -1;
                    }else{
                        return 1;
                    }
                }
            });
        }
        boolean[]track  = new boolean[numVexs];
        int color =1;
        for(int i =0 ;i<numVexs;i++){
           if(track[list.get(i)]==false){
               result[list.get(i)]=color;
               track[list.get(i)]=false;
           }
           for(int j =0;j<numVexs;j++){
               if(matrix[list.get(i)][j]==0&&track[j]==false){
                   result[j]=color;
                   track[j]=true;
                   color++;
                   break;
               }
           }
        }
     }
     public int [] dijktra( int v,int [][]matrix){
        int [] result  = new int[numVexs];
       /* int []path = new int [numVexs];
        for(int i =0;i<matrix.length;i++){
            result[i] = Integer.MAX_VALUE/2;
            path[i] = -1;
        }
        Queue<Integer> queue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if(result[o1]>result[o2])return 1;
                return -1;
            }
        });
        result[v]=0;
        queue.add(v);
        while(!queue.isEmpty()){
             int t =queue.poll();
            for (int j =0;j<numVexs;j++){
                if(matrix[t][j]!=0){
                    if(result[j]>result[t]+matrix[t][j]){
                        result[j]=result[t]+matrix[t][j];
                        queue.add(j);
                       // System.out.println(queue);
                        path[j]=t;
                    }
                }
            }
        }*/
         int [] path  = new int [numVexs];
         for(int i =0;i<numVexs;i++){
             result[i]=Integer.MAX_VALUE;
             path[i]=-1;
         }
         Queue<Integer>queue = new PriorityQueue<>(new Comparator<Integer>() {
             @Override
             public int compare(Integer o1, Integer o2) {
                 if(result[o1]>result[o2])return 1;
                 return -1;
             }
         });
         queue.add(v);
         result[v]=0;
         while(!queue.isEmpty()){
             int u = queue.poll();
             for(int i =0;i<numVexs;i++){
                 if(matrix[u][i]!=0){
                     if(result[i]>result[u]+matrix[u][i]){
                         result[i]=result[u]+matrix[u][i];
                         path[i]=u;
                     }
                 }
             }
         }
        return result;
     }
     public int[][]prim(int d,int[][]matrix){
        int [][]result = new int[numVexs][numVexs];
        int [] temp = new int[numVexs];
        boolean[] track = new boolean[numVexs];
        Queue<Integer>queue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if(temp[o1]>temp[o2])return -1;
                return 1;
            }
        });
        temp[d]=0;
        queue.add(d);
        while(!queue.isEmpty()){
            int t  = queue.poll();
            //System.out.println(t);
            int min =Integer.MAX_VALUE;
            int index = -1;
            track[t]=true;
            for(int j =0;j<numVexs;j++){
               if(checkCycle(d,t,result)) break;
                if(matrix[t][j]!=0&&track[j]==false){
                    queue.add(j);
                    System.out.println(queue);
                    temp[j]=matrix[t][j];
                    //track[j]=true;
                    if(min>matrix[t][j]){
                        min=matrix[t][j];
                        index = j;
                    }
                    if(j==numVexs-1){
                        result[t][j]=min;
                        result[j][t]=min;
                    }
                }
            }
        }
        return result;
     }
     /**
      * 
      */
     public int[][]primReview(int v,int[][]matrix){
        int[][]result= new int [numVexs][numVexs];
        boolean[]track = new boolean[numVexs];
        Queue<Canh>queue = new PriorityQueue<>(new Comparator<Canh>() {
            @Override
            public int compare(Canh o1, Canh o2) {
                if(o1.getTs()>o2.getTs()){
                    return 1;
                }
                return -1;
            }
        });
        int count = 0;
        while(count<numVexs){
            track[v]=true;
            for(int i =0;i<numVexs;i++){
                if(matrix[v][i]!=0&&track[i]==false){
                    queue.add(new Canh(v,i,matrix[v][i]));
                }
            }
            Canh canh =  queue.poll();
            while(checkCycle(canh.getDd(), canh.getDc(),result)){
                if(queue.isEmpty())return result;
                 canh = queue.poll();
            }
            result[canh.getDd()][canh.getDc()]=result[canh.getDc()][canh.getDd()]=canh.getTs();
            count++;
            v=canh.getDc();
        }
        return result;
     }

/**
 * 
 */
     public int[][]krucal(int [][]matrix){
        int [][]result  = new int[numVexs][numVexs];
        Queue<Canh>queue = new PriorityQueue<>(new Comparator<Canh>() {
            public int compare(Canh o1, Canh o2) {
                if(o1.getTs()>o2.getTs()){
                    return 1;
                }
                return -1;
            }
        });
        for(int i =0;i<numVexs;i++){
            for(int j =i;j<numVexs;j++){
                if(matrix[i][j]!=0){
                    queue.add(new Canh(i,j,matrix[i][j]));
                }
            }
        }
        System.out.println(queue);
        int count =0;
        while(count<numVexs){
            Canh s = queue.poll();
            //System.out.println(s.getTs());
           if(!hasCycle(s.getDd(),s.getDc(),result)){
                count++;
                result[s.getDd()][s.getDc()]=result[s.getDc()][s.getDd()]=s.getTs();
            }
        }
        return result;
     }

     public void bellmanford(int[] [] matrix ){
        int []  result = new int [numVexs];
        int[]path = new int [numVexs];
        boolean[]track = new boolean[numVexs];
        for(int i =0;i<numVexs;i++){
            result[i] = Integer.MAX_VALUE;
            path[i]=-1;
        }
        boolean stop;
        int l=0;
        while(l<numVexs){
            l++;
            stop =true;
            for(int i =0;i<numVexs;i++){
                for(int j =0;j<numVexs;j++){
                    if(matrix[i][j]!=0){
                            if(result[j]>result[i]+matrix[i][j]){
                                result[j]=result[i]+matrix[i][j];
                                track[j]=true;
                                stop=false;
                            }
                        }
                    }
                if(l==numVexs){
                    if(stop==false){
                        System.out.println("có chu trinh âm");
                        stop=true;
                        return;
                    }
                }
                }
            }
        }
     public int[][]floy(int[][]matrix){
        int [][]result = new int [numVexs][numVexs];
        //int [] result  = new int[numVexs];
        for(int i =0;i<numVexs;i++){
            for(int j =0;j<numVexs;j++){
                if(matrix[i][j]==0){
            result[i][j] = Integer.MAX_VALUE/2;}
                else result[i][j]=matrix[i][j];
            }
        }
        System.out.println(print(result));
        for(int k =0;k<numVexs;k++){
            for(int i =0;i<numVexs;i++){
                for(int j=0;j<numVexs;j++){
                    if(result[i][j]>result[k][j]+result[i][k]){
                        result[i][j]=result[k][j]+result[i][k];
                    }
                }
            }
        }
        System.out.println(print(result));
        return result;

     }
     public void hamintonCylces(int u){
        if(checkConect()==false)return;
        resetAll();
        pathH = new int[numVexs];
        for(int i =0;i<numVexs;i++){
            pathH[i]=-1;
        }
        pathH[0]=u;
        track[u]=true;
        expendS(1);
     }
     private void expendS(int u){
      for(int j =0;j<numVexs;j++){
          if(Matrixs[pathH[u-1]][j]!=0&&track[j]==false){
              pathH[u]=j;
              if(u<numVexs-1){
                  track[j]=true;
                  expendS(u=1);
                  track[j]=false;
              }else{
                  in = true;
                  if(Matrixs[pathH[u]][pathH[0]]!=0&&in ==true){
                      foundCycle = true;
                      System.out.println(Arrays.toString(pathH));
                      System.out.println(Arrays.toString(pathH));
                      in=false;
                      return;
                  }else{
                      if(in = true){
                          foundPath= true;
                          System.out.println(pathH);
                          return;
                      }
                  }
              }
          }
      }
     }
    public void halminton(int[][] matrix){
        if(checkConect()==false){ System.out.println("đồ thị không liên thông") ;
            return;}
        resetAll();
        pathH = new int[numVexs+1];
        for(int i =0;i<numVexs;i++){
            pathH[i]=-1;
        }
        pathH[0]=0;
        track[0]=true;
        expend(1);
    }
    public void expend(int u){
        for(int j =0;j<numVexs;j++){
           if (Matrixs[pathH[u-1]][j]!=0&&track[j]==false){
               pathH[u]=j;
               if(u<numVexs-1) {
                   track[j] = true;
                   expend(u + 1);
                   track[j] = false;
               }else{
                  if((Matrixs[pathH[u]][0]>0)){
                      pathH[u+1]=0;
                      System.out.println(Arrays.toString(pathH));
                  }else{
                      return;
                  }
               }
           }
        }
    }
     public void floyedexpand(int [][] matrix){
        int [] [] result = new int[numVexs][numVexs];
        int [] [] path = new int [numVexs][numVexs];
        for(int i =0;i<numVexs;i++){
            for(int j =0;j<numVexs;j++){
                if(matrix[i][j]==0){
                    result[i][j]=Integer.MAX_VALUE/2;
                    path[i][j]=Integer.MAX_VALUE/2;
                }else {
                    path[i][j] = j+1;
                    result[i][j]=matrix[i][j];
                }
            }
        }
         System.out.println(print(path));
        for(int k=0;k<numVexs;k++){
            for(int i =0;i<numVexs;i++){
                for(int j =0;j<numVexs;j++){
                    if(result[i][j]>result[i][k]+result[k][j]){
                        result[i][j]=result[i][k]+result[k][j];
                        path[i][j]=k+1;
                    }
                }
            }
        }
        System.out.println(print(result));
        System.out.println(print(path));
     }

    public boolean checkConect() {
        // TODO Auto-generated method stub
        int sodinhdaduyet = 0;// dem so dinh da duyet bat dau bang o
        int v = 0; // bat dau tu dinh 0
        track[v] = true; // danh dau dinh v da duoc duyet
        sodinhdaduyet++; // tang so dinh da duyet len 1
        for (int i = 0; i < Matrixs.length; i++) {
            if (track[i] = true) { // neu dinh i da duoc duyet
                for (int j = 0; j < Matrixs.length; j++) {
                    // kiem tra cac dinh ke voi i
                    if (Matrixs[i][j] != 0 && track[j] == false) { // neu ma tran [i,j] khong rong va track[j] chua duyet

                        track[j] = true; // duyet track[j]

                        sodinhdaduyet++; // tang so dinh da duyet

                        if (sodinhdaduyet == numVexs) { // neu so dinh da duyet = tong so dinh thi do thi lien thong
                            return true;
                        }

                    }
                }
            }

        }

        return false;
    }
    public void Halminton11(int[][] matrix){
        if(checkConect()==false)return;
        resetAll();
        pathH = new int [numVexs];
        for(int i =0;i<numVexs;i++){
            pathH[i]=-1;
        }
        pathH[0]=0;
        track[0]=true;
        expend1(1);
    }
    public void expend1(int u){
        for(int j =0;j<numVexs;j++){
            if(Matrixs[pathH[u-1]][j]!=0&& track[j]==false){
                pathH[u]=j;
                if(u<numVexs-1){
                    track[j]=true;
                    expend1(u+1);
                    track[j]=false;
                }else{
                    if(Matrixs[pathH[u]][0]!=0){
                        System.out.println(Arrays.toString(pathH));
                    }
                }
            }
        }
    }
    public static void main(String[] args) throws IOException {
         practice p = new practice();
        p.readFilefromText("D:\\codeIntellij\\src\\praticalLTDT\\lap1\\ktgk");
       // p.dfsRecusive(0,p.Matrixs);
       //System.out.println(p.print(p.primH(0)));
      //System.out.println(p.Dfs(0,p.Matrixs));
     //System.out.println(p.print(p.kruskal(p.Matrixs)));
     //System.out.println(p.checkCycle(0,5,p.Matrixs));
       // System.out.print(p.CycleEuler(0));
        //System.out.println(p.print(p.prim(p.Matrixs)));
        //System.out.println(Arrays.toString(p.dijktra(0, p.Matrixs)));
        //System.out.println(p.chutrinhEuler());
       // System.out.println(Arrays.toString(p.dijktra(0,p.Matrixs)));

        //p.toMau(0,p.Matrixs);
        //p.color(0,p.Matrixs);
        // System.out.println( Arrays.toString(p.dijktra(0,p.Matrixs)));
       //System.out.println(p.print(p.primReview(0,p.Matrixs)));
        //p.floy(p.Matrixs);
        //System.out.println(p.print(p.krucal(p.Matrixs)));
       //p.prim(0,p.Matrixs);
        //p.floyedexpand(p.Matrixs);
       
    }

}
