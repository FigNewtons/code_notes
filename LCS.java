/**
  FigNewtons
  October 27, 2014
  MIT - introduction to algorithms
  Video url: https://www.youtube.com/watch?v=V5hZoJ6uK-s


  Longest Common Substring
  -------------------------

  Given two strings, x[1..m] and y[1..n]
  with lengths m and n, repsectively, find
  a longest common substring (not necessarily
  unique or contiguous)


  Brute Force: Construct every subsequence of string
  y and check if it is in string x.

    Number of sequences: 2^m  (Power set of x / bit vector)
    Check: O(n)

    O(n * 2^m)

  Recursive Solution: (ignoring base cases)

    def LCS(x, y, i, j):
        if x[i] == y[j]:
            C[i,j] = LCS(x, y, i-1, j-1) + 1
        else:
            C[i,j] = max(LCS(x, y, i, j-1), LCS(x, y, i - 1, j))

        return C[i, j]

  If you draw the recursion tree for this algorithm, you'll see
  that this method solves the same subproblems multiple times, which
  is clearly inefficient. In fact, the height of the tree is m + n,
  which leads to exponential work!


  Dynamic Programming (tabular, bottom-up approach):
    An optimal solution to a problem instance
    contain optimal solutions to its subproblems.

    1. Find the length of the LCS

          Let C[i, j] be the length of the LCS
          of prefixes x and y, where i < m and
          j < n.

          C[i, j] = | LCS(x[1...i], y[1...j]) |

          Theorem: C[i,j] = C[i-1, j-1] + 1           , if x[i] = y[j]
                            max(C[i, j-1], C[i-1. j]) , otherwise



          Using this theorem, precompute the lengths of
          LCS of prefixes of x and y: O(mn) --
          then, fetch the value in C[m,n]


    2. Work backwards from C[m,n] to construct a LCS




*/
import java.util.*;

public class LCS{

    char[] x, y;
    int m, n;
    int[][] c; // Sublength table

    public LCS(String s1, String s2){

        x = s1.toCharArray();
        y = s2.toCharArray();

        m = x.length;
        n = y.length;

        c = new int[m+1][n+1]; // Pad first row and column with zeros

        makeTable();
        //printTable();
    }

    private int max(int a, int b){
        int m = a > b ? a : b;
        return m;
    }

    private int min(int a, int b){
        int m = a > b ? b : a;
        return m;
    }

    private void makeTable(){

        for(int i = 1; i < m + 1; i++){
            for(int j = 1; j < n + 1; j++){

                if( x[i - 1] == y[j - 1])
                    c[i][j] = c[i-1][j-1] + 1;
                else
                    c[i][j] = max(c[i][j-1], c[i-1][j]);
            }
        }
    }

    private void printTable(){
        for (int i = 0; i < m + 1; i++){
            for(int j = 0; j < n + 1; j++){
                System.out.print(c[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    public String getLCS(){
        int i = m - 1;
        int j = n - 1;

        int len = c[i + 1][j + 1];
        int count = len - 1;
        char[] lcs = new char[len];

        // Traverse table backwards to reconstruct string
        while(count >= 0){
            if( x[i] == y[j]){
                lcs[count--] = x[i];
                i--;
                j--;
            }else{
                int t = max(c[i + 1][j], c[i][j + 1]);
                if(c[i + 1][j] == t) j--;
                else i--;
            }
        }

        return new String(lcs);
    }

    public static void main(String[] args){

        Scanner input = new Scanner(System.in);
        String s1, s2;
        LCS l;

        do{
            s1 = input.next();
            s2 = input.next();

            l = new LCS(s1, s2);
            System.out.println("LCS: " + l.getLCS());

        }while(s1.compareTo("exit") != 0);

    }
}
