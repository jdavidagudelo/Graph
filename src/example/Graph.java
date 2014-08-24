package example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

public class Graph {
	private ArrayList<AdjacencyInfo> lists = new ArrayList<AdjacencyInfo>();

	public void addVertice(Vertice vertice)
	{
		AdjacencyInfo info = new AdjacencyInfo(vertice);
		vertice.setIndex(lists.size());
		lists.add(info);
	}
	public void addEdge(int i, int j, Integer cost, boolean directed)
	{
		Vertice vi = lists.get(i).getVertice();
		Vertice vj = lists.get(j).getVertice();
		if(directed)
		{
			lists.get(i).add(new Edge(vi, vj, cost));
		}
		else
		{
			lists.get(i).add(new Edge(vi, vj, cost));
			lists.get(j).add(new Edge(vj, vi, cost));
		}
	}
	public void dfs()
	{
		boolean visited[] = new boolean[lists.size()];
		Stack<Vertice> stack = new Stack<Vertice>();
		stack.push(lists.get(0).getVertice());
		while(!stack.isEmpty())
		{
			Vertice v = stack.pop();
			if(visited[v.getIndex()])
			{
				continue;
			}
			System.out.println(v.getValue());
			visited[v.getIndex()] = true;
			for(Edge edge : lists.get(v.getIndex()))
			{
				if(!visited[edge.getVj().getIndex()])
				{
					stack.push(edge.getVj());
				}
			}
			
		}
		
	}
	public void bfs()
	{
		LinkedList<Vertice> queue = new LinkedList<Vertice>();
		boolean visited[] = new boolean[lists.size()];
		queue.addLast(lists.get(0).getVertice());
		while(!queue.isEmpty())
		{
			Vertice v = queue.removeFirst();
			if(visited[v.getIndex()])
			{
				continue;
			}
			visited[v.getIndex()] = true;
			System.out.println(v.getValue());
			for(Edge edge : lists.get(v.getIndex()))
			{
				queue.addLast(edge.getVj());
			}
		}
	}
	public Set<Vertice> getVertices()
	{
		Set<Vertice> vertices = new HashSet<Vertice>();
		for(AdjacencyInfo info : lists)
		{
			vertices.add(info.getVertice());
		}
		return vertices;
	}
	public Graph spanningTree()
	{
		TreeSet<Edge> set = new TreeSet<Edge>();
		Set<Vertice> X = new HashSet<Vertice>();
		Set<Vertice> T = new HashSet<Vertice>();
		Set<Vertice> V = getVertices();
		X.add(lists.get(0).getVertice());
		for(AdjacencyInfo info : lists)
		{
			for(Edge edge : info)
			{
				set.add(edge);
			}
		}
		System.out.println(set);
//		while(!V.equals(X))
//		{
//			
//		}
		return null;
	}
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for(AdjacencyInfo info : lists)
		{
			sb.append(info.getVertice().getValue());
			for(Edge v : info)
			{
				sb.append(" -> ").append(v.getVj().getValue());
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	public static void main(String args[])
	{
		Graph graph = new Graph();
		Vertice v1 = new Vertice("1");
		Vertice v2 = new Vertice("2");
		Vertice v3 = new Vertice("3");
		graph.addVertice(v1);
		graph.addVertice(v2);
		graph.addVertice(v3);
		graph.addEdge(1, 2, 10, false);
		graph.addEdge(0, 1, 5, false);
		graph.addEdge(0, 2, 4, false);
		System.out.println(graph.toString());
		graph.bfs();
		graph.dfs();
		graph.spanningTree();
		
	}
}
