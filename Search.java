import java.util.*;

/**

  Compares search speed between binary search
  and interpolation search. Both algorithms depend
  upon a sorted array, and interpolation search
  depends on a roughly uniform distribution for
  good performance.

*/
public class Search{

    int[] array, search_items;
    int min, max;

    public Search(int n, int k, int min, int max){

        array = new int[n];
        search_items = new int[k];

        this.max = max;
        this.min = min;

        populate(array, min, max);
        populate(search_items, min, max);

        Arrays.sort(array);
    }

    // Populates an array with integers
    // from l to h inclusive
    public void populate(int[] arr, int l, int h){
        Random rand = new Random();

        for(int i = 0; i < arr.length; i++){
            arr[i] = rand.nextInt(h - l + 1) + l;
        }
    }

    public int binarySearch(int search){
        int low = 0;
        int high = array.length - 1;
        int mid;

        while(low <= high){
            mid = (high + low) / 2;

            if(array[mid] == search)
                return mid;
            else if (array[mid] < search)
                low = mid + 1;
            else
                high = mid - 1;
        }

        return -1;
    }

    public int interpolationSearch(int search){
        int low = 0;
        int high = array.length - 1;
        int mid;
        float c;

        while(low <= high){
              // If the remaining range contains the same value,
              // check if that value is the search value.
              if(array[low] == array[high]){
                  if(array[low] == search) return low;
                  else return -1;
              }

              // Percentage of the list you expect the search value to be
              // Ex: c = .55 means search is expected to be just beyond the
              // halfway point in the array (55%)
              c = (search - array[low]) / (float)(array[high] - array[low]);
              if(c < 0 || c > 1) return -1;

              // Index to check (where search value should be)
              mid = (int)(low + c * (high - low));

              if(array[mid] == search)
                  return mid;
              else if(array[mid] < search)
                  low = mid + 1;
              else
                  high = mid - 1;
        }

        return -1;
    }

    public void run(boolean useBinary){
        long start, end = 0;
        int k = search_items.length;

        start = System.nanoTime();

        if(useBinary){
            System.out.println("Binary search");
            for(int i = 0; i < k; i++){
                binarySearch(search_items[i]);
                //System.out.print(binarySearch(search_items[i]) + " ");
            }
        }else{
            System.out.println("Interpolation search");
            for(int i = 0; i < k; i++){
                interpolationSearch(search_items[i]);
                //System.out.print(interpolationSearch(search_items[i]) + " ");
            }
        }
        end = System.nanoTime();
        System.out.println("\nTime: " + (end - start) / 1000000000.0 + "\n");
    }

    public static void main(String[] args){
        int n = 100;
        int k = 100;  // As k increases, time difference decreases
        int min = 0;
        int max = 100000000;

        Search s = new Search(n, k, min, max);
        s.run(true);
        s.run(false);
    }

}
