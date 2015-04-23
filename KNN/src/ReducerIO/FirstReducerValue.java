package ReducerIO;

import java.io.Serializable;

import VoronoiDiagram.Graph;

import util.Point;

public class FirstReducerValue implements Serializable {
	private static final long serialVersionUID = -2612690250638555351L;
	
	public static final int GRAPH_TYPE = 1;
	public static final int SOURCE_POINT_TYPE = 2;
	public int objType;
	public int mapperId;
	
	public Graph G = null;
	
	public Point sp = null;
	public Point poi = null;
	public double distance = 0;
	
	public FirstReducerValue( int mapperId, Point sp, Point poi, double distance ){
		this.mapperId = mapperId;
		this.sp = sp;
		this.poi = poi;
		this.distance = distance;
		this.objType = SOURCE_POINT_TYPE;
	}
	
	public FirstReducerValue( int mapperId, Graph G ){
		this.mapperId = mapperId;
		this.G = G;
		this.objType = GRAPH_TYPE;
	}
	
	public String toString(){
		String output = "";
		if(this.objType == GRAPH_TYPE){
			output += this.G;
		}
		else{
			output += "{sp: "+this.sp+", ";
			output += "poi: "+this.poi+", ";
			output += "distance: "+this.distance+"}";
		}
		return output;
	}
	
}
