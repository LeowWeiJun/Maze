import javax.swing.*;
//import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
//import java.io.BufferedReader;
//import java.io.FileReader;
import java.util.Random;


public class MainPanel extends JSplitPane implements KeyListener, ActionListener{

    public static Random rand = new Random();
    Mouse m = new Mouse(); //Create mouse
    Cat c1 = new Cat(); //Create 1st Cat
    Cat c2 = new Cat(); //Create 2nd Cat
    JPanel mainPanel;
    JButton startButton;

    public MainPanel(){
        super(JSplitPane.VERTICAL_SPLIT);

        //Top Panel
        mainPanel = new JPanel(new GridLayout(30,30));
        mainPanel.setPreferredSize(new Dimension(600 ,600));
        mainPanel.addKeyListener(this);
        mainPanel.setEnabled(false);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);


        String str = "maze3.txt"; //use the maze i desgined
        new Maze(str,mainPanel);
        //Maze.printMap(Maze.map);

        startButton = new JButton("Start");
        startButton.setPreferredSize(new Dimension(80,20));
        startButton.addActionListener(this);

        //Bottom Panel
        JPanel testPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        testPanel.setPreferredSize(new Dimension(100,20));
        testPanel.add(startButton);

        this.setLeftComponent(mainPanel);
        this.setRightComponent(testPanel);
        this.setDividerSize(0);
        this.setEnabled(false);

    }

    //keyListener
    public void keyPressed(KeyEvent e){
        if(moveMouse(e)){
            //Maze.printMap(Maze.map); //Debug purpose , print the map in 2 dimension
            //System.out.println("----------------------------------------------");
            //System.out.println("cheese : " + m.getCheeseEat());

            moveCat(c1); //Cat1 move
            moveCat(c2); //Cat2 move

            if(m.getExitCondition() == 1){
                m.setExitCondition(0);
                JOptionPane.showMessageDialog(this,"You won!" + "\nTotal Cheese Ate: "+m.getTotalCheese()
                        + "\nTotal Moves: " + m.getTotalMove(), "Game Finished", JOptionPane.INFORMATION_MESSAGE);
                m.clear(); //clear details of mouse
                Maze.clearMaze(); //clear maze
                mainPanel.setEnabled(false); //disable movement of mouse and cats
                startButton.setEnabled(true); //enable start button
            }
            else if(m.getExitCondition() == -1){
                m.setExitCondition(0);
                JOptionPane.showMessageDialog(this,"You lost!" + "\nTotal Cheese Ate: "+m.getTotalCheese()
                        + "\nTotal Moves: " + m.getTotalMove(), "Game Over", JOptionPane.INFORMATION_MESSAGE);
                m.clear(); //clear details of mouse
                Maze.clearMaze(); //clear maze
                mainPanel.setEnabled(false); //disable movement of mouse and cats
                startButton.setEnabled(true); //enable start button
            }
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
                }catch(ArrayIndexOutOfBoundsException a){}
            }
            //down
            if (x==1){

                try{
                    if(cat.moveDown())
                        break;
                }catch(ArrayIndexOutOfBoundsException a){}
            }
            //left
            if (x==2){

                try{
                    if(cat.moveLeft())
                        break;
                }catch(ArrayIndexOutOfBoundsException a){}
            }
            //right
            if (x==3){

                try{
                    if(cat.moveRight())
                        break;
                }catch(ArrayIndexOutOfBoundsException a){}
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

    @Override
    public void actionPerformed(ActionEvent e){
        System.out.println("Pressed start button");
        mainPanel.requestFocusInWindow(); //put the focus in top panel
        mainPanel.setEnabled(true); //enable movements of cats and mouse
        startButton.setEnabled(false); //disable start button
        Maze.initMaze(m,c1,c2); //spawn cheeses, mouse and cats
    }


}