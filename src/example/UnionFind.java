/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;

/**
 *
 * @author ubidotsjd
 */
public class UnionFind {
    private int[] parent;
    public UnionFind(int n){
        parent = new int[n];
        for(int i = 0; i < n; i++){
            parent[i] = -1;
        }
    }
    public int find( int i)
    {
        if (parent[i] == -1)
            return i;
        return find(parent[i]);
    }
 
    // A utility function to do union of two subsets
    public void union( int x, int y)
    {
        int xset = find( x);
        int yset = find(y);
        parent[xset] = yset;
    }
}
