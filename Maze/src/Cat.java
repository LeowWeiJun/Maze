import javax.swing.*;
import java.awt.*;

public class Cat {
    private int catx;
    private int caty;
    private static ImageIcon catIcon = new ImageIcon("cat.jpg");
    private static Image catImg = catIcon.getImage();
    private static Image image = catImg;
    private static Image newCatImg=image.getScaledInstance(20, 20, Image.SCALE_SMOOTH); //set size
    private static ImageIcon currentImage=new ImageIcon(newCatImg);

    public Cat(){

    }
    //moving cat 1 grid upwards
    public boolean moveUp(){
        catx-=1;
        //ignore if blocked by wall or crush one cat2
        if (Maze.map[catx][caty] == 1 || Maze.mazeLabel[catx][caty].getIcon() == getImage()){
            catx+=1;
            return false;
        }
        else{
            Maze.mazeLabel[catx+1][caty].setIcon(null); //remove old cat1 spot
            Maze.mazeLabel[catx][caty].setIcon(Cat.getImage());   //spawn cat1 at new spot
            //System.out.println("cat: "+catx+" "+caty);
            return true;
        }
    }

    //moving cat 1 grid downwards
    public boolean moveDown(){
        catx+=1;
        if (Maze.map[catx][caty] == 1 || Maze.mazeLabel[catx][caty].getIcon() == getImage()){//)
            catx-=1;
            return false;
        }
        else{
            Maze.mazeLabel[catx-1][caty].setIcon(null);
            Maze.mazeLabel[catx][caty].setIcon(Cat.getImage());
            //System.out.println("cat: "+catx+" "+caty);
            return true;
        }
    }

    //moving cat 1 grid to the left
    public boolean moveLeft(){
        caty-=1;
        if (Maze.map[catx][caty] == 1  || Maze.mazeLabel[catx][caty].getIcon() == Cat.getImage()){
            caty+=1;
            return false;
        }
        else{
            Maze.mazeLabel[catx][caty+1].setIcon(null);
            Maze.mazeLabel[catx][caty].setIcon(Cat.getImage());
            //System.out.println("cat: "+catx+" "+caty);
            return true;
        }
    }

    //moving cat 1 grid to the right
    public boolean moveRight(){
        caty+=1;
        if (Maze.map[catx][caty] == 1 || Maze.mazeLabel[catx][caty].getIcon() == Cat.getImage()){
            caty-=1;
            return false;
        }
        else {
            Maze.mazeLabel[catx][caty-1].setIcon(null);
            Maze.mazeLabel[catx][caty].setIcon(Cat.getImage());
            //System.out.println("cat: "+catx+" "+caty);
            return true;
        }
    }
    //Set method for cat x value
    public void setCatx(int catx){
        this.catx = catx;
    }

    //Get method for cat x value
    public int getCatx(){
        return catx;
    }

    //Set method for cat y value
    public void setCaty(int caty){
        this.caty = caty;
    }

    //Get method for cat x value
    public int getCaty(){
        return caty;
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
}
