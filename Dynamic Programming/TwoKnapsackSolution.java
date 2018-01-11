import java.util.Scanner;

/**
 * author: Scott Furman, saf5151
 * filename: TwoKnapsackSolution.java
 * date: 11/03/17
 *
 * input: first line containing the number of items (n), the weight capacity
 * of the first bag (W1), and the weight capacity of the second bad (W2). 
 * Following are n lines, each representing an item with a weight and a cost.
 * 
 * output: first line listing the items to be included in the first knapsack
 * for an optimal solution, and the second line listing the items to be 
 * included in the second knapsack for an optimal solution
 */
public class TwoKnapsackSolution {
    /**
     * Given a list of values, finds the maximum value
     *
     * The algorithm runs in O(n) time, however in this particular implementation, the
     * vals list will never have more than 3 items.  Therefore it is O(1).
     *
     * @param vals: the list of values to get the maximum from
     * @return maximum value in the list
     */
    public static int max( int[] vals )
    {
        int max = vals[0];

        for ( int n = 0; n < vals.length; n++ )
            if ( vals[n] > max )
                max = vals[n];

        return max;
    }


    /**
     * This method constructs our 3-dimensional dynamic programming solution array
     * Each "layer" represents the number of items we are considering, and the rows
     * and columns represent the weights of the first bag and second bag respectively.
     * In other words:
     *     s[i][j][k] -> maximum cost from using j weight in the first pack and k
     *                   weight in the second pack, considering i items
     *
     * The optimal solution is found at s[n+1][w1+1][w2+1]
     * We have to add the one to each because a padding of 0 is used in each dimension
     *
     * Constructing the array takes O(n*w1*w2) time.
     *
     * @param costs: a list of values attributed to each item
     * @param weights: a list of weights attribued to each item
     * @param w1: max weight the first bag can hold
     * @param w2: max weight the second bag can hold
     * @return the fully constructed dynamic programming solution array for double knapsacks
     */
    public static int[][][] findOptimalSolution( int[] costs, int weights[], int w1, int w2 ) {
        int n = costs.length-1;
        int s[][][] = new int[n+1][w1 + 1][w2 + 1];

        // loops through our list of items
        for ( int i = 1; i <= n; i++ ) {
            // loops through the rows of the solution array
            for (int j = w1; j >= 0; j--) {
                // loops through the columns of the solution array
                for (int k = w2; k >= 0; k--) {
                    // we use this options array to store the values that we
                    // will pass into the max function
                    // there are three possible conceptual options:
                    //     - We already have the best possible value (don't use item)
                    //     - We put the i-th item in the first bag
                    //     - We put the i-th item in the second bag
                    int options[] = new int[3];
                    int counter = 1;

                    // not using the item is always an options
                    if (i > 0)
                        options[0] = s[i - 1][j][k];
                    else
                        options[0] = s[i][j][k];

                    // checks to make sure there is room in the first bag for the item
                    if (j >= weights[i]) {
                        if ( i > 0 ) {
                            options[counter] = s[i - 1][j - weights[i]][k] + costs[i];
                            counter++;
                        }
                        else
                            options[counter] = s[i][j - weights[i]][k] + costs[i];
                    }

                    // checks to make sure there is room in the second bag for the item
                    if (k >= weights[i]) {
                        if ( i > 0 )
                            options[counter] = s[i - 1][j][k - weights[i]] + costs[i];
                        else
                            options[counter] = s[i][j][k - weights[i]] + costs[i];
                    }

                    s[i][j][k] = max(options);
                }
            }
        }

        return s;
    }


    /**
     * Given the fully built dynamic programming solution array, s, this algorithm reconstructs
     * the solution, placing items into their corresponding bags.
     *
     * This algorithm runs in O(n) time
     *
     * @param s: the fully constructed dynamic programming solution array for this double knapsack
     * @param costs: a list of values attributed to each item
     * @param weights: a list of weights attributed to each item
     * @param w1: max weight the first bag can hold
     * @param w2: max weight the second bag can hold
     * @return a list of strings, bags.  bags[0] represents the items in the first bag and
     *         bags[1] represents the items in the second bag
     */
    public static String[] reconstructSolution( int[][][] s, int[] costs, int[] weights, int w1, int w2 ) {
        String bag1 = "";
        String bag2 = "";
        int i;
        int j = w1;
        int k = w2;
        for ( i = costs.length-1; i > 0; i-- ) {
            // check if below it is lower ( item used )
            if (s[i - 1][j][k] < s[i][j][k]) {
                // find which bag item was put into
                // TT
                if (j - weights[i] >= 0 && k - weights[i] >= 0) {
                    if (s[i - 1][j - weights[i]][k] >= s[i - 1][j][k - weights[i]]) {
                        bag1 = Integer.toString(i) + " " + bag1; // +1 just to match sample output
                        j = j - weights[i];
                    } else {
                        bag2 = Integer.toString(i) + " " + bag2; // +1 just to match sample output
                        k = k - weights[i];
                    }
                }
                // TF
                else if ( j - weights[i] >= 0 ) {
                    bag1 = Integer.toString(i) + " " + bag1; // +1 just to match sample output
                    j = j - weights[i];
                }
                // FT
                else if ( k - weights[i] >= 0 ) {
                    bag2 = Integer.toString(i) + " " + bag2; // +1 just to match sample output
                    k = k-weights[i];
                }
            }
        }

        String[] bags = {bag1, bag2};
        return bags;
    }


    public static void main( String[] args ) {
        Scanner scnr = new Scanner(System.in);
        int n = scnr.nextInt(); // number of items the user will input
        int w1 = scnr.nextInt(); // weight capacity of the first bag
        int w2 = scnr.nextInt(); // weight capacity of the second bag

        int s[][][];
        int[] weights = new int[n+1];
        int[] costs = new int[n+1];

        // get user input for the items
        weights[0] = 0;
        costs[0] = 0;
        for (int i = 1; i <= n; i++) {
            weights[i] = scnr.nextInt();
            costs[i] = scnr.nextInt();
        }
        scnr.close();

        s = findOptimalSolution( costs, weights, w1, w2 );
        String[] bags = reconstructSolution(s, costs, weights, w1, w2);
        System.out.println(bags[0]);
        System.out.println(bags[1]);
    }
}
