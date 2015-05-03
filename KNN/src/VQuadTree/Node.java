package VQuadTree;

import java.util.ArrayList;

import util.Point;

public class Node {
	public Rectangle r=null;
	public Point poi=null;
	public ArrayList<Node> children = new ArrayList<Node>();
	public boolean isLeaf = false;
	
	public Node(){
	}
	
	public void setR(Rectangle r){
		this.r = r;
	}
	
	public void setLeaf(boolean isLeaf){
		this.isLeaf=isLeaf;
	}
	
	public boolean isEmpty(){
		return this.r==null || this.poi==null;
	}
	
	public String toString(){
		String output="-----Node-----\n";
		output+="Leaf: "+this.isLeaf+"\n";
		output+="Rect: "+r+"\n";
		output+="POI: "+poi+"\n";
		output+="Children: "+children.size();
		return output;
	}
}