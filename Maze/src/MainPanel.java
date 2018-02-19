import javax.swing.*;
//import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
//import java.io.BufferedReader;
//import java.io.FileReader;
import java.util.Random;


public class MainPanel extends JSplitPane implements KeyListener, ActionListener{


    Mouse m = new Mouse(); //Create mouse
    Cat c1 = new Cat(); //Create 1st Cat
    Cat c2 = new Cat(); //Create 2nd Cat
    JPanel mainPanel;
    JButton startButton;
    JButton resetButton;
    JButton quitButton;
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

        //Start Button
        startButton = new JButton("Start");
        startButton.setPreferredSize(new Dimension(80,20));
        startButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        System.out.println("Pressed start button");
                        mainPanel.requestFocusInWindow(); //put the focus in top panel
                        mainPanel.setEnabled(true); //enable movements of cats and mouse
                        startButton.setEnabled(false); //disable start button
                        resetButton.setEnabled(true); //enable reset button
                        Maze.initMaze(str,m,c1,c2); //spawn cheeses, mouse and cats
                        Maze.printMap(Maze.map);
                    }
                }
        );

        //Reset Button
        resetButton = new JButton("Reset");
        resetButton.setPreferredSize(new Dimension(80,20));
        resetButton.setEnabled(false);
        resetButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Pressed Reset Button");
                        Maze.clearMaze();
                        m.clearMouse();

                        //mainPanel.setEnabled(false);
                        Maze.resetIntoInitialPosition(m, c1, c2);
                        mainPanel.requestFocusInWindow();
                    }
                }
        );

        //Reset Button
        quitButton = new JButton("Quit");
        quitButton.setPreferredSize(new Dimension(80,20));
        quitButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Pressed Quit Button");
                        System.exit(0);
                    }
                }
        );

        //Bottom Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setPreferredSize(new Dimension(100,20));
        bottomPanel.add(startButton);
        bottomPanel.add(resetButton);
        bottomPanel.add(quitButton);

        this.setLeftComponent(mainPanel);
        this.setRightComponent(bottomPanel);
        this.setDividerSize(0);
        this.setEnabled(false);

    }

    //keyListener
    public void keyPressed(KeyEvent e){
        System.out.println("Cat count : " + Cat.getCatCount());
        if(moveMouse(e)){
            //System.out.println("cheese : " + m.getCheeseEat());

            System.out.println(Mouse.getExitCondition());
            if(m.getExitCondition() == 0){
                moveCat(c1); //Cat1 move
                moveCat(c2); //Cat2 move
            }

            gameCondition();

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
        if(Maze.mazeLabel[cat.getCatx()][cat.getCaty()].getIcon() == cat.getImage()){
            while(true){
                x = Cat.chaseMouse(cat,m);
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
    }

    public void gameCondition(){
        //Mouse win,Display details message,reset everything
        if(m.getExitCondition() == 1){
            m.setExitCondition(0);
            JOptionPane.showMessageDialog(this,"You won!" + "\nTotal Cheese Ate: "+m.getTotalCheese()
                    + "\nTotal Moves: " + m.getTotalMove(), "Game Finished", JOptionPane.INFORMATION_MESSAGE);
            m.clearMouse(); //clear details of mouse
            Cat.setCatCount(2);
            Maze.clearMaze(); //clear maze
            mainPanel.setEnabled(false); //disable movement of mouse and cats
            startButton.setEnabled(true); //enable start button
            resetButton.setEnabled(false);
        }
        //Mouse lost,Display details message,reset everything
        else if(m.getExitCondition() == -1){
            m.setExitCondition(0);
            JOptionPane.showMessageDialog(this,"You lost!" + "\nTotal Cheese Ate: "+m.getTotalCheese()
                    + "\nTotal Moves: " + m.getTotalMove(), "Game Over", JOptionPane.INFORMATION_MESSAGE);
            m.clearMouse(); //clear details of mouse
            Cat.setCatCount(2);
            Maze.clearMaze(); //clear maze
            mainPanel.setEnabled(false); //disable movement of mouse and cats
            startButton.setEnabled(true); //enable start button
            resetButton.setEnabled(false);
        }
    }
}