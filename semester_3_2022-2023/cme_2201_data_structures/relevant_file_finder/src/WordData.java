import java.nio.file.Path;
import java.util.Iterator;
import java.util.Locale;

public class WordData {
    private HashedDictionary<String, Dictionary<String, Integer>> wordsHashTable;
    private FileFinder fileFinder;
    private WordExtractor wordExtractor;
    private boolean initialize;

    public boolean isInitialize() {
        return initialize;
    }

    public WordData(String directory) {
        this.initialize = true;
        try {
            this.fileFinder = new FileFinder(directory);
            this.wordExtractor = new WordExtractor();
            this.wordsHashTable = new HashedDictionary<>();
            loadHashTable();
        } catch (Exception e) {
            this.initialize = false;
            System.out.println(e);
            System.out.println("WordData failed while loading!");
        }
    }

    // gets current directory for file finder
    public WordData() {
        this("");
    }

    private void loadHashTable() throws Exception {
        // gets number of files
        int numFiles = fileFinder.getFiles().getLength();
        // reads files
        Iterator<Path> filePathsIterator = fileFinder.getFileIterator();
        while (filePathsIterator.hasNext()) {
            Path file = filePathsIterator.next();
            String fileName = file.getFileName().toString();
            String fileDirectory = file.toString().replace('\\', '/');

            // gets words and adds to the hashTable
            try {
                // gets words deletes necessary unicodes -?!. etc.
                AList<String> words = wordExtractor.getWords(fileDirectory);

                // removes stop words from the words list
                // if fails throws exception
                if (!wordExtractor.extractStopWords(words))
                    throw new Exception("An error occurred while extracting stop words!");

                // for each word
                for (int i = 1; i < words.getLength(); i++) {
                    // gets word
                    String word = words.getEntry(i);

                    // if hashtable dont contains
                    if (!wordsHashTable.contains(word)) {
                        // creates temp dict
                        // pass fileName as key
                        // 1 for value (first found)
                        Dictionary<String, Integer> tempDict = new Dictionary<>(numFiles);
                        tempDict.add(fileName, 1);
                        // adds the word to the hashTable and pass the tempDict as value
                        wordsHashTable.add(word, tempDict);
                    } else { // if contains
                        // gets word's value which is dictionary
                        Dictionary<String, Integer> tempDict = wordsHashTable.getValue(word);
                        // if dictionary not contains filneName
                        if (!tempDict.contains(fileName)) {
                            // adds the fileName as key
                            // value as 1 for first found
                            tempDict.add(fileName, 1);
                        } else { // if dictionary contains filneName
                            // gets old value
                            int oldValue = tempDict.getValue(fileName);
                            // increase it once and changes its original value
                            tempDict.add(fileName, ++oldValue);
                        }
                    }
                }

            } catch (Exception e) {
                throw new Exception(e);
            }

        }
    }

    public int count(String word) {
        int count = 0;
        Iterator<Integer> counts = wordsHashTable.getValue(word).getValueIterator();
        while (counts.hasNext())
            count += counts.next();

        return count;
    }

    public void testFunction(String key) {
        wordsHashTable.getValue(key);
    }

    public String search(String query) {
        String relevantFile = "None";
        Double tempMean = (double) Double.MIN_EXPONENT;
        Double tempStd = (double) Double.MAX_EXPONENT;

        // splitting words
        String[] words = query.toLowerCase(Locale.ENGLISH).split(" +");

        // gets file names
        Iterator<Path> files = fileFinder.getFileIterator();
        while (files.hasNext()) {
            String fileName = files.next().getFileName().toString();

            // finds how many times the word passes in a file
            double[] counts = new double[words.length];

            // firstly gets word's dictionary
            // secondly gets passes fileName as key
            // and tries to get value
            // if its null assume it as 0.0

            for (int i = 0; i < words.length; i++) {
                try {
                    counts[i] = wordsHashTable.getValue(words[i]).getValue(fileName);
                } catch (NullPointerException e) {
                    counts[i] = 0.0;
                }
            }

            // calculates mean
            double mean = getMean(counts);

            // means not include any word
            if (mean == 0.0)
                continue;

            // COMPARE #1
            // compare for one word
            // if query includes one word
            // compares means
            if (counts.length == 1) {
                if (mean > tempMean) {
                    tempMean = mean;
                    relevantFile = fileName;
                    continue; // to not execute COMPARE #2
                }
            }
            // calculate variance, standart deviation
            double variance = getVariance(counts, mean);
            double standartDeviation = getStandartDeviation(variance);

            // COMPARE #2
            // compare for more than one word
            // compares standartDeviations and gets lesser one
            if (standartDeviation < tempStd) {
                tempMean = mean;
                tempStd = standartDeviation;
                relevantFile = fileName;
            }
            // for same mean
            // gets the lesser standart deviaton one
            else if (standartDeviation == tempStd && mean > tempMean) {
                tempMean = mean;
                relevantFile = fileName;
            }
        }

        return relevantFile;
    }

    // returns average
    private double getMean(double[] arr) {
        double sum = 0;
        for (double x : arr) {
            sum += x;
        }
        return (sum / arr.length);
    }

    // returns Variance
    private double getVariance(double[] arr, double mean) {
        // if one word queried returns counts of number
        if (arr.length == 1)
            return 999999;

        double sum = 0;
        for (double x : arr) {
            sum += ((x - mean) * (x - mean));
        }
        return (double) (sum / (arr.length - 1));
    }

    // returns standart deviation
    private double getStandartDeviation(double variance) {
        return Math.sqrt(variance);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        Iterator<String> words = wordsHashTable.getKeyIterator();
        Iterator<Dictionary<String, Integer>> dicts = wordsHashTable.getValueIterator();
        while (words.hasNext()) {
            String word = words.next();
            str.append(word + ":\n");

            Dictionary<String, Integer> tempDict = dicts.next();

            Iterator<Path> fileIterator = fileFinder.getFileIterator();

            int x = 1;
            while (fileIterator.hasNext()) {
                String fileName = fileIterator.next().getFileName().toString();
                Object value = tempDict.getValue(fileName);
                if (value == null) {
                    str.append(fileName + " -> ");
                    str.append(String.format("%-6d", 0));
                } else {
                    str.append(fileName + " -> ");
                    str.append(String.format("%-6d", ((int) value)));
                }

                if (x % 7 == 0 && fileIterator.hasNext())
                    str.append("\n");
                x++;
            }

            str.append("\n\n");
        }

        return str.toString();

    }

}
