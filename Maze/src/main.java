import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class main {
    static int columns = 30;
    static int rows = 30;
    public static int map[][] = new int[rows][columns];

    public static void main(String[] args){
        maze();
    }

    public static void maze(){
        JFrame mainframe = new JFrame("Maze");
        mainframe.setSize(1000,800);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setResizable(false);
        mainframe.setLocationRelativeTo(null);


        JPanel mainPanel = new JPanel(new GridLayout(30,30));
        //mainPanel.setPreferredSize(new Dimension(800 ,600));
        mainframe.add(mainPanel);


        //mainPanel.setVisible(true);


        //mainPanel.setBackground(Color.black);
        String str = "maze.txt";
        loadMap(str);

        JLabel[][] mazeLabel = new JLabel[30][30];
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



        mainframe.setVisible(true);




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


