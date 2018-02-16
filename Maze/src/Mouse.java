import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Mouse {
    private int currMousex; //current Mouse x
    private int currMousey; //current Mouse y
    private int newMousex;
    private int newMousey;
    private int cheeseEat = 0;
    private static int totalCheese = 0;
    private int moveCount = 0;
    private static int totalMove = 0;
    private static int exitCondition = 0; //initialise 0 as exit condition
    private boolean crossWall = false;

    private static ImageIcon iconMouse = new ImageIcon("mouse.png","mouse");
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
            newMousex = currMousex-1;
            newMousey = currMousey;
            enteredHole(newMousex, newMousey); //set exit condition if mouse has entered hole
            eatenByCat(newMousex, newMousey);
            eatCheese(newMousex, newMousey); //Eat cheese

            //Cross wall Condition
            if(Maze.map[newMousex][newMousey] == 1 && Maze.map[newMousex-1][newMousey] != 1 && Maze.map[currMousex][currMousey] == 's' ){ //
                newMousex-=1;
                crossWall(); //record if crossed wall
            }
            //ignore if blocked by wall or crush on cats (prevent suicidal)
            else if (Maze.map[newMousex][newMousey]==1  ){//|| (mousex==cat1.getCatx() && mousey ==cat1.getCaty()) || (mousex==cat2.getCatx() && mousey == cat2.getCaty())
                newMousex+=1;
                return false;
            }

            updateLocation(); //update the value in the map
            //System.out.println(newMousex+" "+newMousey);
            return true;

        }catch(ArrayIndexOutOfBoundsException arr){}
        return false;
    }

    //moving mouse 1 grid downwards
    public boolean moveDown(){
        try{
            newMousex = currMousex+1;
            newMousey = currMousey;
            enteredHole(newMousex, newMousey); //set exit condition if mouse has entered hole
            eatenByCat(newMousex, newMousey);
            eatCheese(newMousex, newMousey); //Eat cheese

            if(Maze.map[newMousex][newMousey] == 1 && Maze.map[newMousex+1][newMousey] != 1 && Maze.map[currMousex][currMousey] == 's' ){ //
                newMousex+=1;
                crossWall(); //record if crossed wall
            }
            else if (Maze.map[newMousex][newMousey]==1 ){
                newMousex-=1;
                return false;
            }

            updateLocation();
            //System.out.println(newMousex+" "+newMousey);
            return true;


        }catch(ArrayIndexOutOfBoundsException arr){}
        return false;
    }

    //moving mouse 1 grid to the left
    public boolean moveLeft(){
        try{
            newMousex = currMousex;
            newMousey = currMousey-1;
            enteredHole(newMousex, newMousey); //set exit condition if mouse has entered hole
            eatenByCat(newMousex, newMousey);
            eatCheese(newMousex, newMousey); //Eat cheese

            if(Maze.map[newMousex][newMousey] == 1 && Maze.map[newMousex][newMousey-1] != 1 && Maze.map[currMousex][currMousey] == 's' ){ //
                newMousey-=1;
                crossWall(); //record if crossed wall
            }
            else if (Maze.map[newMousex][newMousey]==1){
                newMousey+=1;
                return false;
            }

            updateLocation();
            //System.out.println(newMousex+" "+newMousey);
            return true;

        }catch(ArrayIndexOutOfBoundsException arr){}
        return false;
    }

    //moving mouse 1 grid to the right
    public boolean moveRight(){
        try{
            newMousex = currMousex;
            newMousey = currMousey+1;
            enteredHole(newMousex, newMousey); //set exit condition if mouse has entered hole
            eatenByCat(newMousex, newMousey);
            eatCheese(newMousex, newMousey); //Eat cheese

            if(Maze.map[newMousex][newMousey] == 1 && Maze.map[newMousex][newMousey+1] != 1 && Maze.map[currMousex][currMousey] == 's' ){ //
                newMousey+=1;
                crossWall(); //record if crossed wall
            }
            else if (Maze.map[newMousex][newMousey]==1 ){
                newMousey-=1;
                return false;
            }

            updateLocation();
            //System.out.println(newMousex+" "+newMousey);
            return true;

        }catch(ArrayIndexOutOfBoundsException arr){}
        return false;
    }
    //Set method for mouse x value
    public void setMousex(int currMousex){
        this.currMousex = currMousex;
    }

    //Get method for mouse x value
    public int getMousex(){
        return currMousex;
    }

    //Set method for mouse y value
    public void setMousey(int currMousey){
        this.currMousey = currMousey;
    }

    //Get method for mouse y value
    public int getMousey(){
        return currMousey;
    }

    //Eat Cheese
    public void eatCheese(int mousex, int mousey){
        try{
            if(Maze.mazeLabel[mousex][mousey].getIcon() == Maze.getCheeseImage()){
                cheeseEat++; //calculate the cheeseEat (for transform usage)
                totalCheese++; //calculate total cheese eat

            }
            transSuperMouse();

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
            Maze.map[newMousex][newMousey] = 's';
            Maze.mazeLabel[newMousex][newMousey].setIcon(currentImg); //display the image on the maze
            cheeseEat = 0; //Reset the cheese eat
            moveCount = 10; //Super mouse form last for 10move
        }
    }

    //transform back into mouse
    public void transMouse(){
        if(moveCount == 0 || crossWall == true){ //when the 10move used up or crossed wall
            setImage(mouseImg); //change the image to mouse image
            Maze.map[newMousex][newMousey] = 2;
            Maze.mazeLabel[newMousex][newMousey].setIcon(currentImg); //display the image on the maze

            crossWall = false; //set the crosswall to false
            //cheeseEat = 0; //Reset the cheese eat that the mouse ate during super mouse form (prevent continuous transform into super mouse)
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

    //cross wall
    public void crossWall(){
        crossWall = true; //set the value to true when cross a wall
    }

    //update the location of mouse in 2D array and display windows
    public void updateLocation(){
        //Set the value m in 2D array if the old coordinate is mouse else set to s if super mouse
        if(Maze.map[currMousex][currMousey] == 'm' && Maze.map[newMousex][newMousey] != 's' ){ //
            Maze.map[newMousex][newMousey] = 'm';
        }
        else if(Maze.map[currMousex][currMousey] == 's'){
            Maze.map[newMousex][newMousey] = 's';
        }

        transMouse(); //Transform back into mouse if the condition is match
        moveCount--; //decrease the movecount (for transform usage)
        totalMove++; //increase the total move count
        Maze.map[currMousex][currMousey] = 0; //set the old location value to 0 in the map
        Maze.mazeLabel[currMousex][currMousey].setIcon(null); //remove old mouse spot
        Maze.mazeLabel[newMousex][newMousey].setIcon(currentImg); //spawn mouse at new spot
        currMousex = newMousex; //Assign the new x coordinate to the current x coordinate
        currMousey = newMousey; //Assign the new y coordinate to the current y coordinate

    }

    //set exitCondition = 1 if mouse entered hole
    public static void enteredHole(int mousex, int mousey){
        try{
            if(Maze.mazeLabel[mousex][mousey].getIcon() == Maze.getExitImage()){
                System.out.println("Entered hole");
                exitCondition = 1;
            }
        }catch(ArrayIndexOutOfBoundsException arr){}

    }

    //set exitCondition = -1 if mouse gets eaten by cat
    public static void eatenByCat(int mousex, int mousey){
        try{
            if(Maze.mazeLabel[mousex][mousey].getIcon() == Cat.getImage()){
                System.out.println("Eaten by cat");
                exitCondition = -1;
            }
        }catch(ArrayIndexOutOfBoundsException arr){}

    }


    //get method for totalCheese
    public static int getTotalCheese(){
        return totalCheese;
    }

    //get method for totalMove
    public static int getTotalMove(){
        return totalMove;
    }

    //get method for exitCondition
    public static int getExitCondition(){
        return exitCondition;
    }

    //set method for exitCondition
    public static void setExitCondition(int num){
        exitCondition = num;
    }

    public void clear(){ //clear cheese eat, move count total cheese, total move and transform back to mouse if it is in supermouse form
                         //for restarting game purpose
        cheeseEat = 0;
        moveCount = 0;
        transMouse();
        totalCheese = 0;
        totalMove = 0;
    }

}