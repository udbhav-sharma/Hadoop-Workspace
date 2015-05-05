package VoronoiDiagram;

import util.Point;

public class Sources extends Vertex {
	
	private static final long serialVersionUID = 229437811440184329L;

	public Sources(Point p){
		super(p);
		this.dist=0;
		this.pi=this.p;
		this.pis.put(this.p,new Generator(this.p, 0, 0));
	}
	
	public void reset(){
		super.reset();
		this.dist = 0;
		this.pi=this.p;
		this.pis.put(this.p,new Generator(this.p, 0, 0));
	}

	public String toString(){
		return super.toString();
	}
	
}
