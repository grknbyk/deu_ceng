import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main{

    ///////////////////////////////////////////////////////////////////////////////
    ///  in lines 24,26,27,80 file path hasn't written like "D:\\example.txt"   ///
    ///////////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
        Stack S1 = new Stack(0); //countries
        Stack S2 = new Stack(0); //alphabet
        Stack S3 = new Stack(0); //player name
        Stack S4 = new Stack(0); //player score
        CircularQueue Q1 = new CircularQueue(0); //selected country
        CircularQueue Q2 = new CircularQueue(0); //selected country as placeholders 

        int step = 0, score = 0, wheel = 0;
        char guess = '!';

        S1 = loadCountries("CountriesUnsorted.txt");
        S2 = loadAlphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        S3 = loadPlayerNames("HighScoreTableUnsorted.txt");
        S4 = loadPlayerScores("HighScoreTableUnsorted.txt");

        int random = randomInt(1, S1.size()); //random number

        System.out.println("Randomly generated number: " + random);
        Q1 = getCountryNameAsQueueFromStack(random, S1); //random country
        Q2 = setPlaceHolderQueue(getString(Q1));
        while (true) {
            step++;

            //prints by aligning (in lines)
            System.out.printf("%-50s", "Word:  " + getString(Q2));
            System.out.printf("%-15s", "Step: " + step);
            System.out.printf("%-15s", "Score: " + score);
            System.out.printf("%-26s %n %n", getString(S2));

            //checks game is over
            if(getString(Q1).equalsIgnoreCase(getString(Q2))){
                break;
            }

            wheel = wheelSpin();

            //prints wheel
            if(wheel == 0){//bankrupt
                System.out.printf("%-20s %n", "Wheel: Bankrupt");
                guess = '-';
                score = 0;
            }else{
                if(wheel == 2){//double money
                    System.out.printf("%-20s %n", "Wheel: Double Money");
                    guess = getLetterFromAlphabet(S2);
                    if(countLetterInQueue(guess, Q1) > 0){
                        updateQueue(guess, Q2, Q1);
                        score *= 2;
                    }
                }else{//others (10,50,100,250,500,1000)
                    guess = getLetterFromAlphabet(S2);
                    System.out.printf("%-20s %n", "Wheel: " + wheel);
                    if(countLetterInQueue(guess, Q1) > 0){
                        updateQueue(guess, Q2, Q1);
                        score += countLetterInQueue(guess, Q1) * wheel;
                    }
                }
            }

            System.out.printf("%-20s %n", "Guess: " + guess);
        }

        System.out.println("You win $" + score);
        System.out.println();

        printAndUpdateScoreTable(score, S3, S4);
        writeToFile("HighScoreTableSorted.txt", S3, S4);

    }


    public static void writeToFile(String file_name, Stack p_names, Stack p_scores) {
        String name;
        int score;
        String str;
        try {
            File myObj = new File(file_name);
            myObj.createNewFile();

            FileWriter myWriter = new FileWriter(file_name);

            while (!p_names.isEmpty()) {
                name = (String) p_names.pop();
                score = (int) p_scores.pop();
                str = String.format("%-7s", name);
                myWriter.write(str);
                str = String.format("%6s %n", String.valueOf(score));
                myWriter.write(str);
            }

            myWriter.close();
          } catch (IOException e) {
            System.out.println("An error occurred while writing. -<" + file_name);
            e.printStackTrace();
          }
    }
    
    public static void printAndUpdateScoreTable(int score, Stack p_names, Stack p_scores) {
        System.out.println("High Score Table");
        
        int len = p_names.size();

        Stack temp_names = new Stack(len);
        Stack temp_scores = new Stack(len);

        boolean isWrtitten = false;
        int a = 0;
        while(a < len) {
            String p_name = (String) p_names.peek();
            int p_score = (int) p_scores.peek();
            if(score >= p_score && !isWrtitten){
                System.out.printf("%-7s", "You");
                System.out.printf("%6s %n", score);
                temp_names.push("You");
                temp_scores.push(score);
                isWrtitten  = true;
                a++;
            }else{
                System.out.printf("%-7s", p_name);
                System.out.printf("%6s %n", p_score);
                p_names.pop();
                p_scores.pop();
                temp_names.push(p_name);
                temp_scores.push(p_score);
                a++;
            }
        }

        if(isWrtitten){ //if is written, there is one piece left in the S3 and S4
            //pops it
            p_names.pop();
            p_scores.pop();
        }
        
        while (!temp_names.isEmpty()) {
            p_scores.push(temp_scores.pop());
            p_names.push(temp_names.pop());
        }
    }

    //replaces guess letters to Q2
    public static void updateQueue(char letter, CircularQueue changed, CircularQueue base) {
        char last_data1 = '#';
        char last_data2 = '#';
        int len = base.size(); 
        for (int i = 0; i < len; i++) {
            last_data1 = (char) base.dequeue();
            last_data2 = (char) changed.dequeue();
            if(last_data1 == letter){
                changed.enqueue(letter);
            }else{
                changed.enqueue(last_data2);
            }
            base.enqueue(last_data1);
        }
    }

    //returns queue with placeholders due to string
    public static CircularQueue setPlaceHolderQueue(String str) {
        CircularQueue queue = new CircularQueue(str.length());
        for (int i = 0; i < str.length(); i++) {

                if(!Character.isAlphabetic(str.charAt(i))){
                    queue.enqueue(str.charAt(i));
                }
                else{
                    queue.enqueue('_');
                }
        }
        return queue;
    }

    public static String getString(CircularQueue queue) {
        String str = "";
        char last_data = '!';
        for (int i = 0; i < queue.size(); i++) {
            last_data = (char) queue.dequeue();
            str += last_data;
            queue.enqueue(last_data);
        }
        return str;
    }

    public static String getString(Stack S2) {
        Stack temp_stack = new Stack(S2.size());
        String str1 = ""; //reverse string
        String str2 = "";
        char last_data = '!';
        while (!S2.isEmpty()) {
            last_data = (char) S2.pop();
            str1 += last_data;
            temp_stack.push(last_data);
        }

        while (!temp_stack.isEmpty()) {
            S2.push(temp_stack.pop());
        }

        //sorting reversly str1
        for (int i = str1.length()-1; i >= 0; i--) {
            str2 += str1.charAt(i);
        }
        return str2;
    }

    public static int countLetterInQueue(char letter, CircularQueue Q1) {
        int counter = 0;
        char last_data = '!';
        for (int i = 0; i < Q1.size(); i++) {
            last_data = (char) Q1.dequeue();
            if( last_data == letter){
                counter ++;
            }
            Q1.enqueue(last_data);
        }
        return counter;
    }

    public static CircularQueue getCountryNameAsQueueFromStack(int random_number, Stack S1) {
        String name = "";

        //pops until encounter the country has no of random number
        for (int i = S1.size(); i > random_number-1; i--) {
            name = (String) S1.pop();
        }

        CircularQueue Q1 = new CircularQueue(name.length());
        for (int i = 0; i < name.length(); i++) {
            Q1.enqueue(name.charAt(i));
        }

        return Q1;
    }

    public static int randomInt(int min, int max) {
        int range = max - min + 1;
        return (int)(Math.random() * range) + min;
    }

    public static int wheelSpin() {
        int random = randomInt(1, 8);
        switch (random) {
            case 1:
                return 10;        
            case 2:
                return 50;        
            case 3:
                return 100;        
            case 4:
                return 250;        
            case 5:
                return 500;        
            case 6:
                return 1000;        
            case 7:
                return 2; //double money        
            case 8:
                return 0; //bankrupt        
            default:
                return -1; //for error
        }
    }
    
    //takes a letter from alphabet and pop from stack
    public static char getLetterFromAlphabet(Stack S2) {
        Stack temp_stack = new Stack(S2.size());
        int random = randomInt(1, S2.size());
        char letter = '!';

        //pops until encounter the letter has no of random number
        for (int i = 0; i < random-1; i++) {
            temp_stack.push((char) S2.pop());
        }

        //returning letter
        letter = (char) S2.pop();

        //fills the queue from poped letters(temp_stack)
        while (!temp_stack.isEmpty()) {
            S2.push(temp_stack.pop());
        }

        return letter;
    }

    //returns the word that comes after ex. wordCompare(zebra,lion) returns lion (used in sorting countries)
    public static String wordCompare(String str1, String str2) {
        str1 = str1.toUpperCase().replace("İ", "I");
        str2 = str2.toUpperCase().replace("İ", "I");
        int a = 0;
        while (a < str1.length() && a < str2.length()) {
            int char1 = str1.charAt(a);
            int char2 = str2.charAt(a);

            if(char1 == char2){
                a++;
                continue;
            }else if(char1 >= char2){
                return str2;
            }else{
                return str1;
            }
        }

        //if they are same ex(DOMINICA & DOMINICAN REPUBLIC)
        if(str1.length() < str2.length()){
            return str1;
        }else{
            return str2;
        }        
    }

    public static Stack loadCountries(String file_name) {
        File board = new File(file_name);
        Scanner boardtext = null;
        try {
            boardtext = new Scanner(board);
        } catch (FileNotFoundException e1) {
            System.out.println("An error occurred while reading. -<" + file_name);
            e1.printStackTrace();
        }
    
        CircularQueue lines = new CircularQueue(200);

        while (boardtext.hasNextLine()) {
            lines.enqueue(boardtext.nextLine());
        }
        
        boardtext.close();

        int len = lines.size();

        Stack countries = new Stack(len);

        String words = "-"; //holds pushed countries

        for (int a = 0; a < len; a++) {//how many time to push
            String last_data = "Zzzzz"; 
            String str = "";
            //determines the country came after in alphabet
            for (int j = 0; j < len; j++) {
                str = (String) lines.dequeue();
                str = str.toUpperCase().replace("İ", "I");

                if(!(words.contains("-"+str+"-"))){//if not pushed
                    last_data = wordCompare(str, last_data);  //update
                }

                lines.enqueue(str);
            }

            countries.push(last_data);
            words += last_data + "-"; 

        }
        
        return countries;
    }

    public static Stack loadAlphabet(String str) {
        Stack alphabet = new Stack(str.length());
        for (int i = 0; i < str.length(); i++) {  
            alphabet.push(str.charAt(i));
        }
        return alphabet;
    }

    public static Stack loadPlayerNames(String file_name) {
        File board = new File(file_name);
        Scanner boardtext = null;
        try {
            boardtext = new Scanner(board);
        } catch (FileNotFoundException e1) {
            System.out.println("An error occurred while reading. -<" + file_name);
            e1.printStackTrace();
        }
    
        CircularQueue lines = new CircularQueue(225);

        while (boardtext.hasNextLine()) {
            lines.enqueue(boardtext.nextLine());
        }
        
        boardtext.close();

        int len = lines.size();

        Stack player_name = new Stack(len);
        String names = "*"; //holds the pushed names
        String name = "";

        //pushs sortly
        for (int j = 0; j < len; j++) {
            int min = 999999;

            //determines a player that has min score and not pushed to stack
            for (int a = 0; a < len; a++) {
                String str = (String) lines.dequeue();

                if(!names.contains("*"+getPlayerName(str)+"*") && getPlayerScore(str)  <= min){
                    min = getPlayerScore(str);
                    name = getPlayerName(str);
                }
                lines.enqueue(str);
            }

            names += name + "*";
            player_name.push(name);
        }

        return player_name;
    }

    public static Stack loadPlayerScores(String file_name) {
        File board = new File(file_name);
        Scanner boardtext = null;
        try {
            boardtext = new Scanner(board);
        } catch (FileNotFoundException e1) {
            System.out.println("An error occurred while reading. -<" + file_name);
            e1.printStackTrace();
        }
    
        CircularQueue lines = new CircularQueue(225);

        while (boardtext.hasNextLine()) {
            lines.enqueue(boardtext.nextLine());
        }
        
        boardtext.close();

        int len = lines.size();

        Stack player_scores = new Stack(len);
        String names = "*"; //holds the pushed names
        String name = "";
        for (int j = 0; j < len; j++) {
            int min = 999999;

            //determines a player that has min score and not pushed to stack
            for (int a = 0; a < len; a++) {
                String str = (String) lines.dequeue();

                if(!names.contains("*"+getPlayerName(str)+"*") && getPlayerScore(str)  <= min){
                    min = getPlayerScore(str);
                    name = getPlayerName(str);
                }
                lines.enqueue(str);
            }

            names += name + "*";
            player_scores.push(min);
        }

        return player_scores;
    }

    //gets alphabetic parts from string ex("Nazan 214" --> "Nazan")
    public static int getPlayerScore(String str) {
        String st = "";
        for (int i = 0; i < str.length(); i++) {
            if(Character.isDigit(str.charAt(i))){
                st += str.charAt(i);
            }            
        }
        return Integer.valueOf(st);
    }

    //gets numeric parts from string ex("Nazan 214" --> "214")
    public static String getPlayerName(String str) {
        String st = "";
        for (int i = 0; i < str.length(); i++) {
            if(Character.isAlphabetic(str.charAt(i))){
                st += str.charAt(i);
            }            
        }
        return st;
    }
 
}