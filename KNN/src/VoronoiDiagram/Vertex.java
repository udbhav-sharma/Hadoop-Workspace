package VoronoiDiagram;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import util.Point;

public class Vertex implements Comparable<Vertex>, Serializable {
	
	private static final long serialVersionUID = 819335279369358695L;
	public Point p;
	public ArrayList<Edge> adjacencies;
	
	public double dist = Double.MAX_VALUE;
	public boolean flag = false;
	public Point pi = null;
	public HashMap<Point, Generator> pis;
	
	public Vertex( Point p ){
		this.p = (Point)p.clone();
		this.adjacencies = new ArrayList<Edge>();
		this.pis = new HashMap<Point,Generator>();
	}
	
	public void reset(){
		this.dist = Double.MAX_VALUE;
		this.flag=false;
		this.pi = null;
		this.pis = new HashMap<Point,Generator>();
	}
	
	public void addNeighbour(Vertex v, double w){
		this.adjacencies.add(new Edge(v,w));
	}
	
	public int compareTo(Vertex other){
		return Double.compare(dist, other.dist);
	}
	
	public String toString(){
		String output="-------\nVertex: ";
		output+=this.p+"\n";
		output+="Dist: "+this.dist+"\n";
		output+="PI: "+this.pi+"\n";
		output+="PI's:\n";
		output+=this.pis;
		
		return output;
	}
	
	public static class Generator implements Serializable{
		
		private static final long serialVersionUID = -490290948040543035L;
		Point p;
		double w;
		double dist;
		
		public Generator(Point p, double w, double dist){
			this.p = p;
			this.w = w;
			this.dist = dist;
		}
		
		public boolean equals( Generator g ){
			return g.p.equals(p);
		}
		
		public String toString(){
			return "{"+p+", "+w+", "+dist+"}";
		}
	}
}
