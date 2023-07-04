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

    public static int DP(int[] salaries, int[] demand, int numbersOfYears, int transferLimit, int costPrice) {
        int[] costs = new int[numbersOfYears + 1]; // cost array for each year period
        int[] emptyPlace = new int[numbersOfYears + 1]; // empty place array for each year

        for (int i = 1; i <= numbersOfYears; i++) { // loop till number of years
            if (demand[i] <= transferLimit) { // if demand is less than transfer limit
                emptyPlace[i] = transferLimit - demand[i]; // assign empty place for current year
                costs[i] = costs[i - 1]; // assign previous year cost for current year

            } else { // if demand is greater than transfer limit
                emptyPlace[i] = 0; // assign no empty place for current year
                int overLimit = demand[i] - transferLimit; // calculate player excess
                int optimizedCost = costPrice * overLimit; // calculate coach cost for excess players
                int holdCost = 0; // hold cost for calculating

                for (int j = 1; j < i; j++) { // loop to find previous year empty place
                    int previousYearEmptyPlace = emptyPlace[i - j]; // get previous year empty place

                    if (previousYearEmptyPlace >= overLimit) { // if previous year empty place is enough for excess
                        holdCost += j * salaries[overLimit]; // calculate hold cost

                        if (holdCost < optimizedCost) { // if hold cost is less than optimized cost
                            optimizedCost = holdCost; // assign hold cost to optimized cost
                            emptyPlace[i - j] -= overLimit; // decrease previous year empty place
                        } else { // if hold cost is greater than optimized cost
                            break; // break loop
                        }

                    } else if (previousYearEmptyPlace > 0) { // if previous year empty place is greater than 0
                        holdCost += j * salaries[previousYearEmptyPlace]; // calculate hold cost
                        overLimit -= previousYearEmptyPlace; // decrease excess player

                        // if hold cost plus excess player cost is less than optimized cost
                        if (holdCost + overLimit * costPrice < optimizedCost) {
                            optimizedCost = holdCost + overLimit * costPrice; // it is profitable, update optimized cost
                            emptyPlace[i - j] -= previousYearEmptyPlace; // decrease previous year empty place
                        } else { // if it is not profitable
                            break;
                        }
                    }
                }
                // assign optimized cost plus previous year cost to current year cost
                costs[i] = costs[i - 1] + optimizedCost;
            }
        }
        return costs[numbersOfYears]; // return last year cost
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
        int n = 20, p = 5, c = 10;

        int[] salary = returnArray("players_salary.txt");
        int[] demand = returnArray("yearly_player_demand.txt");

        int Cost = DP(salary, demand, n, p, c);

        System.out.println("DP Results:" + Cost);

    }
}