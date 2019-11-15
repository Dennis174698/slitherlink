
/**
 * Puzzle maintains the internal representation of a square Slither Link puzzle.
 * 
 */
import java.util.ArrayList;

public class Puzzle
{
    private int[][] puzzle;         // the numbers in the squares, i.e. the puzzle definition
                                    // -1 if the square is empty, 0-3 otherwise
    private boolean[][] horizontal; // the horizontal line segments in the current solution
                                    // true if the segment is on, false otherwise
    private boolean[][] vertical;   // the vertical line segments in the current solution
                                    // true if the segment is on, false otherwise

    /**
     * Creates the puzzle from file filename, and an empty solution.
     * filename is assumed to hold a valid puzzle.
     */
    public Puzzle(String filename)
    {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
        ArrayList<String> lines;
        lines = new ArrayList<String> ();
        FileIO file = new FileIO(filename);
        lines = file.getLines();
        int s = lines.size();
        this.parseFile(lines);
        horizontal = new boolean[s+1][s];
        vertical = new boolean[s][s+1]; 
        clear();
    }
    
    /**
     * Creates the puzzle from "eg3_1.txt".
     */
    public Puzzle()
    {
        this("eg3_1.txt");
    }

    /**
     * Returns the size of the puzzle.
     */
    public int size()
    {
        return puzzle.length;
    }

    /**
     * Returns the number layout of the puzzle.
     */
    public int[][] getPuzzle()
    {
        return puzzle;
    }

    /**
     * Returns the state of the current solution, horizontally.
     */
    public boolean[][] getHorizontal()
    {
        return horizontal;
    }

    /**
     * Returns the state of the current solution, vertically.
     */
    public boolean[][] getVertical()
    {
        return vertical;
    }

    /**
     * Turns lines into a Slither Link puzzle.
     * The first String in the argument goes into puzzle[0], 
     * The second String goes into puzzle[1], etc. 
     * lines is assumed to hold a valid square puzzle; see eg3_1.txt and eg5_1.txt for examples.
     */
    public void parseFile(ArrayList<String> lines)
    {
        int a = lines.size();
        String [][] subLines = new String[a][a];
        puzzle = new int[a][a];
        //reads numbers and stores them to array puzzle[][].
        for(int i=0;i<a;i++)
        {
            subLines[i] = lines.get(i).split(" ");               //split message with space
            for(int j=0;j<a;j++)
            {
                 puzzle [i][j]= Integer.parseInt(subLines[i][j]);//parse strings to integers.
            }
        }
    }
    
    /**
     * Toggles the horizontal line segment to the right of Dot r,c, if the indices are legal.
     * Otherwise do nothing.
     */
    public void horizontalClick(int r, int c)
    {
        if (0 <= r && r <= size() && 0 <= c && c < size())
        {
           horizontal[r][c] = !horizontal[r][c];
        }
        
    }
    
    /**
     * Toggles the vertical line segment below Dot r,c, if the indices are legal.
     * Otherwise do nothing.
     */
    public void verticalClick(int r, int c)
    {

         if (0 <= r && r < size() && 0 <= c && c <= size())
        {
           vertical[r][c] = !vertical[r][c];
        }
        
    }
    
    /**
     * Clears all line segments out of the current solution.
     */
    public void clear()
    {   //sets all line segments to false
        int hrl = horizontal.length;
        int hcl = horizontal[0].length;
        int vrl = vertical.length;
        int vcl = vertical[0].length;
        for(int i=0; i<hrl; i++)
        {
            for(int j=0; j<hcl; j++)
            {
                horizontal[i][j] = false;
            }
        }
        for(int i=0; i<vrl;i++)
        {
            for(int j=0; j<vcl;j++)
            {
                vertical[i][j] = false;
            }
        }
    }
}
