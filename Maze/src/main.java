import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;


public class main extends JFrame implements KeyListener{

    public static JLabel[][] mazeLabel = new JLabel[30][30]; //this mazeLabel initialization i bring up because my keyPressed function nid it
    //public int catx1, caty1, catx2, caty2; //coordinate of mouse and cats
    public static Random rand = new Random();
    Mouse m = new Mouse();
    Cat c1 = new Cat();
    Cat c2 = new Cat();

    public static void main(String[] args){
        new main();
    }

    public main(){
        
        super("Maze"); //call superclass constructor (no more mainFrame initialization)
        this.setSize(700,652);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(30,30));
        mainPanel.setPreferredSize(new Dimension(600 ,600));
        //mainframe.add(mainPanel,BorderLayout.NORTH);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        //mainPanel.setVisible(true);

        //mainPanel.setBackground(Color.black);
        String str = "maze3.txt"; //use the maze i desgined
        new Maze(str);


        

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        
        for(int i = 0 ; i < 30 ; i++){
            for(int j = 0 ; j < 30 ; j++){
                mazeLabel[i][j] = new JLabel();
                mazeLabel[i][j].setOpaque(true);
                if(Maze.map[i][j] == 1)
                    mazeLabel[i][j].setBackground(Color.BLACK);
                mazeLabel[i][j].setBorder(border);
                mainPanel.add(mazeLabel[i][j]);
                //System.out.println("1");
                //mainPanel.repaint();
            }
        }

        JButton testButton = new JButton("Start");
        JButton testButton2 = new JButton("Start1");
        testButton.setPreferredSize(new Dimension(80,20));
        JPanel testPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        testPanel.setPreferredSize(new Dimension(100,20));
        testPanel.add(testButton);

        JSplitPane vsplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        vsplit.setLeftComponent(mainPanel);
        vsplit.setRightComponent(testPanel);
        vsplit.setDividerSize(0);
        vsplit.setEnabled(false);
        this.getContentPane().add(vsplit);

        //testPanel.add(testButton2);
        //mainframe.add(testPanel, BorderLayout.SOUTH);
        //mainPanel.add(testButton, BorderLayout.SOUTH);
        //mainframe.getContentPane().add(testButton2, BorderLayout.SOUTH);
        

        do{
            //random 0-29
            m.setMousex(rand.nextInt(30));
            m.setMousey(rand.nextInt(30));

            //prevent spawn on wall and exit (assume exit at [29][29])
            if(Maze.map[m.getMousex()][m.getMousey()] == 0){
                if(m.getMousex()==29){
                    if(m.getMousey()==29)
                        continue;
                }
                mazeLabel[m.getMousex()][m.getMousey()].setIcon(Mouse.mouse); //spawn mouse
                System.out.println(m.getMousex()+" "+m.getMousey());
                break;
            }
        }while(true);

        do{
            //random 0-29
            c1.setCatx(rand.nextInt(30));
            c1.setCaty(rand.nextInt(30));


            //if coordinate same with mouse then re-random
            if(Maze.map[c1.getCatx()][c1.getCaty()] == 1 || (c1.getCatx() == m.getMousex() && c1.getCaty() == m.getMousey())){
                continue;
            }
            mazeLabel[c1.getCatx()][c1.getCaty()].setIcon(Cat.cat); //spawn cat1
            break;
        }while(true);

        do{
            //random 0-29
            c2.setCatx(rand.nextInt(30));
            c2.setCaty(rand.nextInt(30));

            //if coordinate same with mouse and cat1 then re-random
            if(Maze.map[c2.getCatx()][c2.getCaty()] == 1 || (c2.getCatx() == m.getMousex() && c2.getCaty() == m.getMousey()) || (c2.getCatx() == c1.getCatx() && c2.getCaty() == c1.getCaty())){
                continue;
            }
            mazeLabel[c2.getCatx()][c2.getCaty()].setIcon(Cat.cat); //spawn cat2
            break;
        }while(true);
       //Color tes = testButton.getBackground();
        
        this.setVisible(true);
    }

    //keyListener
    public void keyPressed(KeyEvent e){
        if(moveMouse(e)){
            moveCat(c1);
            moveCat(c2);
        }
    }

    //not using this 2 function
    public void keyTyped(KeyEvent e){}
    public void keyReleased(KeyEvent e){}

    //cat1 movement
    public void moveCat(Cat cat){
        int x;
        while(true){
            x=rand.nextInt(4); //random 4 direction

            //up
            if (x==0){
                try{
                    if (cat.moveUp())
                        break;
                }catch(ArrayIndexOutOfBoundsException a){cat.setCatx(cat.getCatx()+1);}
            }
            //down
            if (x==1){
                try{
                    if(cat.moveDown())
                        break;
                }catch(ArrayIndexOutOfBoundsException a){cat.setCatx(cat.getCatx()-1);}
            } 
            //left
            if (x==2){
                try{
                    if(cat.moveLeft())
                        break;
                }catch(ArrayIndexOutOfBoundsException a){cat.setCaty(cat.getCaty()+1);}
            }
            //right
            if (x==3){
                try{
                    if(cat.moveRight())
                        break;
                }catch(ArrayIndexOutOfBoundsException a){cat.setCaty(cat.getCaty()-1);}
            }

        }
    }

    //control mouse with arrow key and WASD
    public boolean moveMouse(KeyEvent e){
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP || code ==KeyEvent.VK_W){
            if(m.moveUp())
                return true;
        }
        if (code == KeyEvent.VK_DOWN || code ==KeyEvent.VK_S){
            if(m.moveDown())
                return true;
        }
        if (code == KeyEvent.VK_LEFT || code ==KeyEvent.VK_A){
            if(m.moveLeft())
                return true;
        }
        if (code == KeyEvent.VK_RIGHT || code ==KeyEvent.VK_D){
            if(m.moveRight())
                return true;
        }
        return false;
    }


}


