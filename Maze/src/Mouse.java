import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Mouse {
    private int mousex;
    private int mousey;
    private int cheeseEat = 0;
    private int totalCheese = 0;
    private int moveCount = 0;
    private int totalMove = 0;
    private static ImageIcon iconMouse = new ImageIcon("mouse.png");
    private static Image mouseImg = iconMouse.getImage();
    private static ImageIcon iconSuperMouse = new ImageIcon("supermouse.PNG");
    private static Image superMouseImg = iconSuperMouse.getImage();
    private static Image image = mouseImg;
    private static Image newimage=image.getScaledInstance(20, 20, Image.SCALE_SMOOTH); //set size
    private static ImageIcon currentImg=new ImageIcon(newimage);


    public Mouse(){
    }

    //moving mouse 1 grid upwards
    public boolean moveUp(){
        try{
            eatCheese(mousex-1, mousey); //Eat cheese
            mousex-=1;
            //ignore if blocked by wall or crush on cats (prevent suicidal)
            if (Maze.map[mousex][mousey]==1 || Maze.mazeLabel[mousex][mousey].getIcon() == Cat.getImage()  ){//|| (mousex==cat1.getCatx() && mousey ==cat1.getCaty()) || (mousex==cat2.getCatx() && mousey == cat2.getCaty())
                mousex+=1;
                return false;
            }
            else{
                moveCount--; //decrease the movecount (for transform usage)
                totalMove++; //increase the total move count
                Maze.mazeLabel[mousex+1][mousey].setIcon(null); //remove old mouse spot
                Maze.mazeLabel[mousex][mousey].setIcon(currentImg); //spawn mouse at new spot
                System.out.println(mousex+" "+mousey);
                return true;
            }
        }catch(ArrayIndexOutOfBoundsException arr){mousex+=1;}
        return false;
    }

    //moving mouse 1 grid downwards
    public boolean moveDown(){
        try{
            eatCheese(mousex+1, mousey);
            mousex+=1;
            if (Maze.map[mousex][mousey]==1 || Maze.mazeLabel[mousex][mousey].getIcon() == Cat.getImage()){
                mousex-=1;
                return false;
            }
            else{
                moveCount--;
                totalMove++;
                Maze.mazeLabel[mousex-1][mousey].setIcon(null);
                Maze.mazeLabel[mousex][mousey].setIcon(currentImg);
                System.out.println(mousex+" "+mousey);
                return true;
            }
        }catch(ArrayIndexOutOfBoundsException arr){mousex-=1;}
        return false;
    }

    //moving mouse 1 grid to the left
    public boolean moveLeft(){
        try{
            eatCheese(mousex, mousey-1);
            mousey-=1;
            if (Maze.map[mousex][mousey]==1 || Maze.mazeLabel[mousex][mousey].getIcon() == Cat.getImage()){
                mousey+=1;
                return false;
            }
            else{
                moveCount--;
                totalMove++;
                Maze.mazeLabel[mousex][mousey+1].setIcon(null);
                Maze.mazeLabel[mousex][mousey].setIcon(currentImg);
                System.out.println(mousex+" "+mousey);
                return true;
            }
        }catch(ArrayIndexOutOfBoundsException arr){mousey+=1;}
        return false;
    }

    //moving mouse 1 grid to the right
    public boolean moveRight(){
        try{
            eatCheese(mousex, mousey+1);
            mousey+=1;
            if (Maze.map[mousex][mousey]==1 || Maze.mazeLabel[mousex][mousey].getIcon() == Cat.getImage()){
                mousey-=1;
                return false;
            }
            else{
                moveCount--;
                totalMove++;
                Maze.mazeLabel[mousex][mousey-1].setIcon(null);
                Maze.mazeLabel[mousex][mousey].setIcon(currentImg);
                System.out.println(mousex+" "+mousey);
                return true;
            }
        }catch(ArrayIndexOutOfBoundsException arr){mousey-=1;}
        return false;
    }
    //Set method for mouse x value
    public void setMousex(int mousex){
        this.mousex = mousex;
    }

    //Get method for mouse x value
    public int getMousex(){
        return mousex;
    }

    //Set method for mouse y value
    public void setMousey(int mousey){
        this.mousey = mousey;
    }

    //Get method for mouse y value
    public int getMousey(){
        return mousey;
    }

    //Eat Cheese
    public void eatCheese(int mousex, int mousey){
        try{
            if(Maze.mazeLabel[mousex][mousey].getIcon() == Maze.getImage()){
                cheeseEat++; //calculate the cheeseEat (for transform usage)
                totalCheese++; //calculate total cheese eat
            }
        }catch(ArrayIndexOutOfBoundsException arr){}


    }

    //Get method for cheeseEat
    public int getCheeseEat(){
        return cheeseEat;
    }

    //transform into super mouse
    public void transSuperMouse(){
        if(cheeseEat == 3){ //transform after eat 3 cheese
            setImage(superMouseImg); //change the image to super mouse image
            Maze.mazeLabel[mousex][mousey].setIcon(currentImg); //display the image on the maze
            cheeseEat = 0; //Reset the cheese eat
            moveCount = 10; //Super mouse form last for 10move
        }
    }

    //transform back into mouse
    public void transMouse(){
        if(moveCount == 0){ //when the 10move used up
            setImage(mouseImg); //change the image to mouse image
            Maze.mazeLabel[mousex][mousey].setIcon(currentImg); //display the image on the maze

            cheeseEat = 0; //Reset the cheese eat that the mouse ate during super mouse form (prevent continuous transform into super mouse)
        }
    }

    //Set method for Mouse Image
    public void setImage(Image image){
        this.image = image;
        newimage=image.getScaledInstance(20, 20, Image.SCALE_SMOOTH); //set size
        currentImg=new ImageIcon(newimage);
    }

    //Set method for Mouse Image
    public static ImageIcon getImage(){
        return currentImg;
    }

}
