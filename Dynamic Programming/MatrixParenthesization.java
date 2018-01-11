import java.util.Scanner;

/**
 * author: Scott Furman, saf5151
 * filename: MatrixParenthesization.java
 * date: 11/03/17
 *
 * input: first line containing the number of matrices, n, and the second line
 * containing the dimensions for the n matrices. ex:
 *   4
 *   2 3 1 4 2
 * 
 * output: first line is the optimal multiplication cost, and the second line 
 * illustrating the parenthesization of the matrices. ex:
 *   18
 *   ( ( A1 x A2 ) x ( A3 x A4 ) )
 */
public class MatrixParenthesization {

    /**  For our purposes, we can consider this to be infinite  */
    public static final int INFINITY = 2147483647;

    /**
     * Reconstructs the optimal parenthesization for a set of matrices recursively.  This algorithm runs
     * in O(n) time.  Each element in our original list of matrices is recursed into exactly once.
     * We can tell this because our only two options are to return the number that represents the matrix
     * or to recurse further.
     *
     * @param sk: the array that stores the k-values used at each index of the DP solution array
     * @param L: the left-most matrix we are considering
     * @param R: the right-most matrix we are considering
     * @return a string representation of the optimal parenthesization of a set of matrices
     */
    public static String parenthesize( int[][] sk, int L, int R )
    {
        if ( L == R )
            return "A" + Integer.toString( L );
        else
            return "( " + parenthesize( sk, L, sk[L][R] ) + " x " + parenthesize( sk, sk[L][R]+1, R ) + " )";
    }


    public static void main( String[] args )
    {
        Scanner scnr = new Scanner( System.in );
        int n = scnr.nextInt(); // number of matrices the user will input
        int[] numList = new int[n+1]; // list of user inputted matrices

        int s[][] = new int[n+1][n+1];
        int sk[][] = new int[n+1][n+1];

        // get user input
        for( int i = 0; i <= n; i++ )
            numList[i] = scnr.nextInt();
        scnr.close();

        // Initialize the diagonal of our solution array to all zeros
        // The axes of the solution array indicate the leftmost and
        // rightmost matrices we are considering.  Therefore if L==R
        // then we are only considering 1 matrix and the number of
        // operations preformed is 0.
        for ( int L = 1; L < n; L++)
            s[L][L] =0;

        // d essentially represents the spread of the number of matrices we are considering
        // so we iterate until the spread encompasses all matrices in the input list
        for ( int d = 1; d < n; d++)
        {
            // iterate through all possible left-values for the given spread, d
            for ( int L = 1; L <= n-d; L++ )
            {
                int R = L + d;
                s[L][R] = INFINITY;

                for ( int k = L; k < R; k++ )
                {
                    int tmp = s[L][k] + s[k+1][R] + (numList[L-1]*numList[k]*numList[R]);
                    if ( s[L][R] > tmp ) {
                        s[L][R] = tmp;
                        sk[L][R] = k; // keep track of where we made the split
                    }
                }
            }
        }

        System.out.println(s[1][n]);
        System.out.println( parenthesize(sk, 1, n ) );
    }
}
