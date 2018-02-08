import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;


public class main extends JFrame implements KeyListener{
    public static Random rand = new Random();
    Mouse m = new Mouse(); //Create mouse
    Cat c1 = new Cat(); //Create 1st Cat
    Cat c2 = new Cat(); //Create 2nd Cat

    public static void main(String[] args){
        new main();
    }

    public main(){
        
        super("Maze"); //call superclass constructor (no more mainFrame initialization)
        this.setSize(700,652);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        //Top Panel
        JPanel mainPanel = new JPanel(new GridLayout(30,30));
        mainPanel.setPreferredSize(new Dimension(600 ,600));
        //mainframe.add(mainPanel,BorderLayout.NORTH);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);

        String str = "maze3.txt"; //use the maze i desgined
        new Maze(str,mainPanel,m,c1,c2);


        JButton testButton = new JButton("Start");
        testButton.setPreferredSize(new Dimension(80,20));

        //Bottom Panel
        JPanel testPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        testPanel.setPreferredSize(new Dimension(100,20));
        testPanel.add(testButton);

        JSplitPane vsplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        vsplit.setLeftComponent(mainPanel);
        vsplit.setRightComponent(testPanel);
        vsplit.setDividerSize(0);
        vsplit.setEnabled(false);
        this.getContentPane().add(vsplit);

        this.setVisible(true);

    }

    //keyListener
    public void keyPressed(KeyEvent e){
        if(moveMouse(e)){
            //System.out.println(m.getCheeseEat());

            m.transSuperMouse(); //Transform into supermouse if the condition is match
            m.transMouse(); //Transform back into mouse if the condition is match
            moveCat(c1); //Cat1 move
            moveCat(c2); //Cat2 move
        }
    }

    //not using this 2 function
    public void keyTyped(KeyEvent e){}
    public void keyReleased(KeyEvent e){}

    //cat movement
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


