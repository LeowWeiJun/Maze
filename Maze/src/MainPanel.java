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
    String str = "maze3.txt"; //use the maze i desgined

    public MainPanel(){
        super(JSplitPane.VERTICAL_SPLIT);

        //Top Panel
        mainPanel = new JPanel(new GridLayout(30,30));
        mainPanel.setPreferredSize(new Dimension(600 ,600));
        mainPanel.addKeyListener(this);
        mainPanel.setEnabled(false);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);

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
            //System.out.println("cheese : " + m.getCheeseEat());

            System.out.println(Mouse.getExitCondition());
            if(m.getExitCondition() == 0){
                moveCat(c1); //Cat1 move
                moveCat(c2); //Cat2 move
            }

            //Mouse win,Display details message,reset everything
            if(m.getExitCondition() == 1){
                m.setExitCondition(0);
                JOptionPane.showMessageDialog(this,"You won!" + "\nTotal Cheese Ate: "+m.getTotalCheese()
                        + "\nTotal Moves: " + m.getTotalMove(), "Game Finished", JOptionPane.INFORMATION_MESSAGE);
                m.clear(); //clear details of mouse
                Maze.clearMaze(); //clear maze
                mainPanel.setEnabled(false); //disable movement of mouse and cats
                startButton.setEnabled(true); //enable start button
            }
            //Mouse lost,Display details message,reset everything
            else if(m.getExitCondition() == -1){
                m.setExitCondition(0);
                JOptionPane.showMessageDialog(this,"You lost!" + "\nTotal Cheese Ate: "+m.getTotalCheese()
                        + "\nTotal Moves: " + m.getTotalMove(), "Game Over", JOptionPane.INFORMATION_MESSAGE);
                m.clear(); //clear details of mouse
                Maze.clearMaze(); //clear maze
                mainPanel.setEnabled(false); //disable movement of mouse and cats
                startButton.setEnabled(true); //enable start button
            }

            //Maze.printMap(Maze.map); //Debug purpose , print the map in 2 dimension
            //System.out.println("----------------------------------------------");
        }

    }

    //not using this 2 function
    public void keyTyped(KeyEvent e){}
    public void keyReleased(KeyEvent e){}

    //cat movement
    public void moveCat(Cat cat){
        int x;
        int distanceX ;
        int distanceY ;
        if(Maze.mazeLabel[cat.getCatx()][cat.getCaty()].getIcon() == cat.getImage()){
            while(true){
                distanceX = cat.getCatx() - m.getMousex();
                distanceY = cat.getCaty() - m.getMousey();

                System.out.println("X : " + distanceX);
                System.out.println("Y : " + distanceY);
                System.out.println("Cat : " + cat.getCatx() + " " + cat.getCaty());
                if(distanceX <= 3 && distanceX > 0 && cat.getCaty() == m.getMousey() && Maze.map[m.getMousex()][m.getMousey()] != 4 && Maze.map[cat.getCatx()-1][cat.getCaty()] != 1){
                    x = 0;
                    System.out.println("u");
                }
                else if(distanceX >=-3 && distanceX < 0 && cat.getCaty() == m.getMousey() && Maze.map[m.getMousex()][m.getMousey()] != 4 && Maze.map[cat.getCatx()+1][cat.getCaty()] != 1){
                    x = 1;
                    System.out.println("d");
                }
                else if(distanceY <= 3 && distanceY > 0 && cat.getCatx() == m.getMousex() && Maze.map[m.getMousex()][m.getMousey()] != 4 && Maze.map[cat.getCatx()][cat.getCaty()-1] != 1){
                    x = 2;
                    System.out.println("l");
                }
                else if(distanceY >=-3 && distanceY < 0 && cat.getCatx() == m.getMousex() && Maze.map[m.getMousex()][m.getMousey()] != 4 && Maze.map[cat.getCatx()][cat.getCaty()+1] != 1){
                    x = 3;
                    System.out.println("r");
                }
                else if(distanceX <= 3 && distanceX > 0 && distanceY <= 3 && distanceY > 0 && Maze.map[m.getMousex()][m.getMousey()] != 4 && (Maze.map[cat.getCatx()-1][cat.getCaty()] != 1 || Maze.map[cat.getCatx()][cat.getCaty()-1] != 1)){//
                    int y=rand.nextInt(4);
                    if (y % 2 == 0){
                        x = y;
                    }
                    else{
                        x = -1;
                    }
                }
                else if(distanceX >=-3 && distanceX < 0 && distanceY <= 3 && distanceY > 0 && Maze.map[m.getMousex()][m.getMousey()] != 4 && (Maze.map[cat.getCatx()+1][cat.getCaty()] != 1 || Maze.map[cat.getCatx()][cat.getCaty()-1] != 1)){//
                    x = rand.nextInt(1+1)+1;
                }
                else if(distanceX <= 3 && distanceX > 0 && distanceY >=-3 && distanceY < 0 && Maze.map[m.getMousex()][m.getMousey()] != 4 && (Maze.map[cat.getCatx()-1][cat.getCaty()] != 1 || Maze.map[cat.getCatx()][cat.getCaty()+1] != 1)){//
                    int y=rand.nextInt(4);
                    if ( y == 0 || y == 3){
                        x = y;
                    }
                    else{
                        x = -1;
                    }
                }
                else if(distanceX >=-3 && distanceX < 0 && distanceY >=-3 && distanceY < 0 && Maze.map[m.getMousex()][m.getMousey()] != 4 && (Maze.map[cat.getCatx()+1][cat.getCaty()] != 1 || Maze.map[cat.getCatx()][cat.getCaty()+1] != 1) ){//
                    int y=rand.nextInt(4);
                    if (y % 2 != 0){
                        x = y;
                    }
                    else{
                        x = -1;
                    }
                }
                else{
                    x=rand.nextInt(4); //random 4 direction
                    System.out.println("random");
                }


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
        Maze.initMaze(str,m,c1,c2); //spawn cheeses, mouse and cats
    }
}