package example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;
import java.util.TreeSet;

public class Graph {

    private ArrayList<AdjacencyInfo> lists = new ArrayList<AdjacencyInfo>();

    public int getVerticeCount(){
        return lists.size();
    }
    public void addVertice(Vertice vertice) {
        AdjacencyInfo info = new AdjacencyInfo(vertice);
        vertice.setIndex(lists.size());
        lists.add(info);
    }

    public void addEdge(Edge edge, boolean directed) {
        addEdge(edge.getVi().getIndex(), edge.getVj().getIndex(), edge.getCost(), directed);
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

    public Graph spanningTree() {
        Graph result = new Graph();
        TreeSet<Edge> set = new TreeSet<Edge>();
        int[] parent = new int[getVerticeCount()];
        HashMap<Integer, Vertice> X = new HashMap<Integer, Vertice>();
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
            if(unionFind.find(vi.getIndex()) == unionFind.find(vj.getIndex())){
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
        Graph graph = new Graph();
        Vertice<Integer> v1 = new Vertice(1);
        Vertice<Integer> v2 = new Vertice(2);
        Vertice<Integer> v3 = new Vertice(3);
        graph.addVertice(v1);
        graph.addVertice(v2);
        graph.addVertice(v3);
        graph.addEdge(1, 2, 10, false);
        graph.addEdge(0, 1, 5, false);
        graph.addEdge(0, 2, 4, false);
        System.out.println(graph.toString());
        Graph st = graph.spanningTree();
        System.out.println("st: "+st.toString());
    }
}
