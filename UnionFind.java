/*
Created by: FigNewtons
Date: October 23, 2014

Based on Section 1.5 in Algorithms 4th Edition (Sedgewick, Wayne)

----------------------
Dynamic Connectivity
----------------------

Input: pairs of integers (p and q) denoting "p is connected to q"

If we consider the set (U) of possible integer values as our universe,
then "p is connected to q" is an equivalence relation, and the set of
integers also connected to p and q form an equivalence class.

An equivalence relation has three properties:
    1. Reflexive - p is connected to q
    2. Symmetric - p is connected to q is the same as q is connected to p
    3. Transitive - if p is connected to q, and q is connected to r, then p
                  is connected to r

Operations:
    1. Union: Connect two integers together.

    2. Find: What connected component is integer p in?

    3. Connected: Are two integers connected?
      (Are they in the same equivalence class/ connected component?)


Implementation: Weighted quick-union

Analysis:
    Construction: O(n)
    Union: lg(n)
    Find: lg(n)

Note: If you use path compression, one can achieve amortized constant
time for both the union and find operations.

*/

public class UnionFind{

    private int[] id;
    private int[] comp_size;

    private int num_components;

    // Connects integers 0 to n-1 to themselves to have
    // n connected components of size 1
    public UnionFind(int n){
      num_components = n;

      id = new int[n];
      comp_size = new int[n];

      for(int i = 0; i < n; i++){
        id[i] = i;
        comp_size[i] = 1;
      }
    }

    // Finds the root of the connected component tree (lg n height)
    private int find(int p){

      while(p != id[p])
          p = id[p];

      return p;
    }

    // Connects two integers (both find calls are lg n, the rest
    // are constant-time operations
    public void union(int p, int q){

        int i = find(p); // root of tree with p
        int j = find(q); // root of tree with q
        if(i == j) return;

        // Attach smaller tree to larger tree to
        // maintain balance; the smaller tree's root
        // then points to the larger tree's root (and the size increases)
        if(comp_size[i] < comp_size[j]){
            id[i] = j;
            comp_size[j] += comp_size[i];
        }else{
            id[j] = i;
            comp_size[i] += comp_size[j];
        }
        num_components--;
    }


    // Returns true if both integers have the same root
    // (part of the same connected component)
    public boolean connected(int p, int q){
        return find(p) == find(q);
    }

    // Returns the number of connected components
    public int count(){return num_components;}

}
