import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class wordle {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public static void main(String args[]) {
        List<String> correct = new ArrayList<>();
        List<String> allowable = new ArrayList<>();
        readWords(correct, allowable);
        String pickedWord = pickWord(correct).toLowerCase();

        //DEBUG
        System.out.println("picked word: "+pickedWord);

        Scanner sc = new Scanner(System.in);
        boolean gameOver = false;
        String input="";
        System.out.println("Guess a word");
        int tries=0;

        while(!gameOver){
            int score = checkWord(sc.nextLine().toLowerCase(), pickedWord, allowable, correct);

            if(score==5){
                gameOver=true;
                System.out.println("You won in " +(tries+1)+ " tries!");
            }
            else if(tries==5) {
                gameOver = true;
                System.out.println("You lost with a score of "+score+". The word was: "+pickedWord);
            }
            tries++;
        }
    }

    public static int checkWord(String input, String pickedWord, List<String> allowableWords, List<String> pickedList){
        int score=0;
        if(input.equals(pickedWord) || allowableWords.contains(input) || pickedList.contains(input)) {
            for(int i=0; i<input.length(); i++){
                if(pickedWord.indexOf(input.charAt(i)) != -1){
                    if(input.charAt(i) == pickedWord.charAt(i)){
                        System.out.print(ANSI_GREEN + input.charAt(i)+" " + ANSI_RESET);
                        score++;
                    }
                    else{
                        System.out.print(ANSI_YELLOW +input.charAt(i)+" " + ANSI_RESET);
                    }
                }
                else{
                    System.out.print(ANSI_RED + input.charAt(i)+" " + ANSI_RESET);
                }
            }
            System.out.println();
        }
        else{
            System.out.println("invalid word...");
        }
        return score;
    }

    public static String pickWord(List<String> words){
        Random rand = new Random();
        String randomElement = words.get(rand.nextInt(words.size()));
        return randomElement;
    }

    public static void readWords(List<String> words,List<String> words1){
        try{
            BufferedReader reader = new BufferedReader(new FileReader("C:\\pickable.txt"));
            String line;
            while((line=reader.readLine()) != null){
                words.add(line);
            }
            reader.close();

            String line1;
            BufferedReader reader1 = new BufferedReader(new FileReader("C:\\allowable.txt"));
            while((line1=reader1.readLine()) != null){
                words1.add(line1);
            }
            reader1.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
