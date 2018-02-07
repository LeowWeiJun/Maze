import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Mouse {
    private int mousex;
    private int mousey;
    public static ImageIcon icon1 = new ImageIcon("mouse.png");
    public static Image img1 = icon1.getImage();
    public static Image newimg1=img1.getScaledInstance(20, 20, Image.SCALE_SMOOTH); //set size
    public static ImageIcon mouse=new ImageIcon(newimg1);

    public Mouse(){
    }

    public boolean moveUp(){
        try{
            mousex-=1;
            //ignore if blocked by wall or crush on cats (prevent suicidal)
            if (Maze.map[mousex][mousey]==1 || main.mazeLabel[mousex][mousey].getIcon() == Cat.cat  ){//|| (mousex==cat1.getCatx() && mousey ==cat1.getCaty()) || (mousex==cat2.getCatx() && mousey == cat2.getCaty())
                mousex+=1;
                return false;
            }
            else{
                main.mazeLabel[mousex+1][mousey].setIcon(null); //remove old mouse spot
                main.mazeLabel[mousex][mousey].setIcon(mouse); //spawn mouse at new spot
                System.out.println(mousex+" "+mousey);
                return true;
            }
        }catch(ArrayIndexOutOfBoundsException arr){mousex+=1;}
        return false;
    }

    public boolean moveDown(){
        try{
            mousex+=1;
            if (Maze.map[mousex][mousey]==1 || main.mazeLabel[mousex][mousey].getIcon() == Cat.cat){
                mousex-=1;
                return false;
            }
            else{
                main.mazeLabel[mousex-1][mousey].setIcon(null);
                main.mazeLabel[mousex][mousey].setIcon(mouse);
                System.out.println(mousex+" "+mousey);
                return true;
            }
        }catch(ArrayIndexOutOfBoundsException arr){mousex-=1;}
        return false;
    }

    public boolean moveLeft(){
        try{
            mousey-=1;
            if (Maze.map[mousex][mousey]==1 || main.mazeLabel[mousex][mousey].getIcon() == Cat.cat){
                mousey+=1;
                return false;
            }
            else{
                main.mazeLabel[mousex][mousey+1].setIcon(null);
                main.mazeLabel[mousex][mousey].setIcon(mouse);
                System.out.println(mousex+" "+mousey);
                return true;
            }
        }catch(ArrayIndexOutOfBoundsException arr){mousey+=1;}
        return false;
    }

    public boolean moveRight(){
        try{
            mousey+=1;
            if (Maze.map[mousex][mousey]==1 || main.mazeLabel[mousex][mousey].getIcon() == Cat.cat){
                mousey-=1;
                return false;
            }
            else{
                main.mazeLabel[mousex][mousey-1].setIcon(null);
                main.mazeLabel[mousex][mousey].setIcon(mouse);
                System.out.println(mousex+" "+mousey);
                return true;
            }
        }catch(ArrayIndexOutOfBoundsException arr){mousey-=1;}
        return false;
    }
    public void setMousex(int mousex){
        this.mousex = mousex;
    }

    public int getMousex(){
        return mousex;
    }

    public void setMousey(int mousey){
        this.mousey = mousey;
    }

    public int getMousey(){
        return mousey;
    }



}
