import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

public class Maze {
    private static int columns = 30;
    private static int rows = 30;
    private static int cheeseCount = 20;
    private static Random rand = new Random();

    //cheese icon
    private static ImageIcon iconCheese = new ImageIcon("cheese.png");
    private static Image cheeseImg = iconCheese.getImage();
    private static Image image = cheeseImg;
    private static Image newCheeseImg=image.getScaledInstance(20, 20, Image.SCALE_SMOOTH); //set size
    private static ImageIcon currentCheeseImg = new ImageIcon(newCheeseImg);

    //exit icon
    private static ImageIcon iconExit = new ImageIcon("exit.jpg");
    private static Image exitImg = iconExit.getImage();
    private static Image newExitImg = exitImg.getScaledInstance(20, 20, Image.SCALE_SMOOTH); //set size
    private static ImageIcon currentExitImg = new ImageIcon(newExitImg);

    public static int map[][] = new int[rows][columns];
    public static JLabel[][] mazeLabel = new JLabel[30][30]; //this mazeLabel initialization i bring up because my keyPressed function nid it

    public Maze(String str,JPanel panel){
        loadMap(str); //load the map from text file/
        paintWall(panel); //Color the wall/
        //initMaze(mouse,cat1,cat2);
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

    //Spawn 20 cheeses
    public static void spawnCheese(){
        cheeseCount = 20;
        do{
            //random 0-29
            int x = rand.nextInt(30);
            int y = rand.nextInt(30);

            //prevent spawn on wall , mouse and cat
            if(Maze.map[x][y] == 1 || mazeLabel[x][y].getIcon() == Cat.getImage() || mazeLabel[x][y].getIcon() == Mouse.getImage() || mazeLabel[x][y].getIcon() == Maze.getCheeseImage()){
                continue;
            }
            mazeLabel[x][y].setIcon(Maze.getCheeseImage()); //spawn cheese
            System.out.println("Cheese Location: " + x+" "+y);
            cheeseCount--;
            //System.out.print(cheeseCount);

        }while(cheeseCount > 0);
    }

    public static void spawnExit(){
        do{
            //random 0-29
            int x = rand.nextInt(30);
            int y = rand.nextInt(30);

            //prevent spawn on wall , mouse, cat and cheese
            if(Maze.map[x][y] == 1 || mazeLabel[x][y].getIcon() == Cat.getImage()
                    || mazeLabel[x][y].getIcon() == Mouse.getImage() || mazeLabel[x][y].getIcon() == Maze.getCheeseImage()){
                continue;
            }
            mazeLabel[x][y].setIcon(Maze.getExitImage()); //spawn cheese
            System.out.println(x+" "+y);
            break;
        }while(true);
    }

    //Paint the wall
    public void paintWall(JPanel panel){
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

        for(int i = 0 ; i < 30 ; i++){
            for(int j = 0 ; j < 30 ; j++){
                mazeLabel[i][j] = new JLabel();
                mazeLabel[i][j].setOpaque(true);
                if(Maze.map[i][j] == 1)
                    mazeLabel[i][j].setBackground(Color.BLACK);
                mazeLabel[i][j].setBorder(border);
                panel.add(mazeLabel[i][j]);
                //System.out.println("1");
                //mainPanel.repaint();
            }
        }
    }

    //Spawn mouse
    public static void spawnMouse(Mouse m){
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
                Maze.map[m.getMousex()][m.getMousey()] = 2;
                Maze.mazeLabel[m.getMousex()][m.getMousey()].setIcon(Mouse.getImage()); //spawn mouse
                System.out.println(m.getMousex()+" "+m.getMousey());
                break;
            }
        }while(true);

    }



    //Spawn cat
    public static void spawnCat(Cat cat){
        do{
            //random 0-29
            cat.setCatx(rand.nextInt(30));
            cat.setCaty(rand.nextInt(30));


            //if coordinate same with mouse then re-random
            if(Maze.map[cat.getCatx()][cat.getCaty()] == 1 || Maze.mazeLabel[cat.getCatx()][cat.getCaty()].getIcon() == Cat.getImage() || Maze.mazeLabel[cat.getCatx()][cat.getCaty()].getIcon() == Mouse.getImage()){
                continue;
            }
            Maze.map[cat.getCatx()][cat.getCaty()] = 3;
            Maze.mazeLabel[cat.getCatx()][cat.getCaty()].setIcon(Cat.getImage()); //spawn cat1
            break;
        }while(true);
    }

    //print map 2D array // Debug purpose
    public static void printMap(int map[][]){
        for(int i = 0 ; i < rows ; i++){
            for(int j = 0 ; j < columns ; j++){
                System.out.print(Maze.map[i][j]);
                if(j % 30 == 29){
                    System.out.println();
                }
            }
        }

    }

    //Set method for Cheese Image
    public void setCheeseImage(Image image){
        this.image = image;
        newCheeseImg=image.getScaledInstance(20, 20, Image.SCALE_SMOOTH); //set size
        currentCheeseImg=new ImageIcon(newCheeseImg);
    }

    //Get method for Cheese Image
    public static ImageIcon getCheeseImage(){
        return currentCheeseImg;
    }

    //Set method for Exit Image
    public void setExitImage(Image image){
        exitImg = image;
        newExitImg=image.getScaledInstance(20, 20, Image.SCALE_SMOOTH); //set size
        currentExitImg=new ImageIcon(newExitImg);
    }

    //Get method for Exit Image
    public static ImageIcon getExitImage(){
        return currentExitImg;
    }

    public static void clearMaze(){ //clear maze before re-initiating it

        for(int x = 0 ; x < 30; x++) {
            for (int y = 0; y < 30; y++) {
                //System.out.println("clear board" + "x: " + x + ", y:" + y);
                Maze.mazeLabel[x][y].setIcon(null); //set all icons == null
            }
        }
    }
    public static void initMaze(String str, Mouse mouse,Cat cat1,Cat cat2){ //initiate maze with mouse, cats and cheeses
        loadMap(str); //load the map from text file/
        spawnMouse(mouse); // Random Spawn mouse
        spawnCat(cat1); // Random Spawn Cat1
        spawnCat(cat2); // Random Spawn Cat2
        spawnCheese(); // Random Spawn 20 cheese
        spawnExit(); //Spawn exit
    }
}