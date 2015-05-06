package VQuadTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import util.Point;
import util.Pair;

public class Tree {
	
	public Node root;

	public Tree(){
		root=new Node();
	}
	
	public Node init (Rectangle r, HashMap< Point, ArrayList<Point>> points){
		this.root = formTree(r,points);
		
		return this.root;
	}
	
	public Node formTree( Rectangle r, HashMap< Point, ArrayList<Point>> points ){
		Node newNode = new Node();
		boolean split = false;
		ArrayList<Point> lastPOI = null;

		newNode.setR(r);
		
		if(points.size()>0){
			Iterator<Map.Entry<Point,ArrayList<Point>>> it = points.entrySet().iterator();
			Map.Entry<Point,ArrayList<Point>> pair = it.next();
			lastPOI = pair.getValue();
			while(it.hasNext()){
				pair = it.next();
				lastPOI = intersect(lastPOI,pair.getValue());
				if(lastPOI.size()==0)
					split = true;
			}
		}
		
		if(!split){
			newNode.setLeaf(true);
			if(points.size()>0)
				newNode.poi=lastPOI.get(0);
			//Log.d(newNode);
			/*for(Pair<Point,Point> pair: points){
				Log.d(pair.getElement0());
			}*/
		}
		else{
			//Log.d(newNode);
			double newX = r.getX();
			double newY = r.getY();
			double newW = r.getWidth()/2;
			double newH = r.getHeight()/2;
			Rectangle newRectangle;
			
			newRectangle = new Rectangle();
			newRectangle.setRect(newX,newY,newW,newH);
			newNode.children.add(formTree(newRectangle, filterPoints(points,newRectangle)));

			newRectangle = new Rectangle();
			newRectangle.setRect(newX+newW,newY,newW,newH);
			newNode.children.add(formTree(newRectangle, filterPoints(points,newRectangle)));

			newRectangle = new Rectangle();
			newRectangle.setRect(newX,newY-newH,newW,newH);
			newNode.children.add(formTree(newRectangle, filterPoints(points,newRectangle)));

			newRectangle = new Rectangle();
			newRectangle.setRect(newX+newW,newY-newH,newW,newH);
			newNode.children.add(formTree(newRectangle, filterPoints(points,newRectangle)));
		}
		
		return newNode;
	}
	
	public Pair<Point,Double> getGenerator(Point p){
		ArrayList<Pair<Point,Double>> result = new ArrayList<Pair<Point,Double>>();
		this.get1NN(this.root,p,result);
		
		Pair<Point,Double> minPair = null;
		
		for(Pair<Point,Double> pair:result){
			if(minPair == null || pair.getElement1()<minPair.getElement1())
				minPair=pair;
		}
		return minPair;
	}
	
	public String toString(){
		String output="---------Vquad Tree--------\n";
		output+=printTree(this.root);
		return output;
	}
	
	public int getheight(){
		return height(this.root);
	}
	
	private ArrayList<Point> intersect(ArrayList<Point> list1, ArrayList<Point> list2){
		ArrayList<Point> newList = new ArrayList<Point>();
		
		for(Point p1: list1){
			for(Point p2: list2){
				if(p1.equals(p2)){
					newList.add(new Point(p1));
					break;
				}
			}
		}
		
		return newList;
	}
	
	private HashMap<Point, ArrayList<Point>> filterPoints(HashMap<Point,ArrayList<Point>> points, Rectangle r){
		HashMap<Point, ArrayList<Point>> newPoints=new HashMap<Point,ArrayList<Point>>();
		
		Iterator<Map.Entry<Point,ArrayList<Point>>> it = points.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<Point,ArrayList<Point>> pair = it.next();
			if(r.contains( pair.getKey())){
				newPoints.put(pair.getKey(),pair.getValue());
			}
		}
		
		return newPoints;
	}
	
	private void get1NN(Node node, Point p, ArrayList<Pair<Point,Double>> result){
		if(node.r.contains(p)){
			if(node.isLeaf){
				if(node.poi==null)
					return;
				boolean isPresent = false;
				for(Pair<Point,Double> pair:result){
					if(pair.getElement0().equals(node.poi))
						isPresent=true;
				}
				if(!isPresent)
					result.add(new Pair<Point,Double>(node.poi,p.distance(node.poi)));
			}
			else{
				for(Node child:node.children){
					get1NN(child, p, result);
				}
			}
		}
		return;
	}
	
	private int height(Node node){
		int h=0;
		for(Node child:node.children){
			Math.max(height(child)+1, h);
		}
		return h;
	}
	
	private String printTree(Node node){
		String output="";
		output+=node+"\n";
		for(Node n:node.children){	
			output+=printTree(n);
		}
		return output;
	}
}