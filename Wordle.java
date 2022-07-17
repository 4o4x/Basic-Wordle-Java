import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*;
import java.awt.*;

class Wordle{
    
    private HashMap<String,String> wordTable;
    private ArrayList<String> keyList;

    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m"; 
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";

    public static final String ANSI_DELETE_LINE  = "\u001B[2K";



    public Wordle(){
        wordTable = new HashMap();
        keyList = new ArrayList();
    }

    
    public void game(){

        readWords();

        
        
        String answer = pickRandomWord();

        int labelX = 65;
        int labelY = 0;
        
        Color backGround = new Color(18,18,19);
        
        JFrame frame = new JFrame("Wordle");
        frame.setSize(260, 500);
        frame.getContentPane().setBackground(backGround);
        
        Font font1 = new Font("SansSerif", Font.BOLD, 32);




        JTextField t1 = new JTextField(15);

        t1.setFont(font1);
        t1.setBackground(backGround);
        t1.setForeground(Color.white);

        t1.setBounds(65,0 , 130 , 50);

        t1.setHorizontalAlignment(JTextField.LEFT);

        t1.setDocument(new LimitJTextField(5));

        JLabel [][] labels = new JLabel[6][5];

        for (int i = 0; i < labels.length; i++) {

            labelY += 60;
            labelX = 65;

            for (int j = 0; j < labels[i].length; j++) {

                labels[i][j] = new JLabel();
                labels[i][j].setFont(font1);
                labels[i][j].setBounds(labelX,labelY, 26, 50);
                labelX +=26;
            }
            
        }

        
        
        
            
            
        t1.addActionListener(new ActionListener() {
            
            public int i = 0;
            public void actionPerformed(ActionEvent e) {
                
                
                if(t1.getText().length() == 5){

                    if(t1.getText().equals(answer)){
                        t1.setEditable(false);
                    
                        JFrame won= new JFrame("Won");
                        won.setSize(260,100);
                        won.getContentPane().setBackground(backGround);
                        JLabel l = new JLabel("YOU WON! Correct Aswer is " + answer);
                        l.setForeground(Color.green);

                        won.add(l);
                        won.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    
                        won.setVisible(true);
                    }
                    
                    else if(wordTable.get(t1.getText()) != null){
                
                        for (int j = 0; j < labels[i].length; j++) {
                            labels[i][j].setOpaque(true);
                            labels[i][j].setBackground(new Color(58,58,60));
                            
                            if(t1.getText().charAt(j) == answer.charAt(j)){
                                labels[i][j].setForeground(Color.green);
                                labels[i][j].setText("" + t1.getText().charAt(j));
                            }

                            else if(answer.indexOf(t1.getText().charAt(j)) != -1){
                                labels[i][j].setForeground(Color.yellow);
                                labels[i][j].setText("" + t1.getText().charAt(j));
                            }
                
                            else{
                                labels[i][j].setForeground(Color.WHITE);
                                labels[i][j].setText("" + t1.getText().charAt(j));
                            }
                                
                            
                        }
                        
                        i = i+1;

                        t1.setText(null);
                    
                    }

                }

                if(i==6){
                    t1.setEditable(false);
                    
                    JFrame wrong = new JFrame("Fail");
                    wrong.setSize(260,100);
                    wrong.getContentPane().setBackground(backGround);
                    JLabel l = new JLabel("YOU LOST! Correct Aswer is " + answer);
                    l.setForeground(Color.red);

                    wrong.add(l);
                    wrong.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    
                    wrong.setVisible(true);
                     
                }
            }
        });

        
        
        frame.add(t1);

        for (int k = 0; k < labels.length; k++) {

            for (int j = 0; j < labels[k].length; j++) {
                frame.add(labels[k][j]);
                
            }
            
        }
        
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);

    }



    public void readWords(){
        
        File f=new File("words.txt");
        
        FileReader fr=null;
            
        BufferedReader br = null;
        
        try{
            
            String line;
            
            fr = new java.io.FileReader(f);
            br = new BufferedReader(fr);
            
            while(true){
                
                line=br.readLine();

                if(line == null) break;

                if(line.length()==5){
                    line = line.toUpperCase();
                    wordTable.put(line,line);
                    keyList.add(line);
                }

                
            }
            
            br.close();
            
            fr.close();
        }//try
        
        catch (IOException ex) {
        
            System.err.println("Error while reading file: "+ ex.getMessage());
        } //catch

    }


    public String pickRandomWord(){
        
        Random rand = new Random();

        return keyList.get(rand.nextInt(keyList.size()-1));
    }

    
    
}


class LimitJTextField extends PlainDocument {
    private int max;

    LimitJTextField(int max) {
        super();
        this.max = max;
    }

    public void insertString(int offset, String text, AttributeSet attr) throws BadLocationException {

        if (text == null)
            return;

        if ((getLength() + text.length()) <= max) {
            super.insertString(offset, text.toUpperCase(), attr);
        }

    }

}