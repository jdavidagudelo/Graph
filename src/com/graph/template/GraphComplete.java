/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graph.template;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeSet;

/**
 *
 * @author ubidotsjd
 */
public class GraphComplete {

    public class AdjacencyInfo extends LinkedList<Edge> {

        private Vertice vertice;

        public Vertice getVertice() {
            return vertice;
        }

        public void setVertice(Vertice vertice) {
            this.vertice = vertice;
        }

        public AdjacencyInfo(Vertice vertice) {
            super();
            this.vertice = vertice;
        }

    }

    public class Edge implements Comparable<Edge> {

        private Vertice vi;
        private Vertice vj;
        private Integer cost;

        public Vertice getVi() {
            return vi;
        }

        public void setVi(Vertice vi) {
            this.vi = vi;
        }

        public Vertice getVj() {
            return vj;
        }

        public void setVj(Vertice vj) {
            this.vj = vj;
        }

        public Edge(Vertice vi, Vertice vj, Integer cost) {
            super();
            this.vi = vi;
            this.vj = vj;
            this.cost = cost;
        }

        public Integer getCost() {
            return cost;
        }

        public void setCost(Integer cost) {
            this.cost = cost;
        }

        public int compareTo(Edge o) {
            if (cost.compareTo(o.cost) != 0) {
                return cost.compareTo(o.cost);
            }
            if (Integer.compare(vi.getIndex(), o.vi.getIndex()) != 0) {
                return Integer.compare(vi.getIndex(), o.vi.getIndex());
            }
            return Integer.compare(vj.getIndex(), o.vj.getIndex());
        }

        @Override
        public String toString() {
            return cost.toString();
        }
    }

    public class Vertice<V> {

        private V value;
        private Integer index;

        public Object getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        public Vertice(V value) {
            super();
            this.value = value;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((index == null) ? 0 : index.hashCode());
            result = prime * result + ((value == null) ? 0 : value.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            Vertice other = (Vertice) obj;
            if (index == null) {
                if (other.index != null) {
                    return false;
                }
            } else if (!index.equals(other.index)) {
                return false;
            }
            if (value == null) {
                if (other.value != null) {
                    return false;
                }
            } else if (!value.equals(other.value)) {
                return false;
            }
            return true;
        }

    }

    public class UnionFind {

        private final int[] parent;

        public UnionFind(int n) {
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = -1;
            }
        }

        public int find(int i) {
            if (parent[i] == -1) {
                return i;
            }
            return find(parent[i]);
        }

        // A utility function to do union of two subsets
        public void union(int x, int y) {
            int xset = find(x);
            int yset = find(y);
            parent[xset] = yset;
        }
    }
    private final ArrayList<AdjacencyInfo> lists = new ArrayList<AdjacencyInfo>();

    public GraphComplete() {
    }

    public GraphComplete(int[][] g) {
        for (int i = 0; i < g.length; i++) {
            this.addVertice(i);
        }
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[i].length; j++) {
                if (g[i][j] != 0) {
                    addEdge(i, j, g[i][j]);
                }
            }
        }
    }

    public int[][] getG() {
        int g[][] = new int[getVerticeCount()][getVerticeCount()];
        for (AdjacencyInfo info : lists) {
            for (Edge edge : info) {
                g[edge.getVi().getIndex()][edge.getVj().getIndex()] = edge.getCost();
            }
        }
        return g;
    }

    /**
     * Dynamic programming solution to find the number of paths of length k
     * between vertices x and y.
     *
     * @param x start vertex
     * @param y end vertex
     * @param k number of ways
     * @return number of paths of length k between x and y in the graph.
     */
    public int countWalks(int x, int y, int k) {
        int g[][] = getG();
        int dp[][][] = new int[g.length][g.length][k + 1];
        for (int e = 0; e <= k; e++) {
            for (int i = 0; i < g.length; i++) {
                for (int j = 0; j < g.length; j++) {
                    {
                        dp[i][j][e] = 0;
                        if (e == 0 && i == j) {
                            dp[i][j][e] = 1;
                        }
                        if (e == 1 && g[i][j] > 0) {
                            dp[i][j][e] = 1;
                        }
                        if (e > 1) {
                            for (int v = 0; v < g.length; v++) {
                                if (g[i][v] > 0) {
                                    dp[i][j][e] += dp[v][j][e - 1];
                                }
                            }
                        }
                    }
                }
            }
        }
        return dp[x][y][k];
    }

    public static boolean safeColor(int g[][], int color[], int c) {
        for (int i = 0; i < g.length; i++) {
            if (g[i][c] != 0) {
                if (color[i] == c) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Tests if the input graph can be colored with at most C colors.
     *
     * @param color current colors selected for the vertices from 0 to v-1
     * @param v the current vertex to be colored.
     * @param V the number of vertices of the graph.
     * @param C the total number of color available.
     * @return true if the graph can be colored with C different colors, false
     * otherwise.
     */
    public boolean coloring(int color[], int v, int V, int C) {
        int[][] g = getG();
        if (v == V) {
            return true;
        }
        for (int c = 1; c <= C; c++) {
            if (safeColor(g, color, c)) {
                color[v] = c;
                if (coloring(color, v + 1, V, C)) {
                    return true;
                }
                color[v] = 0;
            }
        }
        return false;
    }

    public int getVerticeCount() {
        return lists.size();
    }

    public final void addVertice(int v) {
        Vertice vertice = new Vertice(v);
        AdjacencyInfo info = new AdjacencyInfo(vertice);
        vertice.setIndex(lists.size());
        lists.add(info);
    }

    public void addVertice(Vertice vertice) {
        AdjacencyInfo info = new AdjacencyInfo(vertice);
        vertice.setIndex(lists.size());
        lists.add(info);
    }

    public void addEdge(Edge edge, boolean directed) {
        addEdge(edge.getVi().getIndex(), edge.getVj().getIndex(), edge.getCost(), directed);
    }

    public final void addEdge(int i, int j, int cost) {
        addEdge(i, j, cost, false);
    }

    public void addEdge(int i, int j, Integer cost, boolean directed) {
        Vertice vi = lists.get(i).getVertice();
        Vertice vj = lists.get(j).getVertice();
        if (directed) {
            lists.get(i).add(new Edge(vi, vj, cost));
        } else {
            lists.get(i).add(new Edge(vi, vj, cost));
            lists.get(j).add(new Edge(vj, vi, cost));
        }
    }

    public void dfs() {
        boolean visited[] = new boolean[lists.size()];
        Stack<Vertice> stack = new Stack<Vertice>();
        stack.push(lists.get(0).getVertice());
        while (!stack.isEmpty()) {
            Vertice v = stack.pop();
            if (visited[v.getIndex()]) {
                continue;
            }
            System.out.println(v.getValue());
            visited[v.getIndex()] = true;
            for (Edge edge : lists.get(v.getIndex())) {
                if (!visited[edge.getVj().getIndex()]) {
                    stack.push(edge.getVj());
                }
            }

        }

    }

    /**
     * Returns true if it is possible to visit all the vertices in the graph
     * starting from vertex x, false otherwise.
     *
     * @param g
     * @param x
     * @return
     */
    public boolean testConnectivity(int[][] g, int x) {
        boolean visited[] = new boolean[g.length];
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(x);
        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (visited[v]) {
                continue;
            }
            visited[v] = true;
            for (int i = g.length - 1; i >= 0; i--) {
                if (g[v][i] == 1 && !visited[i]) {
                    stack.push(i);
                }
            }
        }
        for (boolean v : visited) {
            if (!v) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @return
     */
    public boolean stronglyConnected() {
        int[][] g = getG();
        if (!testConnectivity(g, 0)) {
            return false;
        }
        int[][] gt = new int[g.length][g.length];
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g.length; j++) {
                gt[i][j] = g[j][i];
            }
        }
        return testConnectivity(gt, 0);
    }

    public void bfs() {
        LinkedList<Vertice> queue = new LinkedList<Vertice>();
        boolean visited[] = new boolean[lists.size()];
        queue.addLast(lists.get(0).getVertice());
        while (!queue.isEmpty()) {
            Vertice v = queue.removeFirst();
            if (visited[v.getIndex()]) {
                continue;
            }
            visited[v.getIndex()] = true;
            System.out.println(v.getValue());
            for (Edge edge : lists.get(v.getIndex())) {
                queue.addLast(edge.getVj());
            }
        }
    }

    public long getTotalCost() {
        long cost = 0l;
        LinkedList<Vertice> queue = new LinkedList<Vertice>();
        boolean visited[] = new boolean[lists.size()];
        queue.addLast(lists.get(0).getVertice());
        while (!queue.isEmpty()) {
            Vertice v = queue.removeFirst();
            if (visited[v.getIndex()]) {
                continue;
            }
            visited[v.getIndex()] = true;
            for (Edge edge : lists.get(v.getIndex())) {
                if (!visited[edge.getVj().getIndex()]) {
                    cost += edge.getCost();
                    queue.addLast(edge.getVj());
                }
            }
        }
        return cost;
    }

    /**
     * Return the path from x to y in a depth first search in the graph g
     * represented as adjacency matrix.
     * @param x
     * @param y
     * @return
     */
    public String getPathDfs(int x, int y) {
        int[][] g = getG();
        boolean visited[] = new boolean[g.length];
        Stack<Integer> stack = new Stack<Integer>();
        int prev[] = new int[g.length];
        prev[x] = -1;
        stack.push(x);
        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (visited[v]) {
                continue;
            }
            if (v == y) {

                StringBuilder path = new StringBuilder(y + "");
                int i = y;
                while (prev[i] != -1) {
                    path.insert(0, prev[i] + ", ");
                    i = prev[i];
                }
                return path.toString();
            }
            visited[v] = true;
            for (int i = g.length - 1; i >= 0; i--) {
                if (g[v][i] == 1) {
                    if (!visited[i]) {
                        prev[i] = v;
                        stack.push(i);
                    }
                }
            }
        }
        return "";
    }

    public boolean isBiconnected() {
        int g[][] = getG();
        //test the graph is connected
        if (!stronglyConnected()) {
            return false;
        }
        boolean visited[] = new boolean[g.length];
        int[] disc = new int[g.length];
        int[] parent = new int[g.length];
        Arrays.fill(parent, -1);
        int[] low = new int[g.length];
        Arrays.fill(disc, Integer.MAX_VALUE);
        Arrays.fill(low, Integer.MAX_VALUE);
        boolean ap[] = new boolean[g.length];
        time = 0;
        for (int i = 0; i < g.length; i++) {
            if (!visited[i]) {
                getArticulations(i, g, visited, parent, low, disc, ap);
            }
        }
        //test the graph has not articulations
        for (boolean a : ap) {
            if (a) {
                return false;
            }
        }
        return true;
    }
    private int time = 0;

    public void getArticulations(int u, int[][] g, boolean visited[], int[] parent, int low[], int disc[], boolean ap[]) {
        visited[u] = true;
        int children = 0;
        low[u] = disc[u] = ++time;
        //0(n)
        for (int i = 0; i < g.length; i++) {
            if (g[u][i] > 0) {
                if (!visited[i]) {
                    children++;
                    parent[i] = u;
                    getArticulations(i, g, visited, parent, low, disc, ap);
                    low[u] = Math.min(low[u], low[i]);
                    //root with 2 or more children, is a articulation
                    if (parent[u] == -1 && children > 1) {
                        ap[u] = true;
                    }
                    //articulation not root
                    if (parent[u] != -1 && low[i] >= disc[u]) {
                        ap[u] = true;
                    }
                } else if (i != parent[u]) {
                    low[u] = Math.min(low[u], disc[i]);
                }
            }
        }
    }

    public int[][] floyd(int[][] g) {
        int[][] x = getG();
        for (int[] x1 : x) {
            for (int j = 0; j < x.length; j++) {
                if (x1[j] == 0) {
                    x1[j] = -1;
                }
            }
        }
        for (int k = 0; k < g.length; k++) {
            for (int i = 0; i < g.length; i++) {
                for (int j = 0; j < g.length; j++) {
                    if (i != j) {
                        if (x[i][k] != -1 && x[k][j] != -1) {
                            x[i][j] = Math.min(x[i][j], x[i][k] + x[k][j]);
                        }
                    }
                }
            }
        }
        return x;
    }

    /**
     * *
     *
     * @return Returns the minimum spanning tree of the Graph.
     */
    public GraphComplete minimumSpanningTree() {
        GraphComplete result = new GraphComplete();
        TreeSet<Edge> set = new TreeSet<Edge>();
        //O(V)
        for (AdjacencyInfo info : lists) {
            result.addVertice(info.getVertice());
        }
        //O(E)
        for (AdjacencyInfo info : lists) {
            for (Edge edge : info) {
                set.add(edge);
            }
        }
        UnionFind unionFind = new UnionFind(getVerticeCount());
        //O(E)
        while (!set.isEmpty()) {
            Edge edge = set.first();
            //O(log(E)) the time required to query a heap
            set.remove(edge);
            Vertice vi = edge.getVi();
            Vertice vj = edge.getVj();
            if (unionFind.find(vi.getIndex()) == unionFind.find(vj.getIndex())) {
                continue;
            }
            unionFind.union(vi.getIndex(), vj.getIndex());
            result.addEdge(edge, false);
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (AdjacencyInfo info : lists) {
            sb.append(info.getVertice().getValue());
            for (Edge v : info) {
                sb.append(" -> ").append(v.getVj().getValue());
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        GraphComplete gc = new GraphComplete();
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            gc.addVertice(i);
        }
        for (int t = 0; t < m; t++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int r = scanner.nextInt();
            gc.addEdge(x - 1, y - 1, r);
        }
        GraphComplete st = gc.minimumSpanningTree();
        System.out.println(st.getTotalCost());

    }
}
