package example;

import java.util.LinkedList;

public class AdjacencyInfo extends LinkedList<Edge>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
