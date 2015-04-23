package util;

import java.io.Serializable;

public class Point implements Serializable {

	private static final long serialVersionUID = 1L;
	private final double x;
    private final double y;
    
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    
    @Override
    public boolean equals(Object p){
    	return (this.x==((Point)p).getX() && this.y==((Point)p).getY());
    }
    
    @Override
    public int hashCode(){
    	String hash = ((int)x)+""+((int)y);
    	return Integer.parseInt(hash);
    }
    
    public boolean isNull(){
    	return (x==-1 || y==-1);
    }

    @Override
    public String toString() {
        return ("(" + x + "," + y + ")"); 
    }
    
    @Override
    public Object clone(){
    	return new Point(this.x,this.y);
    }
}

