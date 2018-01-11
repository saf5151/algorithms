import java.util.Scanner;

/**
 * author:   Scott Furman, saf5151@rit.edu
 * filename: TwoKnapsack.java
 * date:     11/01/17
 * 
 * Input: first line containing the number of items (n), the weight capacity
 * of the first bag (W1), and the weight capacity of the second bad (W2). 
 * Following are n lines, each representing an item with a weight and a cost.
 * 
 * Output: the maximum cost of a set of items that fits into the two knapsacks
 */
public class TwoKnapsack 
{
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

    public static void main( String[] args )
    {
        Scanner scnr = new Scanner(System.in);
        int n = scnr.nextInt(); // number of items the user will input
        int w1 = scnr.nextInt(); // weight capacity of the first bag
        int w2 = scnr.nextInt(); // weight capacity of the second bag

        // our dynamic programming solution array is two dimensional
        // the rows and columns represent the weights of the first bag
        // and second bag respectively
        // s[j][k] -> maximum cost from using j weight in the first pack
        //            and k weight in the second pack
        int s[][] = new int[w1+1][w2+1]; // plus 1 so we have 0 padding
        int[] weights = new int[n];
        int[] costs = new int[n];

        // get user input
        for (int i = 0; i < n; i++) 
        {
            weights[i] = scnr.nextInt();
            costs[i] = scnr.nextInt();
        }


        // loops through our list of items
        for ( int i = 0; i < n; i++ )
            // loops through the rows of the solution array
            for ( int j = w1; j >= 0; j-- )
                // loops through the columns of the solution array
                for ( int k = w2; k >= 0; k-- )
                {
                    // we use this options array to store the values that we
                    // will pass into the max function
                    // there are three possible conceptual options:
                    //     - We already have the best possible value (don't use item)
                    //     - We put the i-th item in the first bag
                    //     - We put the i-th item in the second bag
                    int options[] = new int[3];
                    int counter = 1;

                    // not using the item is always an options
                    options[0] = s[j][k];

                    // checks to make sure there is room in the first bag for the item
                    if ( j >= weights[i] )
                    {
                        options[counter] = s[ j-weights[i] ][k] + costs[i];
                        counter++;
                    }

                    // checks to make sure there is room in the second bag for the item
                    if ( k >= weights[i] )
                        options[counter] = s[j][ k-weights[i] ] + costs[i];

                    s[j][k] = max( options );
                }

        // output maximum result
        System.out.println( s[w1][w2] );
    }
}
