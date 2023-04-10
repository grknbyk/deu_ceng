import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    // variables
    static SingleLinkedList player1 = new SingleLinkedList();
    static SingleLinkedList player2 = new SingleLinkedList();
    static SingleLinkedList high_score_table = new SingleLinkedList();
    static int turn = 0;
    static int score_p1 = 0;
    static int score_p2 = 0;
    static int winner; // 1 player1, 2 player2, 0 draw
    static int turn_limit = 10; // default 10, can be changed without any error for any other variation
    static int roll_per_turn = 3; // default 3, can be changed without any error for any other variation

    public static void main(String[] args) throws IOException {

        // main game
        for (int i = 0; i < turn_limit; i++) {

            turn++;

            // prints turn
            System.out.println("Turn: " + turn);

            // rolls for each player 3 times (roll_per_turn)
            rollDice(player1, roll_per_turn);
            rollDice(player2, roll_per_turn);

            // prints round
            printRound();

            boolean flag = false; // is something changed

            // checks and updates players' SingleLinkedLists and scores
            while (true) {
                if (checkLargeStraight(player1)) {
                    score_p1 += 30;
                    flag = true;
                } else if (checkYatzee(player1)) {
                    score_p1 += 10;
                    flag = true;
                } else if (checkLargeStraight(player2)) {
                    score_p2 += 30;
                    flag = true;
                } else if (checkYatzee(player2)) {
                    score_p2 += 10;
                    flag = true;
                } else {
                    if (flag) { // prints updated slls
                        printRound();
                    }
                    break;
                }
            }

        }

        System.out.println("Game is over.");

        // prints end game conditions draw or winner
        if (score_p1 > score_p2) {
            System.out.println("The winner is player 1.");
            winner = 1;
        } else if (score_p1 == score_p2) {
            System.out.println("Game is draw.");
            winner = 0;
        } else {
            System.out.println("The winner is player 2.");
            winner = 2;
        }

        System.out.println();

        // reads from file
        high_score_table = loadHighScoreTable("HighScoreTableUnsorted.txt");

        // sorts SLL
        high_score_table = sortedHighScoreTable(high_score_table, winner, score_p1, score_p2);

        System.out.println("High Score Table");

        // prints lines that will be written to file and writes lines to file from SLL
        writeToFileFromSLL("HighScoreTableSorted.txt", high_score_table);

    }

    // prints round
    public static void printRound() {
        // prints player1 dices and score
        System.out.print("Player1: ");
        System.out.printf("%-50s", player1.getString());
        System.out.print("score: " + score_p1);
        System.out.println();

        // prints player2 dices and scores
        System.out.print("Player2: ");
        System.out.printf("%-50s", player2.getString());
        System.out.print("score: " + score_p2);
        System.out.println();

        System.out.println();
    }

    // returns in range [1,6]
    public static int dice() {
        return (int) (Math.random() * 6) + 1;
    }

    // adds rolled dice to SingleLinkedList due to times
    public static void rollDice(SingleLinkedList sll, int times) {
        for (int i = 0; i < times; i++) {
            sll.add(dice());
        }
    }

    // returns true if yatzee happens and removes the 4 digit from SingleLinkedList
    // else returns false
    public static boolean checkYatzee(SingleLinkedList sll) {

        for (int i = 1; i < 7; i++) {
            if (sll.count(i) >= 4) {
                sll.delete(i);
                sll.delete(i);
                sll.delete(i);
                sll.delete(i);
                return true;
            }
        }
        return false;
    }

    // returns true if large straight happens and removes the digits from
    // SingleLinkedList else returns false
    public static boolean checkLargeStraight(SingleLinkedList sll) {
        if (sll.search(1) && sll.search(2) && sll.search(3) && sll.search(4) && sll.search(5) && sll.search(6)) {
            sll.delete(1);
            sll.delete(2);
            sll.delete(3);
            sll.delete(4);
            sll.delete(5);
            sll.delete(6);
            return true;
        } else {
            return false;
        }
    }

    public static SingleLinkedList sortedHighScoreTable(SingleLinkedList sll, int winner, int score_p1, int score_p2) {
        SingleLinkedList temp = new SingleLinkedList();
        SingleLinkedList last_sll = sll;

        boolean isWinnerAdded = false;
        int winner_score;
        if (winner == 0)
            winner_score = score_p1; // draw
        else if (winner == 1)
            winner_score = score_p1; // p1
        else
            winner_score = score_p2; // p2

        for (int i = 0; i < 10; i++) {
            if (!isWinnerAdded && winner_score >= last_sll.findMaxPlayer().getScore()) {
                if (winner == 0) {
                    temp.add(new Player("player1", score_p1));
                    temp.add(new Player("player2", score_p2));
                    isWinnerAdded = true;
                    i++; // for adding one extra
                } else if (winner == 2) {
                    temp.add(new Player("player2", score_p2));
                    isWinnerAdded = true;
                } else {
                    temp.add(new Player("player1", score_p1));
                    isWinnerAdded = true;
                }
            } else {
                Player p = last_sll.findMaxPlayer();
                last_sll.delete(p);
                temp.add(p);
            }

        }

        return temp;
    }

    public static SingleLinkedList loadHighScoreTable(String file_name) {
        SingleLinkedList sll = new SingleLinkedList();
        File board = new File(file_name);
        Scanner boardtext = null;
        try {
            boardtext = new Scanner(board);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        while (boardtext.hasNextLine()) {

            String name = boardtext.nextLine();
            String score = boardtext.nextLine();
            sll.add(new Player(name, Integer.valueOf(score)));
        }
        boardtext.close();
        return sll;
    }

    public static void writeToFileFromSLL(String file_name, SingleLinkedList sll) throws IOException {
        FileWriter writer = new FileWriter(file_name);
        for (int i = 0; i < 10; i++) {
            Player player = (Player) sll.get(i);
            String str = String.format("%s%n", player.getName() + " " + player.getScore());
            System.out.print(str);
            writer.write(str);
        }
        writer.close();
    }
}