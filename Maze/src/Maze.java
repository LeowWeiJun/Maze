import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class Maze {
    static int columns = 30;
    static int rows = 30;
    public static int map[][] = new int[rows][columns];


    public Maze(String str){
        loadMap(str);
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
