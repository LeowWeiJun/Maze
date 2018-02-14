import javax.swing.*;
import java.awt.*;

public class Cat {
    private int currCatx; //current Catx
    private int currCaty; //current Caty
    private int newCatx;
    private int newCaty;
    private boolean eatCheese = false;
    private static ImageIcon catIcon = new ImageIcon("cat.jpg");
    private static Image catImg = catIcon.getImage();
    private static Image image = catImg;
    private static Image newCatImg=image.getScaledInstance(20, 20, Image.SCALE_SMOOTH); //set size
    private static ImageIcon currentImage=new ImageIcon(newCatImg);

    public Cat(){

    }
    //moving cat 1 grid upwards
    public boolean moveUp(){
        newCatx = currCatx - 1;
        newCaty = currCaty ;

        //catx-=1;
        //ignore if blocked by wall or crush one cat2
        if (Maze.map[newCatx][newCaty] == 1 || Maze.mazeLabel[newCatx][newCaty].getIcon() == getImage()){
            newCatx+=1;
            return false;
        }
        else{
            preventEatcheese(currCatx,currCaty);
            eatCheese(newCatx,newCaty);
            updateLocation();
            return true;
        }
    }

    //moving cat 1 grid downwards
    public boolean moveDown(){
        newCatx = currCatx + 1;
        newCaty = currCaty ;

        if (Maze.map[newCatx][newCaty] == 1 || Maze.mazeLabel[newCatx][newCaty].getIcon() == getImage()){//)
            newCatx-=1;
            return false;
        }
        else{
            preventEatcheese(currCatx,currCaty);
            eatCheese(newCatx,newCaty);
            updateLocation();
            return true;
        }
    }

    //moving cat 1 grid to the left
    public boolean moveLeft(){
        newCatx = currCatx ;
        newCaty = currCaty - 1;

        if (Maze.map[newCatx][newCaty] == 1  || Maze.mazeLabel[newCatx][newCaty].getIcon() == Cat.getImage()){
            newCaty+=1;
            return false;
        }
        else{
            preventEatcheese(currCatx,currCaty);
            eatCheese(newCatx,newCaty);
            updateLocation();
            return true;
        }
    }

    //moving cat 1 grid to the right
    public boolean moveRight(){
        newCatx = currCatx ;
        newCaty = currCaty + 1;

        if (Maze.map[newCatx][newCaty] == 1 || Maze.mazeLabel[newCatx][newCaty].getIcon() == Cat.getImage()){
            newCaty-=1;
            return false;
        }
        else {
            preventEatcheese(currCatx,currCaty);
            eatCheese(newCatx,newCaty);
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

    //Set method for Cat Image
    public static ImageIcon getImage(){
        return currentImage;
    }

    //update the location of cat in 2D array and display windows
    public void updateLocation(){

        Maze.map[newCatx][newCaty] = 'c'; //set the new location of cat to value c
        Maze.mazeLabel[newCatx][newCaty].setIcon(Cat.getImage());   //spawn cat1 at new spot
        //System.out.println("cat: "+catx+" "+caty);
        currCatx = newCatx;
        currCaty = newCaty;
    }

    public void eatCheese(int catx, int caty){
        try{
            if(Maze.mazeLabel[catx][caty].getIcon() == Maze.getImage()){
                eatCheese = true;
            }
        }catch(ArrayIndexOutOfBoundsException arr){}
    }

    public void preventEatcheese(int Catx, int Caty){
        if(eatCheese == true ){
            System.out.println("cat : " + getCatx() + " " + getCaty());
            Maze.mazeLabel[Catx][Caty].setIcon(Maze.getImage());   //spawn cheese if cat eat it
            eatCheese = false;
        }
        else{
            Maze.map[currCatx][currCaty] = 0; //set the old location  of cat to value to 0 in the 2D array
            Maze.mazeLabel[currCatx][currCaty].setIcon(null); //remove old cat1 spot
        }
    }
}
