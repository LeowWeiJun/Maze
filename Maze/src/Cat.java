import javax.swing.*;
import java.awt.*;

public class Cat {
    private int catx;
    private int caty;
    public static ImageIcon icon2 = new ImageIcon("cat.jpg");
    public static Image img2 = icon2.getImage();
    public static Image newimg2=img2.getScaledInstance(20, 20, Image.SCALE_SMOOTH); //set size
    public static ImageIcon cat=new ImageIcon(newimg2);

    public Cat(){

    }

    public boolean moveUp(){
        catx-=1;
        //ignore if blocked by wall or crush one cat2
        if (Maze.map[catx][caty] == 1 || main.mazeLabel[catx][caty].getIcon() == Cat.cat){
            catx+=1;
            return false;
        }
        else{
            main.mazeLabel[catx+1][caty].setIcon(null); //remove old cat1 spot
            main.mazeLabel[catx][caty].setIcon(cat);   //spawn cat1 at new spot
            System.out.println("cat: "+catx+" "+caty);
            return true;
        }
    }

    public boolean moveDown(){
        catx+=1;
        if (Maze.map[catx][caty] == 1 || main.mazeLabel[catx][caty].getIcon() == Cat.cat){//)
            catx-=1;
            return false;
        }
        else{
            main.mazeLabel[catx-1][caty].setIcon(null);
            main.mazeLabel[catx][caty].setIcon(cat);
            System.out.println("cat: "+catx+" "+caty);
            return true;
        }
    }

    public boolean moveLeft(){
        caty-=1;
        if (Maze.map[catx][caty] == 1  || main.mazeLabel[catx][caty].getIcon() == Cat.cat){
            caty+=1;
            return false;
        }
        else{
            main.mazeLabel[catx][caty+1].setIcon(null);
            main.mazeLabel[catx][caty].setIcon(cat);
            System.out.println("cat: "+catx+" "+caty);
            return true;
        }
    }

    public boolean moveRight(){
        caty+=1;
        if (Maze.map[catx][caty] == 1 || main.mazeLabel[catx][caty].getIcon() == Cat.cat){
            caty-=1;
            return false;
        }
        else{
            main.mazeLabel[catx][caty-1].setIcon(null);
            main.mazeLabel[catx][caty].setIcon(cat);
            System.out.println("cat: "+catx+" "+caty);
            return true;
        }
    }

    public void setCatx(int catx){
        this.catx = catx;
    }

    public int getCatx(){
        return catx;
    }

    public void setCaty(int caty){
        this.caty = caty;
    }

    public int getCaty(){
        return caty;
    }
}
