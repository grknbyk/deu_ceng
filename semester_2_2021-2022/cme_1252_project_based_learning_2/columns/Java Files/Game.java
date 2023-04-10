import enigma.console.TextAttributes;
import enigma.core.Enigma;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Random;

public class Game {

    enigma.console.Console cn = Enigma.getConsole("Columns", 50, 39, 15);

    KeyListener klis = new KeyListener() {
        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            if (keypr == 0) {
                keypr = 1;
                rkey = e.getKeyCode();
            }
        }

        public void keyReleased(KeyEvent e) {
        }
    };

    int keypr; // key pressed?
    int rkey; //

    boolean isEnterPressed = false;
    boolean isEscapePressed = false;

    // Text Attributes
    TextAttributes DEFAULT = new TextAttributes(hexToColor("#ECE6CE"), Color.BLACK);
    TextAttributes MENUCURSOR = new TextAttributes(Color.GREEN, rgbToColor(0, 0, 80));
    TextAttributes GAMECURSOR = new TextAttributes(Color.BLACK, Color.RED);
    TextAttributes SELECTED = new TextAttributes(Color.RED, Color.BLACK);
    TextAttributes HIGHLIGHTED = new TextAttributes(Color.BLACK, Color.GREEN);
    TextAttributes SCORETABLE = new TextAttributes(Color.pink, Color.black);

    // variables
    String[] MENU = { "Play", "High Score Table", "Options", "How to Play", "Prepared by", "Exit (ESC)" };
    int transfer_number = 0;
    double score = 0; // for try
    int completed_sets = 0;
    int column_amount = 5; // how many columns game has
    int box_number = -1; // -1 if it is invisible else revealed number
    boolean isBoxSelected = false;
    boolean isGameOver = false;
    int x = 0;
    int y = 0;
    int selected_x = -1;
    int selected_y = -1;
    int[] selected_cards = new int[200];
    int line = 0;

    SinglyLinkedList box = new SinglyLinkedList();
    MultiLinkedList columns = new MultiLinkedList();

    // constructor
    public Game() throws Exception {
        cn.getTextWindow().addKeyListener(klis);
        start();
    }

    // game initliaze
    public void start() throws FileNotFoundException, InterruptedException {
        Boolean exit = false;

        do {
            printMenu();
            menuKeys();

            if (isEnterPressed) { // enter
                clear(cn);

                if (line == 0) { // play
                    play();
                } else if (line == 1) { // highscoretable
                    printHighScoreTable(score);
                } else if (line == 2) { // MENU
                    optionsMenu();
                } else if (line == 3) { // how to play
                    printHowToPlay();
                } else if (line == 4) { // prepared by
                    printPreparedBy();
                } else if (line == 5) { // exit
                    exit = true;
                }

                // esc to exit to menu
                boolean flag = false;
                while (!flag) {
                    if (keypr == 1) {
                        if (rkey == KeyEvent.VK_ESCAPE) {
                            flag = true;
                            break;
                        }
                        keypr = 0;
                    }
                }

                isEnterPressed = false;
            }

            Thread.sleep(100);
            clear(cn);
        } while (!exit);
    }

    public void menuKeys() {
        if (keypr == 1) { // if keyboard button pressed
            keypr = 0;
            if (rkey == KeyEvent.VK_UP) {
                if (line > 0) {
                    line--;
                }
            } else if (rkey == KeyEvent.VK_DOWN) {
                if (line < MENU.length - 1) {
                    line++;
                }
            } else if (rkey == KeyEvent.VK_ENTER) {
                isEnterPressed = true;
            }
            keypr = 0; // last action
        }
    }

    public void printMenu() {

        TextAttributes random = randomTextAttributesForColor(Color.BLACK);
        print(cn, "+------------------------+", 12, 1, random);
        print(cn, "|▆▅▃▂▁𝐂𝐎𝐋𝐔𝐌𝐍𝐒▁▂▃▅▆|", 12, 2, random);
        print(cn, "+------------------------+", 12, 3, random);

        for (int i = 0; i < MENU.length; i++) {
            if (line == i) {
                print(cn, MENU[i], 17, 6 + i, MENUCURSOR);
            } else {
                print(cn, MENU[i], 17, 6 + i, DEFAULT);
            }
        }
    }

    public void printHowToPlay() {
        print(cn, "+-----------------------------+", 9, 1, new TextAttributes(Color.black, Color.pink));
        print(cn, "|", 9, 2, new TextAttributes(Color.black, Color.pink));
        print(cn, "         HOW TO PLAY          ", 10, 2, new TextAttributes(Color.white, Color.red));
        print(cn, "|", 39, 2, new TextAttributes(Color.black, Color.pink));
        print(cn, "+-----------------------------+", 9, 3, new TextAttributes(Color.black, Color.pink));
        print(cn, "GENERAL INFORMATION", 15, 5, new TextAttributes(Color.red, Color.black));
        print(cn, "The game is played in 5 columns. Game elements", 1, 6, DEFAULT);
        print(cn, "are numbers (1-10). The aim of the game is", 1, 7, DEFAULT);
        print(cn, "reaching the highest score by collecting number", 1, 8, DEFAULT);
        print(cn, "sets.", 1, 9, DEFAULT);
        print(cn, "GAME ELEMENTS ", 17, 11, new TextAttributes(Color.red, Color.black));
        print(cn, "Number Set = 1, 2, 3, 4, 5, 6, 7, 8, 9, 10.", 1, 12, DEFAULT);
        print(cn, "GAME KEYS", 19, 14, new TextAttributes(Color.red, Color.black));
        print(cn, "Up, Down, Left and Right Arrows: Arrow keys", 1, 15, DEFAULT);
        print(cn, "Arrow keys helps player to travel from column", 1, 16, DEFAULT);
        print(cn, "to column.", 1, 17, DEFAULT);
        print(cn, "Z-key: Pressing Z key, makes player select a", 1, 19, DEFAULT);
        print(cn, "number from column by moving the cursor to that", 1, 20, DEFAULT);
        print(cn, "number.", 1, 21, DEFAULT);
        print(cn, "X-key: Pressing X key, makes player select a", 1, 23, DEFAULT);
        print(cn, "column by moving the cursor to that column and", 1, 24, DEFAULT);
        print(cn, "by moving the cursor to ", 1, 25, DEFAULT);
        print(cn, "the drawn number is transferred to the end", 1, 26, DEFAULT);
        print(cn, "of the column.", 1, 27, DEFAULT);
        print(cn, "B-key: Pressing B key, makes player select and", 1, 29, DEFAULT);
        print(cn, "draw a numberfrom the box. ", 1, 30, DEFAULT);

        print(cn, "Press Escape(ESC) to exit.", 12, 33, DEFAULT);

    }

    public void printPreparedBy() {
        print(cn, " +-------------------+", 14, 1, new TextAttributes(Color.white, Color.gray));
        print(cn, " |    PROJECT III    |", 14, 2, new TextAttributes(Color.white, Color.gray));
        print(cn, " |      COLUMNS      |", 14, 3, new TextAttributes(Color.white, Color.gray));
        print(cn, " |    PREPARED BY    |", 14, 4, new TextAttributes(Color.white, Color.gray));
        print(cn, " +-------------------+", 14, 5, new TextAttributes(Color.white, Color.gray));
        print(cn, " --- LAB6 GROUP4 --- ", 15, 7, DEFAULT);
        print(cn, " -GURKAN BIYIK       ", 15, 8, DEFAULT);
        print(cn, " -DURU CAPAR         ", 15, 9, DEFAULT);
        print(cn, " -DENIZ KATAYIFCI    ", 15, 10, DEFAULT);
        print(cn, " -SAHIN OZTURK       ", 15, 11, DEFAULT);

        print(cn, "    Izmir/Turkey     ", 15, 13, DEFAULT);
        print(cn, "    May 2022         ", 15, 14, DEFAULT);

        print(cn, "Press Escape(ESC) to exit.", 12, 16, DEFAULT);

    }

    public void optionsMenu() throws InterruptedException {

        // esc to exit to menu
        boolean flag = false;
        while (!flag) {

            print(cn, " +------------------------------+", 8, 1,
                    new TextAttributes(Color.white, rgbToColor(0, 0, 120)));
            print(cn, " |            OPTIONS           |", 8, 2,
                    new TextAttributes(Color.white, rgbToColor(0, 0, 120)));
            print(cn, " +------------------------------+", 8, 3,
                    new TextAttributes(Color.white, rgbToColor(0, 0, 120)));

            print(cn, "                           ", 10, 5, MENUCURSOR);
            print(cn, "Column Amount (Default: 5): " + column_amount, 10, 5, MENUCURSOR);

            print(cn, "Use left and right arrow keys", 10, 7, DEFAULT);
            print(cn, "to change option. (<- ->)", 10, 8, DEFAULT);
            print(cn, "Press Escape(ESC) to exit.", 10, 10, DEFAULT);

            Thread.sleep(60);

            if (keypr == 1) {
                if (rkey == KeyEvent.VK_ESCAPE) {
                    flag = true;
                    break;
                } else if (rkey == KeyEvent.VK_LEFT) {
                    if (column_amount > 3) {
                        column_amount--;
                    }
                } else if (rkey == KeyEvent.VK_RIGHT) {
                    if (column_amount < 7) {
                        column_amount++;
                    }
                }
                keypr = 0;
            }
        }
    }

    // game loop
    public void play() throws InterruptedException, FileNotFoundException {

        resetGameVariables();

        createLines(columns, column_amount);
        createBox(box);
        dealTheCards(box, 6);

        while (completed_sets != column_amount) {

            clearValues(3);
            printGameScreen(3);
            gameKeys();
            checkColumns();

            if (isGameOver) {
                break;
            }
            Thread.sleep(30);

        }

        clearValues(3);
        printGameScreen(3);

        if (completed_sets == column_amount) {
            score = (score / transfer_number) + (completed_sets * 100);
            score = (double) (Math.round(score * 100.0) / 100.0);
            print(cn, "End-Game-Score: " + score, 8, 15, DEFAULT);
        } else {
            score = 0;
        }

        print(cn, "Press Escape(ESC) to exit.", 8, 16, DEFAULT);

    }

    public void resetGameVariables() {
        transfer_number = 0;
        score = 0;
        completed_sets = 0;
        box_number = -1; // -1 if it is invisible else revealed number
        isBoxSelected = false;
        isGameOver = false;
        x = 0;
        y = 0;
        selected_x = -1;
        selected_y = -1;
        selected_cards = new int[200];

        box = new SinglyLinkedList();
        columns = new MultiLinkedList();
    }

    // string printer with TextAttributes
    public void print(enigma.console.Console console, String str, int x, int y, TextAttributes attr) {
        for (int i = 0; i < str.length(); i++) {
            console.getTextWindow().output(x + i, y, str.charAt(i), attr);
        }
    }

    // console clear
    public void clear(enigma.console.Console console) {
        int col = console.getTextWindow().getColumns();
        int row = console.getTextWindow().getRows();
        int size = (col * row) - 1;
        String str = "";
        for (int i = 0; i < size; i++) {
            str += " ";
        }
        console.getTextWindow().setCursorPosition(0, 0);
        cn.setTextAttributes(DEFAULT);
        System.out.print(str);
        console.getTextWindow().setCursorPosition(0, 0);
    }

    public void gameKeys() throws InterruptedException {

        if (keypr == 1) { // if keyboard button pressed
            if (rkey == KeyEvent.VK_LEFT) {
                if (x == 0 && columns.lineSize(column_amount - 1) == 0) {
                    x = column_amount - 1;
                    y = 0;
                } else if (x == 0 && y >= columns.lineSize(column_amount - 1)) {
                    x = column_amount - 1;
                    y = columns.lineSize(column_amount - 1) - 1;
                } else if (x == 0) {
                    x = column_amount - 1;
                } else if (x > 0 && columns.lineSize(x - 1) == 0) {
                    x--;
                    y = 0;
                } else if (x > 0 && (y >= columns.lineSize(x - 1))) {
                    y = columns.lineSize(x - 1) - 1;
                    x--;
                } else if (x > 0) {
                    x--;
                }
            } else if (rkey == KeyEvent.VK_RIGHT) {
                if (x == column_amount - 1 && 0 == columns.lineSize(0)) {
                    x = 0;
                    y = 0;
                } else if (x == column_amount - 1 && y >= columns.lineSize(0)) {
                    x = 0;
                    y = columns.lineSize(0) - 1;
                } else if (x == column_amount - 1) {
                    x = 0;
                } else if (x < column_amount - 1 && columns.lineSize(x + 1) == 0) {
                    x++;
                    y = 0;
                } else if (x < column_amount - 1 && (y >= columns.lineSize(x + 1))) {
                    y = columns.lineSize(x + 1) - 1;
                    x++;
                } else if (x < column_amount - 1) {
                    x++;
                }
            } else if (rkey == KeyEvent.VK_UP) {
                if (y == 0 && columns.lineSize(x) != 0) {
                    y = columns.lineSize(x) - 1;
                } else if (y > 0) {
                    y--;
                }
            } else if (rkey == KeyEvent.VK_DOWN) {
                if (y < columns.lineSize(x) - 1) {
                    y++;
                } else if (y == columns.lineSize(x) - 1) {
                    y = 0;
                }
                ;
            } else if (rkey == KeyEvent.VK_Z) {
                // selected_cards[0] == 0 --> no selected card via key z
                if (!isBoxSelected && selected_cards[0] == 0) {
                    int index = 0;
                    selected_x = x;
                    selected_y = y;
                    int len = columns.lineSize(x);
                    for (int i = y; i < len; i++) {
                        int card = (int) columns.getCardFromLine(columns.getLineName(x), i);
                        selected_cards[index] = card;
                        index++;
                    }
                } else if(!isBoxSelected) { // cancel the selection
                    selected_cards = new int[200];
                    selected_x = -1;
                    selected_y = -1;
                }
            } else if (rkey == KeyEvent.VK_B) {
                if (box_number == -1) { // box number not revealead
                    box_number = (int) box.get(box.size() - 1);
                } else { // box number revealead
                    // selected_cards[0] == 0 --> no selected card via key z
                    if (!isBoxSelected && selected_cards[0] == 0) {
                        isBoxSelected = true;
                        box.pop();
                        selected_x = -1;
                        selected_y = -1;
                        selected_cards[0] = box_number;
                    } else if(isBoxSelected){ // cancel the selection
                        isBoxSelected = false;
                        box.add(selected_cards[0]);
                        selected_cards[0] = 0;
                    }
                }

            } else if (rkey == KeyEvent.VK_X) { // adds selected cards to columns
                if (canTransferToColumn(columns, x, selected_cards[0])) {
                    // adds selected cards to column if conditions are true
                    for (int i = 0; i < selected_cards.length; i++) {
                        int card = selected_cards[i];
                        if (card == 0) {
                            break;
                        } else {
                            String line_name = columns.getLineName(x);
                            columns.addCard(line_name, card);

                        }
                    }
                    transfer_number++;
                    if (!isBoxSelected) { // if card not came from box deletes the cards where taken from
                        for (int j = 0; j < selected_cards.length; j++) {
                            if (selected_cards[j] == 0) {
                                break;
                            }
                            String line_name = columns.getLineName(selected_x);
                            columns.linePop(line_name);
                        }
                    }
                    // if added card is came from box resets the box_number
                    if (isBoxSelected) {
                        box_number = -1;
                        isBoxSelected = false;
                    }
                    // resets selected cards
                    selected_cards = new int[200];
                    selected_x = -1;
                    selected_y = -1;
                }

            } else if (rkey == KeyEvent.VK_E) { // exit the game
                isGameOver = true;
            }

            keypr = 0; // last action
        }

    }

    public void printGameScreen(int column_width) {
        print(cn, "Transfer:" + String.format("%6s", transfer_number), column_amount * column_width + 5, 0, DEFAULT);
        print(cn, "Score   :" + String.format("%6s", score), column_amount * column_width + 5, 1, DEFAULT);

        // columns
        for (int i = 0; i < column_amount; i++) {
            print(cn, "C" + (i + 1), i * column_width, 0, DEFAULT);
            print(cn, "--", i * column_width, 1, DEFAULT);

            int len = columns.lineSize(i);

            if (len == 0 && i == x) {
                print(cn, "  ", i * column_width, 2, GAMECURSOR);
            } else if (len == 0 && i != x) {
                print(cn, "  ", i * column_width, 2, DEFAULT);
            } else {
                for (int j = 0; j < len; j++) {
                    int num = (int) columns.getCardFromLine(columns.getLineName(i), j);
                    String str = String.valueOf(num);
                    str = String.format("%2s", str);
                    if (j == y && i == x) {
                        print(cn, str, i * column_width, j + 2, GAMECURSOR);
                    } else if (!isBoxSelected && j == selected_y && i == selected_x) {
                        print(cn, str, i * column_width, j + 2, SELECTED);
                    } else {
                        print(cn, str, i * column_width, j + 2, DEFAULT);
                    }

                    if (j == selected_y && i == selected_x && len == selected_y) {
                        print(cn, "  ", i * column_width, 2, GAMECURSOR);
                    }
                }
            }

        }

        // highligth columns can be added

        for (int i = 0; i < column_amount; i++) {
            if (i != selected_x && (i != x || (i == x && y != (columns.lineSize(i) - 1)))
                    && canTransferToColumn(columns, i, selected_cards[0])) {
                String st;
                if (columns.lineSize(i) == 0) {
                    st = "  ";
                    if (i == x && y == 0) {
                        print(cn, st, i * column_width, 2, GAMECURSOR);
                    } else {
                        print(cn, st, i * column_width, 2, HIGHLIGHTED);
                    }
                } else {
                    int n = (int) columns.getCardFromLine(columns.getLineName(i),
                            columns.lineSize(i) - 1);
                    st = String.valueOf(n);
                    st = String.format("%2s", st);
                    print(cn, st, i * column_width, columns.lineSize(i) + 1, HIGHLIGHTED);
                }
            }
        }

        // box
        print(cn, "Box", column_amount * column_width + 5, 4, DEFAULT);
        print(cn, "+--+", column_amount * column_width + 5, 5, DEFAULT);
        if (box_number == -1) {
            print(cn, "|  |", column_amount * column_width + 5, 6, DEFAULT);
        } else {
            if (isBoxSelected) {
                print(cn, "|", column_amount * column_width + 5, 6, DEFAULT);
                print(cn, String.format("%3s", box_number), column_amount * column_width + 5, 6, SELECTED);
                print(cn, "|", column_amount * column_width + 5, 6, DEFAULT);
            } else {
                print(cn, "|" + String.format("%3s", box_number + "|"), column_amount * column_width + 5, 6, DEFAULT);
            }
        }
        print(cn, "+--+", column_amount * column_width + 5, 7, DEFAULT);
    }

    public void clearValues(int column_width) {
        print(cn, "      ", column_amount * column_width + 14, 0, DEFAULT); // transfer
        print(cn, "      ", column_amount * column_width + 14, 1, DEFAULT); // score

        // columns
        for (int i = 0; i < column_amount; i++) {
            for (int j = 0; j < 37; j++) {
                print(cn, "  ", i * column_width, j + 2, DEFAULT);
            }

        }

        // box
        print(cn, "  ", column_amount * column_width + 6, 6, DEFAULT);
    }

    public void createBox(SinglyLinkedList box) {

        Random rand = new Random();
        int tempRandom = 0;
        int[] counter = new int[10];
        for (int i = 0; i < 10; i++) {
            counter[i] = 0;
        }
        for (int i = 0; i < column_amount * 10;) {
            tempRandom = rand.nextInt(10);

            if (counter[tempRandom] < column_amount) {
                box.add(tempRandom + 1);
                counter[tempRandom]++;
                i++;
            }
        }

    }

    public void createLines(MultiLinkedList columns, int column_amount) {
        for (int i = 0; i < column_amount; i++) {
            columns.addLine("C" + (i + 1));
        }
    }

    public void dealTheCards(SinglyLinkedList box, int number) {

        if (box.size() == 0) {
            System.out.println("List is empty.");
        } else {
            int tempNumber = 0;
            for (int i = 0; i < column_amount; i++) {
                for (int j = 0; j < number; j++) {
                    tempNumber = (Integer) box.get();
                    columns.addCard(columns.getLineName(i), tempNumber);
                    box.delete(tempNumber);
                }
            }
        }
    }

    public boolean canTransferToColumn(MultiLinkedList columns, int line_index, int card) {
        if (card == 0) {
            return false;
        }

        if (columns.lineSize(line_index) == 0 && (card == 1 || card == 10)) {
            return true;
        } else if (columns.lineSize(line_index) == 0 && (card != 1 && card != 10)) {
            return false;
        } else {
            String line_name = (String) columns.getLineName(line_index);
            int column_number = (int) columns.getCardFromLine(line_name, columns.lineSize(line_index) - 1);
            return column_number - 1 == card || column_number == card || column_number + 1 == card;
        }
    }

    // prints score from DLL high_score_table
    public void printHighScoreTable(double playerScore) throws FileNotFoundException {
        print(cn, " +-----------------------+", 12, 1, new TextAttributes(Color.green, Color.DARK_GRAY));
        print(cn, " |     * HIGH SCORE *    |", 12, 2, new TextAttributes(Color.green, Color.DARK_GRAY));
        print(cn, " |       * TABLE *       |", 12, 3, new TextAttributes(Color.green, Color.DARK_GRAY));
        print(cn, " +-----------------------+", 12, 4, new TextAttributes(Color.green, Color.DARK_GRAY));

        String[] arr = new String[5];
        DLL_Node temp = highscoretableData(playerScore).getHead();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = temp.getData().toString();
            temp = temp.getNext();

        }
        for (int j = 0; j < arr.length; j++) {
            print(cn, "                      ", 14, 6 + j, SCORETABLE);
            print(cn, (j + 1) + "-) " + arr[j], 14, 6 + j, SCORETABLE);
        }

        print(cn, "Press Escape(ESC) to exit.", 12, 13, DEFAULT);
    }

    // checks columns if there is set pattern
    // updates columns and increases completed_sets and score
    public void checkColumns() {

        for (int i = 0; i < column_amount; i++) {
            String lineName = columns.getLineName(i);
            if (columns.lineSize(lineName) == 10) {
                boolean set_exist = columns.isThereAnySet(lineName);
                if (set_exist) {
                    for (int j = 0; j < 10; j++) {
                        columns.linePop(lineName);
                    }

                    score += 1000;
                    completed_sets++;
                }
            }

        }
    }

    // creates a DoublyLinkedList type high score table
    public DoublyLinkedList highscoretableData(double playerScore)
            throws FileNotFoundException {
        DoublyLinkedList highScoreTable = new DoublyLinkedList();

        FileReader scoreTable = new FileReader("highscoretable.txt");
        Scanner file = new Scanner(scoreTable);
        while (file.hasNextLine()) {
            String fileData = file.nextLine();
            String[] arr = fileData.split(" ");
            String fullname = arr[0] + " " + arr[1];

            Double score = Double.parseDouble(arr[2]);
            highScoreTable.addAsSorted(fullname, score);

        }
        if (completed_sets == column_amount) {
            highScoreTable.addAsSorted("You", playerScore);
        } else {
            highScoreTable.addAsSorted("You", 0);
        }
        file.close();

        return highScoreTable;

    }

    public TextAttributes randomTextAttributesBacColor(Color BackgroundColor) {
        Color[] arr = { Color.GREEN, Color.BLUE, Color.YELLOW, Color.RED };
        int random = (int) (Math.random() * arr.length);
        return new TextAttributes(arr[random], BackgroundColor);
    }

    public TextAttributes randomTextAttributesForColor(Color ForegroundColor) {
        Color[] arr = { Color.GREEN, Color.BLUE, Color.YELLOW, Color.RED };
        int random = (int) (Math.random() * arr.length);
        return new TextAttributes(ForegroundColor, arr[random]);
    }

    public Color rgbToColor(int r, int g, int b) {
        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) { // returns color red for errors
            return Color.RED;
        } else {
            Color myColor = new Color(r, g, b);
            return myColor;
        }
    }

    public Color hexToColor(String hex) {
        int r = Integer.valueOf(hex.substring(1, 3), 16);
        int g = Integer.valueOf(hex.substring(3, 5), 16);
        int b = Integer.valueOf(hex.substring(5, 7), 16);
        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) { // returns color red for errors
            return Color.RED;
        } else {
            Color myColor = new Color(r, g, b);
            return myColor;
        }
    }
}