import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Gurkan_Biyik_2020510019
 */
public class Gurkan_Biyik_2020510019 {

    public static int Greedy(int[] salaries, int[] demand, int numbersOfYears, int transferLimit, int costPrice) {
        int cost = 0; // total cost
        int emptyPlace = 0; // empty place for previous year

        for (int i = 1; i <= numbersOfYears; i++) { // loop till number of years
            if (demand[i] <= transferLimit) { // if demand is less than transfer limit
                emptyPlace = transferLimit - demand[i]; // calculate empty place
            } else { // if demand is greater than transfer limit
                int playerExcess = demand[i] - transferLimit; // calculate excess player
                if (emptyPlace >= playerExcess) // if empty place is enough for excess player
                    cost += salaries[playerExcess]; // add salary to cost
                else // if empty place is not enough , add all empty place and rent coach for remaining
                    cost += (salaries[emptyPlace] + (playerExcess - emptyPlace) * costPrice); // salary and coach price

                emptyPlace = 0; // used all transfers
            }
        }
        return cost; // return total cost
    }

    // return array from file
    public static int[] returnArray(String filename) {
        List<Integer> list = new ArrayList<>();

        // read file
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            br.readLine(); // passing first line text
            String line = br.readLine();
            while (line != null) {
                String secValue = line.split("	")[1];// getting the second column value
                list.add(Integer.parseInt(secValue));
                line = br.readLine(); // read next line
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("IO Exception!");
        }

        // convert list to int array with indexing from 1
        int[] array = new int[list.size() + 1];
        for (int j = 1; j <= list.size(); j++) {
            array[j] = list.get(j - 1);
        }
        return array;
    }

    public static void main(String[] args) {
        int n = 3, p = 5, c = 5;

        int[] salary = returnArray("players_salary.txt");
        int[] demand = returnArray("yearly_player_demand.txt");

        int Cost = Greedy(salary, demand, n, p, c);

        System.out.println("Greedy Results:" + Cost);

    }
}