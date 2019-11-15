
/**
 * SlitherLink does the user interaction for a square Slither Link puzzle.
 * 
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JButton;

public class SlitherLink implements MouseListener
{    
    private Puzzle game;     // internal representation of the game
    private SimpleCanvas sc; // the display window
    /**
     * Creates a display for playing the puzzle p.
     */
    public SlitherLink(Puzzle p)
    {   //create puzzle maps of different sizes
        this.game = p;
        int a = this.getGame().size()+1;
        int b =100;
        /*
        if(a<=6){
            b=100;
        }
        else if(a>6){
            b=50;
        }
        */
        sc = new SimpleCanvas("SlitherLink",b+a*b,b+a*b,Color.white);
        sc.addMouseListener(this);
        //creates three buttons for control panel
        JButton clear = new JButton("Clear");
        JButton finish = new JButton("Finish");
        JButton New = new JButton("New");
        clear.setBounds(25,75,100,25);
        finish.setBounds(150,75,100,25);
        New.setBounds(75,30,150,25);
        sc.frame.add(clear);
        sc.frame.add(finish);
        sc.frame.add(New);
        
        sc.frame.setVisible(true);
        clear.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                clearAction();                              //clears all lines on the screen
                displayPuzzle();
            }
        });
        finish.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String msg = AnalyzeSolution.finished(p);
                sc.drawString(msg, 50, 50, Color.red);
            }
        });
        New.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JFileChooser chooser = new JFileChooser();            
                chooser.setMultiSelectionEnabled(true); 
                int returnVal = chooser.showOpenDialog(New);
                String filename = chooser.getSelectedFile().getName();
                Puzzle p1 = new Puzzle(filename);
                SlitherLink s1 = new SlitherLink (p1);
            }
        });
        displayPuzzle();
        
        
    }
    /**
     * Returns the current state of the game.
     */
    public Puzzle getGame()
    {
        return game;
    }

    /**
     * Returns the current state of the canvas.
     */
    public SimpleCanvas getCanvas()
    {
        return sc;
    }

    /**
     * Displays the initial puzzle on sc. 
     * Have a look at puzzle-loop.com for a basic display, or use your imagination. 
     */
    public void displayPuzzle()
    {
        int a = game.size()+1;
        int b =100 ;
        /*
        if(a<=6){
            b=100;
        }
        else if(a>6){
            b=50;
        }
        */
        int c = b/2;
        int d = a-1;
        for(int i=b,m=1;m<=a;m++)                             //draws dots
        {
            for(int j=b,n=1;n<=a;n++){
                sc.drawDisc(i,j,5,Color.black);
                j+=b;
            }
            i+=b;
        }
        sc.setFont(new Font("Times New Roman",Font.BOLD,20));  //draws numbers
        for(int i=b+c,m=0;m<d;m++)
        {
            for(int j=b+c,n=0;n<d;n++)
            {
                int puzzles[][] = game.getPuzzle();
                int num = puzzles[n][m];
                if (num!=-1){
                    sc.drawString(num,i-5,j+5,Color.black);    //make numbers locates in center
                }
                j+=b;
            }
            i+=b;
        }    
    }
    
    /**
     * Makes a horizontal click to the right of Dot r,c.
     * Update game and the display, if the indices are legal; otherwise do nothing.
     */
    public void horizontalClick(int r, int c)
    {
        int a = game.size()+1;
        int b =100 ;
        /*
        if(a<=6){
            b=100;
        }
        else if(a>6){
            b=50;
        }
        */
        game.horizontalClick(r,c);
        try{
            boolean horizon[][] = game.getHorizontal();
            if(horizon[r][c]){
                sc.drawLine(c*b+b, r*b+b, c*b+2*b, r*b+b, Color.black);//draws a Line from left dot to right dot
            }
            if(!horizon[r][c]){
                sc.drawLine(c*b+b, r*b+b, c*b+2*b, r*b+b, Color.white);//dras a white line to cover it 
            }
            }catch (ArrayIndexOutOfBoundsException e){
            }
            
    }
    
    /**
     * Makes a vertical click below Dot r,c. 
     * Update game and the display, if the indices are legal; otherwise do nothing. 
     */
    public void verticalClick(int r, int c)
    {
        int a = game.size()+1;
        int b =100 ;
        /*
        if(a<=6){
            b=100;
        }
        else if(a>6){
            b=50;
        }
        */
        game.verticalClick(r,c);
        try{
            boolean vertical[][] = game.getVertical();
            if(vertical[r][c]){
                sc.drawLine(c*b+b, r*b+b, c*b+b, r*b+2*b, Color.black);//draws a line from top to botton
            }
            if(!vertical[r][c]){
                sc.drawLine(c*b+b, r*b+b, c*b+b, r*b+2*b, Color.white);//draws a white line to cover it
            }
            }catch (ArrayIndexOutOfBoundsException e){
            }
    }
    
    /**
     * Actions for a mouse press.
     */
    public void mousePressed(MouseEvent e) 
    {
        sc.drawRectangle(0, 0, 200, 80 , Color.white);
        int a = this.getGame().size()+1;
        int b =100 ;
        /*
        if(a<=6){
            b=100;
        }
        else if(a>6){
            b=50;
        }
        */
        
        //gets the position of mousePressed, and refer it to a dot.
        int r = e.getY()/b -1;                 
        int c = e.getX()/b -1;
        if (e.getX()%b>e.getY()%b){
            if(e.getX()%b>(b-e.getY()%b)){this.verticalClick(r,c+1);}
            else{
            this.horizontalClick(r,c);}
        }
        if(e.getX()%b<e.getY()%b){
            if(e.getX()%b>(b-e.getY()%b)){this.horizontalClick(r+1,c);}
            else{
            this.verticalClick(r,c);}
        } 
        //else{sc.drawString(AnalyzeSolution.finished(game), a*75/2, 75, Color.white);}
        
    }
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    /**
     * Button clear actions, clears all the lines on the sceen;
     */
    public void clearAction(){
        boolean horizon[][] = game.getHorizontal();
        boolean vertical[][] = game.getVertical();
        int hrl = horizon.length;
        int hcl = horizon[0].length;
        int vrl = vertical.length;
        int vcl = vertical[0].length;
        for(int i=0; i<hrl; i++)
        {
            for(int j=0; j<hcl; j++)
            {
                if(horizon[i][j]){horizontalClick(i,j);}
            }
        }
        for(int i=0; i<vrl;i++)
        {
            for(int j=0; j<vcl;j++)
            {
                if(vertical[i][j]){verticalClick(i,j);}
            }
        }
    }
    
}

