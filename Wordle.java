import java.util.*;
import java.io.*;

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

        Scanner reader = new Scanner(System.in);
        
        String answer = pickRandomWord();

        System.out.println(answer+"\n\n\n");

        int i = 0;

        while(i<6){
            
            
            System.out.print("Tahmin " + (i+1) + ": ");
       
            String guess = reader.nextLine();

            if(guess.length() == 5){

                guess = guess.toUpperCase(new Locale("tr"));

                if(wordTable.get(guess) == null){

                    System.out.print("\u001B[A" + ANSI_DELETE_LINE);
                    
                    System.out.print("Böyle bir kelime yok");
                    
                    waitSec(1);

                    System.out.print("\u001B[G" + ANSI_DELETE_LINE );
                    

                    continue;
                }

                

                else if(guess.equals(answer)){
                    System.out.println("\n\n----Kazandın----");
                    break;
                }

                else{
                    wordPrint(answer,guess);
                    i++;
                    System.out.println();
                }

            }


            else{
                
                
                System.out.print("\u001B[A" + ANSI_DELETE_LINE);
                    
                System.out.print("Kelime 5 harf uzunluğunda olmalıdır");
                    
                waitSec(1);

                System.out.print("\u001B[G" + ANSI_DELETE_LINE );
            }

            
        }

        System.out.println("\n\n--Doğru Cevap: " + answer + "---- \n" );

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
                    line = line.toUpperCase(new Locale("tr"));
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

    private void wordPrint(String answer,String guess){
        
        System.out.print("\u001B[A" + "\u001B[10C"+ANSI_WHITE);

        for(int i = 0; i<5;i++){

            if(answer.charAt(i) == guess.charAt(i)){
                System.out.print(ANSI_GREEN_BACKGROUND + guess.charAt(i));
            }

            else if(answer.indexOf(guess.charAt(i)) != -1){
                System.out.print(ANSI_YELLOW_BACKGROUND + guess.charAt(i));
            }

            else{
                System.out.print(ANSI_BLACK_BACKGROUND + guess.charAt(i));
            }

        }

        System.out.println(ANSI_RESET);

    }

    private void waitSec(long sec){
        
        long startTime;
        long endTime;


        startTime = System.nanoTime();
        endTime = startTime;

        while(endTime-startTime < sec*1000000000){
            endTime = System.nanoTime();
        }

    }
}