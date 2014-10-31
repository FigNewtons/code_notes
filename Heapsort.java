// Maxheap implementation
import java.util.*;

public class Heapsort{

    private int[] heap; // heap[0] will store heap size

    public Heapsort(int size){
        if(size < 10) size = 10;
        heap = new int[size + 1];
    }

    public void insert(int value){
        heap[++heap[0]] = value;
        siftup(heap[0]);
    }

    public int[] sort(){
        int size = heap[0];
        int[] array = new int[size];

        for(int i = size - 1; i > 1; i--){
            array[i] = removemax();
        }

        return array;
    }

    private int removemax(){
        swap(1, heap[0]);
        int max = heap[heap[0]--];
        siftdown(1);
        return max;
    }

    private void siftdown(int index){
        int i = index;
        boolean heapify = false;

        while(!heapify){
            if(hasRight(i)){
                int m = max(heap[2 * i], heap[2 * i + 1]);
                int m_index = m == heap[2*i] ? 2*i : 2*i+1;
                if(heap[i] < m){
                    swap(i, m_index);
                    i = m_index;
                }else
                    heapify = true;
            }else if(hasLeft(i) && heap[i] < heap[2*i]){
                swap(i, 2 * i);
                i = 2 * i;
            }else
                heapify = true;
        }
    }

    private void siftup(int index){

        while(index > 1){
            // If child is greater than parent
            if(heap[index] > heap[index/2]){
                swap(index, index/2);
                index = index/2;
            }else break;
        }
    }

    private void swap(int index1, int index2){
        int temp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = temp;
    }

    private boolean hasLeft(int index){
      if(2 * index > heap[0]) return false;
      else return true;
    }

    private boolean hasRight(int index){
      if(2 * index + 1 > heap[0]) return false;
      else return true;
    }

    private int max(int a, int b){
      if(a > b) return a;
      else return b;
    }

    public void print(){
      for(int i = 1; i < heap[0] + 1; i++)
        System.out.print(heap[i] + " ");

      System.out.println("");
    }

    public static void main(String[] args){
        Heapsort hs = new Heapsort(100);
        Random rand = new Random();

        for(int i = 0; i < 100; i++){
            hs.insert(rand.nextInt(100) + 1);
        }

        int[] arr = hs.sort();
        for(int a: arr)
            System.out.print(a + " ");
        System.out.println("");
    }
}
