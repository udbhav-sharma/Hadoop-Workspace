package VQuadTree;

import java.awt.geom.Rectangle2D;

import util.Point;

public class Rectangle extends Rectangle2D {
	private double x=0.0;		//Left topmost Vertex X coordinate
	private double y=0.0;		//Left topmost Vertex Y coordinate
	private double w=0.0;
	private double h=0.0;
	
	public Rectangle(){
	}

	@Override
	public Rectangle2D createIntersection(Rectangle2D r) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rectangle2D createUnion(Rectangle2D r) {
		// TODO Auto-generated method stub
		double newX=0.0, newY=0.0, newW=0.0, newH=0.0;
		Rectangle newRectangle = new Rectangle();
		
		newX = Math.min(this.x, r.getX());
		newY = Math.max(this.y, r.getY());
		newW = Math.max(this.getMaxX(), r.getMaxX()) - newX;
		newH = newY - Math.min(this.getMinY(), r.getMinY());
		
		newRectangle.setRect(newX, newY, newW, newH);
		
		return newRectangle;
	}

	@Override
	public int outcode(double x, double y) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setRect(double x, double y, double w, double h) {
		// TODO Auto-generated method stub
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return this.h;
	}

	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return this.w;
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return this.x;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return this.y;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return (this.h==0 || this.w==0);
	}
	
	public boolean contains(Point p){
		return (p.getX()>=this.x && p.getY()>=this.y-this.h && p.getX()<=this.x+this.w && p.getY()<=this.y);
	}
	
	public String toString(){
		String output="";
		output+="{ x:"+this.x+", y:"+this.y+", w:"+this.w+", h:"+this.h+" }";
		return output;
	}
}