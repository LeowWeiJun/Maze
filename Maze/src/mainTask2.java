import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;


public class mainTask2 extends JFrame implements KeyListener{
    static int columns = 30;
    static int rows = 30;
    public static int map[][] = new int[rows][columns];
    public static int mousex,  mousey, catx1, caty1, catx2, caty2; //coordinate of mouse and cats
    public static JLabel[][] mazeLabel = new JLabel[30][30]; //this mazeLabel initialization i bring up because my keyPressed function nid it
    public static Random rand = new Random();

    //set mouse icon
    public static ImageIcon icon1 = new ImageIcon("mouse.png");
    public static Image img1 = icon1.getImage();
    public static Image newimg1=img1.getScaledInstance(20, 20, Image.SCALE_SMOOTH); //set size
    public static ImageIcon mouse=new ImageIcon(newimg1);

    //set cat icon
    public static ImageIcon icon2 = new ImageIcon("cat.jpg");
    public static Image img2 = icon2.getImage();
    public static Image newimg2=img2.getScaledInstance(20, 20, Image.SCALE_SMOOTH); //set size
    public static ImageIcon cat=new ImageIcon(newimg2);

    public static void main(String[] args){
        new main();
    }


    public mainTask2(){
        
        super("Whack-a-mole"); //call superclass constructor (no more mainFrame initialization)
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
        loadMap(str);


        

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        
        for(int i = 0 ; i < 30 ; i++){
            for(int j = 0 ; j < 30 ; j++){
                mazeLabel[i][j] = new JLabel();
                mazeLabel[i][j].setOpaque(true);
                if(map[i][j] == 1)
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
            mousex = rand.nextInt(30);
            mousey = rand.nextInt(30);

            //prevent spawn on wall and exit (assume exit at [29][29])
            if(map[mousex][mousey] == 0){
                if(mousex==29){
                    if(mousey==29)
                        continue;
                }
                mazeLabel[mousex][mousey].setIcon(mouse); //spawn mouse
                System.out.println(mousex+" "+mousey);
                break;
            }
        }while(true);

        do{
            //random 0-29
            catx1 = rand.nextInt(30);
            caty1 = rand.nextInt(30);

            //if coordinate same with mouse then re-random
            if(map[catx1][caty1] == 1 || (catx1 == mousex && caty1 == mousey)){
                continue;
            }
            mazeLabel[catx1][caty1].setIcon(cat); //spawn cat1
            break;
        }while(true);

        do{
            //random 0-29
            catx2 = rand.nextInt(30);
            caty2 = rand.nextInt(30);

            //if coordinate same with mouse and cat1 then re-random
            if(map[catx1][caty1] == 1 || (catx2 == mousex && caty2 == mousey) || (catx2 == catx1 && caty2 == caty1)){
                continue;
            }
            mazeLabel[catx2][caty2].setIcon(cat); //spawn cat2
            break;
        }while(true);
       //Color tes = testButton.getBackground();
        
        this.setVisible(true);
    }

    //keyListener
    public void keyPressed(KeyEvent e){
        moveMouse(e);
        
    } 
    //not using this 2 function
    public void keyTyped(KeyEvent e){}
    public void keyReleased(KeyEvent e){}
    
    //control mouse with arrow key and WASD
    public static void moveMouse(KeyEvent e){
        int code = e.getKeyCode();
        try{
            if (code == KeyEvent.VK_UP || code ==KeyEvent.VK_W){
                mousex-=1;
                //ignore if blocked by wall or crush on cats (prevent suicidal)
                if (map[mousex][mousey]==1 || (mousex==catx1 && mousey ==caty1) || (mousex==catx2 && mousey == caty2)){
                    mousex+=1;
                } 
                else{
                    mazeLabel[mousex+1][mousey].setIcon(null); //remove old mouse spot
                    mazeLabel[mousex][mousey].setIcon(mouse); //spawn mouse at new spot
                    System.out.println(mousex+" "+mousey);
                    moveCat1();
                    moveCat2();
                }
            }
        }catch(ArrayIndexOutOfBoundsException arr){mousex+=1;} //ignore if out of maze range 30x30
        try{
            if (code == KeyEvent.VK_DOWN || code ==KeyEvent.VK_S){
                mousex+=1;
                if (map[mousex][mousey]==1 || (mousex==catx1 && mousey ==caty1) || (mousex==catx2 && mousey == caty2)){
                    mousex-=1;
                }
                else{
                    mazeLabel[mousex-1][mousey].setIcon(null);
                    mazeLabel[mousex][mousey].setIcon(mouse); 
                    System.out.println(mousex+" "+mousey);
                    moveCat1();
                    moveCat2();
                }
            }
        }catch(ArrayIndexOutOfBoundsException arr){mousex-=1;}
        try{
            if (code == KeyEvent.VK_LEFT || code ==KeyEvent.VK_A){
                mousey-=1;
                if (map[mousex][mousey]==1 || (mousex==catx1 && mousey ==caty1) || (mousex==catx2 && mousey == caty2)){
                    mousey+=1;
                } 
                else{
                    mazeLabel[mousex][mousey+1].setIcon(null);
                    mazeLabel[mousex][mousey].setIcon(mouse); 
                    System.out.println(mousex+" "+mousey);
                    moveCat1();
                    moveCat2();
                }
            }
        }catch(ArrayIndexOutOfBoundsException arr){mousey+=1;}
        try{
            if (code == KeyEvent.VK_RIGHT || code ==KeyEvent.VK_D){
                mousey+=1;
                if (map[mousex][mousey]==1 || (mousex==catx1 && mousey ==caty1) || (mousex==catx2 && mousey == caty2)){
                    mousey-=1;
                }
                else{
                    mazeLabel[mousex][mousey-1].setIcon(null);
                    mazeLabel[mousex][mousey].setIcon(mouse); 
                    System.out.println(mousex+" "+mousey);
                    moveCat1();
                    moveCat2();
                }
                    
            } 
        }catch(ArrayIndexOutOfBoundsException arr){mousey-=1;} 
    }

    //cat1 movement
    public static void moveCat1(){
        int x;
        while(true){
            x=rand.nextInt(4); //random 4 direction

            //up
            if (x==0){
                try{
                    catx1-=1;
                    //ignore if blocked by wall or crush one cat2
                    if (map[catx1][caty1] == 1 || (catx1 == catx2 && caty1 == caty2)){
                        catx1+=1;
                    }
                    else{
                        mazeLabel[catx1+1][caty1].setIcon(null); //remove old cat1 spot
                        mazeLabel[catx1][caty1].setIcon(cat);   //spawn cat1 at new spot
                        System.out.println("cat: "+catx1+" "+caty1);
                        break;
                    }
                }catch(ArrayIndexOutOfBoundsException a){catx1+=1;}
            }
            //down
            if (x==1){
                try{
                    catx1+=1;
                    if (map[catx1][caty1] == 1 || (catx1 == catx2 && caty1 == caty2)){
                        catx1-=1;
                    }
                    else{
                        mazeLabel[catx1-1][caty1].setIcon(null);
                        mazeLabel[catx1][caty1].setIcon(cat);
                        System.out.println("cat: "+catx1+" "+caty1);
                        break;
                    }
                }catch(ArrayIndexOutOfBoundsException a){catx1-=1;} 
            } 
            //left
            if (x==2){
                try{
                    caty1-=1;
                    if (map[catx1][caty1] == 1 || (catx1 == catx2 && caty1 == caty2)){
                        caty1+=1;
                    }
                    else{
                        mazeLabel[catx1][caty1+1].setIcon(null);
                        mazeLabel[catx1][caty1].setIcon(cat);
                        System.out.println("cat: "+catx1+" "+caty1);
                        break;
                    }
                }catch(ArrayIndexOutOfBoundsException a){caty1+=1;}
            }
            //right
            if (x==3){
                try{
                    caty1+=1;
                    if (map[catx1][caty1] == 1 || (catx1 == catx2 && caty1 == caty2)){
                        caty1-=1;
                    }
                    else{
                        mazeLabel[catx1][caty1-1].setIcon(null);
                        mazeLabel[catx1][caty1].setIcon(cat);
                        System.out.println("cat: "+catx1+" "+caty1);
                        break;
                    }
                }catch(ArrayIndexOutOfBoundsException a){caty1-=1;} 
            }

        }
    }

    //cat2 movement
    public static void moveCat2(){
        int x;
        while(true){
            x=rand.nextInt(4);
           
            if (x==0){
                try{
                    catx2-=1;
                    if (map[catx2][caty2] == 1 || (catx2 == catx1 && caty2 == caty1)){
                        catx2+=1;
                    }
                    else{
                        mazeLabel[catx2+1][caty2].setIcon(null);
                        mazeLabel[catx2][caty2].setIcon(cat);
                        System.out.println("cat: "+catx2+" "+caty2);
                        break;
                    }
                }catch(ArrayIndexOutOfBoundsException a){catx2+=1;}
            }
            if (x==1){
                try{
                    caty2-=1;
                    if (map[catx2][caty2] == 1 || (catx2 == catx1 && caty2 == caty1)){
                        caty2+=1;
                    }
                    else{
                        mazeLabel[catx2][caty2+1].setIcon(null);
                        mazeLabel[catx2][caty2].setIcon(cat);
                        System.out.println("cat: "+catx2+" "+caty2);
                        break;
                    }
                }catch(ArrayIndexOutOfBoundsException a){caty2+=1;}
            }
            if (x==2){
                try{
                    caty2+=1;
                    if (map[catx2][caty2] == 1 || (catx2 == catx1 && caty2 == caty1)){
                        caty2-=1;
                    }
                    else{
                        mazeLabel[catx2][caty2-1].setIcon(null);
                        mazeLabel[catx2][caty2].setIcon(cat);
                        System.out.println("cat: "+catx2+" "+caty2);
                        break;
                    }
                }catch(ArrayIndexOutOfBoundsException a){caty2-=1;} 
            }
            if (x==3){
                try{
                    catx2+=1;
                    if (map[catx2][caty2] == 1 || (catx2 == catx1 && caty2 == caty1)){
                        catx2-=1;
                    }
                    else{
                        mazeLabel[catx2-1][caty2].setIcon(null);
                        mazeLabel[catx2][caty2].setIcon(cat);
                        System.out.println("cat: "+catx2+" "+caty2);
                        break;
                    }
                }catch(ArrayIndexOutOfBoundsException a){catx2-=1;} 
            }   
        }
    }


    public static void loadMap(String str){
        try{

            BufferedReader br = new BufferedReader(new FileReader(str));
            System.out.println("x");
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String mapStr = sb.toString();


            int counter = 0;
            for(int y = 0; y < rows; y++){
                for(int x = 0; x < columns; x++){
                    String mapChar = mapStr.substring(counter, counter+1);
                    if(!mapChar.equals("\r\n") && !mapChar.equals("\n")&& !mapChar.equals("\r")){//If it's a number
                        //System.out.print(mapChar);
                        map[y][x] = Integer.parseInt(mapChar);
                    }else{//If it is a line break
                        x--;
                        System.out.print(mapChar);
                    }
                    counter++;
                }
            }
        }catch(Exception e){
            System.out.println("Unable to load existing map(if exists), creating new map.");
        }
    }

}


