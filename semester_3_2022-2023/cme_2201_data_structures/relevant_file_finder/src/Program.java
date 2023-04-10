import java.util.Scanner;

public class Program {
    private WordData data;
    private Scanner sc;
    private String query;

    // optinal path constructor
    public Program(String path) {
        this.data = new WordData(path);
        if (this.data.isInitialize()) {
            this.sc = new Scanner(System.in);
            this.query = new String();
            mainProgram();
        } else {
            System.out.println("Program failed to start!");
        }
    }

    // default path constructor
    public Program() {
        this("sport");
    }

    private void mainProgram() {
        System.out.println("Enter 'q' as query to exit");
        while (true) {

            // taking input query
            System.out.print("Enter a query:");
            query = sc.nextLine();

            // query 'q' to exit
            if (query.equals("q")) {
                System.out.println("Closing the program...");
                break;
            }

            // gets current time
            long start = System.nanoTime();
            // executes the search method
            String fileName = data.search(query);
            // gets current time subtracting start and converts it to milli seconds
            long duration = (System.nanoTime() - start) / 1_000_000;

            // prints output
            System.out.println("Relevant file name: " + fileName +
                    "\nExecution time: " + duration + " ms");
        }
        sc.close();
    }
}
