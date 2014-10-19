import java.util.*;

/**
    Algorithms (MIT - CLRS)
    Section 9.1 (p. 214)

    Compares two different approaches
    to simultaneously obtain the min
    and max values in an array of distinct
    integers.
 */

public class Selection{

    int[] array;
    int[] sort;

    public Selection(int n){
        if(n < 1){
            array = new int[10];
            sort = new int[10];
        }
        else{
            array = new int[n];
            sort = new int[n];
        }

        // Populate array with distinct numbers from 1-n
        for(int i = 0; i < n; i++){
            array[i] = i + 1;
            sort[i] = i + 1;
         }

        shuffle(array);
        shuffle(sort);
    }

    // Durstenfield Algorithm
    // Permutes an array with distinct elements in O(n)
    public void shuffle(int[] arr){
        Random rand = new Random();

        for(int i = arr.length - 1; i > 0; i--){
            int j = rand.nextInt(i + 1);
            swap(arr, i, j);
        }
    }

    public void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Returns [min, max] from an array of distinct
    // integers in 3n/2 comparisons (3 comparisons for each adjacent pair)
    public int[] getMinMax(){

        int min, max, i;

        if(array.length % 2 == 1){
            min = array[0];
            max = array[0];
            i = 1;
        }else{
            if(array[0] > array[1]){
              min = array[1];
              max = array[0];
            }else{
              min = array[0];
              max = array[1];
            }
            i = 3;
        }

        for(int j = i + 1; j < array.length; j += 2){

            if(array[j] > array[j - 1]){
                if(array[j] > max) max = array[j];
                if(array[j-1] < min) min = array[j-1];
            }else{
                if(array[j-1] > max) max = array[j-1];
                if(array[j] < min) min = array[j];
            }
        }
        int[] extrema = new int[2];
        extrema[0] = min;
        extrema[1] = max;

        return extrema;
    }

    // Sort array, then retrieve min and max values
    public int[] getMinMaxS(){
        Arrays.sort(sort);
        int min = sort[0];
        int max = sort[sort.length - 1];

        int[] extrema = new int[2];
        extrema[0] = min;
        extrema[1] = max;

        return extrema;
    }

    public void run(boolean useSort){

        long start, end = 0;
        int[] ex;

        start = System.nanoTime();

        if(useSort){
            System.out.println("Sorting algorithm");
            ex = getMinMaxS();
        }else{
            System.out.println("Pairing algorithm");
            ex = getMinMax();
        }

        System.out.println("Min: " + ex[0] + "\nMax: " + ex[1]);
        end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1000000000.0 + "\n");
    }


    public static void main(String[] args){

        Selection s = new Selection(100000000);
        s.run(false);
        s.run(true);
    }
}
