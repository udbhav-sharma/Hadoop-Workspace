package MapperIO;

import java.io.Serializable;

import util.Point;

public class FirstMapperKey implements Serializable {
	
	private static final long serialVersionUID = 5346417430571472794L;
	
	public static final int GRAPH = 1; 
	public static final int POINT = 2;
	public int objType = GRAPH;
	public Point p = null;
	
	public FirstMapperKey() {}
	
	public FirstMapperKey( Point p){
		this.objType = POINT;
		this.p = p;
	}
	
	public String toString(){
		String output = "------First Mapper Key------\n";
		output+="ObjType: "+this.objType;
		if(p!=null)
			output+="Point:"+this.p;
		
		return output;
	}
	
}
