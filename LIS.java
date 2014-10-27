/**
  FigNewtons
  October 27, 2014

  Longest Increasing Sequence
  -----------------------------

  Given an array of integers nums[1...n],
  find a longest increasing sequence
  (each term is monotonically increasing).

  Dynamic Programming (tabular, bottom-up approach):

    Solution: O(n^2)

    1. Find the length of the LIS
        Create an array len[1...n] that corresponds
        to the LIS with nums[i] as the first integer
        in the sequence.

        Start backwards in the nums array, noting that
        len[i] = 1 if no numbers to the right of nums[i]
        are greater than or equal to it, and len[i] = 1 + max(list)
        where list contains the lengths of the LIS for the numbers
        greater than or equal to nums[i].


    2. Reconstruct the LIS by working forward through nums
       and len.

*/

import java.util.*;

public class LIS{

    private int[] nums, len;
    private int n;

    public LIS(int[] nums){

        this.nums = nums;
        len = new int[nums.length];
        n = nums.length;

        computeLengths();
        //printLengths();
    }

    private int max(ArrayList<Integer> list){
        int m = Integer.MIN_VALUE;

        for(int i = 0; i < list.size(); i++){
            if(list.get(i) > m)
              m = list.get(i);
        }

        return m;
    }


    private void computeLengths(){
        ArrayList<Integer> list = new ArrayList<Integer>(n);
        len[n - 1] = 1; // Initialize end of list

        for(int i = n - 2; i > 0; i--){
            list.clear();
            for(int j = i + 1; j < n; j++){
                if(nums[i] <= nums[j])
                  list.add(len[j]);
            }

            if (list.size() == 0) len[i] = 1;
            else len[i] = 1 + max(list);
        }
    }


    private void printLengths(){

      for(int i = 0; i < n; i++){
          System.out.print(len[i] + " ");
      }
      System.out.println(" ");
    }



    public int[] getLIS(){

        ArrayList<Integer> seq = new ArrayList<Integer>();
        int max = 0;
        int index = 0;      // Index of starting value in seq

        for(int i = 0; i < n; i++){
            if(len[i] > max){
                max = len[i];
                index = i;
            }
        }

        seq.add(nums[index]);
        max--;

        for(int j = index + 1; j < n; j++){
              if(len[j] == max){
                  seq.add(nums[j]);
                  max--;
              }
        }

        int[] sequence = new int[seq.size()];
        for(int i = 0; i < seq.size(); i++){
            sequence[i] = seq.get(i);
        }

        return sequence;
    }

    public static void main(String[] args){

        int[] nums;
        Scanner input = new Scanner(System.in);

        int cases = input.nextInt();

        for(int i = 0; i < cases; i++){

            int size = input.nextInt();
            nums = new int[size];

            for(int j = 0; j < size; j++){
                nums[j] = input.nextInt();
            }

            LIS lis = new LIS(nums);
            int[] seq = lis.getLIS();

            for(int s: seq)
              System.out.print(s + " ");

            System.out.println(" ");
        }
    }
}
