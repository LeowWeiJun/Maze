import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

public class main {
    static int columns = 30;
    static int rows = 30;
    public static int map[][] = new int[rows][columns];

    public static void main(String[] args){
        maze();
    }

    public static void maze(){
        JFrame mainframe = new JFrame("Maze");
        mainframe.setSize(700,652);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setResizable(false);
        mainframe.setLocationRelativeTo(null);


        JPanel mainPanel = new JPanel(new GridLayout(30,30));
        mainPanel.setPreferredSize(new Dimension(600 ,600));
        //mainframe.add(mainPanel,BorderLayout.NORTH);





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

        JButton testButton = new JButton("Start");
        JButton testButton2 = new JButton("Start1");
        testButton.setPreferredSize(new Dimension(80,20));
        JPanel testPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        testPanel.setPreferredSize(new Dimension(100,20));
        testPanel.add(testButton);

        JSplitPane vsplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        vsplit.setLeftComponent(mainPanel);
        vsplit.setRightComponent(testPanel);
        vsplit.setDividerSize(0);
        vsplit.setEnabled(false);
        mainframe.getContentPane().add(vsplit);

        //testPanel.add(testButton2);
        //mainframe.add(testPanel, BorderLayout.SOUTH);
        //mainPanel.add(testButton, BorderLayout.SOUTH);
        //mainframe.getContentPane().add(testButton2, BorderLayout.SOUTH);
        Random rand = new Random();



        do{
            int  x = rand.nextInt(28) + 1;
            int  y = rand.nextInt(28) + 1;

            if(map[x][y] == 0){
                mazeLabel[x][y].setBackground(Color.BLUE);
                break;
            }
        }while(true);

        int catAmount = 0;

        do{
            int  x = rand.nextInt(28) + 1;
            int  y = rand.nextInt(28) + 1;

            if(map[x][y] == 0){
                mazeLabel[x][y].setBackground(Color.RED);
                catAmount++;
            }

            if(catAmount == 2){
                break;
            }

        }while(true);
       //Color tes = testButton.getBackground();






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


