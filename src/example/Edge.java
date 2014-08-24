package example;

public class Edge implements Comparable<Edge>{
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
		return cost.compareTo(o.cost);
	}
	public String toString()
	{
		return cost.toString();
	}
}
