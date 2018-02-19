import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Cat {
    private int currCatx; //current Catx
    private int currCaty; //current Caty
    private int newCatx;
    private int newCaty;
    private boolean removeCheese = false;
    private boolean removeExit = false;
    private static int catCount = 0;
    private static ImageIcon catIcon = new ImageIcon("cat.jpg");
    private static Image catImg = catIcon.getImage();
    private static Image image = catImg;
    private static Image newCatImg=image.getScaledInstance(20, 20, Image.SCALE_SMOOTH); //set size
    private static ImageIcon currentImage=new ImageIcon(newCatImg);
    public static Random rand = new Random();

    public Cat(){
        catCount++;
        System.out.println("catCountL: " + catCount);
    }
    //moving cat 1 grid upwards
    public boolean moveUp(){
        newCatx = currCatx - 1;
        newCaty = currCaty ;
        eatMouse(newCatx, newCaty);

        //catx-=1;
        //ignore if blocked by wall or crush one cat2 or super mouse
        if (Maze.map[newCatx][newCaty] == 1 || Maze.mazeLabel[newCatx][newCaty].getIcon() == getImage() || Maze.map[newCatx][newCaty] == 4){
            newCatx+=1;
            return false;
        }
        else{
            preventRemoveCheeseOrExit(currCatx,currCaty);
            removeCheeseOrExit(newCatx,newCaty);
            updateLocation();
            return true;
        }
    }

    //moving cat 1 grid downwards
    public boolean moveDown(){
        newCatx = currCatx + 1;
        newCaty = currCaty ;
        eatMouse(newCatx, newCaty);

        if (Maze.map[newCatx][newCaty] == 1 || Maze.mazeLabel[newCatx][newCaty].getIcon() == getImage() || Maze.map[newCatx][newCaty] == 4){//)
            newCatx-=1;
            return false;
        }
        else{
            preventRemoveCheeseOrExit(currCatx,currCaty);
            removeCheeseOrExit(newCatx,newCaty);
            updateLocation();
            return true;
        }
    }

    //moving cat 1 grid to the left
    public boolean moveLeft(){
        newCatx = currCatx ;
        newCaty = currCaty - 1;
        eatMouse(newCatx, newCaty);

        if (Maze.map[newCatx][newCaty] == 1  || Maze.mazeLabel[newCatx][newCaty].getIcon() == Cat.getImage() || Maze.map[newCatx][newCaty] == 4){
            newCaty+=1;
            return false;
        }
        else{
            preventRemoveCheeseOrExit(currCatx,currCaty);
            removeCheeseOrExit(newCatx,newCaty);
            updateLocation();
            return true;
        }
    }

    //moving cat 1 grid to the right
    public boolean moveRight(){
        newCatx = currCatx ;
        newCaty = currCaty + 1;
        eatMouse(newCatx, newCaty);

        if (Maze.map[newCatx][newCaty] == 1 || Maze.mazeLabel[newCatx][newCaty].getIcon() == Cat.getImage() || Maze.map[newCatx][newCaty] == 4){
            newCaty-=1;
            return false;
        }
        else {
            preventRemoveCheeseOrExit(currCatx,currCaty);
            removeCheeseOrExit(newCatx,newCaty);
            updateLocation();
            return true;
        }
    }
    //Set method for cat x value
    public void setCatx(int currCatx){
        this.currCatx = currCatx;
    }

    //Get method for cat x value
    public int getCatx(){
        return currCatx;
    }

    //Set method for cat y value
    public void setCaty(int currCaty){
        this.currCaty = currCaty;
    }

    //Get method for cat x value
    public int getCaty(){
        return currCaty;
    }

    //Set method for Cat Image
    public void setImage(Image image){
        this.image = image;
        Image newCatImg=image.getScaledInstance(20, 20, Image.SCALE_SMOOTH); //set size
        ImageIcon currentImage=new ImageIcon(newCatImg);
    }

    //Eat Mouse
    public void eatMouse(int catx, int caty){
        try{
            if(Maze.mazeLabel[catx][caty].getIcon() == Mouse.getImage() && Maze.map[catx][caty] != 4) {
                System.out.println("Cat eats mouse");
                //cheeseEat++; //calculate the cheeseEat (for transform usage)
                //totalCheese++; //calculate total cheese eat
                Mouse.setExitCondition(-1);

            }

        }catch(ArrayIndexOutOfBoundsException arr){}


    }

    //Set method for Cat Image
    public static ImageIcon getImage(){
        return currentImage;
    }

    //update the location of cat in 2D array and display windows
    public void updateLocation(){

        Maze.map[newCatx][newCaty] = 3; //set the new location of cat to value c
        Maze.mazeLabel[newCatx][newCaty].setIcon(Cat.getImage());   //spawn cat1 at new spot
        //System.out.println("cat: "+catx+" "+caty);
        currCatx = newCatx;
        currCaty = newCaty;
    }

    public void removeCheeseOrExit(int catx, int caty){ //check if cat removes cheese or exit and set removeCheese and removeExit
        try{
            if(Maze.mazeLabel[catx][caty].getIcon() == Maze.getCheeseImage()){ //if cat is on the same grid as cheese
                removeCheese = true;
            }
            if(Maze.mazeLabel[catx][caty].getIcon() == Maze.getExitImage()){ //if cat is on the same grid as exit
                removeExit = true;
            }
        }catch(ArrayIndexOutOfBoundsException arr){}
    }

    public void preventRemoveCheeseOrExit(int Catx, int Caty){
        if(removeCheese == true ){
            //System.out.println("cat : " + getCatx() + " " + getCaty());
            Maze.mazeLabel[Catx][Caty].setIcon(Maze.getCheeseImage());   //spawn cheese if cat removes it
            Maze.map[Catx][Caty] = 0;
            removeCheese = false;
        }
        else if(removeExit == true ){
            //System.out.println("cat : " + getCatx() + " " + getCaty());
            Maze.mazeLabel[Catx][Caty].setIcon(Maze.getExitImage());   //spawn exit if cat removes it
            Maze.map[Catx][Caty] = 0;
            removeExit = false;
        }
        else{
            Maze.map[currCatx][currCaty] = 0; //set the old location  of cat to value to 0 in the 2D array
            Maze.mazeLabel[currCatx][currCaty].setIcon(null); //remove old cat1 spot
        }
    }

    public static int getCatCount(){return catCount;}
    public static void setCatCount(int catCount){Cat.catCount = catCount;} //when supermouse eats cat, set catCount--;

    public static int chaseMouse(Cat cat, Mouse m){

        int x ;
        int distanceX = cat.getCatx() - m.getMousex();
        int distanceY = cat.getCaty() - m.getMousey();

        if(distanceX <= 3 && distanceX > 0 && cat.getCaty() == m.getMousey() && Maze.map[m.getMousex()][m.getMousey()] != 4 && Maze.map[cat.getCatx()-1][cat.getCaty()] != 1 && Maze.map[cat.getCatx()-1][cat.getCaty()] != 3){
            x = 0; //move Up
        }
        else if(distanceX >=-3 && distanceX < 0 && cat.getCaty() == m.getMousey() && Maze.map[m.getMousex()][m.getMousey()] != 4 && Maze.map[cat.getCatx()+1][cat.getCaty()] != 1 && Maze.map[cat.getCatx()+1][cat.getCaty()] != 3){
            x = 1; //move Down
        }
        else if(distanceY <= 3 && distanceY > 0 && cat.getCatx() == m.getMousex() && Maze.map[m.getMousex()][m.getMousey()] != 4 && Maze.map[cat.getCatx()][cat.getCaty()-1] != 1 && Maze.map[cat.getCatx()][cat.getCaty()-1] != 3){
            x = 2; //move Left
        }
        else if(distanceY >=-3 && distanceY < 0 && cat.getCatx() == m.getMousex() && Maze.map[m.getMousex()][m.getMousey()] != 4 && Maze.map[cat.getCatx()][cat.getCaty()+1] != 1 && Maze.map[cat.getCatx()][cat.getCaty()+1] != 3){
            x = 3; //move Right
        }
        else if(distanceX <= 3 && distanceX > 0 && distanceY <= 3 && distanceY > 0 && Maze.map[m.getMousex()][m.getMousey()] != 4 && ((Maze.map[cat.getCatx()-1][cat.getCaty()] != 1 && Maze.mazeLabel[cat.getCatx()-1][cat.getCaty()].getIcon() != Cat.getImage()) || (Maze.map[cat.getCatx()][cat.getCaty()-1] != 1 && Maze.mazeLabel[cat.getCatx()][cat.getCaty()-1].getIcon() != Cat.getImage()))){//
            int y=rand.nextInt(4);
            //move Up or Left
            if (y % 2 == 0){
                x = y;
            }
            else{
                x = -1;
            }
        }
        else if(distanceX >=-3 && distanceX < 0 && distanceY <= 3 && distanceY > 0 && Maze.map[m.getMousex()][m.getMousey()] != 4 && ((Maze.map[cat.getCatx()+1][cat.getCaty()] != 1 && Maze.mazeLabel[cat.getCatx()+1][cat.getCaty()].getIcon() != Cat.getImage()) || (Maze.map[cat.getCatx()][cat.getCaty()-1] != 1 && Maze.mazeLabel[cat.getCatx()][cat.getCaty()-1].getIcon() != Cat.getImage()))){//
            x = rand.nextInt(1+1)+1; //move Down or left
        }
        else if(distanceX <= 3 && distanceX > 0 && distanceY >=-3 && distanceY < 0 && Maze.map[m.getMousex()][m.getMousey()] != 4 && ((Maze.map[cat.getCatx()-1][cat.getCaty()] != 1 && Maze.mazeLabel[cat.getCatx()-1][cat.getCaty()].getIcon() != Cat.getImage())|| (Maze.map[cat.getCatx()][cat.getCaty()+1] != 1 && Maze.mazeLabel[cat.getCatx()][cat.getCaty()+1].getIcon() != Cat.getImage()))){//
            int y=rand.nextInt(4);
            //move Up or Right
            if ( y == 0 || y == 3){
                x = y;
            }
            else{
                x = -1;
            }
        }
        else if(distanceX >=-3 && distanceX < 0 && distanceY >=-3 && distanceY < 0 && Maze.map[m.getMousex()][m.getMousey()] != 4 && ((Maze.map[cat.getCatx()+1][cat.getCaty()] != 1 && Maze.mazeLabel[cat.getCatx()+1][cat.getCaty()].getIcon() != Cat.getImage()) || (Maze.map[cat.getCatx()][cat.getCaty()+1] != 1 && Maze.mazeLabel[cat.getCatx()][cat.getCaty()+1].getIcon() != Cat.getImage())) ){//
            int y=rand.nextInt(4);
            //move Down or Right
            if (y % 2 != 0){
                x = y;
            }
            else{
                x = -1;
            }
        }
        else{
            x=rand.nextInt(4); //random 4 direction
        }

        return x;
    }
}