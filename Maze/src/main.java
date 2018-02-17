//import sun.applet.Main;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;


public class main extends JFrame{
    public static Random rand = new Random();

    public static void main(String[] args){
        new main();
    }

    public main(){

        super("Maze"); //call superclass constructor (no more mainFrame initialization)
        this.setSize(700,652);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        MainPanel mainPanel = new MainPanel();
        this.add(mainPanel);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        this.setVisible(true);

    }

}