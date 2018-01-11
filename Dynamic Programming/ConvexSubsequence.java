import java.util.Scanner;

/**
 * author:   Scott Furman, saf5151@rit.edu
 * filename: ConvexSubsequence.java
 * date:     11/01/17
 *
 * input: first line contains the number of values, n, and the second line
 * contains those values
 * 
 * output: the maximum length of a convex subsequence from those n values
 */
public class ConvexSubsequence {

    /**
     * Compares two integer values to find the greatest
     *
     * @param a: the first int
     * @param b: the second int
     * @return the greater of the two numbers
     */
    public static int max( int a, int b )
    {
        if ( a >= b )
            return a;
        else
            return b;
    }

    public static void main(String[] args )
    {
        Scanner scnr = new Scanner( System.in );
        int n = scnr.nextInt(); // number of elements user will input
        int[] numList = new int[n]; // list of user inputted numbers

        // our dynamic programming solutions array, where the element at s[j][k]
        // is the max length of a convex sub-sequence ending with the k-th element
        // of our numList, followed by the j-th element of our numList
        int s[][] = new int[n][n];
        int longestSubseq = 0;

        // get user input
        for( int i = 0; i < n; i++ )
            numList[i] = scnr.nextInt();
        scnr.close();

        // this needs to be initialized to 2 (the smallest sub-sequence it could be)
        // ahead of time, because it is never initialized later
        s[1][0] = 2;

        // iterate through each number in our numberList
        for (int i = 2; i < n; i++ ) {
            // iterate through each number that could come directly before the i-th
            // number of our numList in the sub-sequence
            for (int j = 1; j < i; j++) {
                // iterate through each number that could come directly before the
                // j-th number of our numList in the sub-sequence
                for (int k = 0; k < j; k++) {
                    // if the value was previously uninitialized, we need to
                    // initialize it to the smallest sub-sequence it could be: 2
                    if ( s[i][j] == 0 )
                        s[i][j] = 2;

                    // if the i-th element could be appended to the sub-sequence
                    // that currently ends with the j-th element, which is preceded
                    // by the k-th element, our sub-sequence may grow
                    if (numList[j] <= (numList[k] + numList[i]) / 2)
                        s[i][j] = max(s[i][j], s[j][k] + 1);

                    // keep track of the longest sub-sequence to avoid having to
                    // traverse the array after building it
                    if ( s[i][j] > longestSubseq )
                        longestSubseq = s[i][j];
                }
            }
        }

        // output the longest convex sub-sequence from our numList
        System.out.println( longestSubseq );
    }
}
