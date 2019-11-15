
/**
 * AnalyzeSolution methods are used to analyze the state of a Slither Link puzzle, 
 * to determine if the puzzle is finished. 
 * 
 * @author Guangming Chen 
 * @version v1.0
 */
import java.util.*;

public class AnalyzeSolution
{
    /**
     * We don't need to create any objects of class AnalyzeSolution; all of its methods are static.
     */
    private AnalyzeSolution() {}

    /**
     * Returns the number of line segments surrounding Square r,c in p.
     * Returns 0 if the indices are illegal.
     */
    public static int linesAroundSquare(Puzzle p, int r, int c)
    {
        boolean [][] hCheck = p.getHorizontal();
        boolean [][] vCheck = p.getVertical();
        int sLines = 0;
        try{
            if (hCheck[r][c]){sLines++;}
            if (hCheck[r+1][c]){sLines++;}
            if (vCheck[r][c]){sLines++;}
            if (vCheck[r][c+1]){sLines++;}
        }catch(ArrayIndexOutOfBoundsException e){
            sLines = 0;
        }
        return sLines;
        
    }
    
    /**
     * Returns all squares in p that are surrounded by the wrong number of line segments.
     * Each item on the result will be an int[2] containing the indices of a square.
     * The order of the items on the result is unimportant.
     */
    public static ArrayList<int[]> badSquares(Puzzle p)
    {
        int puzzle[][] = p.getPuzzle();
        int n = p.size();
        ArrayList<int[]> badSquares = new ArrayList<int[]>();
        for(int i = 0;i<n;i++){
            for(int j=0;j<n;j++){
                if(puzzle[i][j]==-1){continue;}                  
                if(puzzle[i][j]!=linesAroundSquare(p,i,j)){
                    int[] a = new int[]{i,j};
                    badSquares.add(a);
                }
            }
        }
        return badSquares;
    }

    /**
     * Returns all dots connected by a single line segment to Dot r,c in p.
     * Each item on the result will be an int[2] containing the indices of a dot.
     * The order of the items on the result is unimportant.
     * Returns null if the indices are illegal.
     */
    public static ArrayList<int[]> getConnections(Puzzle p, int r, int c)
    {
        int n = p.size() +1;
        boolean [][] hCheck = new boolean[n][n];     
        boolean [][] vCheck = new boolean[n][n];
        boolean [][]h = p.getHorizontal();
        boolean [][]v = p.getVertical();
        ArrayList<int[]> getConnections = new ArrayList<int[]>();
        for(int i=0;i<h.length;i++){
            for(int j=0;j<h[0].length;j++){
                hCheck [i][j]= h[i][j];}
            }
        for(int i=0;i<v.length;i++){
            for(int j=0;j<v[0].length;j++){
                vCheck [i][j]= v[i][j];}
            }
        try{                                   //check every directions of the dots
            if(c>0){                           //if true adds to arrays 
            if (hCheck[r][c]){
                int[] a = new int[]{r,c+1};
                getConnections.add(a);}
            if(hCheck[r][c-1]){
                int[] a = new int[]{r,c-1};
                getConnections.add(a);}
            }
            if(c==0){                          //if a dot is at edge, only check two directions
                if (hCheck[r][c]){
                int[] a = new int[]{r,c+1};
                getConnections.add(a);}
            }
                
            if(r>0){    
            if(vCheck[r][c]){
                int[] a = new int[]{r+1,c};
                getConnections.add(a);}
            if(vCheck[r-1][c]){
                int[] a =new int[]{r-1,c};
                getConnections.add(a);}
            }
            if(r==0){                          //if a dot is at edge, only check two directions
                if(vCheck[r][c]){
                int[] a = new int[]{r+1,c};
                getConnections.add(a);}
            }
                
            }catch(ArrayIndexOutOfBoundsException e){
                return null;
            }
            
        return getConnections;
    }

    /**
     * Returns an array of length 3 whose first element is the number of line segments in the puzzle p, 
     * and whose other elements are the indices of a dot on any one of those segments. 
     * Returns {0,0,0} if there are no line segments on the board. 
     */
    public static int[] lineSegments(Puzzle p)
    {
        boolean hCheck[][] = p.getHorizontal();
        boolean vCheck[][] = p.getVertical();
        int [] lineSegments ;
        int lines = 0;
        int r=0;int c=0;
        for(int i=0; i<hCheck.length;i++){
            for(int j=0;j<hCheck[0].length;j++){
                if(hCheck[i][j]==true){lines++;r=j;c=i;}
            }
        }
        for(int i=0;i<vCheck.length;i++){
            for(int j=0;j<vCheck[0].length;j++){
                if(vCheck[i][j]==true){lines++;r=i;c=j;}
            }
        }
        lineSegments=new int[]{lines,r,c};
        
        return lineSegments;
    }
    
    /**
     * Tries to trace a closed loop starting from Dot r,c in p. 
     * Returns either an appropriate error message, or 
     * the number of steps in the closed loop (as a String). 
     * See the project page and the JUnit for a description of the messages expected. 
     */
    public static String tracePath(Puzzle p, int r, int c)
    {
        int [] lineSegments;
        lineSegments = lineSegments(p);
        int lineNums = lineSegments[0];
        int steps =1;
        int [] lPoint = {r,c};           //last point
        int [] nPoint;                   //next point
        boolean breakout =false;
        String msg ="";
        ArrayList<int[]> a = getConnections(p,r,c);
    
        if(a.size()==0){msg="No path";}
        if(a.size()>0){
            nPoint = a.get(0);                  //sets nPoint as one of startpoint's connections
        for(int i=0;i<lineNums;i++){
            a = getConnections(p,nPoint[0],nPoint[1]); //sets a as nPoint's connections
            int [] sPoint ={nPoint[0],nPoint[1]};      //creates a switch point
            nPoint = a.get(0);                         //sets nPoint to one of its connection
            
            if(a.size()==1){
                msg="Dangling end";
                breakout = true;
            }
            else if(a.size()>2){
                msg="Branching line";
                breakout = true;
            }
            else if(a.size()==2){
                //if next point equals last point, sets nPoint to its another connection
                if(lPoint[0]==nPoint[0] && lPoint[1]==nPoint[1]){
                    lPoint = sPoint;                   //moves last point to switch point
                    nPoint = a.get(1);
                }
                else {
                    lPoint = sPoint;      
                    nPoint = a.get(0);                 
                }
                if(nPoint[0]==r && nPoint[1]==c){      //break if its equals startpoint
                    breakout = true;
                }
                steps++;
                msg = String.valueOf(steps);
            }   
            if(breakout)
            break;
            }
        }
    return msg;
    }
    
    /**
     * Returns a message on whether the puzzle p is finished. 
     * p is finished iff all squares are good, and all line segments form a single closed loop. 
     * An algorithm is given on the project page. 
     * See the project page and the JUnit for a description of the messages expected.
     */
    public static String finished(Puzzle p)
    {
        String msg ="";
        ArrayList badSquares = new ArrayList<int[]>();
        badSquares = badSquares(p);
        int [] lineSegments = lineSegments(p);
        int lineNums = lineSegments[0];
        int r = lineSegments[1];
        int c = lineSegments[2];
        
        if(badSquares.size()>0){msg="Wrong number";}
        else if("No path".equals(tracePath(p,r,c))){
            msg="No path";
        }
        else if("Dangling end".equals(tracePath(p,r,c))){
            msg="Dangling end";
        }
        else if("Branching line".equals(tracePath(p,r,c))){
            msg="Branching line";
        }
        else if(lineNums>Integer.parseInt(tracePath(p,r,c))){
            msg="Disconnected lines";
        }
        else if(lineNums == Integer.parseInt(tracePath(p,r,c))){
            msg="Finished";
        }
        return msg;
    }
}
