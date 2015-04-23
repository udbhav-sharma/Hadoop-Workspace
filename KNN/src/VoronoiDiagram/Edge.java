package VoronoiDiagram;

import java.io.Serializable;

public class Edge implements Serializable{
	
	private static final long serialVersionUID = -1190295737066769124L;
	public Vertex v;
	public double w;
	
	public Edge(Vertex v){
		this.v = v;
		this.w = 0;
	}
	
	public Edge(Vertex v, double w){
		this.v = v;
		this.w = w;
	}
	
	public boolean equals(Edge e){
		return this.v==e.v && this.w==e.w;
	}
	
	public String toString(){
		return v.p+" "+w;
	}
}
