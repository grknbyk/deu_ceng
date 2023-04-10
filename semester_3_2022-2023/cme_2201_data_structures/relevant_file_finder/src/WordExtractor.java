import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class WordExtractor {
    private static final String FILENAME = "stop_words_en.txt";
    private AList<String> stop_words;
    private boolean initialize;

    public boolean isInitialize() {
        return initialize;
    }

    public WordExtractor(String filename) {
        try {
            this.initialize = true;
            this.stop_words = loadStopWords(filename);
        } catch (Exception e) {
            this.initialize = false;
        }
    }

    public WordExtractor() {
        this(FILENAME);
    }

    // reads stop words
    private AList<String> loadStopWords(String fileName) throws Exception {

        if (!fileName.endsWith(".txt")) {
            System.out.println("Couldn't load the stop words!");
            System.out.println("stop word file not found: " + fileName);
            throw new Exception("UnsupportedFileException: must endswith: '.txt'");

        }

        AList<String> stopWords = new AList<>();

        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            StringBuilder content = new StringBuilder();
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine() + "-");
            }
            scanner.close();

            String[] contentWords = content.toString().split("-+");
            for (String word : contentWords)
                stopWords.add(word.toLowerCase(Locale.ENGLISH));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found: " + fileName);
            throw new Exception(e);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred!");
        }
        return stopWords;
    }

    public AList<String> getWords(String fileName) throws Exception {

        if (!fileName.endsWith(".txt")) {
            System.out.println("Couldn't load the stop words!");
            System.out.println("stop word file not found: " + fileName);
            throw new Exception("UnsupportedFileException: must endswith: '.txt'");
        }

        AList<String> words = new AList<>();

        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            StringBuilder content = new StringBuilder();
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine() + " ");
            }
            scanner.close();

            String[] contentWords = content.toString()
                    .replaceAll("[\"é!'^+%&/()=?_£#$½{}.:;,~¨«‘’“”@»*<>0-9-]", " ")
                    .split(" +");
            for (String word : contentWords)
                words.add(word.toLowerCase(Locale.ENGLISH));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found: " + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred!");
        }

        return words;
    }

    public boolean extractStopWords(AList<String> words) {
        try {
            for (int index = 1; index <= stop_words.getLength(); index++) {
                String stop_word = stop_words.getEntry(index);
                words.removeAll(stop_word);
            }
            return true;
        } catch (Exception e) {
            System.out.println("Couldn't extracted the stop words");
            System.out.println("Stop words not loaded!");
            return false;
        }
    }

}
