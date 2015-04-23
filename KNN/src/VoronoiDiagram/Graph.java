package VoronoiDiagram;

import java.io.Serializable;
import java.util.ArrayList;

import util.Point;


public class Graph implements Serializable {

	private static final long serialVersionUID = 7491764464913162727L;
	private ArrayList<Vertex> V;
	
	public Graph(){
		V=new ArrayList<Vertex>();
	}
	
	public Vertex addV( Point p, boolean isSource ){
		if(isSource)
			this.V.add(new Sources(p));
		else
			this.V.add(new Vertex(p));
		return this.V.get(this.V.size()-1);
	}
	
	public void addE(Vertex u, Vertex v, double w){
		if(u!=null && v!=null){
			u.addNeighbour(v,w);
			v.addNeighbour(u, w);
		}
	}
	
	public ArrayList< Vertex > getV(){
		return this.V;
	}
	
	public Vertex findV( Point p ){
		Vertex v = null;
		for(Vertex u:this.V){
			if(u.p.equals(p))
				v=u;
		}
		return v;
	}
	
	public String toString(){
		String output="----Graph----\n";
		output+=V;
		return output;
	}
}
