package VoronoiDiagram;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import util.Pair;
import util.Point;

public class NetworkVoronoiDiagram implements Serializable{
	private static final long serialVersionUID = 20L;
	public HashMap<Point, NetworkVoronoiPolygon> nvps;
	
	public NetworkVoronoiDiagram(){
		this.nvps = new HashMap<Point, NetworkVoronoiPolygon>();
	}
	
	public void addE(Point p,Edge e){
		NetworkVoronoiPolygon nvp = this.nvps.get(p);
		if(nvp==null){
			nvp = new NetworkVoronoiPolygon(p);
			nvp.graph.add(e);
			this.nvps.put(p, nvp);
		}
		else
			nvp.graph.add(e);
	}
	
	public void addB(Point p, Point b, double w){
		NetworkVoronoiPolygon nvp = this.nvps.get(p);
		if(nvp==null){
			nvp = new NetworkVoronoiPolygon(p);
			nvp.borderPoints.add(new Pair<Point,Double>(b,w));
			this.nvps.put(p, nvp);
		}
		else
			nvp.borderPoints.add(new Pair<Point,Double>(b,w));
	}
	
	public String toString(){
		String output="--------Network Voronoi Diagram-------\n";
		Iterator<Map.Entry<Point,NetworkVoronoiPolygon>> it = this.nvps.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<Point,NetworkVoronoiPolygon> pair = it.next();
			output+= pair.getValue()+"\n";
		}
		return output;
	}
	
	public static class NetworkVoronoiPolygon implements Serializable{
		private static final long serialVersionUID = 30L;
		public Point p = null;
		public ArrayList<Edge> graph = null;
		public ArrayList<Pair<Point,Double>> borderPoints = null;
		
		public NetworkVoronoiPolygon(Point p){
			this.p = p;
			this.graph = new ArrayList<NetworkVoronoiDiagram.Edge>();
			this.borderPoints = new ArrayList<Pair<Point,Double>>();
		}
		
		public String toString(){
			String output = "Network Voronoi Polygon\n";
			output += "Generator: "+p+"\n";
			output += "Graph: "+this.graph.size()+"\n";
			output += this.graph+"\n";
			output += "Border Points: "+this.borderPoints.size()+"\n";
			output += this.borderPoints;
			return output;
		}
	}
	
	public static class Edge implements Serializable{
		private static final long serialVersionUID = 40L;
		public Point p1;
		public Point p2;
		public double w;
		
		public Edge(Point p1, Point p2, double w){
			this.p1=p1;
			this.p2=p2;
			this.w=w;
		}
		
		public String toString(){
			return "{"+p1+", "+p2+", "+w+"}";
		}
	}
}
